<style lang="less">
    svg.aside-svg {
        max-height: 1.2rem;
        max-width: 1.2rem;
        margin-right: .1rem;
        color: #4598f8;
    }

    #sidebar {
        .el-menu-item {
            /*display: flex;*/
            align-items: center;
        }
    }
</style>

<template>
    <el-menu
            id="sidebar"
            :collapse="isCollapse"
            :router="true"
            default-active="/">

        <template v-for="item in menuList">
            <el-menu-item :key="item.id" v-if="item.type == 'menu'" :index="item.router">
                <svg class="aside-svg">
                    <use :xlink:href="'#svg-'+item.icon"></use>
                </svg>
                <span slot="title">{{item.name}}</span>
            </el-menu-item>

            <el-submenu :key="item.id" v-if="item.type == 'subMenu'" :index="item.router">
                <template slot="title">
                    <svg class="aside-svg">
                        <use :xlink:href="'#svg-'+item.icon"></use>
                    </svg>
                    <span>{{item.name}}</span>
                </template>
                <template v-for="child in item.children">
                    <el-menu-item v-if="child.type == 'menu'" :key="child.id" :index="child.router">
                        <svg class="aside-svg">
                            <use :xlink:href="'#svg-'+child.icon"></use>
                        </svg>
                        {{child.name}}
                    </el-menu-item>

                    <el-submenu :key="child.id" v-if="child.type == 'subMenu'" :index="child.router">
                        <template slot="title">
                            <svg class="aside-svg">
                                <use :xlink:href="'#svg-'+child.icon"></use>
                            </svg>
                            <span>{{child.name}}</span>
                        </template>
                        <template v-for="child3 in child.children">
                            <el-menu-item v-if="child3.type == 'menu'" :key="child3.id" :index="child3.router">
                                <svg class="aside-svg">
                                    <use :xlink:href="'#svg-'+child3.icon"></use>
                                </svg>
                                {{child3.name}}
                            </el-menu-item>
                        </template>
                    </el-submenu>
                </template>
            </el-submenu>
        </template>
        <update-pwd v-if="flag" @close="close"/>
    </el-menu>
</template>

<script>
    import userApi from "../../js/api/admin/user/userApi";
    import UpdatePwd from "../Header/updatePwd"

    export default {
        components: {UpdatePwd},
        data() {
            return {
                menuList: [],
                operations: {},
                flag: false
            }
        },
        computed: {
            isCollapse() {
                return this.$store.state.UI.asideIsCollapse
            },
            dicMap() {
                return this.$store.state.__dicMap;
            }
        },
        methods: {
            close() {
                this.flag = false;
            },
            showUpadatePwd() {
                if (this.dicMap.username && this.dicMap.pwdFlag !== '1'){
                    this.flag = true;
                }
            },
            loadMenusByOnlineUser() {
                userApi.loadMenusByOnlineUser().then(res => {
                    if (res.data.length === 1) {
                        // this.$store.commit('saveMenuList',res.data[0].children)
                        this.menuList = res.data[0].children;
                        //将按钮的权限放入store 避免多次请求
                        this.showUpadatePwd()
                        this.storeButtonPermission(this.menuList);
                        this.$store.commit('saveOperations', this.operations)

                    }
                }).catch()
            },
            storeButtonPermission(menuList) {
                menuList.forEach(menu => {
                    let ops = [];
                    if (menu.children.length > 0) {
                        this.storeButtonPermission(menu.children)
                        menu.children.forEach(child => {
                            if (child.type === 'operation') {
                                ops.push(child);
                            }
                        })
                    }
                    if (menu.type === 'menu') {
                        this.operations[menu.router.substr(1)] = ops;
                    }
                })
            }
        },
        mounted() {
            this.loadMenusByOnlineUser();
        }
    }
</script>
