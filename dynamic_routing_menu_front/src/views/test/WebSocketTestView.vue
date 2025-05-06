<template>
  <el-button plain @click="dialogVisible = true">
    开始训练
  </el-button>

  <el-dialog class="train-dialog" v-model="dialogVisible" width="500" draggable align-center>
    <h2 class="train-title">训练进度 [任务ID: {{ taskId }}]</h2>
    <div v-if="status">
      <!--训练信息-->
      <span class="train-item-label">训练进度</span>
      <el-progress :percentage="safePercentage(status.progress)" />
      <span class="train-item-label">训练损失</span>
      <el-progress :percentage="Number(lossValue.toFixed(2))" />
      <span class="train-item-label">训练准确率</span>
      <el-progress :percentage="Number(accuracyValue.toFixed(2))" />
      
      <!-- 状态和时间 -->
      <div class="status-info">
        <el-tag :type="statusTagType">{{ status.status || '训练中' }}</el-tag>
        <small class="update-time">更新时间: {{ formatTime(status.timestamp) }}</small>
      </div>
    </div>
    <div v-else class="waiting-connection">
      <el-icon class="loading-icon"><Loading /></el-icon>
      <p>等待连接训练任务...</p>
    </div>
    
    
    <template #footer>
      <div class="train-dialog-footer">
        <el-button @click="handleCancel">取消训练</el-button>
        <el-button type="primary" @click="connectWebSocket" :loading="connecting">
          开始训练
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>


<script setup lang="ts">
import { Loading } from '@element-plus/icons-vue'
import { useWebSocket } from "../../components/websocket/websocket"
import { useTrainStore } from '../../store/train';
import pinia from '../../store';


const data = {
    'csvSourcePath': 'F:\\PythonWeb\\myDjango\\app\\dataset\\train_dataset_03.csv', 
    'csvDestPath': '', 
    'labelColumn': 'Label', 
    'batchSize': 32, 
    'rows': 3000, 
    'numEpochs': 10, 
    'lr': '0.001', 
    'stepSize': 0, 
    'gamma': 0, 
    'modelName': 'model-18', 
    'frame': 2, 
    'modelSavePath': 'F:\\PythonWeb\\myDjango\\app\\model\\rnn_models', 
    'isSaveCsvData': false, 
    'modelVersion': 'v1.0.0', 
    'modelId': 18, 
    'taskId': 'task_85781393', 
    'modelPath': '', 
    'patience': 10, 
    'initMode': 0, 
    'factor': '0.5', 
    'weightDecay': '1e-5', 
    'validateMode': 0
}

const store = useTrainStore(pinia);

store.setTrainData(data);

// 使用组合式函数
const {
  taskId,
  status,
  dialogVisible,
  connecting,
  accuracyValue,
  lossValue,
  statusTagType,
  formatTime,
  handleCancel,
  connectWebSocket,
  safePercentage

} = useWebSocket(store.getTrainData);
// export default {

//   setup(props) {
//     const status = ref(null);
//     const dialogVisible = ref(false);
//     const connecting = ref(false);
//     const taskId = train_data.taskId;
//     const socket = ref(null);
//     // 修改safePercentage方法确保返回有效数值
//     const safePercentage = (value) => {
//       if (value === null || value === undefined) return 0
//       const num = typeof value === 'string' ? parseFloat(value.replace('%', '')) : Number(value)
//       return Math.min(100, Math.max(0, isNaN(num) ? 0 : num))
//     }

//     const lossValue = computed(() => {
//       const loss = status.value?.metrics?.loss || status.value?.metrics?.final_loss
//       return loss ? loss.toFixed(4) : '0.0000'
//     })

//     // 计算属性 - 确保返回数值且在0-100范围内
//     const accuracyValue = computed(() => {
//       const acc = status.value?.metrics?.accuracy || status.value?.metrics?.final_accuracy;
//       return acc;
//     })

//     const lossPercentage = computed(() => {
//       const loss = status.value?.metrics?.loss || status.value?.metrics?.final_loss;
//       return loss;
//     })

//     const statusTagType = computed(() => {
//       switch(status.value?.status) {
//         case '完成': return 'success'
//         case '失败': return 'danger'
//         case '暂停': return 'warning'
//         default: return 'info'
//       }
//     })
    
//     // 方法
//     const formatTime = (isoString) => {
//       if (!isoString) return '';
//       return new Date(isoString).toLocaleTimeString();
//     }
    
//     // const progressColor = (percentage) => {
//     //   percentage = Number(percentage)
//     //   if (percentage >= 90) return '#67C23A'
//     //   if (percentage >= 70) return '#409EFF'
//     //   if (percentage >= 50) return '#E6A23C'
//     //   return '#F56C6C'
//     // }
    
//     // const accuracyColor = (percentage) => {
//     //   percentage = Number(percentage)
//     //   if (percentage >= 90) return '#67C23A'
//     //   if (percentage >= 70) return '#409EFF'
//     //   if (percentage >= 50) return '#E6A23C'
//     //   return '#F56C6C'
//     // }
    
//     // const lossColor = (percentage) => {
//     //   percentage = Number(percentage)
//     //   if (percentage >= 90) return '#67C23A'
//     //   if (percentage >= 70) return '#409EFF'
//     //   if (percentage >= 50) return '#E6A23C'
//     //   return '#F56C6C'
//     // }
    

    
//     const connectWebSocket = async() => {
      
     
//       try {
//         connecting.value = true;
//         const loadingInstance = ElLoading.service({
//           lock: true,
//           text: '正在启动训练服务...',
//           background: 'rgba(0, 0, 0, 0.7)'
//         });
            
//         const wsUrl = taskId 
//           ? `ws://localhost:8000/ws/training/${taskId}/`
//           : 'ws://localhost:8000/ws/training/task_123/'
        
//         socket.value = new WebSocket(wsUrl)

//         socket.value.onopen = () => {
//           loadingInstance.close()
//           ElMessage.success('训练连接已建立')
//           axios.post("http://127.0.0.1:8000/training_model/", train_data)
//             .catch(error => {
//               ElMessage.error(`训练请求失败: ${error.message}`)
//               console.error('训练请求错误:', error)
//             })
//         }

//         socket.value.onmessage = (event) => {
//           try {
//             const data = JSON.parse(event.data)
//             // 确保progress是数值类型
//             if (data.progress) {
//               data.progress = safePercentage(data.progress)
//             }
//             status.value = data
            
//             // 新增：当进度达到100%时关闭连接
//             if (data.progress >= 100) {
//               setTimeout(() => {
//                 if (socket.value) {
//                   socket.value.close()
//                   ElMessage.success('训练已完成，连接已关闭')
//                 }
//               }, 1000) // 延迟1秒关闭，确保最后一条消息处理完成
//             }
//           } catch (e) {
//             console.error('解析消息失败:', e)
//           }
//         }
        
//         socket.value.onerror = (error) => {
//           loadingInstance?.close();
//           connecting.value = false;
//           ElMessage.error(`连接错误: ${error.message}`);
//         }
        
//         socket.value.onclose = () => {
//           connecting.value = false;
//           // 只有当不是主动关闭时才显示消息
//           if (status.value?.progress < 100) {
//             ElMessage.info('训练连接已关闭');
//           }
//         }
        
//         return socket.value;

//       } catch (error) {
//         loadingInstance?.close()
//         connecting.value = false
//         ElMessage.error(`训练启动失败: ${error.message}`)
//         console.error('训练错误:', error)
//         if (socket) socket.close()
//         throw error
//       }
//     }

//     // 新增取消处理方法
//     const handleCancel = () => {
//       // 关闭对话框
//       dialogVisible.value = false;
      
//       setTimeout(() => {
//         if (socket.value) {
//           socket.value.close();
//           socket.value = null;
//           status.value = null;
//           ElMessage.success('已取消训练');
//         }
//       }, 1000) // 延迟1秒关闭，确保最后一条消息处理完成
//       // 重置状态
//       connecting.value = false;
//     }

//     return {
//       taskId,
//       status,
//       dialogVisible,
//       connecting,
//       accuracyValue,
//       lossValue,
//       lossPercentage,
//       statusTagType,
//       formatTime,
//       //progressColor,
//       //accuracyColor,
//       //lossColor,
//       handleCancel,
//       connectWebSocket,
//       Loading,
//       safePercentage
//     }
//   }
// }
</script>

<style lang="css">
/* 保持原有的样式不变 */
.train-dialog {
  width: 800px;
  height: 400px;
  text-align: center;
  /* background-color: antiquewhite; */
}

.train-title {
  margin-bottom: 20px;
  color: #409EFF;
}

.status-info {
  margin-top: 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.update-time {
  color: #909399;
}

.waiting-connection {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
}

.train-item-label{
   width: 50px;
   height: 25px;
   color: black;
   text-align: left;
   margin-right: 90%;
   /* background-color: aliceblue; */
}

.loading-icon {
  animation: rotating 2s linear infinite;
  margin-bottom: 10px;
  font-size: 24px;
  color: #409EFF;
}

@keyframes rotating {
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
}
</style>