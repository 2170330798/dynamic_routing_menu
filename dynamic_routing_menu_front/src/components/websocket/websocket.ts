// src/utils/websocket.ts
import { ref } from 'vue'

interface TrainingMessage {
  status: 'started' | 'training' | 'epoch_summary' | 'early_stopping' | 'completed'
  message: string
  epoch?: number
  total_epochs?: number
  loss?: number
  accuracy?: number
  progress?: number
  timestamp?: string
}

export function useTrainingWebSocket(taskId: string) {
  const messages = ref<TrainingMessage[]>([])
  const connectionStatus = ref<'disconnected' | 'connecting' | 'connected'>('disconnected')
  const socket = ref<WebSocket | null>(null)
  
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
    messages,
    connectionStatus,
    connect,
    disconnect
  }
}