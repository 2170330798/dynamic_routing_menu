/// <reference types="vite/client" />


declare module '*.vue' {
    import { DefineComponent } from 'vue';
    const component: DefineComponent<{}, {}, any>;
    export default component;
}

export const GET_MENU_REQUEST_API = 'http://localhost:8080/menu/get'
export const ADD_MENU_REQUEST_API = 'http://localhost:8080/menu/add';
export const DELETE_MENU_REQUEST_API ='http://localhost:8080/menu/delete';
export const UPDATE_MENU_REQUEST_API = 'http://localhost:8080/menu/update';
