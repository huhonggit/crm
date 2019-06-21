<template>
    <el-dialog :title="userId?'修改用户':'添加用户'" width="550px" class="flex-column" :close-on-click-modal="false" @close="childClose"
               :visible.sync="show" >
        <el-form :model="form" label-width="150px" :rules="rules" ref="form">
            <el-form-item label="登录名：" prop="username">
                <el-input placeholder="请输入登录名" v-model="form.username"></el-input>
            </el-form-item>
            <el-form-item label="姓名：" prop="name">
                <el-input placeholder="请输入姓名" v-model="form.name"></el-input>
            </el-form-item>

            <el-form-item label="密码："  prop="password">
                <el-input v-if="userId?false:true" type='password' v-model="form.password" placehodder='数字和字母'></el-input>
                <el-button v-else type="primary" size="small" @click="editPassword">修改密码</el-button>
            </el-form-item>
            <el-form-item label="权限："  prop="roleList">
                <el-checkbox-group size="small" v-model="form.roleIds">
                    <template v-for="item in roles">
                        <el-checkbox :key="item.id" :label=item.id>{{item.name}}</el-checkbox>
                    </template>
                </el-checkbox-group>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="childClose" size="small">取消</el-button>
            <el-button type="primary" size="small" @click="submit">保存</el-button>
        </div>
    </el-dialog>
</template>
<script>
    import ElButton from "../../../../node_modules/element-ui/packages/button/src/button";
    import userApi from "../../../js/api/admin/user/userApi";
    import roleApi from "../../../js/api/admin/role/roleApi";
    export default {
        components: {ElButton},
        props: ['userId','orgId'],
        data () {
            return {
                show: true,
                roles:[],
                form:{
                    id:undefined,
                    orgId:undefined,
                    username:"",
                    name:"",
                    password:"",
                    roleIds:[],
                },
                rules:{
                    name:[{required:true,message:"必填项"}],
                    username:[{required:true,message:"必填项"}],
                    password:[{required:true,message:"必填项"}],
                },
                onlineOrgId:window.__dicMap.orgId
            }
        },
        computed: {
        },
        methods: {
            childClose () {
                this.$emit('childClose');
            },
            loadData(){
                this.form.id = this.userId;
                this.form.orgId = this.orgId;
                roleApi.loadRoles({}).then(res => {
                    if(res.code === 200){
                        this.roles = res.data;
                    }
                    if(this.userId){
                        userApi.getUserById(this.userId).then(res => {
                            this.form = res.data;
                        }).catch()
                    }
                }).catch()
            },
            submit(){
                this.$refs["form"].validate((valid) => {
                    if (valid) {
                        userApi.saveUser(this.form).then(res => {
                            if(res.code === 200){
                                this.$notify({title: '成功', message: '保存用户成功！', type: 'success'});
                                this.childClose();
                            }
                        }).catch()
                    } else {
                        return false;
                    }
                });

            },
            editPassword () {
                this.$prompt('请输入新密码', '修改密码', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    inputValidator (value) {
                        if (value) {
                            if (value.trim()) {
                                return true
                            } else {
                                return false
                            }
                        } else {
                            return false
                        }

                    },
                    inputErrorMessage: '新密码不能为空',
                }).then(({value}) => {
                    this.form.password = value;
                    userApi.editPassword(this.form).then(res => {
                        if (res.code === 200) {
                            this.$message.success('密码修改成功!')
                        }
                    }).catch(e => {
                        if (e) {
                            this.$notify.error({
                                title: '错误',
                                message: e.toString(),
                            })
                        }
                    })
                }).catch(() => { })
            },
        },
        mounted () {
            this.loadData();
        },
    }

</script>


