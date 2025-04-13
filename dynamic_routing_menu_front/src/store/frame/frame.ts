// stores/menu.ts
import { defineStore } from "pinia";
import type { IFrame } from "../../components/frame/frame";

export const useFrameStore = defineStore('frame', {
  state: () => ({
    frameData: [] as IFrame[],
  }),
  getters: {
    getIFrame(): IFrame[] {
      return this.frameData;
    },
  },
  actions: {
    setFrame(frameData: IFrame[]) {
      this.frameData = frameData;
    }
  }
});