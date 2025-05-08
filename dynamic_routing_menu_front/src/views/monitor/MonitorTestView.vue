<script lang="ts" setup>
import { ref } from 'vue';
import { connectWebSocket, 
    type SystemMetrics,
    cpu,
    memory,
    network,
    disconnectWebSocket
} from '../../components/system/websocket';

const metrics = ref<SystemMetrics>();



// const startMonitor = () => {
        
//         connectWebSocket('ws://localhost:8080/ws', '/topic/system-metrics', (newMetrics) => {
//           metrics.value = newMetrics;
//         });
        
//         // 触发后端处理
//         //fetch('http://localhost:8080/system/info', { method: 'POST' });
// };

const startMonitor = () => {
  connectWebSocket("ws://localhost:8000/ws/system-metrics/", (newMetrics) => {
    metrics.value = newMetrics;
    console.log("Received metrics:", metrics);
  });
}

const stoptMonitor = () => {
     disconnectWebSocket();
}
</script>

<template>
  <div>
    <h3>CPU使用率: {{cpu.usage}}%</h3>
    <h3>内存使用: {{ memory.free / memory.total }}%</h3>
    <h3>网络: ↑{{ network.uploadSpeed }} KB/s ↓{{ network.downloadSpeed }} KB/s</h3>
  </div>
  <el-button type="primary" @click="startMonitor">开始处理</el-button>
  <el-button type="primary" @click="stoptMonitor">暂停</el-button>
</template>