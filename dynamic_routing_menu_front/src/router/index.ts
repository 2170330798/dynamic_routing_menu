//创建路由
import { createRouter, createWebHistory,  type RouteRecordRaw } from "vue-router";
//创建路由映射表
const routes: RouteRecordRaw[] = [
    // 这里添加你的路由配置
    // 示例：
    // {
    //   path: '/',
    //   name: 'Home',
    //   component: () => import('@/views/Home.vue')
    // },
    {
        //根路径
        path: '/',
        name: 'Layout',
        component: () => import('../views/layout/LayoutView.vue'),
        children: [
            {
                //首页
                path: '/home',
                name: 'Home',
                component: () => import('../views/home/HomeView.vue'),    
            },
            {
                //系统管理
                path: '/system',
                name: 'System',
                children:[
                        
                    {
                        //用户管理
                        path: 'user',
                        name: 'User',
                        component: () => import('../views/user/UserView.vue'),
                    },
                    {
                        //权限管理
                        path: 'auth',
                        name: 'Authorization',
                        component: () => import('../views/auth/AuthView.vue'),
                    },
                    {
                        //角色管理
                        path: 'role',
                        name: 'Role',
                        component: () => import('../views/role/RoleView.vue'),
                    },
                    {
                        //菜单管理
                        path: 'menu',
                        name: 'Menu',
                        component: () => import('../views/menu/MenuView.vue'),
                    }
                ]
            },
            {
                //日志管理
                path: '/log',
                name: 'Log',
                component: () => import('../views/log/LogView.vue'),
            }
        ]
    }
];
//创建路由对象
const router = createRouter({
    routes,
    history: createWebHistory()
});

/*
// ✅ 全局路由守卫（检查登录状态）
router.beforeEach((to, from, next) => {
    const isLoggedIn = localStorage.getItem('token');
    if (to.meta.requiresAuth && !isLoggedIn) {
      next('/login'); // 跳转到登录页
    } else {
      next(); // 放行
    }
});
*/

//导出路由对象
export default router;