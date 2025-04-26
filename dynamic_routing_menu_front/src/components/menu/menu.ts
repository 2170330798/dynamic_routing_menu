
// 定义菜单列表
export interface IMenuList {
    
  //id: number;
  menuId: number;
  menuName: string;
  menuIcon: string;
  menuPath: string;
  menuTitle: string;
  parentId: number;
  menuComponent : string;
  isDirectory?: boolean;
}

// 定义菜单（树）项接口
export interface IMenuItem extends IMenuList {
  
  children: IMenuItem[];

}

export interface ICurrentMenuItem extends IMenuList{
  //isExisted: boolean;
}

export const tempMenuList: IMenuList = {
      //id: 0,
      menuId: 0,
      menuName: "",
      menuIcon: "",
      menuPath: "",
      menuTitle: "",
      parentId: -1,
      menuComponent: ""
};


export const initTempMenuList = (list: IMenuList):IMenuList => {
        
        list.menuId = 0;
        list.menuName = "";
        list.menuIcon = "";
        list.menuPath = "";
        list.menuTitle = "";
        list.parentId = -1;
        list.menuComponent = "";
        
        return list;
}