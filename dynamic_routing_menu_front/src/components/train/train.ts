
export interface TrainData{
    
    file: string;
    databse: string;
    frameId: number;
    frameName: string;

}

export interface TRAIN{
      csvSourcePath: string;//训练数据集csv文件路径
      csvDestPath: string;//保存打乱数据集csv文件路径
      labelColumn: string;//Label标签列
      batchSize: number;//训练数据批次大小
      rows: number;//读取csv多少行
      numEpochs: number;//训练次数
      lr: number;//学习率
      stepSize: number;//动态更新学习率的步长
      gamma: number;//学习率衰减比率
      modelId: number;//模型ID
      modelName: string;//模型名字
      modelVersion: string;//模型版本
      frameId: number;//框架ID
      modelSavePath: string;//模型保存路径
      isSaveCsvPath: boolean; //是否保存打乱的数据集
      taskId: string; //任务进程ID
}

export const trainDataItem: TRAIN = {
    csvSourcePath: "",
    csvDestPath: "",
    labelColumn: "",
    batchSize: 0,
    rows: 0,
    numEpochs: 0,
    lr: 0,
    stepSize: 0,
    gamma: 0,
    modelName: "",
    frameId: 0,
    modelSavePath: "",
    isSaveCsvPath: false,
    modelVersion: "",
    modelId: 0,
    taskId: ""
}

// 在组件外部定义常量
export const INITIAL_TRAIN_DATA: TRAIN = {
    csvSourcePath: "",
    csvDestPath: "",
    labelColumn: "",
    batchSize: 0,
    rows: 0,
    numEpochs: 0,
    lr: 0,
    stepSize: 0,
    gamma: 0,
    modelName: "",
    frameId: 0,
    modelSavePath: "",
    isSaveCsvPath: false,
    modelVersion: "",
    modelId: 0,
    taskId: ""
};


// src/utils/websocket.ts
import { ref } from 'vue'

interface TrainingMessage {
  status: 'started' | 'training' | 'epoch_complete' | 'early_stopping' | 'completed'
  message: string
  epoch?: number
  total_epochs?: number
  progress?: number
  batch_progress?: number
  batch?: number
  total_batches?: number
  loss?: number
  accuracy?: number
  model_path?: string
}

export const useTrainingWebSocket = (taskId: string) => {
  const socket = ref<WebSocket | null>(null)
  const messages = ref<TrainingMessage[]>([])
  const connectionStatus = ref<'disconnected' | 'connecting' | 'connected'>('disconnected')
  
  const connect = () => {
    const protocol = window.location.protocol === 'https:' ? 'wss://' : 'ws://'
    const host = window.location.host
    const url = `${protocol}${host}/ws/training/${taskId}/`
    
    connectionStatus.value = 'connecting'
    socket.value = new WebSocket(url)
    
    socket.value.onopen = () => {
      connectionStatus.value = 'connected'
      console.log('WebSocket connected')
    }
    
    socket.value.onmessage = (event) => {
      try {
        const data: TrainingMessage = JSON.parse(event.data)
        messages.value.push(data)
        console.log('Received message:', data)
      } catch (error) {
        console.error('Error parsing WebSocket message:', error)
      }
    }
    
    socket.value.onclose = () => {
      connectionStatus.value = 'disconnected'
      console.log('WebSocket disconnected')
    }
    
    socket.value.onerror = (error) => {
      console.error('WebSocket error:', error)
      connectionStatus.value = 'disconnected'
    }
  }
  
  const disconnect = () => {
    if (socket.value) {
      socket.value.close()
      socket.value = null
    }
  }
  
  return {
    socket,
    messages,
    connectionStatus,
    connect,
    disconnect
  }
}
