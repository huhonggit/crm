import Vue from 'vue'
import Vuex from 'vuex'
import App from './App.vue'
import store from './store'
import router from './router'

import _ from 'lodash'
import moment from 'moment'
import 'moment/locale/zh-cn'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import '@/assets/global-css'
// import './helper/error-handler'
import './components/index.js'
import '@/js/filter'
import '@/js/util'
import './icons/icons'
import '@/js/directive'
import cgAxios from '@/axios'


moment.locale('zh-cn')

Vue.use(Vuex)
Vue.use(ElementUI)

// 方便模板中使用
Vue.prototype._ = _
Vue.prototype.moment = moment

//自定义组件
import cgUI from './components/hh/index.js'
import cgApi from "./components/hh/hhApi";

Vue.use(cgUI)

// 只提供这两个全局变量
window.moment = moment
window._ = _
window.__dicMap = {}

router.beforeEach((to, from, next) => {

  let operations = store.getters['getOperations'];
  let router =to.path;
  let operation = operations[router.substr(1)];
  if (operation === undefined) {
    cgApi.loadButtons(router).then(res => {
      if(res){
        let data = {};
        data.key = router.substr(1)
        data.value = res.data
        store.commit("saveOperationItem",data)
      }
    }).catch();
  }


  document.title = to.name || 'CGEDU-ADMIN'
  next()
})

Vue.config.productionTip = false
window.Vue = Vue
new Vue({
  router,
  store,
  render: h => h(App),
  mounted () {
    Vue.prototype.$loading({
      body: true,
      text: '正在初始化...',
    })

    cgAxios.get('/v1/admin/login/loadLoginInfo').then(res => {
      if (res.code !== 200) {
        throw new Error('login')
      }
      Vue.prototype.$loading().close()
      store.commit('setLoginStatus', true)
      store.commit('setDicMap', res.data)
    }).catch(() => {
      Vue.prototype.$loading().close()
      location.hash = '/login'
    })
  },
}).$mount('#app')
