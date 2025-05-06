<template>
  <el-container class="main-container">
    <el-header class="header-container">
      <HeaderView />
    </el-header>
    <el-container class="content-container">
      <el-aside class="aside-container">
        <el-scrollbar>
          <el-menu :default-active="activeIndex" class="menu-container">
            <el-tree
              ref="menuTree"
              class="menu-tree"
              :data="menus"
              :props="treeProps"
              node-key="menuPath"
              :current-node-key="activeIndex"
              :expand-on-click-node="false"
              :highlight-current="true"
              @node-click="handleSelect"
            >
              <!-- 树形菜单内容 -->
              <template #default="{ node, data }">
                <span class="tree-node-content">
                  <el-icon v-if="data.menuIcon" class="menu-icon">
                    <component :is="data.menuIcon" />
                  </el-icon>
                  <span class="menu-label">{{ node.label }}</span>
                </span>
              </template>
            </el-tree>
          </el-menu>
        </el-scrollbar>
      </el-aside>
      <el-main class="main-content">
        <router-view v-slot="{ Component }">
          <transition name="fade" mode="out-in">
            <div v-if="Component"> <!-- 添加包装元素 -->
              <component :is="Component"/>
            </div>
          </transition>
        </router-view>
      </el-main>
    </el-container>
  </el-container>
</template>

<script lang="ts" setup>
import { ref, computed, watch, onMounted, nextTick } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useMenuStore } from '../../store/menu/index'
import pinia from '../../store'
import HeaderView from './HeaderView.vue'

const route = useRoute()
const router = useRouter()
const store = useMenuStore(pinia)
const menuTree = ref()

const activeIndex = ref(route.path)
const treeProps = {
  label: 'menuTitle',
  children: 'children'
}

// 使用计算属性获取菜单数据
const menus = computed(() => store.getMenuTree)

// 添加加载状态
const menuLoaded = ref(false)

// 安全版本的 expandActiveNodeParents
const expandActiveNodeParents = () => {
  try {
    if (!menuTree.value?.store?.nodesMap) return
    
    const currentNode = menuTree.value.getNode(activeIndex.value)
    if (!currentNode) return

    let parent = currentNode.parent
    while (parent) {
      const nodeInMap = menuTree.value.store.nodesMap[parent.id]
      if (nodeInMap) {
        nodeInMap.expanded = true
      } else {
        break
      }
      parent = parent.parent
    }
  } catch (error) {
    console.warn('Failed to expand menu nodes:', error)
  }
}

// 确保菜单数据加载完成后再初始化
const setupMenuWatcher = () => {
  watch(
    () => store.getMenuTree,
    (newMenus) => {
      if (newMenus?.length > 0) {
        menuLoaded.value = true
        nextTick(expandActiveNodeParents)
      }
    },
    { immediate: true }
  )
}

// 监听路由变化
const setupRouteWatcher = () => {
  watch(
    () => route.path,
    (newPath) => {
      activeIndex.value = newPath
      if (menuLoaded.value) {
        nextTick(expandActiveNodeParents)
      }
    }
  )
}

// 处理菜单选择
const handleSelect = (menu: any) => {
  if (menu.menuPath && menu.menuPath !== activeIndex.value) {
    router.push(menu.menuPath)
  }
}

// 初始化
onMounted(() => {
  setupMenuWatcher()
  setupRouteWatcher()
  nextTick(expandActiveNodeParents)
})
</script>

<style lang="scss" scoped>
.main-container {
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.header-container {
  height: 55px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-bottom: 1px solid var(--el-border-color-light);
  box-shadow: 0 1px 4px rgba(0, 0, 0, 0.08);
}

.content-container {
  flex: 1;
  overflow: hidden;
}

.aside-container {
  width: 220px !important;
  height: 100%;
  background-color: var(--el-bg-color);
  border-right: 1px solid var(--el-border-color-light);
  transition: width 0.3s;

  .menu-container {
    border-right: none;
    height: 100%;
  }
}

.main-content {
  padding: 16px;
  background-color: var(--el-fill-color-light);
}

.menu-tree {
  background: transparent;
  
  // 使用 :deep() 处理作用域穿透
  :deep(.el-tree-node) {
    margin: 4px 0;
    
    &.is-current {
      > .el-tree-node__content {
        background-color: var(--el-color-primary-light-9);
        color: var(--el-color-primary);
        
        .menu-icon {
          color: var(--el-color-primary);
        }
      }
    }
  }
  
  // 单独处理嵌套样式
  :deep(.el-tree-node__content) {
    height: 36px;
    display: flex;
    align-items: center;
    padding-right: 16px;
    transition: all 0.3s;
    
    &:hover {
      background-color: var(--el-color-primary-light-9);
    }
  }
  
  :deep(.el-tree-node__expand-icon) {
    color: var(--el-text-color-secondary);
    font-size: 16px;
    padding: 0;
    margin-right: 4px;
    
    &.expanded {
      transform: rotate(0);
    }
  }
}

.tree-node-content {
  display: flex;
  align-items: center;
  flex: 1;
}

.menu-icon {
  margin-right: 8px;
  font-size: 18px;
  color: var(--el-text-color-secondary);
}

.menu-label {
  font-size: 14px;
  color: var(--el-text-color-primary);
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>