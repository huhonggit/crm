<template>
    <el-dialog :visible.sync="visible"
               :close-on-click-modal="false"
               @close="close"
               id="add-dict-dialog"
               title="字典">
        <div class="dialog-form">
        <el-form :model="form"  label-width="150px" :rules="rules" ref="form">
            <el-form-item label="代码：" prop="code">
                <el-input placeholder="请输入代码" v-model="form.code"></el-input>
            </el-form-item>
            <el-form-item label="名称：" prop="name">
                <el-input placeholder="请输入名称" v-model="form.name"></el-input>
            </el-form-item>
            <!--<el-form-item label="拼音码：" prop="pym">-->
            <!--<el-input placeholder="请输入代码" v-model="form.pym"></el-input>-->
            <!--</el-form-item>-->
            <el-form-item  label="类型：" prop="type">
                <el-select v-model="form.type"    placeholder="请选择">
                    <el-option label="字典" value="字典"/>
                    <!--<el-option label="SQL" value="SQL"/>-->
                </el-select>
            </el-form-item>
            <el-form-item v-if="form.type == '字典'" label="数据类型：" prop="dataType">
                <el-select v-model="form.dataType" placeholder="请选择">
                    <el-option label="String" value="string"/>
                    <el-option label="Integer" value="int"/>
                </el-select>
            </el-form-item>

            <el-form-item v-if="form.type == 'SQL'" label="SQL：" prop="sqlvalue">
                <el-input type="textarea" v-model="form.sqlvalue" :rows="5" placeholder="请输入sql,返回字段带value ,text"></el-input>
            </el-form-item>
            <el-form-item  label="字典说明：" prop="info">
                <el-input placeholder="请输入说明" v-model="form.info"></el-input>
            </el-form-item>

            <el-form-item>
                <cg-button type="primary" @click="submit" perms="saveDict">保存</cg-button>
                <el-button @click="visible=false;">取消</el-button>
            </el-form-item>
        </el-form>
        </div>
    </el-dialog>
</template>
<script>
    import dictApi from './dictApi.js'
  export default {
      props: ['pid','id'],
    data () {
      return {
          visible:true,
          form:{
              name:undefined,
              code:undefined,
              pym:undefined,
              info:undefined,
              sqlvalue:undefined,
              pid:this.pid,
              dataType:'String',
              type:'字典',
              id:this.id
          },
          rules:{
              name:[{required:true,message:"必填项"}],
              code:[{required:true,message:"必填项"}],
              dataType:[{required:true,message:"必填项"}],
              type:[{required:true,message:"必填项"}],
              sqlvalue:[{required:true,message:"必填项"}],
          }
      }
    },
    computed:{
        isEdit() {
            return this.id?true:false;
        }
    },
    methods: {
        computeCode(){
            dictApi.computeCode(this.form.name).then(res=>{
                if(res.status === 200){
                    this.form.pym = res.data;
                }
            }).catch()
        },
      close () {
          this.$emit('close');
      },
      submit(){
          this.$refs["form"].validate((valid) => {
              if (valid) {
                  if(this.form.pid){
                      dictApi.saveDict(this.form).then(res=>{
                          if(res.code === 200){
                              this.$notify({title: '成功', message: '保存成功！', type: 'success'})
                              this.$emit('close');
                          }else{
                              this.$notify({title: '失败', message: res.msg, type: 'error'})
                          }
                      }).catch()
                  }
              } else {
                  return false;
              }
          });
      },
      edit(){
          this.$refs["form"].validate((valid) => {
              if (valid) {
                  if(this.form.pid){
                      dictApi.editDict(this.form).then(res=>{
                          if(res.code === 200){
                              this.$notify({title: '成功', message: '修改成功！', type: 'success'})
                              this.$emit('close');
                          }else{
                              this.$notify({title: '失败', message: res.errorMsg, type: 'error'})
                          }
                      }).catch()
                  }
              } else {
                  return false;
              }
          });
      }
    },
    mounted () {
      if(this.id){
          dictApi.getDictById(this.id).then(res=>{
              if(res.code === 200){
                  this.form = res.data;
              }
          }).catch()
      }
    }
  }
</script>