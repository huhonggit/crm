<template>
  <div :class="className" :style="{height:height,width:width}" />
</template>

<script>
import echarts from 'echarts'
require('echarts/theme/macarons') // echarts theme

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
      default: '100%'
    },
    autoResize: {
      type: Boolean,
      default: true
    },
    chartData: {
      type: Object,
      required: true
    }
  },
  data() {
    return {
      chart: null,
      sidebarElm: null
    }
  },
  watch: {
    chartData: {
      deep: true,
      handler(val) {
        this.initChart()
      }
    }
  },
  computed:{
    series() {
      if (this.chartData['lineName']){
        let result = [];
        this.chartData.lineName.forEach(item =>{
          let temp = {
            name: item,
            type: this.chartData.type[item],
            data: this.chartData.data[item]
          }
          if (this.chartData.yAxisIndex[item] ){
            temp.yAxisIndex=this.chartData.yAxisIndex[item]
          }

          result.push(temp);
        })
        return result;
      }else {
        return [];
      }

    }
  },
  mounted() {
    if(this.chartData['lineName']){
      this.initChart()
    }
    this.sidebarElm = document.getElementsByClassName('sidebar-container')[0]
    this.sidebarElm && this.sidebarElm.addEventListener('transitionend', this.sidebarResizeHandler)
  },
  beforeDestroy() {
    if (!this.chart) {
      return
    }
    if (this.autoResize) {
      window.removeEventListener('resize', this.__resizeHandler)
    }

    this.sidebarElm && this.sidebarElm.removeEventListener('transitionend', this.sidebarResizeHandler)

    this.chart.dispose()
    this.chart = null
  },
  methods: {
    sidebarResizeHandler(e) {
      if (e.propertyName === 'width') {
        this.__resizeHandler()
      }
    },
    initChart() {
      this.chart = echarts.init(this.$el, 'macarons')
      this.chart.setOption({
        grid: {
          left: 25,
          right: 15,
          bottom: 5,
          top: 5,
          containLabel: true
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          }
        },
        legend: {
          data: this.chartData.lineName
        },
        xAxis: [
          {
            type: 'category',
            data: this.chartData.xName,
            axisPointer: {
              type: 'shadow'
            }
          }
        ],
        yAxis: [
          {
            type: 'value',
            min: 0,
            axisLabel: {
              formatter: '{value}'
            }
          },
          {
            type: 'value',
            axisLabel: {
              formatter: '{value}'
            }
          }
        ],
        series: this.series
      })
    }
  }
}
</script>
