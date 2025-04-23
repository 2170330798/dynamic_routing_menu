<template>
    <div>
      <progress :value="progress" max="100"></progress>
      <span>{{ progress }}%</span>
      <button @click="startLongProcess">开始处理</button>
    </div>
  </template>
  
  <script lang="ts">
  import { defineComponent, onBeforeUnmount, ref } from 'vue';
  import { connectWebSocket, disconnectWebSocket } from '../../components/progress/progress';
  
  export default defineComponent({
    setup() {
      const progress = ref(0);
  
      const startLongProcess = () => {
        progress.value = 0;
        // 连接WebSocket
        connectWebSocket('ws://localhost:8080/ws', '/topic/progress', (newProgress) => {
          progress.value = newProgress;
        });
        
        // 触发后端处理
        fetch('http://localhost:8080/progress/api/start_process', { method: 'POST' });
      };
  
      onBeforeUnmount(() => {
        disconnectWebSocket();
      });
  
      return { progress, startLongProcess };
    }
  });
</script>
  
  <style scoped>
  .progress-container {
    width: 100%;
    max-width: 1000px;
    height: 100px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    gap: 10px;
    background-color: beige;
    padding: 20px;
    margin: 0 auto;
  }
  
  progress {
    width: 80%;
    height: 20px;
  }
  
  button {
    padding: 8px 16px;
    background-color: #4CAF50;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
  }
  
  button:disabled {
    background-color: #cccccc;
    cursor: not-allowed;
  }
  </style>