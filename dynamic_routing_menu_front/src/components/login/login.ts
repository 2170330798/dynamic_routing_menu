export interface User {

    userId: string;
    userName: string;
    password: string;
    phone: string;
    createTime: string;
    updateTime: string;
    role: boolean;
    avatar: string;
    token: string;
}


export const tmpUserData: User = {
    userId: "",
    userName: "",
    password: "",
    phone: "",
    role: false,
    createTime: "",
    updateTime: "",
    token: "",
    avatar: ""
}

export const initUserForm = (data: User) => {
    tmpUserData.userId = data.userId;
    tmpUserData.userName = data.userName;
    tmpUserData.password = data.password;
    tmpUserData.phone = data.phone;
    tmpUserData.createTime = data.createTime;
    tmpUserData.updateTime = data.updateTime;
    tmpUserData.role = data.role;
    tmpUserData.token = data.token;
} 