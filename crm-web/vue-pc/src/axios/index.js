import 'nprogress/nprogress.css'
import NProgress from 'nprogress'

import axios from 'axios'
import Vue from 'vue'

NProgress.configure()

let requestList = 0

// 配置csrfToken字段
const mAxios = axios.create()

mAxios.interceptors.request.use(request => {
  NProgress.start()
  requestList += 1
  return request
})

mAxios.interceptors.response.use(response => {
  // debugger
  requestList -= 1
  NProgress.set(0.8)
  if (requestList === 0) {
    NProgress.done()
  }
  return response
}, error => {
  requestList -= 1
  NProgress.set(0.8)
  if (requestList === 0) {
    NProgress.done()
  }
  return error
})

mAxios.interceptors.request.use(
  function (config) {
    return config
  },
  function (error) {
    // Do something with request error
    return Promise.reject(error)
  },
)

//直接返回data，避免使用方写出res.data.data这种长路径
mAxios.interceptors.response.use(
  function (res) {
    // 如果返回的content-type为json，但response.data为string
    // 则表明服务器返回了一个错误的json
    if (
      _.trim(_.get(res, `headers['content-type']`)).
        indexOf('application/json') > -1 &&
      'string' === typeof res
    ) {
      // 如果直接抛出异常会被axios捕获并丢弃，
      // 所以增加setTimeout
      setTimeout(() => {
        throw new Error(
          'JSON反序列化失败，请检查JSON格式是否合法，原始数据：\n' +
          res,
        )
      })
      return Promise.reject()
    }
    //401 错误 证明session 过期。跳转登录页
    if(_.get(res, 'data.code') === 401){
      location.hash = '/login'
      return
    }

    if (_.get(res, 'data.code') !== 200 && _.get(res, 'data.code') !== 401) {
      let msg = _.get(res, 'data.msg')
      Vue.prototype.$notify({
        title: `${res.data.code} 错误`,
        message: msg ? msg : '发生错误',
        type: 'error',
      })
    }
    return res.data;
  },
  function (error) {
    if (error.response.status === 401) {
      location.hash = '/login'
      return
    } else {
      let msg = _.get(error.response, 'data.errorMsg')
      Vue.prototype.$notify({
        title: `${error.response.status} 错误`,
        message: msg ? msg : _.get(error.response, 'data.error'),
        type: 'error',
      })
    }
    return Promise.reject(_.get(error.response, 'data'))
  },
)

export default mAxios
