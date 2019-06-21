<template>
    <!--胡红 借助vue2.4 新特性 v-bind="$attrs" v-on="$listeners"-->
    <el-button v-bind="$attrs" v-on="$listeners" v-if="disable">
        <slot></slot>
    </el-button>
</template>


<script>
    import hhApi from '../hhApi'

    export default {
        props: {
            perms: {
                type: String,
                required: true
            },
        },
        data: function () {
            return {
                disable: false
            }
        },
        methods: {},
        watch: {},
        mounted: function () {
            let operations = this.$store.getters['getOperations'];
            let router = this.$router.currentRoute.path;
            let operation = operations[router.substr(1)];
            if (operation === undefined) {
                hhApi.loadButtons(router).then(res => {
                    operation = res.data
                    let data = {};
                    data.key = router.substr(1)
                    data.value = res.data
                    this.$store.commit("saveOperationItem",data)
                    operation.forEach(item => {
                        if (item.router == this.perms) {
                            this.disable = true;
                        }
                    })
                }).catch();
            }else {
                operation.forEach(item => {
                    if (item.router == this.perms) {
                        this.disable = true;
                    }
                })
            }

        },

    }
</script>