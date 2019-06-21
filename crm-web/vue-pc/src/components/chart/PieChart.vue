<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import echarts from 'echarts'
require('echarts/theme/macarons')

export default {
  props: {
    className: {
      type: String,
      default: 'chart'
    },
    width: {
      type: String,
      default: '100%'
    },
    height: {
      type: String,
      default: '400px'
    },
    data:{
      type : Array,
    },
    name:{
      type : String,
      default :'统计'
    }
  },
  data() {
    return {
      chart: null,
      legend:[]
    }
  },
  watch: {
    data() {
      this.legend = [];
      this.data.forEach(item =>{
        this.legend.push(item.name)
      })
      this.initChart();
    }

  },
  mounted() {
    if(this.data){
      this.legend = [];
      this.data.forEach(item =>{
        this.legend.push(item.name)
      })
    }

    this.initChart()
    window.addEventListener('resize', this.__resizeHandler)
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    window.removeEventListener('resize', this.__resizeHandler)
    this.chart.dispose()
    this.chart = null
  },
  methods: {
    initChart() {
      this.chart = echarts.init(this.$el, 'macarons')
      this.chart.setOption({
        noDataLoadingOption: {
          text: '暂无数据',
          effect: 'bubble',
          effectOption: {
            effect: {
              n: 0
            }
          }
        },
        tooltip: {
          trigger: 'item',
          formatter: '{a} <br/>{b} : {c} ({d}%)'
        },
        legend: {
          left: 'center',
          bottom: '10',
          data: this.legend
        },
        calculable: true,
        series: [
          {
            name: this.name,
            type: 'pie',
            // roseType: 'radius',
            radius: [0, 120],
            center: ['50%', '40%'],
            data: this.data,
            animationEasing: 'cubicInOut',
            animationDuration: 2600
          }
        ]
      })
    }
  }
}
</script>
