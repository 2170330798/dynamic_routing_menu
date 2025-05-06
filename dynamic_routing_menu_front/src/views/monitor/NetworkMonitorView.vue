<!-- src/components/NetworkMonitor.vue -->
<script setup lang="ts">
import { Line } from 'vue-chartjs';
import { Chart as ChartJS, Title, Tooltip, Legend, LineElement, LinearScale, PointElement, CategoryScale } from 'chart.js';
import { onMounted, ref, watch } from 'vue';
import { network } from '../../components/system/system';

ChartJS.register(Title, Tooltip, Legend, LineElement, LinearScale, PointElement, CategoryScale);

const props = defineProps<{
    network: {
        uploadSpeed: number;
        downloadSpeed: number;
    };
}>();

const chartData = ref({
    labels: Array(20).fill(''),
    datasets: [
        {
            label: 'Download Speed (KB/s)',
            backgroundColor: '#4CAF50',
            borderColor: '#4CAF50',
            data: Array(20).fill(0)
        },
        {
            label: 'Upload Speed (KB/s)',
            backgroundColor: '#2196F3',
            borderColor: '#2196F3',
            data: Array(20).fill(0)
        }
    ]
});

const chartOptions = {
    responsive: true,
    maintainAspectRatio: false,
    scales: {
        y: {
            beginAtZero: true,
            title: {
                display: true,
                text: 'KB/s'
            }
        }
    }
};

const updateChart = (download: number, upload: number) => {
    if (chartData.value.datasets[0].data.length >= 20) {
        chartData.value.datasets[0].data.shift();
        chartData.value.datasets[1].data.shift();
        chartData.value.labels.shift();
    }
    chartData.value.datasets[0].data.push(download);
    chartData.value.datasets[1].data.push(upload);
    chartData.value.labels.push('');
};

console.log(network);

watch(() => props.network, (newVal) => {
    updateChart(newVal.downloadSpeed, newVal.uploadSpeed);
}, { deep: true });

onMounted(() => {
    // 初始化图表数据
    chartData.value.datasets[0].data = Array(20).fill(props.network.downloadSpeed || 0);
    chartData.value.datasets[1].data = Array(20).fill(props.network.uploadSpeed || 0);
});
</script>

<template>
    <div class="monitor-card">
        <h3>Network Speed</h3>
        <p>Download: {{ network.downloadSpeed }} KB/s</p>
        <p>Upload: {{ network.uploadSpeed }} KB/s</p>
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