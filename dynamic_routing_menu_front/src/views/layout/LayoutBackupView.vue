<template>
    <el-container>
      <el-header>
        <div class="header-container">
           <HeaderView />
        </div>  
      </el-header>
      <el-container>
        <el-aside class="aside-container">
          <el-menu :default-active="activeIndex">
            <!-- 使用 @node-click 并设置 current-node-key -->
            <el-tree class="menu-tree" :data="menus" :props="treeProps" node-key="menuPath" :current-node-key="activeIndex" @node-click="handleSelect">
              <template #default="{ node, data }">
                <span class="icon-tree-node">
                  <el-icon v-if="data.menuIcon">
                    <component :is="data.menuIcon" />
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
  import { ref, computed, watch } from 'vue';
  import { useRouter, useRoute } from 'vue-router';
  import { useMenuStore } from '../../store/menu/index';
  import pinia from '../../store';
  import HeaderView from './HeaderView.vue';
  
  const route = useRoute();
  const router = useRouter();
  const store = useMenuStore(pinia);
  const activeIndex = ref(route.path);
  const treeProps = ref({
    label: 'menuTitle',
    children: 'children',
  });
  
  // 使用计算属性获取菜单数据
  const menus = computed(() => store.getMenuTree);
  
  // 监听路由变化更新高亮
  watch(() => route.path, (newPath) => {
    activeIndex.value = newPath;
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
  
  
  
  <style lang="css">
  .header-container{
     width: 100%;
     height: 55px;
     display: flex;
     justify-content: center;
     align-items: center;
     border-bottom: 1px solid #d1d2d4;
  }
  
  .aside-container{
     width: 20%;
     height: 90vh;
     text-align: left;
     border-right: none;
  }
  
  .el-aside {
    width: 15%;
    height: 100%;
    margin-left: 20px;
    text-align: center;
    border-right: none;
  }
  
  .el-container {
    height: 80vh; 
  }
  
  .el-header h1{
      display: flex;
      justify-content: center;
      text-align: center;
  }
  
  /* 将展开图标移到右侧 */
  .menu-tree .el-tree-node__expand-icon {
    position: absolute;
    right: 20px;
    width: 16px;
    height: 16px;
    color: rgba(23, 23, 24, 0.449);
  }
  
  /* 调整子节点的缩进（可选） */
  .menu-tree .el-tree-node__content{
      display: flex;
      justify-content: left;
      align-items: center;
      font-size: 13px;
      border-right: none;
      color: rgba(20, 32, 59, 0.449);
  }
  
  .icon-tree-node {
    margin-right: 5%;
    width: 16px;
    height: 16px;
    /* background-color: #d1d2d4; */
  }
  
  .el-icon {
    font-size: 18px;
    margin-left: 0px;
  }
  
  .custom-tree{
     color: #8d909b;
     width: 100%;
  }
  
  </style>