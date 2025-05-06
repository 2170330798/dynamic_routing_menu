<!-- src/components/TrafficPieChart.vue -->
<script setup lang="ts">
import { computed, onMounted, ref } from 'vue';
import { useTrafficStore } from '../../store/traffic/index';
import { getStatisticsTraffic } from '../../api/traffic';
import * as echarts from 'echarts';

const trafficStore = useTrafficStore();
const chartRef = ref<HTMLDivElement>();

// 定义更严格的类型
type LabelMapping = {
    [key in 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9 | 10 | 11 | 12 | 13 | 14 | 15 | 16 | 17 | 18]: string;
};

// 映射字典
const labelMapping: LabelMapping = {
    0: 'DrDoS_DNS',
    1: 'DrDoS_LDAP',
    2: 'DrDos_MSSQL',
    3: 'DrDos_NetBIOS',
    4: 'DrDos_NTP',
    5: 'DrDos_SNMP',
    6: 'DrDos_SSDP',
    7: 'DrDos_UDP',
    8: 'Syn',
    9: 'TFTP',
    10: 'UDP-lag',
    11: 'BENIGN',
    12: 'WebDDoS',
    13: 'Portmap',
    14: 'NetBIOS',
    15: 'LDAP',
    16: 'MSSQL',
    17: 'UDP',
    18: 'Normal'
};

// 颜色映射
const colorMap = {
    'DrDoS_DNS': '#FF5722',
    'DrDoS_LDAP': '#FF9800',
    'DrDos_MSSQL': '#FFC107',
    'DrDos_NetBIOS': '#FFEB3B',
    'DrDos_NTP': '#CDDC39',
    'DrDos_SNMP': '#8BC34A',
    'DrDos_SSDP': '#4CAF50',
    'DrDos_UDP': '#009688',
    'Syn': '#F44336',
    'TFTP': '#E91E63',
    'UDP-lag': '#9C27B0',
    'BENIGN': '#2196F3',
    'WebDDoS': '#673AB7',
    'Portmap': '#3F51B5',
    'NetBIOS': '#00BCD4',
    'LDAP': '#FF5722',
    'MSSQL': '#FF9800',
    'UDP': '#FFC107',
    'Normal': '#FFF234'
};

// 安全的获取标签名称
const getLabelName = (labelId: number): string => {
    if (labelId in labelMapping) {
        return labelMapping[labelId as keyof LabelMapping];
    }
    return `Unknown (${labelId})`;
};

// 调用API获取数据
const fetchTrafficData = async () => {
    await getStatisticsTraffic();
    initChart();
};

// 计算总流量
const totalTraffic = computed(() => {
    return Object.values(trafficStore.labelCount).reduce((sum, count) => sum + count, 0);
});

// 格式化数字
const formatNumber = (num: number) => {
    return num.toLocaleString();
};

// 准备图表数据
const chartData = computed(() => {
    return Object.entries(trafficStore.labelCount)
        .map(([labelId, count]) => ({
            name: getLabelName(parseInt(labelId)),
            value: count,
            percentage: totalTraffic.value > 0 ? ((count / totalTraffic.value) * 100).toFixed(2) : '0',
            itemStyle: {
                color: colorMap[getLabelName(parseInt(labelId)) as keyof typeof colorMap] || '#9E9E9E'
            }
        }))
        .sort((a, b) => b.value - a.value);
});

// 初始化图表
const initChart = () => {
  if (!chartRef.value) return;
  
  const chart = echarts.init(chartRef.value);
  
  const option = {
    title: {
      text: '流量类型分布',
      left: 'center',
      textStyle: {
        fontSize: 18,
        fontWeight: 'bold'
      }
    },
    tooltip: {
      trigger: 'item',
      formatter: (params: any) => {
        return `${params.name}<br/>
                数量: ${formatNumber(params.value)}<br/>
                占比: ${params.data.percentage}%`;
      }
    },
    legend: {
      type: 'scroll', // 改为可滚动的图例
      orient: 'vertical',
      right: 10,
      top: 'middle',
      height: '80%',
      pageIconColor: '#666',
      pageTextStyle: {
        color: '#333'
      }
    },
    series: [
      {
        name: '流量分布',
        type: 'pie',
        radius: ['35%', '65%'], // 调整半径减少拥挤感
        center: ['40%', '50%'], // 向左移动给图例留空间
        avoidLabelOverlap: true,
        itemStyle: {
          borderRadius: 5,
          borderColor: '#fff',
          borderWidth: 2
        },
        label: {
          show: false, // 默认隐藏所有标签
          position: 'outside',
          alignTo: 'edge',
          edgeDistance: 20,
          formatter: (params: any) => {
            return parseFloat(params.data.percentage) > 10 ? // 只显示大于10%的标签
              `{b|${params.name}}\n{per|${params.data.percentage}%}` : '';
          },
          rich: {
            b: {
              fontSize: 12,
              lineHeight: 20
            },
            per: {
              color: '#666',
              fontSize: 11
            }
          }
        },
        emphasis: {
          label: {
            show: true // 鼠标悬停时显示标签
          }
        },
        labelLine: {
          show: true,
          length: 15,
          length2: 15,
          smooth: 0.2
        },
        data: chartData.value
      }
    ]
  };

  chart.setOption(option);
  window.addEventListener('resize', () => chart.resize());
};

// 组件挂载时初始化
onMounted(() => {
    fetchTrafficData();
});
</script>

<template>
    <div class="traffic-dashboard">
        <h2>Network Traffic Overview</h2>
        
        <!-- 总流量概览 -->
        <div class="summary-card">
            <div class="summary-item">
                <h3>Total Traffic</h3>
                <div class="stat-value">{{ formatNumber(totalTraffic) }}</div>
                <div class="stat-label">Packets</div>
            </div>
            <div class="summary-item">
                <h3>Attack Types</h3>
                <div class="stat-value">{{ Object.keys(trafficStore.labelCount).length }}</div>
                <div class="stat-label">Categories</div>
            </div>
        </div>
        
        <!-- 扇形图展示 -->
        <h3>Traffic Distribution by Type</h3>
        <div ref="chartRef" style="width: 100%; height: 500px;"></div>
    </div>
</template>

<style scoped>
.traffic-dashboard {
    background: white;
    border-radius: 8px;
    padding: 20px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
    margin-bottom: 20px;
    grid-column: span 3; /* 占据3列的宽度 */
    width: calc(100% - 50px); /* 减去padding */
    margin-left: 10px;
    margin-right: 10px;
}

.summary-card {
    display: flex;
    gap: 20px;
    margin-bottom: 30px;
}

.summary-item {
    flex: 1;
    padding: 20px;
    border-radius: 8px;
    text-align: center;
    background: #f8f9fa;
    border-top: 4px solid #2196F3;
    min-width: 200px; /* 确保卡片最小宽度 */
}

.stat-value {
    font-size: 24px; /* 稍微增大字体 */
    font-weight: bold;
    margin: 8px 0;
}

.stat-label {
    color: #666;
    font-size: 14px;
}

h2 {
    font-size: 1.8rem; /* 增大标题 */
    color: #333;
    border-bottom: 2px solid #eee;
    padding-bottom: 12px;
    margin-bottom: 20px;
}

h3 {
    font-size: 1.4rem; /* 增大子标题 */
    color: #444;
    margin: 25px 0 20px;
}

/* 图表容器响应式调整 */
.pie-chart-container {
    width: 100%;
    height: 550px; /* 增加高度 */
    margin-top: 20px;
}

/* 响应式布局调整 */
@media (max-width: 1200px) {
    .traffic-dashboard {
        grid-column: span 2;
    }
}

@media (max-width: 768px) {
    .traffic-dashboard {
        grid-column: span 1;
        width: calc(100% - 20px);
    }
    
    .summary-card {
        flex-direction: column;
    }
    
    .pie-chart-container {
        height: 400px;
    }
}
</style>