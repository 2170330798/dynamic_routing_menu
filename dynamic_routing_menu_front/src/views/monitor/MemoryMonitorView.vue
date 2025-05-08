<script setup lang="ts">
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
import { onMounted, ref, watch } from 'vue';
import { memory } from '../../components/system/websocket';

ChartJS.register(Title, Tooltip, Legend, LineElement, LinearScale, PointElement, CategoryScale);

const props = ref(memory);

// 生成带时间戳的初始标签
const generateTimeLabels = (count: number) => {
  return Array.from({ length: count }, (_, i) => {
    const date = new Date();
    date.setSeconds(date.getSeconds() - (count - i - 1));
    return date.toLocaleTimeString();
  });
};

// 计算Y轴最大值（总内存的1.2倍）
const calculateYMax = (totalGB: number) => {
  return Math.ceil(totalGB * 1.2 * 2) / 2; // 取最近的0.5倍数
};

const chartData = ref({
  labels: generateTimeLabels(20),
  datasets: [
    {
      label: 'Used Memory (GB)',
      backgroundColor: 'rgba(76, 175, 80, 0.2)',
      borderColor: '#4CAF50',
      data: Array(20).fill(0),
      tension: 0.4,
      borderWidth: 2,
      fill: {
        target: 'origin',
        above: 'rgba(76, 175, 80, 0.05)'
      }
    },
    {
      label: 'Total Memory (GB)',
      backgroundColor: 'rgba(33, 150, 243, 0.1)',
      borderColor: '#2196F3',
      borderDash: [5, 5],
      data: Array(20).fill(Number(memory.value.total) / (1024 ** 3)),
      borderWidth: 1,
      pointRadius: 0
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
      radius: 0
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
      beginAtZero: true,
      max: calculateYMax(Number(memory.value.total) / (1024 ** 3)),
      ticks: {
        stepSize: 0.5
      },
      title: {
        display: true,
        text: 'GB'
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
      intersect: false,
      callbacks: {
        label: (context) => {
          return `${context.dataset.label}: ${context.parsed.y.toFixed(2)} GB`;
        }
      }
    }
  }
});

const updateChart = (usedGB: number) => {
  const now = new Date();
  const totalGB = Number(props.value.total) / (1024 ** 3);
  
  // 更新Y轴最大值（动态适应）
  if (chartOptions.value.scales?.y) {
    chartOptions.value.scales.y.max = calculateYMax(totalGB);
  }

  // 创建新数组
  const newUsedData = [...chartData.value.datasets[0].data.slice(1), usedGB];
  const newTotalData = [...chartData.value.datasets[1].data.slice(1), totalGB];
  const newLabels = [...chartData.value.labels.slice(1), now.toLocaleTimeString()];
  
  // 更新图表数据
  chartData.value = {
    labels: newLabels,
    datasets: [
      {
        ...chartData.value.datasets[0],
        data: newUsedData
      },
      {
        ...chartData.value.datasets[1],
        data: newTotalData
      }
    ]
  };
};

const formatMemory = (bytes: number) => {
  const gb = bytes / (1024 ** 3);
  return `${gb.toFixed(2)} GB`;
};

watch(() => props.value, (newVal) => {
  const usedGB = Number(newVal.used) / (1024 ** 3);
  updateChart(usedGB);
}, { deep: true });

onMounted(() => {
  // 初始化填充数据
  const initialUsed = Array(20).fill(Number(props.value.used) / (1024 ** 3));
  const initialTotal = Array(20).fill(Number(props.value.total) / (1024 ** 3));
  
  chartData.value = {
    labels: generateTimeLabels(20),
    datasets: [
      {
        ...chartData.value.datasets[0],
        data: initialUsed
      },
      {
        ...chartData.value.datasets[1],
        data: initialTotal
      }
    ]
  };
});
</script>

<template>
  <div class="monitor-card">
    <h3>Memory Usage: {{ props.usage }}%</h3>
    <div class="memory-stats">
      <div class="stat-item total">
        <span class="label">Total:</span>
        <span class="value">{{ formatMemory(props.total) }}</span>
      </div>
      <div class="stat-item used">
        <span class="label">Used:</span>
        <span class="value">{{ formatMemory(props.used) }}</span>
      </div>
      <div class="stat-item free">
        <span class="label">Free:</span>
        <span class="value">{{ formatMemory(props.free) }}</span>
      </div>
    </div>
    <div class="chart-container">
      <Line 
        :data="chartData"
        :options="chartOptions"
        :key="chartData.labels.length + chartData.datasets[0].data.length"
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
  margin-top: 15px;
}

h3 {
  margin-top: 0;
  color: #333;
  font-size: 1.2rem;
}

.memory-stats {
  display: flex;
  gap: 15px;
  margin: 10px 0;
}

.stat-item {
  padding: 8px 12px;
  border-radius: 4px;
  font-size: 0.9rem;
}

.stat-item .label {
  font-weight: bold;
  margin-right: 5px;
}

.total {
  background: rgba(33, 150, 243, 0.1);
  color: #2196F3;
}

.used {
  background: rgba(76, 175, 80, 0.1);
  color: #4CAF50;
}

.free {
  background: rgba(158, 158, 158, 0.1);
  color: #9E9E9E;
}
</style>