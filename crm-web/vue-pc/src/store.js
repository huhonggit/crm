import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    // 提供全局的UI操作，如是否折叠侧边栏等等
    UI: {
      asideIsCollapse: false,
    },
    isLogin: false,
    __dicMap: {},
    //以下参数用来实现Tab 页面系统
    activeIndex: "/home",
    options: [{ route: '/home', name: '首页' , children:false}],
    operations: {},//控制按钮权限

  },
  mutations: {
    toggleAside (state) {
      state.UI.asideIsCollapse = !state.UI.asideIsCollapse
    },
    setDicMap (state, dicMap) {
      window.__dicMap = dicMap
      state.__dicMap = dicMap
    },
    setLoginStatus (state, bool) {
      state.isLogin = bool
    },

    saveOperations (state, operations) {
      state.operations = operations
    },
    saveOperationItem (state, operation) {
      state.operations[operation.key] = operation.value
    },
    add_tabs(state, data) {
      let  flag = true;
      this.state.options.forEach(item =>{
        if(item.route === data.route){
          flag = false;
        }
      })
      if(flag){
        this.state.options.push(data);
      }
      this.state.activeIndex = data.route
    },
    // 删除tabs
    delete_tabs(state, route) {
      let index = 0;
      for (let option of state.options) {
        if (option.route === route) {
          break;
        }
        index++;
      }
      this.state.options.splice(index, 1);
    },
    delete_all(state, route) {
      if(route){//关闭其他
        console.log(route)
        this.state.options= [{ route: '/home', name: '首页' , children:false}]
        let  flag = true;
        this.state.options.forEach(item =>{
          if(item.route === route.route){
            flag = false;
          }
        })
        if(flag){
          this.state.options.push(route);
        }
        this.state.activeIndex = route.route
      }else{
        this.state.options= [{ route: '/home', name: '首页' , children:false}]
        this.state.activeIndex = '/home'
      }
    },
    // 设置当前激活的tab
    set_active_index(state, index) {
      this.state.activeIndex = index;
    },
  },
  actions: {},
  getters: {
    getOperations: state => {
      return state.operations;
    }
  }
})
