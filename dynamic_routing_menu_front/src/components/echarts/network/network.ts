

export const networkOption = {
  tooltip: {
    trigger: 'item'
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
      name: 'NetWork Flow',
      type: 'pie',
      center: ['70%', '60%'],
      radius: ['40%', '70%'],
      avoidLabelOverlap: false,
      itemStyle: {
        borderRadius: 10,
        borderColor: '#fff',
        borderWidth: 2
      },
      label: {
        show: false,
        position: 'center'
      },
      emphasis: {
        label: {
          show: true,
          fontSize: 40,
          fontWeight: 'bold'
        }
      },
      labelLine: {
        show: true
      },
      data: []
    }
  ]
};