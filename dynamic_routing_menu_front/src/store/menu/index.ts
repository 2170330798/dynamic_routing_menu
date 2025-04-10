// stores/menu.ts
import { defineStore } from "pinia";
import type { ICurrentMenuItem, IMenuItem } from "../../components/menu/menu";

export const useMenuStore = defineStore('menu', {
  state: () => ({
    menuTree: [] as IMenuItem[],
    curMenu:[] as ICurrentMenuItem[],
    isLoadedRouters : false,
    isUpdate: false
  }),
  //getter 仅依赖 state
  getters: {
    // 不需要参数，可以通过this访问state
    getMenuTree(): IMenuItem[] {
      return this.menuTree;
    },
    getCurMenu(): ICurrentMenuItem[] {
      return this.curMenu;
    },
    getIsLoadedRouters() : Boolean {
      return this.isLoadedRouters;
    },
    getIsUpdate (): Boolean{
       return this.isUpdate; 
    }
  },
  //action 类似getter可通过 this 访问整个 store 实例
  actions: {
    // 修正action，不需要传入state
    setMenuTree(menuTree: IMenuItem[]) {
      this.menuTree = menuTree;
      console.log('菜单树数据持久化完毕')
    },
    setIsLoadedRouters(isLoadedRouters: boolean) {
       this.isLoadedRouters = isLoadedRouters;
    },
    setCurMenu(curMenu: ICurrentMenuItem[]) {
      this.curMenu = curMenu;
      console.log('已有菜单数据持久化完毕')
    },
    setIsUpdate(isUpdate: boolean) {
       this.isUpdate = isUpdate;
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