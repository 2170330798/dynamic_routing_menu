import { defineStore } from "pinia";
import type { PAGE } from "../../components/page/page";

export const useTrafficStore = defineStore('traffic', {
  state: () => ({
      page: {} as PAGE 
  }),
  //getter 仅依赖 state
  getters: {
    
      getPage(): PAGE {
          return this.page;
      } 
  },
  //action 类似getter可通过 this 访问整个 store 实例
  actions: {
  
      setPage(page: PAGE){
           this.page = page;
      }
  }
});