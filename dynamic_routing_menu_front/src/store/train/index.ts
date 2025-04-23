// stores/menu.ts
import { defineStore } from "pinia";
import { type TRAIN} from "../../components/train/train"

export const useTrainStore = defineStore('train', {
    state: () => ({
      trainData: {} as TRAIN
    }),
    getters: {
      getTrainData(): TRAIN {
        return this.trainData  // 使用 this 访问 state
      }
    },
    actions: {
      setTrainData(trainData: TRAIN) {
        this.trainData = trainData;
      }
    }
  });