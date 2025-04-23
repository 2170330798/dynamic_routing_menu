import axios from "axios";
import type { User } from "../components/login/login";
import { LOGIN_REQUEST_API } from "../vite-env.d";
import { useAuthStore } from "../store/auth/auth";


export const login = async (data: User) => {
      const store = useAuthStore();
      const request = await axios.post(LOGIN_REQUEST_API,data);
      console.log('提交验证数据', data);
      if (request.data.code === 200){
             console.log('放回登入用户数据', request.data.data);
             store.setLoginInfo(request.data.data);
             return 200;
      }else if(request.data.code == 500){
            console.log('提交验证违数据')
      }else {
            console.log('提交验证数据失败', request.data.code);
            return request.data.code;
      }

}