import axios from "axios";
import type { IMenuItem } from "../../store/menu";
import { useMenuStore } from "../../store/menu/menu";

const store = useMenuStore();

// 定义一维化的类型（移除 children）
export interface IFlatMenuItem extends Omit<IMenuItem, 'children'> {
  // 可以额外添加扁平化后的辅助字段（可选）
  level?: number;     // 层级深度
  isLeaf?: boolean;  // 是否是叶子节点
}

// 扁平化函数
export const flattenMenuTree = (tree: IMenuItem[]) => {
  const result: IFlatMenuItem[] = [];
  
  function traverse(node: IMenuItem, level: number = 0) {
    const { children, ...rest } = node;
    result.push({ 
      ...rest, 
      level, 
      isLeaf: !children || children.length === 0 
    });

    if (children) {
      children.forEach(child => traverse(child, level + 1));
    }
  }

  tree.forEach(root => traverse(root));
  console.log('一维化菜单树完成');
  console.log('一维菜单数据', result);
  return result;
}

// 获取层级菜单数据
export const fetchMenuData = async () => {
    
    try {
      const response = await axios.get('http://localhost:8080/menu');
      if (response.data.code === 200) {
       
        //将响应数据存到store中
        store.setMenuTree(response.data.data);
        console.log('菜单数据:', store.getMenuTree);

      }else {
        // 处理code不是200的情况
        console.warn('获取菜单数据成功，但返回状态码非200:', response.data);
        // 可以选择在这里抛出错误或进行其他处理
        throw new Error(response.data.message || '获取菜单数据失败');
      }

    } catch (err) {
        // 更详细的错误处理
        if (axios.isAxiosError(err)) {
              // 处理Axios特有的错误
              if (err.response) {
                // 请求已发出，服务器响应状态码不在2xx范围内
                console.error('获取菜单失败，服务器返回错误:', err.response.status, err.response.data);
              } else if (err.request) {
                // 请求已发出，但没有收到响应
                console.error('获取菜单失败，无服务器响应:', err.request);
              } else {
                // 请求设置出错
                console.error('获取菜单失败，请求设置错误:', err.message);
              }
        } else {
              // 处理非Axios错误
              console.error('获取菜单失败，发生意外错误:', err instanceof Error ? err.message : err);
        }
        console.error('获取菜单失败:', err);
        // 可以选择将错误传递到上层或进行其他处理
        throw err;

    } finally {
        // 无论成功或失败都会执行的代码
        console.log('菜单获取尝试完成');
        // 可以在这里添加一些清理逻辑或状态更新
        // 例如：store.setLoading(false);
    }
  }