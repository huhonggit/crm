<template>
  <div class="kxb-wrapper flex-column">
    <login v-if="!isLogin"/>
    <el-container
      v-else
      class="flex-column kxb-container">
      <el-header>
        <Header/>
      </el-header>
      <el-container>
        <el-aside :width="asideIsCollapse ? '65px' : '180px'">
          <Aside/>
        </el-aside>
        <el-main class="flex-column">
          <div>
            <tags-nav/>
          </div>
          <keep-alive >
            <router-view v-if="!$route.meta.notKeepAlive" class="container" style="margin-top: 3px"/>
          </keep-alive>
          <router-view v-if="$route.meta.notKeepAlive" class="container" style="margin-top: 3px"/>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
  import Header from '@/components/Header/index.vue'
  import Aside from '@/components/Aside/index.vue'
  import Login from '@/components/Login/login.vue'
  import TagsNav from '@/components/TagsNav/index.vue'

  export default {
    components: {Header, Aside, Login,TagsNav},
    computed: {
      asideIsCollapse () {
        return this.$store.state.UI.asideIsCollapse
      },
      isLogin () {
        return this.$store.state.isLogin
      }
    },
    mounted(){
    },
    methods:{
      _isMobile() {
        let flag = navigator.userAgent.match(/(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i)
        return flag;
      }
    }
  }
</script>

<style lang="less">

  .kxb-wrapper {
    height: 100%;
    .kxb-container {
      width: 100%;
      height: 100%;
      > .el-header {
        padding: 0;
        height: 48px !important;
        border-bottom: solid 1px #f2f2f2;
      }
      > .el-container {
        overflow: hidden;
        > .el-aside {
          border-right: solid 1px #f2f2f2;
          transition: width 0.1s;
          overflow: auto;
          > .el-menu {
            border-right: none;
          }
        }
        > .el-main {
          background: #f3f6f9;
          padding: 0;
          display: flex;
          > container {
            flex: 1 1 0;
            box-sizing: border-box;
            display: flex;
            overflow: hidden;
          }
        }
      }
    }
  }
</style>
