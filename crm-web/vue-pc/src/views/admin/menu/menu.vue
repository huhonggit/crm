<template>
    <div  class="flex-column">
        <transition name="el-zoom-in-center">
            <menu-dialog v-if="menuDialogVisible"
                     :menuId="menuId"
                     :type="dialogType"
                     :menuType="menuType"
                     :pName="pName"
                     @refresh="search"
                     @close="close"></menu-dialog>
        </transition>
        <div>
            <el-row :gutter="24">
                <el-col :span="4">
                    <el-button type="primary" size="small" @click="search">刷新</el-button>
                </el-col>
                <!--<el-col :span="8">-->
                    <!--<el-upload-->
                            <!--ref="upload"-->
                            <!--action="/v1/admin/menu/uploadMenu"-->
                            <!--accept=".json"-->
                            <!--:multiple="false"-->
                            <!--:file-list="fileList"-->
                            <!--:on-success="uploadMenuSuccess"-->
                            <!--:on-error="uploadMenuError"-->
                            <!--:auto-upload="false">-->
                        <!--<cg-button slot="trigger" size="small" type="primary" perms="upload">选取文件</cg-button>-->
                        <!--<cg-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload" perms="upload">上传到服务器</cg-button>-->
                    <!--</el-upload>-->
                <!--</el-col>-->

            </el-row>

        </div>
        <div class="flex-column">
            <el-table :data="menuTree" ref="tree" border height="100%" width="100%" :expand-row-keys="['root']" row-key="id" class="flex-column">
                <el-table-column prop="name" label="资源名称"  min-width="220"/>
                <el-table-column prop="router" header-align="center" align="center" min-width="180" label="路由"/>
                <el-table-column prop="uri" header-align="center" align="center" min-width="50" label="uri资源"/>
                <el-table-column prop="icon" header-align="center" align="center" min-width="130" label="图标">
                    <template slot-scope="scope">
                        {{scope.row.icon}}
                        <svg  class="aside-svg">
                            <use :xlink:href="'#svg-'+scope.row.icon"></use>
                        </svg>
                    </template>
                </el-table-column>
                <hh-dict-table-column  prop="type" dict="menu.type" align="center" min-width="80" label="类型"/>
                <el-table-column prop="sort" header-align="center" align="center" min-width="80" label="显示顺序"/>
                <el-table-column prop="status" header-align="center" align="center" min-width="80" label="状态">
                    <template slot-scope="scope">
                        <el-tag size="small" v-if="scope.row.status=='disable'" type="danger">停用</el-tag>
                        <el-tag size="small" v-if="scope.row.status=='enable'" type="success">启用</el-tag>
                    </template>
                </el-table-column>
                <el-table-column prop="op" header-align="center" align="center" min-width="110px" label="操作">
                    <template slot-scope="scope">
                        <el-button  @click="addMenu(scope.row,'edit')" type="text" size="mini" perms="edit"> 编辑</el-button>
                        <el-button  @click="addMenu(scope.row,'add')" v-if="scope.row.type !== 'operation'" type="text" size="mini" perms="add">新增下级</el-button>
                        <el-button  @click="delMenu(scope.row)" type="text" style="color: red" size="mini" perms="delete"> 删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
    </div>
</template>
<script>
    import menuApi from "../../../js/api/admin/menu/menuApi";
    import menuDialog from './menu-dialog.vue'
    export default {
        components: {
            'menu-dialog': menuDialog,
        },
        name: 'menu_list',
        //表格各行的数据
        data() {
            return {
                menuType:undefined,
                menuTree:[],
                menuDialogVisible:false,
                menuId:undefined,
                dialogType:undefined,
                pName:undefined,
                treeKey:"id",
                parentKey:"parentId",
                childKey:"children",
                expandAll:true,
                fileList:[]
            };
        },
        methods:{
            submitUpload() {
                this.$refs.upload.submit();
            },
            uploadMenuSuccess(response){
                if(response.code === 200){
                    this.$message.success("上传成功")
                    this.fileList=[]
                }else {
                    this.$message.error("处理文件异常")
                }
            },
            uploadMenuError(){
                this.$message.error("上传失败")
            },
            addMenu(row,type){
                this.menuId = row.id;
                this.menuDialogVisible = true;
                this.dialogType = type;
                this.menuType = row.type
                this.pName = row.name;
            },
            search(){
                menuApi.loadMenuTree().then(res => {
                    this.menuTree = res.data;
                }).catch()
            },

            close(){
                this.menuDialogVisible = false;
                setTimeout(() => {
                    this.search()
                }, 300);
            },
            delMenu(row){
                if(row){
                    menuApi.delMenu(row.id).then(res =>{
                        if(res.code === 200){
                            this.$message({
                                message: '操作成功',
                                type: 'success'
                            })

                            setTimeout(() => {
                                this.search()
                            }, 300);
                        }
                    }).catch()
                }
            },
            handleModifyStatus(row,status){
                menuApi.handleModifyStatus(row.id,status).then(res =>{
                    if(res.code === 200){
                        this.$message({
                            message: '操作成功',
                            type: 'success'
                        })
                        row.status = status
                    }
                }).catch()

            }
        },
        mounted () {
            this.search()
        }
    };
</script>

<style lang="less">
</style>