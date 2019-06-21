<template>
    <el-dialog :visible.sync="visible"
               :close-on-click-modal="false"
               @close="close"
               id="add-dict-item-dialog"
               title="字典">
        <div class="dialog-form">
        <el-form :model="form"  label-width="150px" :rules="rules" ref="form">
            <el-form-item label="值：" prop="value">
                <el-input placeholder="请输入值" v-model="form.value"></el-input>
            </el-form-item>
            <el-form-item label="名：" prop="text">
                <el-input placeholder="请输入名称" v-model="form.text"></el-input>
            </el-form-item>
            <el-form-item label="显示顺序：" prop="inx">
                <el-input placeholder="请输入顺序" v-model.number="form.inx"></el-input>
            </el-form-item>
            <el-form-item>
                <cg-button type="primary" @click="submit" perms="saveDictItem">保存</cg-button>
                <el-button @click="visible=false;">取消</el-button>
            </el-form-item>
        </el-form>
        </div>
    </el-dialog>
</template>
<script>
    import dictApi from './dictApi.js'
  export default {
      props: ['dictId','dictCode','id'],
    data () {
      return {
          visible:true,
          form:{
              value:undefined,
              text:undefined,
              inx:null,
              dictId:this.dictId,
              dictCode:this.dictCode,
              id:this.id
          },
          rules:{
              value:[{required:true,message:"必填项"}],
              text:[{required:true,message:"必填项"}],
              inx:[
                  { required: true, message: '必填项'},
                  { type: 'number', message: '必须为数字值'}
              ],
          }
      }
    },
    computed:{
        isEdit() {
            return this.id?true:false;
        },
        ifuse() {
            return this.ifuse
        }
    },
    methods: {
      close () {
          this.$emit('itemclose');
      },
      submit(){
          this.$refs["form"].validate((valid) => {
              if (valid) {
                  if(this.form.dictId){
                      dictApi.saveDictItem(this.form).then(res=>{
                          if(res.code === 200){
                              this.$notify({title: '成功', message: '保存成功！', type: 'success'})
                              this.$emit('itemclose');
                          }else{
                              this.$notify({title: '失败', message: res.errorMsg, type: 'error'})
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
                    if(this.form.dictId){
                        dictApi.editDictItem(this.form).then(res=>{
                            if(res.code === 200){
                                this.$notify({title: '成功', message: '修改成功！', type: 'success'})
                                this.$emit('itemclose');
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
    },
    mounted () {
        if(this.id){
            dictApi.getDictItemById(this.id).then(res=>{
                if(res.code === 200){
                    this.form = res.data;
                }
            }).catch()
        }
    }
  }
</script>