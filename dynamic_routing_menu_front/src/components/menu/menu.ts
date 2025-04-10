
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

// export interface IMenuValidList {

//   validId: boolean;
//   validName: boolean;
//   validPath: boolean;
//   validIcon: boolean;
//   validTitle: boolean;
//   validParentId: boolean;
//   validComponent : boolean;
//   validDirectory?: boolean;
// }

// export const ValidForm: IMenuValidList = {
//   validId: false,
//   validName: false,
//   validPath: false,
//   validTitle: false,
//   validParentId: false,
//   validComponent: false
// };


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
