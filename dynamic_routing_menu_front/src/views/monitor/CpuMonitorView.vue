<script setup lang="ts">
import { ref, onMounted, watch } from 'vue';
import { Line } from 'vue-chartjs';
import { 
  Chart as ChartJS, 
  Title, 
  Tooltip, 
  Legend, 
  LineElement, 
  LinearScale, 
  PointElement, 
  CategoryScale,
  type ChartOptions
} from 'chart.js';
import { cpu } from '../../components/system/websocket';

ChartJS.register(Title, Tooltip, Legend, LineElement, LinearScale, PointElement, CategoryScale);

const props = ref(cpu);

// 生成带时间戳的初始标签
const generateTimeLabels = (count: number) => {
  return Array.from({ length: count }, (_, i) => {
    const date = new Date();
    date.setSeconds(date.getSeconds() - (count - i - 1));
    return date.toLocaleTimeString();
  });
};

const chartData = ref({
  labels: generateTimeLabels(20),
  datasets: [
    {
      label: 'CPU Usage %',
      backgroundColor: '#f87979',
      borderColor: '#f87979',
      data: Array(20).fill(0),
      tension: 0.4, // 曲线平滑度
      borderWidth: 2,
      fill: false
    }
  ]
});

const chartOptions = ref<ChartOptions<'line'>>({
  responsive: true,
  maintainAspectRatio: false,
  animation: {
    duration: 300
  },
  elements: {
    point: {
      radius: 0 // 隐藏数据点
    }
  },
  scales: {
    x: {
      grid: {
        display: false
      },
      ticks: {
        maxRotation: 0,
        autoSkip: true,
        maxTicksLimit: 8
      }
    },
    y: {
      min: 0,
      max: 100,
      ticks: {
        stepSize: 10
      },
      grid: {
        color: 'rgba(0, 0, 0, 0.05)'
      }
    }
  },
  plugins: {
    legend: {
      position: 'top' as const,
      labels: {
        boxWidth: 12,
        usePointStyle: true
      }
    },
    tooltip: {
      mode: 'index' as const,
      intersect: false
    }
  }
});

const updateChart = (newValue: number) => {
  const now = new Date();
  
  // 创建新数组
  const newData = [...chartData.value.datasets[0].data.slice(1), newValue];
  const newLabels = [...chartData.value.labels.slice(1), now.toLocaleTimeString()];
  
  // 更新图表数据
  chartData.value = {
    labels: newLabels,
    datasets: [
      {
        ...chartData.value.datasets[0],
        data: newData
      }
    ]
  };
};

watch(() => props.value, (newVal) => {
  updateChart(Number(newVal.usage) || 0);
}, { deep: true });

onMounted(() => {
  // 初始化填充数据
  const initialData = Array(20).fill(Number(props.value.usage) || 0);
  chartData.value = {
    labels: generateTimeLabels(20),
    datasets: [
      {
        ...chartData.value.datasets[0],
        data: initialData
      }
    ]
  };
});
</script>

<template>
  <div class="monitor-card">
    <h3>CPU Usage: {{ props.usage }}%</h3>
    <div class="chart-container">
      <Line 
        :data="chartData"
        :options="chartOptions"
        :key="chartData.labels.length"
      />
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
  height: 300px;
  position: relative;
}

h3 {
  margin-top: 0;
  color: #333;
  font-size: 1.2rem;
}
</style>