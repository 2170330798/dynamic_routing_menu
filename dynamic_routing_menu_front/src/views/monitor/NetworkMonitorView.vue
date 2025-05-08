<script setup lang="ts">
import { Line } from 'vue-chartjs';
import { Chart as ChartJS, Title, Tooltip, Legend, LineElement, LinearScale, PointElement, CategoryScale, type ChartOptions } from 'chart.js';
import { onMounted, ref, watch } from 'vue';
import { network } from '../../components/system/websocket';

ChartJS.register(Title, Tooltip, Legend, LineElement, LinearScale, PointElement, CategoryScale);

// 响应式数据
const props = ref(network);

// 初始化图表数据
const chartData = ref({
  labels: Array.from({ length: 20 }, (_, i) => {
    const date = new Date();
    date.setSeconds(date.getSeconds() - (20 - i));
    return date.toLocaleTimeString();
  }),
  datasets: [
    {
      label: 'Download Speed (KB/s)',
      backgroundColor: '#4CAF50',
      borderColor: '#4CAF50',
      data: Array(20).fill(0),
      tension: 0.4,
      borderWidth: 2,
      pointRadius: 0
    },
    {
      label: 'Upload Speed (KB/s)',
      backgroundColor: '#2196F3',
      borderColor: '#2196F3',
      data: Array(20).fill(0),
      tension: 0.4,
      borderWidth: 2,
      pointRadius: 0
    }
  ]
});

// 图表配置
const chartOptions = ref<ChartOptions<'line'>>({
  responsive: true,
  maintainAspectRatio: false,
  animation: {
    duration: 300
  },
  scales: {
    x: {
      grid: {
        display: false
      },
      ticks: {
        maxRotation: 0,
        autoSkip: true,
        maxTicksLimit: 10
      }
    },
    y: {
      beginAtZero: true,
      title: {
        display: true,
        text: 'KB/s'
      },
      grid: {
        color: 'rgba(0, 0, 0, 0.05)'
      }
    }
  },
  plugins: {
    legend: {
      position: 'top' as const, // 明确指定为字面量类型
      labels: {
        boxWidth: 12
      }
    },
    tooltip: {
      mode: 'index' as const,
      intersect: false
    }
  }
});

// 更新图表数据
const updateChart = (download: number, upload: number) => {
  const now = new Date();
  
  // 创建新数据数组
  const newLabels = [...chartData.value.labels.slice(1), now.toLocaleTimeString()];
  const newDownloadData = [...chartData.value.datasets[0].data.slice(1), download];
  const newUploadData = [...chartData.value.datasets[1].data.slice(1), upload];

  // 更新图表数据
  chartData.value = {
    labels: newLabels,
    datasets: [
      {
        ...chartData.value.datasets[0],
        data: newDownloadData
      },
      {
        ...chartData.value.datasets[1],
        data: newUploadData
      }
    ]
  };
};

// 模拟数据更新（实际使用时替换为你的WebSocket数据）
const simulateData = () => {
  setInterval(() => {
    const download = Math.floor(Math.random() * 1000);
    const upload = Math.floor(Math.random() * 500);
    updateChart(download, upload);
  }, 1000);
};

// 监听WebSocket数据
watch(() => props.value, (newVal) => {
  if (newVal) {
    updateChart(
      Number(newVal.downloadSpeed) || 0,
      Number(newVal.uploadSpeed) || 0
    );
  }
}, { deep: true });

onMounted(() => {
  // 初始化时填充一些随机数据
  for (let i = 0; i < 20; i++) {
    updateChart(
      Math.floor(Math.random() * 800),
      Math.floor(Math.random() * 400)
    );
  }
  
  // 开始模拟数据（实际使用时注释掉这行）
  simulateData();
});
</script>

<template>
  <div class="network-monitor">
    <h3>Network Speed Monitor</h3>
    <div class="speed-display">
      <div class="download-speed">
        <span class="label">Download:</span>
        <span class="value">{{ props.downloadSpeed.toFixed(2) }} KB/s</span>
      </div>
      <div class="upload-speed">
        <span class="label">Upload:</span>
        <span class="value">{{ props.uploadSpeed.toFixed(2) }} KB/s</span>
      </div>
    </div>
    <div class="chart-wrapper">
      <Line 
        :data="chartData"
        :options="chartOptions"
        :key="chartData.labels.length"
      />
    </div>
  </div>
</template>

<style scoped>
.network-monitor {
  padding: 20px;
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
}

h3 {
  margin-top: 0;
  color: #333;
}

.speed-display {
  display: flex;
  justify-content: space-around;
  margin: 15px 0;
}

.download-speed, .upload-speed {
  padding: 8px 15px;
  border-radius: 4px;
}

.download-speed {
  background: rgba(76, 175, 80, 0.1);
  color: #4CAF50;
}

.upload-speed {
  background: rgba(33, 150, 243, 0.1);
  color: #2196F3;
}

.label {
  font-weight: bold;
  margin-right: 5px;
}

.chart-wrapper {
  height: 300px;
  position: relative;
}
</style>
