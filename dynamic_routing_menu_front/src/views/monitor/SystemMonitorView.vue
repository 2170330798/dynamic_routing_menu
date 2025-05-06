<!-- src/components/SystemStats.vue -->
<script setup lang="ts">
import { computed } from 'vue';
import { memory, network } from '../../components/system/system';
const props = withDefaults(defineProps<{
    cpuUsage: {
        usage: number,       // 转换为number
        cores: number        // 额外字段
    };
    memory: {
        usage: number;
        total: number;
        used: number;
        free: number;
    };
    network: {
        uploadSpeed: number;
        downloadSpeed: number;
    };
}>(), {
    cpuUsage: () => ({
        usage: 0,       // 转换为number
        cores: 0        // 额外字段
    }),
    memory: () => ({
        usage: 0,
        total: 0,
        used: 0,
        free: 0,
    }),
    network: () => ({
        uploadSpeed: 0,
        downloadSpeed: 0,
    }),
});

const formatMemory = (bytes: number) => {
    const gb = bytes / (1024 * 1024 * 1024);
    return `${gb.toFixed(2)} GB`;
};

const cpuStatus = computed(() => {
    if (props.cpuUsage.usage > 80) return 'danger';
    if (props.cpuUsage.usage > 60) return 'warning';
    return 'success';
});

const memoryStatus = computed(() => {
    if (props.memory.usage > 80) return 'danger';
    if (props.memory.usage > 60) return 'warning';
    return 'success';
});

console.log(memory);
console.log(network);
// 从 props.network 中解构出 uploadSpeed 和 downloadSpeed
const { uploadSpeed, downloadSpeed } = props.network;
</script>

<template>
    <div class="stats-card">
        <h2>System Overview</h2>
        <div class="stats-grid">
            <div class="stat-item" :class="cpuStatus">
                <h3>CPU</h3>
                <div class="stat-value">{{ cpuUsage.usage }}%</div>
                <div class="stat-label">Usage</div>
            </div>
            <div class="stat-item" :class="memoryStatus">
                <h3>Memory</h3>
                <div class="stat-value">{{ memory.usage }}%</div>
                <div class="stat-label">Usage</div>
                <div class="stat-detail">Used: {{ formatMemory(memory.used) }}</div>
                <div class="stat-detail">Total: {{ formatMemory(memory.total) }}</div>
            </div>
            <div class="stat-item">
                <h3>Network</h3>
                <div class="stat-value">{{ downloadSpeed }} KB/s</div>
                <div class="stat-label">Download</div>
                <div class="stat-value">{{ uploadSpeed }} KB/s</div>
                <div class="stat-label">Upload</div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.stats-card {
    background: white;
    border-radius: 8px;
    padding: 20px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    margin-bottom: 20px;
}

.stats-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
    gap: 20px;
}

.stat-item {
    padding: 15px;
    border-radius: 8px;
    text-align: center;
    background: #f8f9fa;
}

.stat-item.success {
    border-top: 4px solid #4CAF50;
}

.stat-item.warning {
    border-top: 4px solid #FFC107;
}

.stat-item.danger {
    border-top: 4px solid #F44336;
}

.stat-value {
    font-size: 24px;
    font-weight: bold;
    margin: 10px 0;
}

.stat-label {
    color: #666;
    font-size: 14px;
}

.stat-detail {
    font-size: 12px;
    color: #777;
    margin-top: 5px;
}
</style>