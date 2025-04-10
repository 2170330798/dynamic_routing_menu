<template>

  <div ref="chartRef" class="serial-box"></div>
  
</template>
 
<script lang="ts" setup>
import { ref, onMounted } from 'vue';
import echarts from '../../components/echarts/echarts';
// 基于准备好的dom，初始化ECharts实例
const chartRef = ref<HTMLDivElement | null>(null);

onMounted(() => {
  if (!chartRef.value) return;
  
  const myChart = echarts.init(chartRef.value);
  myChart.setOption({
    xAxis: { 
      data: ['衬衫', '羊毛衫', '雪纺衫', '裤子', '高跟鞋', '袜子'] 
    },
    yAxis: {},
    series: [{ 
      name: '销量', 
      type: 'bar', 
      data: [5, 20, 36, 10, 10, 20] 
    }]
  });
});

</script>

<style lang="css">
.serial-box {
   width: 100%;
   height: 90vh;
   display: flex;
   justify-content: center;
   align-items: center;
   background-color: antiquewhite;
}



</style>
 
