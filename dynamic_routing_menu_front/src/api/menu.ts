import axios from "axios";
import { useMenuStore } from "../store/menu";
import type { IMenuItem, IMenuList } from "../components/menu/menu";
import pinia from "../store";
import { ADD_MENU_REQUEST_API, ADD_MENU_REQUEST_API_DJANGO, DELETE_MENU_REQUEST_API, DELETE_MENU_REQUEST_API_DJANGO, GET_MENU_REQUEST_API, GET_MENU_REQUEST_API_DJANGO, UPDATE_MENU_REQUEST_API } from "../vite-env.d";
// 定义一维化的类型（移除 children）
export interface IFlatMenuItem extends IMenuList {
  // 可以额外添加扁平化后的辅助字段（可选）
  level?: number;     // 层级深度
  isLeaf?: boolean;  // 是否是叶子节点
}

// 扁平化函数
export const flattenMenuTree = (tree: IMenuItem[]) => {
  console.log('开始获取菜单数据')
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
  return result;
}

// export const isValidForm = () => {

//   return ValidForm.validId && ValidForm.validName && ValidForm.validPath &&  ValidForm.validTitle && ValidForm.validParentId && ValidForm.validComponent;
// }



// 获取层级菜单数据
export const getMenuData = async () => {
    
    const store = useMenuStore(pinia);
    try {
      const request = await axios.get(GET_MENU_REQUEST_API_DJANGO);
      if (request.data.code === 200) {
       
        //将响应数据存到store中
        store.setMenuTree(request.data.data);
        console.log('菜单数据:', store.getMenuTree);

      }else {
        // 处理code不是200的情况
        console.warn('获取菜单数据成功，但返回状态码非200:', request.data);
        // 可以选择在这里抛出错误或进行其他处理
        throw new Error(request.data.message || '获取菜单数据失败');
      }

    } catch (err) {
        // 更详细的错误处理
        if (axios.isAxiosError(err)) {
              // 处理Axios特有的错误
              if (err.request) {
                // 请求已发出，服务器响应状态码不在2xx范围内
                console.error('获取菜单失败，服务器返回错误:', err.request.status, err.request.data);
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
        console.log('菜单获取完成');
        // 可以在这里添加一些清理逻辑或状态更新
        // 例如：store.setLoading(false);
    }
}

export const addMenu = async(data : IMenuList) => {

    
    try {
      const request = await axios.post(ADD_MENU_REQUEST_API_DJANGO, data);
      if (request.data.code === 200){
          console.log('提交数据成功', request.data.code);
          console.log('提交数据', data);
          return '提交数据成功';
      }else if(request.data.code == 401){
        console.log('菜单ID违规，请重新提交');
    }else {
        console.log('提交数据失败', request.data.code);
        return '提交数据失败';
    }


    } catch (err) {
        // 更详细的错误处理
        if (axios.isAxiosError(err)) {
              // 处理Axios特有的错误
              if (err.request) {
                // 请求已发出，服务器响应状态码不在2xx范围内
                console.error('提交数据失败，服务器返回错误:', err.request.status, err.request.data);
              } else if (err.request) {
                // 请求已发出，但没有收到响应
                console.error('提交数据失败，无服务器响应:', err.request);
              } else {
                // 请求设置出错
                console.error('提交数据失败，请求设置错误:', err.message);
              }
        } else {
              // 处理非Axios错误
              console.error('提交数据失败，发生意外错误:', err instanceof Error ? err.message : err);
        }
        console.error('提交数据失败:', err);
        // 可以选择将错误传递到上层或进行其他处理
        throw err;

    } finally {
        // 无论成功或失败都会执行的代码
        console.log('提交数据完成');
        // 可以在这里添加一些清理逻辑或状态更新
        // 例如：store.setLoading(false);
    }

} 

export const deleteMenu = async(menuid: number) => {

  try {
    const request = await axios.delete(DELETE_MENU_REQUEST_API_DJANGO+`?id=${menuid}`);
    if (request.data.code === 200){
        console.log('删除数据成功', request.data.code);
        return '删除数据成功';
    }else {
        console.log('删除数据失败', request.data.code);
        return '删除数据失败';
    }

  } catch (err) {
      // 更详细的错误处理
      if (axios.isAxiosError(err)) {
            // 处理Axios特有的错误
            if (err.request) {
              // 请求已发出，服务器响应状态码不在2xx范围内
              console.error('删除数据失败，服务器返回错误:', err.request.status, err.request.data);
            } else if (err.request) {
              // 请求已发出，但没有收到响应
              console.error('删除数据失败，无服务器响应:', err.request);
            } else {
              // 请求设置出错
              console.error('删除数据失败，请求设置错误:', err.message);
            }
      } else {
            // 处理非Axios错误
            console.error('删除数据失败，发生意外错误:', err instanceof Error ? err.message : err);
      }
      console.error('删除数据失败:', err);
      // 可以选择将错误传递到上层或进行其他处理
      throw err;

  } finally {
      // 无论成功或失败都会执行的代码
      console.log('删除数据成功');
      // 可以在这里添加一些清理逻辑或状态更新
      // 例如：store.setLoading(false);
  }
} 


export const updateMenu = async(data : IMenuList) => {

  try {
    const request = await axios.put(UPDATE_MENU_REQUEST_API, data);
    if (request.data.code === 200){
        console.log('更新数据成功', request.data.code);
        return '更新数据成功';
    }else if(request.data.code == 401){
        console.log('菜单ID违规，请重新提交',request.data.code);
    }else {
        console.log('NO.1更新数据失败', request.data.code);
        return '更新数据失败';
    }


  } catch (err) {
      // 更详细的错误处理
      if (axios.isAxiosError(err)) {
            // 处理Axios特有的错误
            if (err.request) {
              // 请求已发出，服务器响应状态码不在2xx范围内
              console.error('更新数据失败，服务器返回错误:', err.request.status, err.request.data);
            } else if (err.request) {
              // 请求已发出，但没有收到响应
              console.error('更新数据失败，无服务器响应:', err.request);
            } else {
              // 请求设置出错
              console.error('更新数据失败，请求设置错误:', err.message);
            }
      } else {
            // 处理非Axios错误
            console.error('更新数据失败，发生意外错误:', err instanceof Error ? err.message : err);
      }
      console.error('NO.2更新数据失败:', err);
      // 可以选择将错误传递到上层或进行其他处理
      throw err;

  } finally {
      // 无论成功或失败都会执行的代码
      console.log('更新数据完成');
      // 可以在这里添加一些清理逻辑或状态更新
      // 例如：store.setLoading(false);
  }

} 