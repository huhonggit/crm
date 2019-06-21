
import Vue from 'vue'
import moment from 'moment'


Vue.filter('sexFilter', function (sex) {
  if (sex == 0) {
    return '未知'
  }
  if (sex == 1) {
    return '男'
  }
  if (sex == 2) {
    return '女'
  }
  return '其他'
})

Vue.filter('fToy', function (fen) {
  if (!fen) {
    return 0
  }
  fen = accDiv(fen, 10000).toString() //分到元
  // 保留两位小数
  return parseFloat(fen.replace(/^(.+)\.(\d{2})(.+)$/, '$1.$2'))
})

Vue.filter('fToyNotFixed', function (fen) {
  if (!fen) {
    return 0
  }
  fen = accDiv(fen, 10000)//里到元
  return fen
})

// 返回年龄（只包含年数）
Vue.filter('age', function (birth) {
  var b = moment(new Date(birth))
  return moment().diff(b, 'year')
})

Vue.filter('howManyYears', (val) => {
  let today = moment(new Date)
  let date = moment(val)
  if (!date.isValid()) {
    return
  }
  let year = today.diff(date, 'year')
  return year + '年' + (today.diff(date, 'month') % 12) + '个月'
})

Vue.filter('diffYearFilter', function (date) {
  if (date) {
    return moment().diff(date, 'years')
  }
  return ''
})

Vue.filter('formatDate', function (value, format) {
  if (value && format) {
    let instance = moment(value)
    if (instance.isValid()) {
      return instance.format(format)
    } else {
      return '--'
    }
  } else {
    return '--'
  }
})

Vue.filter('number', function (value, len) {
  try {
    if (len === undefined) {
      return value
    }
    var num = parseFloat(parseFloat(value).toFixed(parseInt(len)))
    if (isNaN(num)) {
      return value
    } else {
      return num
    }
  } catch (e) {
    return value
  }
})

Vue.filter('formatDateWeb', function (value) {
  let date = new Date(value)
  return moment(date).format('YYYY-MM-DD HH:mm')
})

Vue.filter('secondToStr', function (second) {
    if(second===null || second===undefined){
      return '0s'
    }
    var h = parseInt(second / 3600);
    var m = parseInt((second % 3600) / 60);
    var s = parseInt(second % 60);
    console.log(h,m,s)
    if (h > 0) {
      return h + "h" + m + "min" + s + "s"
    }
    else if (m > 0) {
      return m + "min" + s + "s"
    }
    return s+"s"
})

Vue.filter('fileSizeToString',function(size){
  if(size >= 1000000){
    return ((size/1000000.0).toFixed(1))+'M'
  }else if(size >= 100){
    return ((size/1000.0).toFixed(1)) + 'k'
  }else {
    return ((size/1000.0).toFixed(3)) + 'k'
  }
})
