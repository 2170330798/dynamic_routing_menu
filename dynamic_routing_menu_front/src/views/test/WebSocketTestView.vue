<template>
    <div class="fixed-training-status">
      <h3>训练状态 (任务ID: {{ taskId }})</h3>
      <div v-if="status">
        <p>进度: {{ status.progress }}</p>
        <p>状态: {{ status.status || '训练中' }}</p>
        <div v-if="status.metrics">
          <p>损失: {{ status.metrics.loss?.toFixed(4) || status.metrics.final_loss?.toFixed(4) }}</p>
          <p>准确率: {{ status.metrics.accuracy?.toFixed(2) || status.metrics.final_accuracy?.toFixed(2) }}%</p>
        </div>
        <small>更新时间: {{ formatTime(status.timestamp) }}</small>
      </div>
      <div v-else>
        <p>等待连接训练任务...</p>
      </div>
    </div>
  </template>
  
<script>
import axios from 'axios'
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'

axios.post("http://127.0.0.1:8000/test_send_message/", { taskId: "task_123" });

export default {
    props: {
      taskId: {
        type: String,
        default: ''
      }
    },
    setup(props) {
      const status = ref(null)
      
      const formatTime = (isoString) => {
        if (!isoString) return ''
        return new Date(isoString).toLocaleTimeString()
      }
  
      const connectWebSocket = () => {
        const wsUrl = props.taskId 
          ? `ws://localhost:8000/ws/training/${props.taskId}/`
          : 'ws://localhost:8000/ws/training/task_123/'
        
        const socket = new WebSocket(wsUrl)
        
        socket.onmessage = (event) => {
          status.value = JSON.parse(event.data)
        }
        
        socket.onclose = () => {
          console.log('WebSocket连接关闭')
        }
        
        return socket
      }
  
      onMounted(() => {
        connectWebSocket()
      })
  
      return {
        status,
        formatTime
      }
    }
}
</script>
  
<style scoped>
.fixed-training-status {
    position: fixed;
    top: 20px;
    right: 20px;
    width: 300px;
    background: white;
    padding: 15px;
    border-radius: 8px;
    box-shadow: 0 2px 10px rgba(0,0,0,0.1);
    z-index: 1000;
    border: 1px solid #eee;
}
.fixed-training-status h3 {
    margin-top: 0;
    color: #2c3e50;
    border-bottom: 1px solid #eee;
    padding-bottom: 8px;
}
  
.fixed-training-status p {
  margin: 8px 0;
}
  
.fixed-training-status small {
    color: #666;
    font-size: 0.8em;
}
</style>
  