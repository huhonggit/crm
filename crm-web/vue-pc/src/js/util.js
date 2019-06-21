// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
// 例子：
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18

Date.prototype.format = function (fmt) { //author: meizz
  var o = {
    'M+': this.getMonth() + 1,                 //月份
    'd+': this.getDate(),                    //日
    'h+': this.getHours(),                   //小时
    'm+': this.getMinutes(),                 //分
    's+': this.getSeconds(),                 //秒
    'q+': Math.floor((this.getMonth() + 3) / 3), //季度
    'S': this.getMilliseconds(),             //毫秒
  }
  if (/(y+)/.test(fmt))
    fmt = fmt.replace(RegExp.$1,
      (this.getFullYear() + '').substr(4 - RegExp.$1.length))
  for (var k in o)
    if (new RegExp('(' + k + ')').test(fmt))
      fmt = fmt.replace(RegExp.$1,
        (RegExp.$1.length == 1) ? (o[k]) : (('00' + o[k]).substr(
          ('' + o[k]).length)))
  return fmt
}

// 除法函数，用来得到精确的除法结果
// 说明：javascript的除法结果会有误差，在两个浮点数相除的时候会比较明显。这个函数返回较为精确的除法结果。
// 调用：accDiv(arg1,arg2)
// 返回值：arg1除以arg2的精确结果
window.accDiv = function (arg1, arg2) {
  arg1 = arg1 ? arg1.toString() : '0'
  arg2 = arg2 ? arg2.toString() : '0'
  var t1 = 0, t2 = 0, r1, r2
  if (arg1.indexOf('.') > -1) {
    t1 = arg1.split('.')[1].length
  }
  if (arg2.indexOf('.') > -1) {
    t2 = arg2.split('.')[1].length
  }
  r1 = Number(arg1.replace('.', ''))
  r2 = Number(arg2.replace('.', ''))
  return (r1 / r2) * Math.pow(10, t2 - t1)
}

// 乘法函数，用来得到精确的乘法结果
// 说明：javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。
// 调用：accMul(arg1,arg2)
// 返回值：arg1乘以arg2的精确结果
window.accMul = function (arg1, arg2) {
  arg1 = arg1 ? arg1 : 0
  arg2 = arg2 ? arg2 : 0
  var m = 0, s1 = arg1.toString(), s2 = arg2.toString()
  try {
    m += s1.split('.')[1].length
  } catch (e) {
    console.log(e)
  }
  try {
    m += s2.split('.')[1].length
  } catch (e) {
    console.log(e)
  }
  return Number(s1.replace('.', '')) * Number(s2.replace('.', '')) /
    Math.pow(10, m)
}

// 加法函数，用来得到精确的加法结果
// 说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。
// 调用：accAdd(arg1,arg2)
// 返回值：arg1加上arg2的精确结果
window.accAdd = function (arg1, arg2) {
  arg1 = arg1 ? arg1 : 0
  arg2 = arg2 ? arg2 : 0
  var r1, r2, m
  try {
    r1 = arg1.toString().split('.')[1].length
  } catch (e) {
    r1 = 0
  }
  try {
    r2 = arg2.toString().split('.')[1].length
  } catch (e) {
    r2 = 0
  }
  m = Math.pow(10, Math.max(r1, r2))
  if (r1 > r2) {
    return (Number(arg1.toString().replace('.', '')) +
      Number(arg2.toString().replace('.', '')) * Math.pow(10, r1 - r2)) / m
  } else {
    return (Number(arg1.toString().replace('.', '')) * Math.pow(10, r2 - r1) +
      Number(arg2.toString().replace('.', ''))) / m
  }
}

//减法函数，用来得到精确的减法结果
//说明：javascript的减法结果会有误差，在两个浮点数减法的时候会比较明显。这个函数返回较为精确的减法结果。
//调用：accSub(arg1,arg2)
//返回值：arg1减法arg2的精确结果
window.accSub = function (arg1, arg2) {
  return accAdd(arg1, -arg2)
}

window.accAddAll = function () {
  var i, sum = 0
  for (i = 0; i < arguments.length; i++) {
    sum = accAdd(sum, arguments[i])
  }
  return sum
}

window.openDownload = function (verb, url, data, target) {
  var form = document.createElement('form')
  form.action = url
  form.method = verb
  form.target = target || '_self'
  if (data) {
    for (var key in data) {
      var input = document.createElement('textarea')
      input.name = key
      input.value = typeof data[key] === 'object'
        ? JSON.stringify(data[key])
        : data[key]
      form.appendChild(input)
    }
  }
  form.style.display = 'none'
  document.body.appendChild(form)
  form.submit()
}

window.forceInteger = function (data) {
  if (data) {
    // 禁止输入小数
    var tmp = data.toString()
    var startIndex = tmp.indexOf('.')
    if (startIndex !== -1) {
      tmp = tmp.substring(0, startIndex)
      return parseInt(tmp, 10)
    }
    return parseInt(tmp, 10)
  }

  return 0
}


//在input type=number禁用mousewheel，以便滚动滚轮可以改变数字
document.addEventListener('mousewheel', (e) => {
  if (e.target.nodeName === 'INPUT' && e.target.type === 'number') {
    e.preventDefault()
  }
})