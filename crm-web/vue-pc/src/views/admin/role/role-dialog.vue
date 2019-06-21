<template>
    <el-dialog :visible.sync="visible"
               :close-on-click-modal="false"
               @close="close"
               id="add-org-dialog"
               :title="title">
        <div class="dialog-form">
        <el-form :model="form" label-width="150px" :rules="rules" ref="form">
            <el-form-item label="角色名称：" prop="name">
                <el-input placeholder="请输入菜单名称" v-model="form.name"></el-input>
            </el-form-item>
            <el-form-item label="角色说明：" prop="note">
                <el-input placeholder="请输入说明" v-model="form.remark"></el-input>
            </el-form-item>
            <el-tree ref="tree"
                     :data="tree"
                     :props="treeProps"
                     node-key="id"
                     accordion
                     :expand-on-click-node="false"
                     :check-on-click-node ="true"
                     :default-expand-all ="true"
                     show-checkbox></el-tree>
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
  import roleApi from "../../../js/api/admin/role/roleApi";

  export default {
      props: ['roleId'],
    data () {
      return {
        visible: true,
        form: {
            id:undefined,
            name:undefined,
            remark:undefined,
            status:undefined,
            menuIds:[]
        },
          tree: [],
          treeProps: {label: "name", children: "children"},
          rules:{
              name:[{required:true,message:"必填项"}],
          }
      }
    },
    computed:{
      title(){
          return this.roleId?"编辑角色":"新增角色"
      }
    },
    methods: {
      close () {
          this.$emit('close');
      },
      submit(){
          //获取全部选中的 和选中一半的节点
          let halfNodes= this.$refs.tree.getHalfCheckedKeys();
          let nodes= this.$refs.tree.getCheckedKeys();

         nodes = nodes.concat(halfNodes);
          this.form.menuIds = nodes;
          this.$refs["form"].validate((valid) => {
              if (valid) {
                  roleApi.saveRole(this.form).then(res => {
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
          menuApi.loadMenuTree().then(res => {
              this.tree = res.data;
              if(this.roleId){//编辑加载
                  roleApi.getRoleWithMenu(this.roleId).then(res => {
                    this.form = res.data;
                    //填充数据
                    this.$refs.tree.setCheckedKeys(res.data.checkMenuId);

                  }).catch()
              }else{
                  this.form.status = 1;
              }
          }).catch()
      }
    },
    mounted () {
      this.loadMenu()
    }
  }
</script>