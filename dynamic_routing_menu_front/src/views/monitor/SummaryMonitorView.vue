<script setup lang="ts">
import { ref, onMounted, toValue, onUnmounted } from 'vue';
import { connectWebSocket, 
    type SystemMetrics,
    cpu,
    memory,
    network,
    disconnectWebSocket 
} from '../../components/system/system';
import SystemMonitorView from './SystemMonitorView.vue';
import CpuMonitorView from './CpuMonitorView.vue';
import MemoryMonitorView from './MemoryMonitorView.vue';
import NetworkMonitorView from './NetworkMonitorView.vue';
import NetworkFlowMonitorView from './NetworkFlowMonitorView.vue';

const isLoading = ref(true);
const systemData = ref<{
    cpuUsage: { usage: number, cores: number};
    memory: { usage: number; total: number; used: number; free: number };
    network: { uploadSpeed: number; downloadSpeed: number };
} | null>(null);

const metrics = ref<SystemMetrics>();

onMounted(() => {
    // 获取监控数据
    connectWebSocket('ws://localhost:8080/ws', '/topic/system-metrics', (newMetrics) => {
          metrics.value = newMetrics;
    });
    
    // 触发后端处理
    fetch('http://localhost:8080/system/info', { method: 'POST' });

    // 转换为普通值
    systemData.value = {
        cpuUsage: toValue(cpu),
        memory: toValue(memory),
        network: toValue(network) // 使用 toValue 解构 network
    };
    
    isLoading.value = false;
});


onUnmounted(()=>{
    disconnectWebSocket();
})
</script>

<template>
    <div class="monitor-dashboard">
        <h1>System Monitoring Dashboard</h1>
        
        <template v-if="!isLoading && systemData">
            <SystemMonitorView
                :cpu-usage="systemData.cpuUsage"
                :memory="systemData.memory"
                :network="systemData.network"
            />
            
            <div class="monitors-grid">
                <CpuMonitorView :cpu-usage="systemData.cpuUsage" />
                <MemoryMonitorView :memory="systemData.memory" />
                <NetworkMonitorView :network="systemData.network" />
                <NetworkFlowMonitorView />
            </div>
        </template>
        
        <div v-else class="loading-message">
            <span>Loading system data...</span>
        </div>
    </div>
</template>

<style>
body {
    font-family: Arial, sans-serif;
    margin: 0;
    padding: 20px;
    background-color: #f5f5f5;
}

.monitor-dashboard {
    max-width: 1200px;
    height: 70%;
    margin: 0 auto;
}

h1 {
    text-align: center;
    color: #333;
    margin-bottom: 30px;
}

.monitors-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(350px, 1fr));
    gap: 20px;
}

.loading-message {
    text-align: center;
    padding: 40px;
    color: #666;
    font-size: 1.2em;
}
</style>