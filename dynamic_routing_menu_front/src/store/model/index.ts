// stores/menu.ts
import { defineStore } from "pinia";
import type { IMODEL } from "../../components/model/model";

export const useModelStore = defineStore('model', {
  state: () => ({
      ModelList: [] as IMODEL[],
      isUpdate: false,

  }),
  //getter 仅依赖 state
  getters: {
      getModelList():IMODEL[] {
           return this.ModelList;
      }
  },
  //action 类似getter可通过 this 访问整个 store 实例
  actions: {
      setModelList(ModelList:IMODEL[]) {
           this.ModelList = ModelList;
      }
  }
});