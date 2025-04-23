import axios from "axios";
import { ADD_USER_REQUEST_API, DELETE_USER_REQUEST_API, GET_USER_REQUEST_API, UPDATE_USER_REQUEST_API } from "../vite-env.d";
import { useUserStore } from "../store/user/user";
import type { User } from "../components/login/login";

export const getUser = async() => {

    const store = useUserStore();
    const request = await axios.get(GET_USER_REQUEST_API);
    store.setUserData(request.data.data);
    console.log('获取用户数据',store.getUserData);
    if (request.data.code === 200){
          console.log('获取用户数据成功', request.data.code);
          return 200;
    }else {
          console.log('提交训练数据失败', request.data.code);
          return request.data.code;
    }
} 

export const deleteUser = async(userId: string) => {

    const request = await axios.delete(DELETE_USER_REQUEST_API+`?id=${userId}`);
    console.log('删除用户',request.data.code);
    if (request.data.code === 200){
          console.log('删除用户数据成功', request.data.code);
          return 200;
    }else {
          console.log('删除用户数据失败', request.data.code);
          return request.data.code;
    }
} 

export const updateUser = async(data: User) => {

    const request = await axios.put(UPDATE_USER_REQUEST_API, data);
    console.log('提交用户数据',data);
    if (request.data.code === 200){
          console.log('提交用户数据成功', request.data.code);
          return 200;
    }else {
          console.log('提交用户数据失败', request.data.code);
          return request.data.code;
    }
} 

export const addUser = async(data: User) => {

    const request = await axios.post(ADD_USER_REQUEST_API, data);
    console.log('提交用户数据',request.data.data);
    if (request.data.code === 200){
          console.log('提交用户数据成功', request.data.code);
          return 200;
    }else {
          console.log('提交用户数据失败', request.data.code);
          return request.data.code;
    }
} 