import { defineStore } from "pinia";
import type { User} from "../../components/login/login";

export const useUserStore = defineStore('user', {
    state: () => ({
      userData: [] as User[],
      currentUser: {} as User,
      isUpdate: false
    }),
    getters: {
      getUserData(): User[] {
        return this.userData;
      },
      getIsUpdate(): boolean {
        return this.isUpdate;
      },
      getCurrentUser(): User {
         return this.currentUser;
      }
    },
    actions: {
      setUserData(userData: User[]) {
        this.userData = userData;
      },
      setIsUpdate(isUpdate: boolean){
         this.isUpdate = isUpdate;
      },
      setCurrentUser(currentUser: User) {
         this.currentUser = currentUser;
      }
    }
  });