<template>
    <div id="header">
        <div class="main">
            <div :class="{small : asideIsCollapse}" class="logo">
                <div class="big-logo" v-if="!asideIsCollapse">
                    <img :src="bigLogo" alt="">
                </div>
                <div class="small-logo" v-else>
                    <img :src="smallLogo" alt="">
                </div>
            </div>
            <a :class="asideIsCollapse ? 'collapse' : ''" class="toggle" href="javascript:void(0)" @click="toggle">
                <el-tooltip :content="asideIsCollapse ? '展开侧边栏' : '收起侧边栏'" effect="dark">
                    <i class="el-icon-d-arrow-left"/>
                </el-tooltip>
            </a>
            <div class="org-name"></div>
        </div>
        <div class="control">
            <el-dropdown @command="userHandle">
                <div class="el-dropdown-link">
                    {{_.get(dicMap, 'name','用户')}}
                    <i class="el-icon-arrow-down el-icon--right"/>
                </div>
                <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item command="updatePwd" divided>修改密码</el-dropdown-item>
                    <el-dropdown-item command="logout" divided>退出登录</el-dropdown-item>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
        <update-pwd v-if="flag" @close="close"/>
    </div>
</template>

<script>
    import bigLogo from '@/assets/logo.jpg'
    import smallLogo from '@/assets/logo-min.jpg'

    import mAxios from "@/axios";
    import UpdatePwd from './updatePwd'


    export default {
        components: {UpdatePwd},
        comments: {
            UpdatePwd
        },
        computed: {
            asideIsCollapse() {
                return this.$store.state.UI.asideIsCollapse;
            },
            dicMap() {
                return this.$store.state.__dicMap;
            }
        },
        data() {
            return {
                bigLogo,
                smallLogo,
                flag: false
            }
        },
        methods: {
            close() {
                this.flag = false
            },
            toggle() {
                this.$store.commit("toggleAside");
            },
            userHandle(key) {
                if (_.isFunction(this[key])) {
                    this[key]();
                }
            },
            updatePwd() {
                this.flag=true
            },
            logout() {
                mAxios
                    .get(`/auth/logout`)
                    .then(() => {
                        this.$store.commit("setLoginStatus", false);
                        this.$router.replace("/Login");
                        setTimeout(() => {
                            location.reload(true);
                        }, 50);
                    })
                    .catch(e => {
                        console.error(e);
                    });
            }
        },
        mounted() {
            // let user = window.__dicMap;
            // if (user.id && user.pwdFlag === '1'){
            //   this.updatePwd();
            // }
        }
    };
</script>

<style lang="less">
    #header {
        height: 100%;
        display: flex;
        align-items: center;
        box-sizing: border-box;
        justify-content: space-between;

        .main {
            display: flex;
            height: 100%;

            .logo {
                height: 100%;
                width: 180px;
                display: flex;

                svg {
                    margin: auto 12px;
                    max-width: 100%;
                    max-height: 30px;
                }

                &.small {
                    max-width: 65px;
                    height: 46px;
                }

                .big-logo {
                    /*width: 120px;*/
                    height: 46px;
                    margin: auto;

                    img {
                        width: 100%;
                        height: 100%;
                    }
                }

                .small-logo {
                    height: 46px;
                    padding-left: 10px;
                    padding-top: 2px;

                    img {
                        height: 44px;
                        width: auto;
                        display: block;
                        margin: 0 auto;
                    }
                }
            }

            .toggle {
                margin-right: 0.4rem;
                height: 100%;
                display: flex;
                padding: 0 0.2rem;
                color: #000;
                transition: all 0.2s;

                &.collapse {
                    transform: rotateY(180deg);
                }

                &:hover {
                    background-color: #f9f9f9;
                }

                i {
                    margin: auto;
                }
            }

            .org-name {
                display: flex;
                height: 100%;
                align-items: center;
            }
        }

        .control {
            margin-right: 1rem;
        }

        .logo-con {
            height: 46px;

            img {
                height: 44px;
                width: auto;
                display: block;
                margin: 0 auto;
            }
        }
    }
</style>
