import axios from "axios";
import pinia from "../store";
import { useTrafficStore } from "../store/traffic";
import { GET_STATISTIC_TRAFFIC_API, GET_TRAFFIC_BY_PAGE_REQUEST_API } from "../vite-env.d";
//
// export const getTrafficData = async () => {
    
//     const store = useTrafficStore(pinia);
//     try {
//       const request = await axios.get(GET_TRAFFIC_REQUEST_API);
//       if (request.data.code === 200) {
       
//         //将响应数据存到store中
//         store.setTrafficList(request.data.data);
//         console.log('网络流量数据:', store.getTrafficList);

//       }else {
//         // 处理code不是200的情况
//         console.warn('获取网络流量数据成功，但返回状态码非200:', request.data);
//         // 可以选择在这里抛出错误或进行其他处理
//         throw new Error(request.data.message || '获取网络流量数据失败');
//       }

//     } catch (err) {
//         // 更详细的错误处理
//         if (axios.isAxiosError(err)) {
//               // 处理Axios特有的错误
//               if (err.request) {
//                 // 请求已发出，服务器响应状态码不在2xx范围内
//                 console.error('获取网络流量失败，服务器返回错误:', err.request.status, err.request.data);
//               } else if (err.request) {
//                 // 请求已发出，但没有收到响应
//                 console.error('获取网络流量失败，无服务器响应:', err.request);
//               } else {
//                 // 请求设置出错
//                 console.error('获取网络流量失败，请求设置错误:', err.message);
//               }
//         } else {
//               // 处理非Axios错误
//               console.error('获取网络流量失败，发生意外错误:', err instanceof Error ? err.message : err);
//         }
//         console.error('获取网络流量失败:', err);
//         // 可以选择将错误传递到上层或进行其他处理
//         throw err;

//     } finally {
//         // 无论成功或失败都会执行的代码
//         console.log('网络流量获取完成');
//         // 可以在这里添加一些清理逻辑或状态更新
//         // 例如：store.setLoading(false);
//     }
// }

export const getTrafficDataByPage = async (pageNumber: number, pageSize: number) => {
    
  const store = useTrafficStore(pinia);
  try {
    console.log('提交数据', pageNumber, pageSize);
    const request = await axios.get(GET_TRAFFIC_BY_PAGE_REQUEST_API+`?pageNumber=${pageNumber}&pageSize=${pageSize}`);
    if (request.data.code === 200) {
      //将响应数据存到store中
      store.setPage(request.data.data);
      console.log('流量数据:', store.getPage);

    }else {
      // 处理code不是200的情况
      console.warn('获取网络流量数据成功，但返回状态码非200:', request.data);
      // 可以选择在这里抛出错误或进行其他处理
      throw new Error(request.data.message || '获取网络流量数据失败');
    }

  } catch (err) {
      // 更详细的错误处理
      if (axios.isAxiosError(err)) {
            // 处理Axios特有的错误
            if (err.request) {
              // 请求已发出，服务器响应状态码不在2xx范围内
              console.error('获取网络流量失败，服务器返回错误:', err.request.status, err.request.data);
            } else if (err.request) {
              // 请求已发出，但没有收到响应
              console.error('获取网络流量失败，无服务器响应:', err.request);
            } else {
              // 请求设置出错
              console.error('获取网络流量失败，请求设置错误:', err.message);
            }
      } else {
            // 处理非Axios错误
            console.error('获取网络流量失败，发生意外错误:', err instanceof Error ? err.message : err);
      }
      console.error('获取网络流量失败:', err);
      // 可以选择将错误传递到上层或进行其他处理
      throw err;

  } finally {
      // 无论成功或失败都会执行的代码
      console.log('网络流量获取完成');
      // 可以在这里添加一些清理逻辑或状态更新
      // 例如：store.setLoading(false);
  }
}


// api/traffic.ts
export const getStatisticsTraffic = async () => {
  const store = useTrafficStore(pinia);
  const response = await axios.get(GET_STATISTIC_TRAFFIC_API);
  if (response.data.code === 200) {
      // 确保data是对象类型
      store.setLabelCount(response.data.data);
      console.log('流量数据:', store.getLabelCount);
      return 200; // 直接返回数据
  }else 
      return response.data.code;
}