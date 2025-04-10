<template>
    <el-container>
        <el-header>
            <h1>动态路由</h1>    
        </el-header>
        <el-container>
            <el-aside >
                <el-menu :default-active="activeIndex" @select="handleSelect">
                  <el-tree
                    :data="menus"
                    @node-click="handleSelect"
                  />
                    <!--动态路由菜单
                    <template v-for="menu in menuTree" :key="menu.id">
                      <el-menu-item v-if="menu.children && menu.children.length == 0" :index="menu.menuPath">{{ menu.menuTitle }}</el-menu-item>
                      <el-sub-menu v-if="menu.children && menu.children.length > 0" :index="menu.menuPath">
                            <template #title>{{ menu.menuTitle }}</template>
                            <el-menu-item v-for="child in menu.children" :key="child.id" :index="child.menuPath">
                              {{ child.menuTitle }}
                            </el-menu-item>
                          </el-sub-menu>
                    </template>
                    --> 
                    <!--静态路由菜单
                    <el-menu-item index="/home">首页</el-menu-item>
                    <el-sub-menu index="/system">
                        <template #title>系统管理</template>
                        <el-menu-item index="/system/user">用户角色</el-menu-item>
                        <el-menu-item index="/system/auth">权限管理</el-menu-item>
                        <el-menu-item index="/system/role">角色管理</el-menu-item>
                        <el-menu-item index="/system/menu">菜单管理</el-menu-item>
                        <el-menu-item index="/log">日志管理</el-menu-item>
                    </el-sub-menu>
                    -->
                </el-menu>
            </el-aside>
            <el-main>
                <router-view />
    
            </el-main>
        </el-container>
    </el-container>
</template>


<script lang="ts" setup>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router';
import { useMenuStore } from '../../components/menu/menu';
import { fetchMenuData, flattenMenuTree } from '../menu/menu';

const route = useRoute();
const router = useRouter();
const activeIndex = ref('');
const store = useMenuStore();

onMounted(
    async () => {
        activeIndex.value = route.path;// 初始化时设置当前路由路径为高亮菜单
        console.log('数据加载完成',activeIndex.value)
        fetchMenuData(); // 确保菜单数据加载完成
});

const menus = flattenMenuTree(store.getMenuTree);

const handleSelect = (key: string, path: string) => {
  console.log(key, path);
  router.push(key);
};



/*
// 定义菜单项接口
interface MenuItem {
  id: number;
  name: string;
  menuComponent: string;
  menuId: number;
  menuName: string;
  menuPath: string;
  menuTitle: string;
  parentId: number;
  children: MenuItem[];
}

// 存储菜单数据
const menuTree = ref<MenuItem[]>([]); 
const loading = ref(false);
const error = ref<Error | null>(null); ;
const route = useRoute();
const router = useRouter();
const activeIndex = ref('')

const handleSelect = (key: string, path: string) => {
  console.log(key, path);
  router.push(key);
};

onMounted(
    async () => {
        activeIndex.value = route.path;// 初始化时设置当前路由路径为高亮菜单
        console.log(activeIndex.value)
        await getMenuTree(); // 确保菜单数据加载完成
});

async function getMenuTree() {
  loading.value = true;
  error.value = null;  // 明确赋值为 null 是允许的
  try {
    const response = await axios.get<{data: MenuItem[]}>('http://localhost:8080/menu');
    menuTree.value = response.data.data;
  } catch (err) {
    error.value = err as Error;  // 类型断言
  } finally {
    loading.value = false;
  }
}
*/
</script>


<style scoped>
.el-container {
  border: 1px solid #ccc;
  height: 100vh; /* 确保容器占满全屏 */
}

.el-header h1{
    display: flex;
    justify-content: center;
    text-align: center;
}

.el-aside {
  width: 150px;
  height: 100%;
  background-color: #f5f5f5;
}

.el-main {
  padding: 20px;
}

</style>