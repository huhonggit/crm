<style lang="less">
  #LOGIN_PAGE {
    // 主体背景，图片定义在各自的主题文件中
    background-image: url(login-bg.jpg);
    section {
      background-position: right center;
      background-repeat: no-repeat;
      background-size: 100% 100%;
      flex: 1 1 0;
    }
    aside {
      position: relative;
      right: 10%;
      top: 50%;
      transform: translateY(-60%);
      width: 340px;
      height: 280px;
      display: flex;
      flex-direction: column;
      /*flex: 0 0 31.25%;*/
      justify-content: center;
      background: #fff;
      border:0px solid;

      border-radius:6px;
    }

    .form {
      width: 100%;
      padding: 0 0.8em;
      justify-content: center;
      .logo {
        text-align: center;
        svg {
          width: 139px;
        }
      }
    }
    input {
      border: none;
      border-bottom: solid 1px #e5e9f2;
      border-radius: 0;
      background: none;
      text-indent: 1.2em;
    }
    input:focus + svg,
    .el-form-item:hover svg {
      color: #4a9af5;
    }

    .el-form-item {
      position: relative;
      svg {
        position: absolute;
        z-index: 1;
        bottom: 0.9em;
        left: 0;
        width: 14px;
        height: 14px;
        pointer-events: none;
        color: #d3dce6;
        transition: color 0.2s;
      }
      input[type="password"]:focus + svg + span.caps-lock.capsLockOpen {
        opacity: 1;
      }
      span.caps-lock {
        position: absolute;
        right: 0;
        top: 3px;
        color: #999;
        line-height: normal;
        opacity: 0;
        transition: opacity 0.15s;
        svg {
          position: static;
          margin-left: 5px;
          width: 20px;
          height: 20px;
          display: inline-block;
          vertical-align: middle;
          color: #333;
        }
      }
    }
    .forget-password {
      text-align: right;
    }
    .submit {
      width: 100%;
      /*border-radius: 15px;*/
    }
    .copyright {
      position: absolute;
      left: 0;
      bottom: 0;
      width: 100%;
      color: #606266;
      text-align: center;
      font-size: 12px;
      pointer-events: none;
    }
  }
</style>

<template>
  <div id="LOGIN_PAGE" class="flex-row" justify="end">
    <section/>
    <aside v-loading="loading" :element-loading-text="loadingText">
      <el-row class="form">
        <!--<div class="logo">-->
          <!--<svg>-->
            <!--<use xlink:href="#svg-admin-logo"></use>-->
          <!--</svg>-->
        <!--</div>-->
        <el-form ref="form" :model="form" @submit.native.prevent="submit">
          <el-form-item align="center" style="font-size: 14px;color: #606266">
            欢迎登录
          </el-form-item>
          <el-form-item>
            <input ref="username" v-model="form.username" type="text" placeholder="用户名" autocomplete="username"
                    class="el-input__inner" >
            <svg>
              <use xlink:href="#svg-login-user-name"></use>
            </svg>
          </el-form-item>
          <el-form-item style="margin-top: 20px !important;">
            <input ref="password" v-model="form.password" type="password" autocomplete="user-password" placeholder="密码"
                    class="el-input__inner" >
            <svg>
              <use xlink:href="#svg-login-password"></use>
            </svg>
            <span :class="{capsLockOpen : capsLockStatus}" class="caps-lock">
              <svg>
                <use xlink:href="#svg-login-caps-lock"></use>
              </svg>
              大写键打开
            </span>
          </el-form-item>
          <el-form-item style="margin-top: 20px !important;">
            <button :disabled="loading" type="submit" class="el-button submit el-button--primary">
              <span>登录</span>
            </button>
          </el-form-item>
        </el-form>
      </el-row>
      <el-row class="copyright">
        <p>&copy; 2018 - {{ new Date().getFullYear() }}  xx</p>
      </el-row>
    </aside>
  </div>
</template>

<script>
  import capsLock from "capslock";
  import axios from "axios";

  export default {
    data() {
      return {
        loading: false,
        loadingText: "登录中...",
        form: {
          username: "",
          password: "",
        },
        capsLockStatus: false
      };
    },
    mounted() {
      if (_.get(window, "__dicMap.currentUser")) {
        this.$router.replace("/");
        return;
      }

      this.$store.commit('setLoginStatus', false)

      // 大小写监听
      capsLock.observe(status => {
        this.capsLockStatus = status;
      });

      if (this.form.username === "") {
        this.$refs.username.focus();
      }
    },
    methods: {
      // 登录
      submit() {
        var param = {
          username: this.form.username,
          password: this.form.password,
        };

        if (param.username === "") {
          this.$refs.username.focus();
          return;
        }

        if (param.password === "") {
          this.$refs.password.focus();
          return;
        }

        this.loading = true;
        axios.get(`/auth/login?username=${param.username}&password=${param.password}`).then(res => {
                  if (res.status === 200 && res.data.code === 200) {
                    this.loadingText = "登录成功，正在跳转...";
                    let routerurl = "/home";
                    setTimeout(() => {
                      this.$router.push(routerurl);
                      this.$store.commit('setLoginStatus', true)
                      // setTimeout(() => {
                      //   location.reload(true);
                      // }, 10);
                    }, 30);
                  } else {
                    throw new Error(res.data.msg);
                  }
                })
                .catch(e => {
                  this.loading = false;
                  if (e) {
                    this.$message.error({
                      title: "登录失败",
                      message: e.toString()
                    });
                  }
                });
      }
    }
  };
</script>
