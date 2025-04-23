import { defineStore } from "pinia";
import type { User } from "../../components/login/login";

export const useAuthStore = defineStore('auth', {
  state: () => ({
    loginInfo: {} as User,
    isAuthenticated : false
  }),
  getters: {
    getIsAuthenticated(): boolean {
        return this.isAuthenticated;
    },
    getLoginInfo() : User {
        return this.loginInfo;
    }

  },
  actions: {
    setIsAuthenticated(isAuthenticated: boolean) {
        this.isAuthenticated = isAuthenticated;
    },
    setLoginInfo(loginIfo: User) {
         this.loginInfo = loginIfo;
    }
  },
  persist: true, // 启用持久化
});