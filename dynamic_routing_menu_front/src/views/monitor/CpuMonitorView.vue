<!-- src/components/CpuMonitor.vue -->
<script setup lang="ts">
import { ref, onMounted, watch } from 'vue';
import { Line } from 'vue-chartjs';
import { Chart as ChartJS, Title, Tooltip, Legend, LineElement, LinearScale, PointElement, CategoryScale } from 'chart.js';

ChartJS.register(Title, Tooltip, Legend, LineElement, LinearScale, PointElement, CategoryScale);

const props = defineProps<{
    cpuUsage: {
        usage: number,       // 转换为number
        cores: number       // 额外字段
    }
}>();

const chartData = ref({
    labels: Array(20).fill(''),
    datasets: [
        {
            label: 'CPU Usage %',
            backgroundColor: '#f87979',
            borderColor: '#f87979',
            data: Array(20).fill(0)
        }
    ]
});

const chartOptions = {
    responsive: true,
    maintainAspectRatio: false,
    scales: {
        y: {
            min: 0,
            max: 100,
            ticks: {
                stepSize: 10
            }
        }
    }
};

const updateChart = (newValue: number) => {
    if (chartData.value.datasets[0].data.length >= 20) {
        chartData.value.datasets[0].data.shift();
        chartData.value.labels.shift();
    }
    chartData.value.datasets[0].data.push(newValue);
    chartData.value.labels.push('');
};

watch(() => props.cpuUsage, (newVal) => {
    updateChart(newVal.usage);
});

onMounted(() => {
    // 初始化图表数据
    chartData.value.datasets[0].data = Array(20).fill(props.cpuUsage.usage || 0);
});
</script>

<template>
    <div class="monitor-card">
        <h3>CPU Usage: {{ cpuUsage.usage }}%</h3>
        <div class="chart-container">
            <Line :data="chartData" :options="chartOptions" />
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