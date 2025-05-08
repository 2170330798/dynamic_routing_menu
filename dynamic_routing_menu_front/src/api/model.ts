import axios from "axios";
import { useModelStore } from "../store/model";
import pinia from "../store";
import { DELETE_MODEL_REQUEST_API, DELETE_MODEL_REQUEST_API_DJANGO, GET_MODEL_REQUEST_API, GET_MODEL_REQUEST_API_DJANGO, UPDATE_MODEL_REQUEST_API } from "../vite-env.d";
import type { IMODEL } from "../components/model/model";


export const getModelData = async () => {
    
    const store = useModelStore(pinia);
    try {
      const request = await axios.get(GET_MODEL_REQUEST_API_DJANGO);
      if (request.data.code === 200) {
       
        //将响应数据存到store中
        store.setModelList(request.data.data);
        console.log('请求模型数据',request.data.data);

      }else {
        // 处理code不是200的情况
        console.warn('获取模型数据成功，但返回状态码非200:', request.data);
        // 可以选择在这里抛出错误或进行其他处理
        throw new Error(request.data.message || '获取模型数据失败');
      }

    } catch (err) {
        // 更详细的错误处理
        if (axios.isAxiosError(err)) {
              // 处理Axios特有的错误
              if (err.request) {
                // 请求已发出，服务器响应状态码不在2xx范围内
                console.error('获取模型失败，服务器返回错误:', err.request.status, err.request.data);
              } else if (err.request) {
                // 请求已发出，但没有收到响应
                console.error('获取模型失败，无服务器响应:', err.request);
              } else {
                // 请求设置出错
                console.error('获取模型失败，请求设置错误:', err.message);
              }
        } else {
              // 处理非Axios错误
              console.error('获取模型失败，发生意外错误:', err instanceof Error ? err.message : err);
        }
        console.error('获取模型失败:', err);
        // 可以选择将错误传递到上层或进行其他处理
        throw err;

    } finally {
        // 无论成功或失败都会执行的代码
        console.log('模型获取完成');
        // 可以在这里添加一些清理逻辑或状态更新
        // 例如：store.setLoading(false);
    }
}

// export const addModel = async(data :IMODEL) => {

    
//     try {
//       const request = await axios.post(ADD_MODEL_REQUEST_API, data);
//       if (request.data.code === 200){
//           console.log('提交模型数据成功', request.data.code);
//           console.log('提交模型数据', data);
//           return '提交模型数据成功';
//       }else if(request.data.code == 401){
//         console.log('模型ID违规，请重新提交');
//     }else {
//         console.log('提交模型数据失败', request.data.code);
//         return '提交模型数据失败';
//     }


//     } catch (err) {
//         // 更详细的错误处理
//         if (axios.isAxiosError(err)) {
//               // 处理Axios特有的错误
//               if (err.request) {
//                 // 请求已发出，服务器响应状态码不在2xx范围内
//                 console.error('提交模型数据失败，服务器返回错误:', err.request.status, err.request.data);
//               } else if (err.request) {
//                 // 请求已发出，但没有收到响应
//                 console.error('提交模型数据失败，无服务器响应:', err.request);
//               } else {
//                 // 请求设置出错
//                 console.error('提交模型数据失败，请求设置错误:', err.message);
//               }
//         } else {
//               // 处理非Axios错误
//               console.error('提交模型数据失败，发生意外错误:', err instanceof Error ? err.message : err);
//         }
//         console.error('提交模型数据失败:', err);
//         // 可以选择将错误传递到上层或进行其他处理
//         throw err;

//     } finally {
//         // 无论成功或失败都会执行的代码
//         console.log('提交模型数据完成');
//         // 可以在这里添加一些清理逻辑或状态更新
//         // 例如：store.setLoading(false);
//     }

// } 

export const deleteModel = async(id: number) => {

  try {
    const request = await axios.delete(DELETE_MODEL_REQUEST_API_DJANGO+`?id=${id}`);
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


export const updateModel = async(data: IMODEL) => {

  try {
    const request = await axios.put(UPDATE_MODEL_REQUEST_API, data);
    if (request.data.code === 200){
        console.log('更新数据成功', request.data.code);
        return 200;
    }else if(request.data.code == 401){
        console.log('菜单ID违规，请重新提交',request.data.code);
        return 401;
    }else {
        console.log('NO.1更新数据失败', request.data.code);
        return request.data.code;
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