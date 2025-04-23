/// <reference types="vite/client" />


declare module '*.vue' {
    import { DefineComponent } from 'vue';
    const component: DefineComponent<{}, {}, any>;
    export default component;
}

//动态菜单数据请求路径
export const GET_MENU_REQUEST_API = 'http://localhost:8080/menu/get'
export const ADD_MENU_REQUEST_API = 'http://localhost:8080/menu/add';
export const DELETE_MENU_REQUEST_API ='http://localhost:8080/menu/delete';
export const UPDATE_MENU_REQUEST_API = 'http://localhost:8080/menu/update';

//模型数据请求路径
export const GET_MODEL_REQUEST_API = 'http://localhost:8080/model/get'
export const ADD_MODEL_REQUEST_API = 'http://localhost:8080/model/add';
export const DELETE_MODEL_REQUEST_API ='http://localhost:8080/model/delete';
export const UPDATE_MODEL_REQUEST_API = 'http://localhost:8080/model/update';

//模型框架请求路径
export const GET_FRAME_REQUEST_API = 'http://localhost:8080/frame/get'
export const ADD_FRAME_REQUEST_API = 'http://localhost:8080/frame/add';
export const DELETE_FRAME_REQUEST_API ='http://localhost:8080/frame/delete';
export const UPDATE_FRAME_REQUEST_API = 'http://localhost:8080/frame/update';

//网络流量数据请求路径
export const GET_TRAFFIC_REQUEST_API = 'http://localhost:8080/traffic/get'
export const GET_TRAFFIC_BY_PAGE_REQUEST_API = 'http://localhost:8080/traffic/page' 
export const ADD_TRAFFIC_REQUEST_API = 'http://localhost:8080/traffic/add';
export const DELETE_TRAFFIC_REQUEST_API ='http://localhost:8080/traffic/delete';
export const UPDATE_TRAFFIC_REQUEST_API = 'http://localhost:8080/traffic/update';

//训练模型
export const TRAIN_MODEL_REQUEST_API = 'http://localhost:8080/train/start';



//登入验证
export const LOGIN_REQUEST_API = 'http://localhost:8080/user/login'

//用户
export const GET_USER_REQUEST_API = 'http://localhost:8080/user/list'
export const DELETE_USER_REQUEST_API = 'http://localhost:8080/user/delete'
export const UPDATE_USER_REQUEST_API = 'http://localhost:8080/user/update'
export const ADD_USER_REQUEST_API = 'http://localhost:8080/user/add'