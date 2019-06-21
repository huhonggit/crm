<template>
    <div class="flex-column" style="height: 100%">
        <role-dialog v-if="roleDialogVisible"
                     :roleId="roleId"
                     @refresh="search"
                     @close="close"></role-dialog>

        <el-form :inline="true">
            <el-input v-model="form.name" style="width: 200px" size="small" placeholder="名称"></el-input>
            <cg-button type="primary" style="margin-left: 5px" size="small" @click="search" perms="search">查询</cg-button>
            <cg-button type="primary" style="margin-left: 5px" size="small" @click="openRoleDialog(null)" perms="add">新增</cg-button>
        </el-form>
        <div style="margin-top: 5px;" class="flex-column">
            <el-table
                    :data="tableData"
                    border
                    height="100%"
                    style="width: 100%">
                <el-table-column prop="name" label="名称"/>
                <el-table-column prop="remark" label="描述"/>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button type="primary" size="mini" perms="edit"
                                   @click="openRoleDialog(scope.row.id)">编辑
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>
<script>
    import roleDialog from './role-dialog.vue'
    import roleApi from "../../../js/api/admin/role/roleApi";
    export default {
        components: {
            'role-dialog': roleDialog,
        },
        name: 'org_list',
        //表格各行的数据
        data() {
            return {
                form:{
                    name:undefined
                },
                roleDialogVisible:false,
                roleId:undefined,
                tableData:[]
            };
        },
        methods:{
            search(){
                roleApi.loadRoles(this.form).then(res =>{
                    this.tableData = res.data;
                }).catch()
            },
            openRoleDialog(id){
                this.roleDialogVisible = true
                this.roleId = id;
            },
            close(){
                this.roleDialogVisible = false
            }
        },
        mounted () {
            this.search();
        }
    };
</script>

<style lang="less">
</style>