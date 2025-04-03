// 定义菜单项接口
export interface IMenuItem {
    
    id: number;
    name: string;
    menuComponent: string;
    menuId: number;
    menuName: string;
    menuPath: string;
    menuTitle: string;
    parentId: number;
    children: IMenuItem[];

}