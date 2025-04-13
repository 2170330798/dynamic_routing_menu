<template>
  <el-container>
    <el-header>
      <h1>动态路由</h1>
    </el-header>
    <el-container>
      <el-aside class="aside-container">
        <el-menu :default-active="activeIndex">
          <!-- 使用 @node-click 并设置 current-node-key -->
          <el-tree class="custom-tree" :data="menus" :props="treeProps" node-key="menuPath" :current-node-key="activeIndex" @node-click="handleSelect">
            <template #default="{ node, data }">
              <span class="custom-tree-node">
                <el-icon v-if="data.menuIcon">
                  <component :is="data.menuIcon" /> <!-- 动态图标组件 -->
                </el-icon>
              </span>
              <span>{{ node.label }}</span>
            </template>
          </el-tree>
        </el-menu>
      </el-aside>
      <el-main>
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script lang="ts" setup>
import { ref, computed } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useMenuStore } from '../../store/menu/index';
import pinia from '../../store';

const route = useRoute();
const router = useRouter();
const store = useMenuStore(pinia);
const activeIndex = ref(route.path);
//const menuKey = ref(0); // 用于强制重新渲染
const treeProps = ref({
  label: 'menuTitle',
  children: 'children',
});

import { watch } from 'vue';

// 使用计算属性获取菜单数据
const menus = computed(() => store.getMenuTree);

// 监听路由变化更新高亮
watch(() => route.path, (newPath) => {
  activeIndex.value = newPath;
});

// 监听菜单数据变化，必要时强制重新渲染
// watch(() => store.getMenuTree, () => {
//   menuKey.value += 1;
// }, { deep: true });

// 修正：使用 el-tree 的 node-click 事件
const handleSelect = (menu: any) => {
  if (menu.menuPath) {
    activeIndex.value = menu.menuPath; // 更新高亮
    router.push(menu.menuPath); // 跳转路由
    console.log(menu.menuPath);
  }
};

// const isCollapse = ref(true)
// const handleOpen = (key: string, keyPath: string[]) => {
//   console.log(key, keyPath)
// }
// const handleClose = (key: string, keyPath: string[]) => {
//   console.log(key, keyPath)
// }
</script>


<style>
.el-container {
  /* border: 1px solid #ccc; */
  height: 80vh; /* 确保容器占满全屏 */
}

.el-header h1{
    display: flex;
    justify-content: center;
    text-align: center;
}

.el-aside {
  width: 145px;
  height: 100%;
  background-color: #f5f5f5;
}

/* 将展开图标移到右侧 */
.custom-tree .el-tree-node__expand-icon {
  position: absolute;
  right: 10px;
}

/* 调整子节点的缩进（可选） */
.custom-tree .el-tree-node__content{
    display: flex;
    justify-content: left;
    align-items: center;
}

.custom-tree-node {
  margin-left: 0px;
  width: 32px;
}

.el-icon {
  font-size: 18px;
  margin-left: 0px;
}

.custom-tree{
   color: #8d909b;
   width: 100%;
}


.aside-container{
   width: 150px;
   height: 100%;
}
</style>