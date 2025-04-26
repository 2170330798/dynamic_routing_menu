// src/components/echarts/speed/speed.ts
export const speedOption = {
    tooltip: {
      formatter: '{a} <br/>{b} : {c}kbps'
    },
    legend: {
        orient: 'vertical',      // 垂直排列
        left: 'left',           // 靠左对齐
        top: '2%',              // 距离顶部2%
        align: 'left',          // 文字左对齐
        itemGap: 10,            // 图例项之间的间隔
        itemWidth: 25,          // 图例标记的宽度
        itemHeight: 14,         // 图例标记的高度
        textStyle: {
          width: 100,           // 设置文字固定宽度（可选，防止换行）
          overflow: 'truncate'  // 文字过长时显示省略号
        }
    },
    series: [
      {
        name: 'Network Speed',
        type: 'gauge',
        min: 0,
        max: 1000, // 根据你的实际需求调整最大值
        progress: {
          show: true,
          width: 16
        },
        axisLine: {
          lineStyle: {
            width: 18
          }
        },
        axisTick: {
          distance: -30,
          length: 8,
          lineStyle: {
            color: 'white',
            width: 2
          }
        },
        splitLine: {
          distance: -30,
          length: 10,
          lineStyle: {
            color: "#fff",
            width: 4
          }
        },
        axisLabel: {
          distance: 25,
          color: '#999',
          fontSize: 12
        },
        detail: {
          valueAnimation: true,
          formatter: '{value}kbps',
          color: 'inherit',
          fontSize: 20,
          offsetCenter: [0, '90%']
        },
        data: [
          {
            value: 0,
            name: 'LOAD SPEED'
          }
        ]
      }
    ]
  };
  