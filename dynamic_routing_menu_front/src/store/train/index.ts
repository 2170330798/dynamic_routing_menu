// stores/menu.ts
import { defineStore } from "pinia";

export const useTrainStore = defineStore('train', {
    state: () => ({
      trainData: {} as any
    }),
    getters: {
      getTrainData(): any {
        return this.trainData  // 使用 this 访问 state
      }
    },
    actions: {
      setTrainData(trainData: any) {
        trainData.factor = Number(trainData.factor);
        trainData.gamma = Number(trainData.gamma);
        trainData.lr = Number(trainData.lr);
        trainData.weightDecay = Number(trainData.weightDecay);
        this.trainData = trainData;
      }
    }
  });