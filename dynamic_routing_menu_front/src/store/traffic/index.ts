import { defineStore } from "pinia";
import type { PAGE } from "../../components/page/page";

export const useTrafficStore = defineStore('traffic', {
  state: () => ({
      page: {} as PAGE,
      labelCount: {} as Record<number, number>
  }),
  //getter 仅依赖 state
  getters: {
    
      getPage(): PAGE {
          return this.page;
      },
      getLabelCount(): Record<number, number> {
        return this.labelCount; // 确保总是返回对象
      }
  },
  //action 类似getter可通过 this 访问整个 store 实例
  actions: {
  
      setPage(page: PAGE){
           this.page = page;
      },
      setLabelCount(labelCount: Record<number, number>) {
        this.labelCount = labelCount;
      }
  }
});