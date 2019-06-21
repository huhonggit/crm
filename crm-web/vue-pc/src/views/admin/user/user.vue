<template>
    <div class="flex-column">
    <user-detail-dialog v-if="userDetailVisible" @childClose="childClose"
                            :userId="userId" :orgId="orgId"></user-detail-dialog>
        <div>
            <cg-button type="primary" size="small" @click="openUserDialog(null)" perms="add">新增用户</cg-button>
        </div>
        <div class="flex-column">
            <el-table  :data="gridData" v-loading="tableLoading" height="100%">
                <el-table-column type="index" width="50"/>
                <el-table-column prop="name" label="名称" min-width="50"></el-table-column>
                <el-table-column prop="username" label="登录名" min-width="50"></el-table-column>
                <el-table-column prop="roleNames" label="权限" min-width="150">
                    <template slot-scope="scope">
                        <el-tag type="success" style="margin-right: 2px"
                                :key="it.id"
                                v-for="it in scope.row.roleList">{{it.name}}</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="status" header-align="center" align="center" min-width="80" label="状态">
                    <template slot-scope="scope">
                        <el-tag size="small" v-if="scope.row.status === 'disable'" type="danger">停用</el-tag>
                        <el-tag size="small" v-if="scope.row.status === 'enable'" type="success">启用</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="操作" min-width="120">
                    <template slot-scope="scope">
                        <el-button-group>
                            <cg-button type="primary" size="small" @click="openUserDialog(scope.row.id)" perms="edit">编辑</cg-button>
                            <!--<el-button type="success"  v-if="scope.row.status === 'disable'"  size="small" @click="handleModifyStatus(scope.row,'enable')">启用</el-button>-->
                            <!--<el-button type="danger"  v-if="scope.row.status === 'enable'" size="small" @click="handleModifyStatus(scope.row,'disable')">停用</el-button>-->
                        </el-button-group>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange"
                       :current-page="page.pageNum" :page-sizes="[20, 50, 100, 200]"
                       :page-size="page.pageSize" layout="total, sizes, prev, pager, next, jumper"
                       :total="total" >
        </el-pagination>
    </div>
</template>
<script>
    import userDetailDialog from './user-detail-dialog.vue'
    import userApi from "../../../js/api/admin/user/userApi";
    export default {
        props: ['orgId'],
        components: {
            'user-detail-dialog': userDetailDialog
        },
        data() {
            return {
                userDetailVisible: false,
                userId: null,
                isDialogShow: true,
                gridData: [],
                filter: {
                    name: '',
                },
                page: {
                    pageNum: 1,
                    pageSize: 20,
                },
                total:0,
                tableLoading: false,
                onlineOrgId: window.__dicMap.orgId
            }
        },
        computed: {},
        methods: {
            close() {
                this.$emit('close')
            },
            childClose() {
                this.userDetailVisible = false
                this.search()
            },
            //更改分页大小
            handleSizeChange: function(count) {
                this.$data.filter.count = count
                this.search()
            },
            //切换当前页
            handleCurrentChange: function(page) {
                this.$data.filter.page = page
                this.search()
            },
            openUserDialog: function(userId) {
                this.userDetailVisible = true
                this.userId = userId
            },
            handleModifyStatus(row,status){
                userApi.handleModifyStatus(row.id,status).then(res =>{
                    if(res.code === 200){
                        row.status = status
                        this.$message({
                            message: '操作成功',
                            type: 'success'
                        })
                    }
                }).catch()

            },
            search: function() {
                this.$data.tableLoading = true
                let param = {};
                param.obj = this.filter
                param.page = this.page
                userApi.queryUser(param)
                    .then(res => {
                        console.log(res)
                        this.$data.tableLoading = false
                        this.gridData = res.data.list
                        this.total = res.data.total
                    })
                    .catch()
            },

        },
        mounted() {
            this.search()
        }
    }
</script>

<style lang="less">
</style>


