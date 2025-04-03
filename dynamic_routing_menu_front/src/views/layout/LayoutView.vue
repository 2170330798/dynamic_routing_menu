<template>
  <el-container>
    <el-header>
      <h1>动态路由</h1>
    </el-header>
    <el-container>
      <el-aside>
        <el-menu :default-active="activeIndex">
          <!-- 使用 @node-click 并设置 current-node-key -->
          <el-tree class="custom-tree" :data="menus" :props="treeProps" node-key="menuPath" :current-node-key="activeIndex" @node-click="handleSelect"/>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useMenuStore } from '../../store/menu/menu';
import { fetchMenuData } from '../menu/menu';

const route = useRoute();
const router = useRouter();
const store = useMenuStore();
const activeIndex = ref(route.path);
const menus = ref<any[]>([]);
const treeProps = ref({
  label: 'menuTitle',
  children: 'children',
});

onMounted(async () => {
  await fetchMenuData();
  menus.value = store.getMenuTree;
  console.log('当前路由:', route.path); // 调试用
});

// 修正：使用 el-tree 的 node-click 事件
const handleSelect = (menu: any) => {
  if (menu.menuPath) {
    activeIndex.value = menu.menuPath; // 更新高亮
    router.push(menu.menuPath); // 跳转路由
    console.log(menu.menuPath);
  }
};
</script>


<style>
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


/* 将展开图标移到右侧 */
.custom-tree .el-tree-node__expand-icon {
  position: absolute;
  right: 10px;
}

/* 调整子节点的缩进（可选） */
.custom-tree .el-tree-node__content{
    display: flex;
    justify-content: center;
    align-items: center;
}

</style>