<!-- src/components/MemoryMonitor.vue -->
<script setup lang="ts">
import { Doughnut } from 'vue-chartjs';
import { Chart as ChartJS, ArcElement, Tooltip, Legend } from 'chart.js';
import { computed } from 'vue';
import { memory } from '../../components/system/system';

ChartJS.register(ArcElement, Tooltip, Legend);

const props = defineProps<{
    memory: {
        total: number;
        used: number;
        free: number;
        usage: number;
    };
}>();

const chartData = computed(() => ({
    labels: ['Used', 'Free'],
    datasets: [
        {
            backgroundColor: ['#f87979', '#4CAF50'],
            data: [props.memory.used, props.memory.free]
        }
    ]
}));

const chartOptions = {
    responsive: true,
    maintainAspectRatio: false
};

const formatMemory = (bytes: number) => {
    const gb = bytes / (1024 * 1024 * 1024);
    return `${gb.toFixed(2)} GB`;
};

console.log('memory:',memory);
</script>

<template>
    <div class="monitor-card">
        <h3>Memory Usage: {{ memory.usage }}%</h3>
        <p>Total: {{ formatMemory(memory.total) }}</p>
        <p>Used: {{ formatMemory(memory.used) }}</p>
        <p>Free: {{ formatMemory(memory.free) }}</p>
        <div class="chart-container">
            <Doughnut :data="chartData" :options="chartOptions" />
        </div>
    </div>
</template>

<style scoped>
.monitor-card {
    background: white;
    border-radius: 8px;
    padding: 20px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    margin-bottom: 20px;
}

.chart-container {
    height: 200px;
    position: relative;
}
</style>