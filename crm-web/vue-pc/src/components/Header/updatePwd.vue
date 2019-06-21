<template>
    <el-dialog title="修改密码" width="450px" class="flex-column" :close-on-click-modal="false" @close="close"
               :visible.sync="show" >
        <el-form :model="form" label-width="150px" :rules="rules" ref="form" >
            <el-form-item label="旧密码："  prop="oldPassword">
                <el-input  type='password' v-model="form.oldPassword" placehodder='旧密码'></el-input>
            </el-form-item>
            <el-form-item label="新密码："  prop="password1">
                <el-input  type='password' v-model="form.password1" placehodder='新密码'></el-input>
            </el-form-item>
            <el-form-item label="确认新密码："  prop="password2">
                <el-input  type='password' v-model="form.password2" placehodder='确认新密码'></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button type="primary" size="small" @click="submit">保存</el-button>
        </div>
    </el-dialog>
</template>
<script>
    import userApi from "../../js/api/admin/user/userApi";

    export default {
        components: {},
        props: [],
        data () {
            return {
                show: true,
                form:{
                    oldPassword:undefined,
                    password1:undefined,
                    password2:undefined,
                },
                rules:{
                    oldPassword:[{required:true,message:"必填项"}],
                    password1:[{required:true,message:"必填项"}],
                    password2:[{required:true,message:"必填项"}],
                },
            }
        },
        computed: {
        },
        methods: {
            close () {
                this.$emit('close');
            },
            submit(){
                this.$refs["form"].validate((valid) => {
                    if (valid) {
                        if (this.form.password1 !== this.form.password2){
                            this.$message.error('2次输入密码不一致');
                            return
                        }

                        userApi.updatePwd(this.form.oldPassword,this.form.password1).then(res=>{
                            if(res.code === 200){
                                this.$notify.success('修改密码成功');
                                this.close()
                            }
                        }).catch()
                    } else {
                        return false;
                    }
                });

            },

        },
        mounted () {
        },
    }

</script>


