import axios from 'axios'
import { ref, computed } from 'vue'
import { ElMessage, ElLoading } from 'element-plus'

interface TrainingStatus {
  progress?: number
  status?: string
  metrics?: {
    accuracy?: number
    final_accuracy?: number
    loss?: number
    final_loss?: number
  }
  timestamp?: string
}

export const useWebSocket = (data: any) => {
 
    const status = ref<TrainingStatus | null>(null);
    const dialogVisible = ref(false);
    const connecting = ref(false);
    const taskId = ref(data.taskId);
    const socket = ref<WebSocket | null>(null);


    // 安全转换百分比数值
    const safePercentage = (value: number | string | null | undefined): number => {
      if (value === null || value === undefined) return 0;
      const num = typeof value === 'string' ? parseFloat(value.replace('%', '')) : Number(value);
      return Math.min(100, Math.max(0, isNaN(num) ? 0 : num));
    }

    // 计算属性
    const lossValue = computed(() => {
      const loss = (status.value?.metrics?.loss || status.value?.metrics?.final_loss);
      return Math.min(safePercentage(Number(loss)) * 100, 100);
    })

    const accuracyValue = computed(() => {
      const acc = status.value?.metrics?.accuracy || status.value?.metrics?.final_accuracy;
      return Math.min(safePercentage(Number(acc)) * 100, 100);
    })

    const statusTagType = computed(() => {
      switch (status.value?.status) {
        case '完成': return 'success'
        case '失败': return 'danger'
        case '暂停': return 'warning'
        default: return 'info'
      }
    })

    // 格式化时间
    const formatTime = (isoString?: string): string => {
      if (!isoString) return '';
      return new Date(isoString).toLocaleTimeString();
    }

    // 连接WebSocket
    const connectWebSocket = () => {
      console.log("训练数据:", data);
      try {
        connecting.value = true;
        const loadingInstance = ElLoading.service({
          lock: true,
          text: '正在启动训练服务...',
          background: 'rgba(0, 0, 0, 0.7)'
        });

        const wsUrl = taskId.value
          ? `ws://localhost:8000/ws/training/${taskId.value}/`
          : 'ws://localhost:8000/ws/training/task_123/';

        socket.value = new WebSocket(wsUrl);

        socket.value.onopen = () => {
          loadingInstance.close();
          ElMessage.success('训练连接已建立');
          axios.post("http://127.0.0.1:8000/training_model/", data)
            .catch((error: Error) => {
              ElMessage.error(`训练请求失败: ${error.message}`);
              console.error('训练请求错误:', error);
            })
        }

        socket.value.onmessage = (event: MessageEvent) => {
          try {
            const data: TrainingStatus = JSON.parse(event.data as string);
            if (data.progress) {
              data.progress = safePercentage(data.progress);
            }
            status.value = data;

            if (data.progress && data.progress >= 100) {
              setTimeout(() => {
                if (socket.value) {
                  socket.value.close();
                  ElMessage.success('训练已完成，连接已关闭');
                }
              }, 1000)
            }
          } catch (e) {
            console.error('解析消息失败:', e);
          }
        }

        socket.value.onerror = (event: Event) => {
          loadingInstance?.close();
          connecting.value = false;
          ElMessage.error(`连接错误: ${(event as ErrorEvent).message}`);
        }

        socket.value.onclose = () => {
          connecting.value = false;
          if (status.value?.progress && status.value.progress < 100) {
            ElMessage.info('训练连接已关闭');
          }
        }

      } catch (error) {
        ElMessage.error(`训练启动失败: ${(error as Error).message}`)
        console.error('训练错误:', error);
        if (socket.value) {
          socket.value.close();
        }
        connecting.value = false;
      }
    }

    // 取消处理方法
    const handleCancel = (): void => {
      dialogVisible.value = false;
      if (socket.value) {
        socket.value.close();
        socket.value = null;
      }
      connecting.value = false;
      ElMessage.info('已取消训练');
    }

    
    return {
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
      safePercentage,
      
    }
}