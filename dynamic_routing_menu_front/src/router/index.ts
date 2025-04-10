//创建路由
import { createRouter, createWebHistory,  type RouteRecordRaw } from "vue-router";
import { useMenuStore } from "../store/menu";
import type { ICurrentMenuItem, IMenuList } from "../components/menu/menu";
import { flattenMenuTree, getMenuData } from "../api/menu";
import pinia from "../store";

//创建路由映射表
const routes: RouteRecordRaw[] = [
    {
        //根路径
        path: '/',
        name: 'Layout',
        component: () => import('../views/layout/LayoutView.vue'),
        children: []
    },
    {
        path: '/:pathMatch(.*)*',
        name: '404',
        component: () => import('../views/error/404View.vue')
    }
];

//创建路由对象
const router = createRouter({
    routes,
    history: createWebHistory()
});

/**
 * 路由机制原理
 * 1.在浏览器的地址栏输入页面路由地址
 * 2.浏览器访问router/index.ts中的路由映射表
 * 
 * 
 * 
 */
//获取菜单数据
await getMenuData();

// 独立的路由重置函数
const resetRouter = () => {
    router.getRoutes().forEach(route => {
      if (route.name && !['Layout', '404', 'Login'].includes(route.name as string)) {
        router.removeRoute(route.name);
      }
    });
  }

export const setDynamicRouter = async(forceReload = false) => {
      
    const store = useMenuStore(pinia);
    // 缓存当前路由路径用于刷新后重定向
    const currentPath = router.currentRoute.value.fullPath;
    // 如果已经加载过路由且不需要强制重载，则跳过
    if (store.getIsLoadedRouters && !forceReload) return;
    //重置路由
    resetRouter()
    // 重新添加路由
    const curMenu: ICurrentMenuItem[] = [];
    //扁平化菜单树
    //console.log('一维化菜单');
    const menuList: IMenuList[] = flattenMenuTree(store.getMenuTree);
    //遍历菜单列表
    menuList.forEach((menu) => {
        const dynamicRouterList: RouteRecordRaw = {
            //根路径
            path: menu.menuPath,
            name: menu.menuName,
            component: () => import(/* @vite-ignore */ menu.menuComponent),
            meta: {
                title: menu.menuTitle
            },
        }
            // 可选：阻止目录型路由的导航
        if (!menu.menuComponent?.trim()) {
            dynamicRouterList.beforeEnter = (_to, _from, next) => next(false);
        }
        //获取当前存在的目录
        const curmenu: ICurrentMenuItem = {

            menuName: menu.menuName,
            menuPath: menu.menuPath,
            menuIcon: menu.menuIcon,
            menuComponent: menu.menuComponent,//只需要字符串
            menuId: menu.menuId,
            menuTitle: menu.menuTitle,
            parentId: menu.parentId,
            isDirectory: menu.isDirectory
        }
        curMenu.push(curmenu);
        router.addRoute('Layout', dynamicRouterList);
    })
    store.setCurMenu(curMenu);
    store.setIsLoadedRouters(true);
    console.log(router.getRoutes());
    
    // 强制刷新当前路由（关键步骤）
    if (forceReload) {
        await router.replace('/').catch(() => {});
        await router.replace(currentPath);
    }
    //console.log('一维化菜单完毕');
    //console.log('获取当前菜单完毕');
}

//全局路由导航守卫
router.beforeEach(async(to) => {
    const store = useMenuStore(pinia);
    console.log(store.isLoadedRouters);
    if (!store.getIsLoadedRouters) {
        await setDynamicRouter();
        return to.fullPath;
    }
});

//导出路由对象
export default router;