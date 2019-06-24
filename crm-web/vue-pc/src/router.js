import Vue from 'vue'
import Router from 'vue-router'
import Home from './views/Home/index.vue'
import Login from '@/components/Login/login.vue'
import Dict from '@/components/Dict/dict.vue'
import menu from '@/views/admin/menu/menu.vue'
import role from '@/views/admin/role/role.vue'
import user from '@/views/admin/user/user.vue'


Vue.use(Router);

export default new Router({
    routes: [
        {
            path: '/',
            component: Home,
            name: '首页',
            meta: {
                notKeepAlive: true // 不需要被缓存 默认缓存 避免多次加载dict数据 刷新页面后失效
            }
        },
        {
            path: '/login',
            component: Login,
            name: '登录',
            meta: {
                notKeepAlive: true
            }
        },
        // ---------------------系统管理 start----------------

        {
            path: '/dict', //字典管理
            component: Dict,
            name: '字典管理'
        },
        {
            path: '/menu',
            component: menu,
            name: '菜单管理'
        },
        {
            path: '/role',
            component: role,
            name: '角色管理'
        },
        {
            path: '/user',
            component: user,
            name: '用户管理'
        }


    ],
})
