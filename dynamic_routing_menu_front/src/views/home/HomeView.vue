<template>
  <div class="echarts-container">
    <div ref="networkChartRef" class="network-echarts"></div>
    <div ref="speedChartRef" class="network-speed-echarts"></div>
  </div>
  <div ref="memoryChart" class="free-echarts"></div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted, watchEffect, nextTick } from 'vue'
import * as echarts from 'echarts'
import { speedOption } from '../../components/echarts/speed/speed'
import { getStatisticsTraffic } from '../../api/traffic'
import { mapNumberToLabel } from '../../components/traffic/traffic'
import { networkOption } from '../../components/echarts/network/network'
import { useTrafficStore } from '../../store/traffic'


const store = useTrafficStore();
// 图表引用
const networkChartRef = ref<HTMLDivElement | null>(null)
const speedChartRef = ref<HTMLDivElement | null>(null)
const memoryChart = ref<HTMLDivElement | null>(null)

// 图表实例和性能观察器
let networkChart: echarts.ECharts | null = null
let speedChart: echarts.ECharts | null = null
let memoryChartInstance: echarts.ECharts | null = null
let performanceObserver: PerformanceObserver | null = null

// 流量统计数据
const trafficStats = ref<Record<number, number>>({})
const loading = ref(false)
const error = ref<string | null>(null)

// 性能数据缓存
const speedHistory: number[] = []
let lastSpeedValue = 0

// 组件挂载时初始化
onMounted(async () => {

  const intervalId = initCharts()
  window.addEventListener('resize', handleResize)
   // 确保图表实例已创建
   await nextTick()
   await fetchTrafficStats();

  // 设置定时刷新流量数据（每5分钟）
  const refreshInterval = setInterval(fetchTrafficStats, 5 * 60 * 1000)
  
  return () => {
    if (intervalId) clearInterval(intervalId)
    clearInterval(refreshInterval)
  }
})

// HomeView.vue
const fetchTrafficStats = async () => {
  try {
    loading.value = true;
    error.value = null;
    
    const res = await getStatisticsTraffic();
    if (res == 200) {
      trafficStats.value = store.getLabelCount;
      updateNetworkChart();
    }else {
       console.log("获取流量数据失败！");
    }
    
  } catch (err) {
    error.value = err instanceof Error ? err.message : '未知错误';
    //console.error('获取流量数据失败:', err);
    
    // 设置默认数据避免图表错误
    trafficStats.value = { 0: 1 }; // 默认显示一个值
    updateNetworkChart();
  } finally {
    loading.value = false;
  }
}

const updateNetworkChart = () => {
  if (!networkChart) return;
  console.log('update network!');
  
    const stats = trafficStats.value || {};
    
    const chartData = Object.entries(stats)
      .map(([label, value]) => ({
        value: Number(value) || 0, // 确保value是数字
        name: mapNumberToLabel(Number(label)) || `未知标签 ${label}`
      }))
      .filter(item => item.value > 0)
      .sort((a, b) => b.value - a.value);
    
    // 如果没有数据，显示一个默认项
    if (chartData.length === 0) {
      chartData.push({ value: 1, name: '暂无数据' });
    }
    console.log('chartData:', chartData);
    networkChart.setOption({
      series: [{
        data: chartData
      }]
    }); // 添加true参数确保完全替换
    
    // 动态调整图例
    // networkChart.setOption({
    //   legend: {
    //     type: chartData.length > 10 ? 'scroll' : 'plain',
    //     pageIconSize: 12,
    //     pageTextStyle: { color: '#333' }
    //   }
    // });
}


// 更新速度图表数据
const updateSpeedChart = (speed: number) => {
  if (!speedChart) return
  
  speedChart.setOption({
    series: [{
      data: [{ value: speed }],
      animationDuration: 300,
      animationEasing: 'cubicOut'
    }]
  })
}

// 计算当前网络速度（基于资源加载）
const calculateCurrentSpeed = (): number => {
  try {
    const resources = performance.getEntriesByType('resource') as PerformanceResourceTiming[]
    if (resources.length === 0) return lastSpeedValue

    const now = performance.now()
    const recentResources = resources.filter(r => 
      r.responseEnd > now - 1000 && r.transferSize
    )

    if (recentResources.length === 0) return lastSpeedValue

    const totalBytes = recentResources.reduce((sum, r) => sum + r.transferSize!, 0)
    const timeWindow = Math.min(1000, now - Math.min(...recentResources.map(r => r.startTime)))
    const speed = Math.round(((totalBytes * 8 / 1024) / (timeWindow / 1000)) * 100) / 100
    
    lastSpeedValue = speed
    speedHistory.push(speed)
    
    if (speedHistory.length > 100) {
      speedHistory.shift()
    }
    
    return speed
  } catch (error) {
    console.error('Error calculating current speed:', error)
    return lastSpeedValue
  }
}

// 初始化实时性能监控
const initRealtimeMonitoring = () => {
  if (typeof PerformanceObserver === 'undefined') {
    console.warn('PerformanceObserver not supported, falling back to interval')
    return window.setInterval(updateSpeedChart, 1000)
  }

  performanceObserver = new PerformanceObserver(() => {
    const speed = calculateCurrentSpeed()
    updateSpeedChart(speed)
  })

  performanceObserver.observe({ 
    entryTypes: ['resource', 'navigation'] 
  })

  return null
}

// 初始化所有图表
const initCharts = () => {
  if (networkChartRef.value) {
    networkChart = echarts.init(networkChartRef.value)
    networkChart.setOption(networkOption)
  }
  
  if (speedChartRef.value) {
    speedChart = echarts.init(speedChartRef.value)
    speedChart.setOption({
      ...speedOption,
      series: [{
        ...speedOption.series[0],
        animationDuration: 1000,
        animationEasing: 'cubicOut'
      }]
    })
  }

  if (memoryChart.value) {
    memoryChartInstance = echarts.init(memoryChart.value)
    // 这里可以初始化内存图表配置
  }
  
  const intervalId = initRealtimeMonitoring()
  updateSpeedChart(calculateCurrentSpeed())
  fetchTrafficStats()
  
  return intervalId
}

// 防抖函数
const debounce = (fn: Function, delay: number) => {
  let timer: number | null = null
  return function(this: any, ...args: any[]) {
    if (timer) clearTimeout(timer)
    timer = window.setTimeout(() => {
      fn.apply(this, args)
      timer = null
    }, delay)
  }
}

// 处理窗口大小变化
const handleResize = debounce(() => {
  networkChart?.resize()
  speedChart?.resize()
  memoryChartInstance?.resize()
}, 200)

// 组件卸载时清理
onUnmounted(() => {
  performanceObserver?.disconnect()
  networkChart?.dispose()
  speedChart?.dispose()
  memoryChartInstance?.dispose()
  window.removeEventListener('resize', handleResize)
})

// 响应式更新网络图表
watchEffect(() => {
  if (trafficStats.value) {
    updateNetworkChart()
  }
})
</script>

<style lang="css" scoped>

.echarts-container{
    width: 100%;
    height: 50vh;
    display: flex;
    justify-content: center;
    background-color: rgba(245, 248, 248, 0.117);
}

.network-echarts {
   width: 525px; 
   height: 300px;
   margin-top: 0%;
   padding: auto;
   margin-right: 10px;
   background-color: rgb(253, 253, 253);
}

.network-speed-echarts {
   width: 525px; 
   height: 300px;
   margin-top: 0%;
   display: flex;
   justify-content: center;
   align-items: center;
   padding: auto;
   background-color: rgb(255, 255, 255);
}

.free-echarts{
   width: 100%;
   height: 300px;
   background-color: azure;
}
</style>