// stores/menu.ts
import { defineStore } from "pinia";
import type { IMenuItem } from "./index";

export const useMenuStore = defineStore('menu', {
  state: () => ({
    menuTree: [] as IMenuItem[]
  }),
  //getter 仅依赖 state
  getters: {
    // 不需要参数，可以通过this访问state
    getMenuTree(): IMenuItem[] {
      return this.menuTree;
    }
  },
  //action 类似getter可通过 this 访问整个 store 实例
  actions: {
    // 修正action，不需要传入state
    setMenuTree(menuTree: IMenuItem[]) {
      this.menuTree = menuTree;
      console.log('数据持久化完毕')
    }
  }
});

/*
 *使用自定义store
 *   <script setup>
 *   import { useCounterStore } from './counterStore'

 *   const store = useMenuStore()
 *   </script>

 *   <template>
 *     <p>Double count is {{ store.getMenuTree }}</p>
 *   </template>
 *
 *
 */