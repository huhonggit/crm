/**
 * 错误处理函数,发送报错邮件
 */
import $ from 'jquery'
import 'stackframe'

var mouseDownHistory = []
const isLocal = /(\d+\.\d+|localhost)/.test(location.host)

import ErrorStackParser from 'error-stack-parser'
import _ from 'lodash'
import moment from 'moment'

// 存储最近3次的mousedown历史，方便排查js错误
import Vue from 'vue'

const errorHandler = function (err) {
  if (err instanceof Error && !window.location.port) {
    console.error(err)
    console.error(err.toString())
    // 在控制台也进行打印，方便排错
    console.error('开始发送错误')
    $.ajax({
      type: 'POST',
      url: '/javascript-errors',
      contentType: 'application/json',
      data: JSON.stringify({
        errorUrl: window.location.href,
        errorMessage: err.toString(),
        richErrorMessage: `<b>发生时间：</b>${moment().format(
          'YYYY-MM-DD HH:mm:ss:SSS',
        )}<br>
          ${err.toString()}<br />
          <font color="red">点击历史:</font><br />
          <div style="font-size:12px;">${_.cloneDeep(mouseDownHistory).
          reverse().
          join(' <br /> ')}</div>`,
        stackTrace: ErrorStackParser.parse(err),
      }),
    })
    console.log('错误已开始发送')
  }
}

const errorNotify = function (err) {
  if (err) {
    errorHandler(err)
    Vue.prototype.$notify.error({
      title: '错误',
      message: err.toString(),
    })
  }
}

//axios中的catch使用,以便在需要的时候发送邮件,并弹窗提示用户有错;
window.errorNotify = errorNotify

//开发模式不启用
if (!isLocal) {
  window.addEventListener('unhandledrejection', function (err) {
    //err为事件对象,取其中的reason属性
    if (_.get(err, 'reason')) {
      errorHandler(err.reason)
    }
  })

  window.onerror = function (msg, url, lineNo, columnNo, err) {
    console.error('捕获到异常：', err)
    //本地开发时,存在js来源和网站不同源的情况,不同源下,error为null
    errorHandler(err)
    return false
  }

  Vue.config.errorHandler = function (err, vm, info) {
    console.error('Vue.config.errorHandler', err, vm, info)
    //render函数报错
    errorHandler(err)
  }

  Vue.config.warnHandler = function (msg, vm, trace) {
    console.warn('Vue.config.warnHandler', msg, vm, trace)
  }
}

function className (target) {
  return _.trim(_.get(target, 'className')).replace(/\s+/, '.')
}

function info (target, pointTarget) {
  let nodeName = _.toLower(target.nodeName)
  let str = ''
  let text = ''

  let _cls = className(target)
  if (_cls) {
    _cls = '.' + _cls
  }

  if (pointTarget) {
    text = target.innerText
    // 被点击的按钮，不打印class
    if (text) {
      _cls = ''
    }
  }

  /*
  * 为了避免泄密，不能传输任何表单值
  * */
  switch (nodeName) {
    case 'tr':
      str = `${nodeName}[${target.rowIndex}]${_cls} ${text}`
      break
    case 'td':
      str = `${nodeName}[${target.cellIndex}]${_cls} ${text}`
      break
    case 'select':
      str = `${nodeName}${_cls}`
      break
    case 'button':
      str = `${nodeName}${_cls} ${text}`
      break
    case 'textarea':
      str = `${nodeName}${_cls}`
      break
    case 'input':
      str = `${nodeName}${_cls}`
      break
    default:
      str = `${nodeName}${_cls} ${text}`
      break
  }

  if (pointTarget) {
    return '【' + str + '】'
  } else {
    return str
  }
}

$(document.body).on('mousedown.performance', ev => {
  let target = ev.target
  let arr = []
  if (target.innerText && target.innerText.length > 100) {
    return
  } else {
    arr.push(info(target, true))
    let parentTarget = target.parentNode
    if (!parentTarget) {
      return
    }
    while (parentTarget.nodeName !== 'BODY') {
      arr.push(info(parentTarget))
      parentTarget = parentTarget.parentNode
      if (parentTarget === null) {
        break
      }
    }
  }
  mouseDownHistory.push(
    `<font color="red">点击发生时间：</font>${moment().format(
      'YYYY-MM-DD HH:mm:ss:SSS',
    )}：${arr.join(' > ')}`,
  )
  if (mouseDownHistory.length > 3) {
    mouseDownHistory = mouseDownHistory.slice(-3)
  }
})
