<template>
    <el-dialog :visible.sync="visible"
               :close-on-click-modal="false"
               @close="close"
               id="add-org-dialog"
               :title="title">
        <div >
            <el-form :model="form" label-width="150px" :rules="rules" ref="form">
                <el-form-item label="菜单名称：" prop="name">
                    <el-input placeholder="请输入菜单名称" v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item v-if="type =='add'" label="上级菜单名称：" prop="name">
                    <el-input placeholder="请输入路由" :disabled="true" v-model="pName"></el-input>
                </el-form-item>
                <el-form-item  label="菜单类型：" prop="type">
                    <hh-select v-model="form.type" dict="menu.type" placeholder="请选择类型" style="width: 100%;"/>
                </el-form-item>
                <el-form-item label="路由：" prop="router">
                    <el-input placeholder="请输入路由" v-model="form.router"></el-input>
                </el-form-item>
                <el-form-item label="显示序号：" prop="sort">
                    <el-input placeholder="请输入顺序" type="number" v-model.number="form.sort"></el-input>
                </el-form-item>
                <el-form-item label="图标：" prop="route">
                    <el-input type="textarea" :rows="4" placeholder="请输入图标" v-model="form.icon"></el-input>
                </el-form-item>
                <el-form-item  label="状态：" prop="status">
                    <hh-select v-model="form.status" dict="status.type" placeholder="请选择类型" style="width: 100%;"/>
                </el-form-item>
                <!--<el-form-item>-->
                    <!--<el-button type="primary" @click="submit">保存</el-button>-->
                    <!--<el-button @click="visible=false;">取消</el-button>-->
                <!--</el-form-item>-->
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="visible=false;" size="small">取消</el-button>
                <el-button type="primary" @click="submit" size="small">保存</el-button>
            </div>
        </div>
    </el-dialog>
</template>
<script>
    import menuApi from "../../../js/api/admin/menu/menuApi";

    export default {
        props: ['menuId','type','pName','menuType'],
        data () {
            return {
                visible: true,
                form: {
                    id:undefined,
                    name:undefined,
                    type:undefined,
                    router:undefined,
                    parentId:undefined,
                    icon:undefined,
                    status:undefined,
                    sort:undefined,
                },
                rules:{
                    name:[{required:true,message:"必填项"}],
                    status:[{required:true,message:"必填项"}],
                    type:[{required:true,message:"必填项"}],
                    sort:[{required:true,message:"必填项"}],
                }
            }
        },
        computed:{
            title(){
                return this.value === 'add'?"添加菜单":"编辑菜单"
            }
        },
        methods: {
            close () {
                this.$emit('close');
            },
            submit(){
                this.$refs["form"].validate((valid) => {
                    if (valid) {
                        menuApi.saveMenu(this.form).then(res => {
                            if(res.code === 200){
                                this.$message({ message: '保存成功！', type: 'success'})
                                this.$emit('refresh');
                                this.$emit('close');
                            }
                        }).catch()
                    } else {
                        return false;
                    }
                });
            },
            loadMenu(){
                if(this.type === "edit"){
                    this.form.id = this.menuId;
                    menuApi.getMenu(this.menuId).then(res => {
                        this.form.parentId = res.data.parentId;
                        this.form.id = res.data.id;
                        this.form.name = res.data.name;
                        this.form.type = res.data.type;
                        this.form.router = res.data.router;
                        this.form.icon = res.data.icon;
                        this.form.status = res.data.status;
                        this.form.sort = res.data.sort;
                    }).catch()
                }else{ //新增设置父ID
                    this.form.parentId = this.menuId;
                    if(this.menuType === 'root'){
                        this.form.type = 'subMenu'
                    }else if(this.menuType === 'subMenu'){
                        this.form.type = 'menu'
                    }else if(this.menuType === 'menu'){
                        this.form.type = 'operation'
                    }
                    this.form.status = 'enable';
                }
            }
        },
        mounted () {
            this.loadMenu()
        }
    }
</script>