<template>
    <div class="flex-row content-padding">
        <div style="width: 120px;">
            <cg-button type="text" size="small" @click="addCatalog" perms="addCatalog">新增目录</cg-button>
            <el-tree :data="catalog" :highlight-current="true" :props="defaultProps" node-key="id" default-expand-all
                     :expand-on-click-node="false" @node-click="handleNodeClick"></el-tree>
        </div>
        <div style="width: 450px;margin-left: 3px;" class="flex-column">
           <el-form :inline="true">
               <el-input v-model="dictSearch.name" :clearable="true" style="width: 200px" size="small" placeholder="字典名、代码"></el-input>
               <el-button style="margin-left: 5px" size="small" @click="searchDict" >查询</el-button>
               <cg-button type="primary" style="margin-left: 5px" size="small" @click="addDict" perms="addDict">新增</cg-button>
               <!--<el-button type="primary" style="margin-left: 5px" size="small" @click="evictAllChache" >清除所有缓存</el-button>-->
           </el-form>
            <div style="margin-top: 5px;margin-bottom: 5px" class="flex-column">
                <el-table
                        v-loading="dictLoading"
                        :data="dictData"
                        height="100%"
                        @row-click="handleDictClick"
                        highlight-current-row
                        style="width: 100%" border>
                    <el-table-column prop="code" label="编码" width="190">
                        <template slot-scope="scope">
                            <a href="javascript:void(0)" @click="addDict(scope.row)">{{scope.row.code}}</a>
                        </template>
                    </el-table-column>
                    <el-table-column prop="name" label="名称" width="120"/>
                    <el-table-column prop="type" label="类型" width="50"/>
                    <el-table-column prop="info" label="说明"/>
                    <!--<el-table-column prop="id" label="操作" width="50">-->
                        <!--<template slot-scope="scope">-->
                            <!--<a href="javascript:void(0)" @click="evictChache(scope.row.code)">清除</a>-->
                        <!--</template>-->
                    <!--</el-table-column>-->
                </el-table>
            </div>
            <el-pagination
                    background
                    :current-page="page.pageNum"
                    :page-size="page.pageSize"
                    :total="total"
                    @current-change="handleCurrentChange"
                    layout="total, prev, pager, next">
            </el-pagination>
        </div>
        <div class="flex-column" style="margin-left: 3px;">
            <el-form :inline="true">
                <cg-button type="primary"  style="margin-left: 5px" size="small" @click="addDictItem" perms="addDictItem">新增明细</cg-button>
            </el-form>
            <div style="margin-top: 5px;margin-bottom: 5px" class="flex-column">
                <el-table
                        v-loading="dictItemLoading"
                        :data="dictItemData"
                        height="100%"
                        highlight-current-row
                        style="width: 100%" border>
                    <el-table-column prop="value" label="字典值" width="180">
                        <template slot-scope="scope">
                            <span v-if="clickDictType === 'SQL'" >{{scope.row.value}}</span>
                            <a href="javascript:void(0)" v-if="clickDictType === '字典'" @click="addDictItem(scope.row)">{{scope.row.value}}</a>

                        </template>
                    </el-table-column>
                    <el-table-column prop="text" label="字典名" width="180"/>
                    <el-table-column prop="inx" label="顺序"/>
                </el-table>
            </div>
        </div>
        <transition name="el-zoom-in-center">
            <dict-dialog v-if="dictDialogVisible"  @close="close"
                     :pid="clickCatalogId" :id="dictId"></dict-dialog>
        </transition>
        <transition name="el-zoom-in-center">
            <dict-item-dialog  v-if="dictItemDialogVisible"  @itemclose="itemclose"
                     :dictId="clickDictId" :id="dictItemId" :dictCode="clickDictCode"></dict-item-dialog>
        </transition>
    </div>
</template>

<script>
    import dictApi from './dictApi.js'
    import dictDialog from './dict-dialog.vue'
    import dictItemDialog from './dict-item-dialog.vue'
    export default {
        components: {
            'dict-dialog': dictDialog,
            'dict-item-dialog': dictItemDialog},
        data() {
            return {
                catalog: [],
                catalogForm:{
                    name:undefined
                },
                dictSearch:{
                    pid:undefined,
                    name:undefined
                },
                clickCatalogId:undefined,
                dictId:undefined,
                dictItemId:undefined,
                clickDictId:undefined,
                clickDictCode:undefined,
                clickDictType:undefined,
                page: {
                    pageNum: 1,
                    pageSize: 20,
                },
                total:0,
                dictDialogVisible:false,
                dictItemDialogVisible:false,
                dictData:[],
                dictItemData:[],
                dictLoading:false,
                dictItemLoading:false,
                defaultProps: {
                    children: 'children',
                    label: 'name'
                }
            };
        },
        methods: {
            handleCurrentChange(val) {
                this.page.pageNum = val
                this.searchDict()
            },
            handleNodeClick(data) {
                this.dictSearch.pid = data.id;
                this.clickCatalogId = data.id;
                this.searchDict();
            },
            addCatalog() {
                this.$prompt('请输入目录名', '新增目录', {
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
                    inputErrorMessage: '目录名不能为空',
                }).then(({value}) => {
                    dictApi.saveCatalog(value).then(res => {
                        if(res.code === 200){
                            this.$notify({title: '成功', message: '保存目录成功！', type: 'success'})
                            setTimeout(() => {
                                this.loadCatalog();
                            }, 300);

                        }else{
                            this.$notify({title: '失败', message: res.msg, type: 'error'})
                        }
                    }).catch();
                }).catch(() => { })
            },
            loadCatalog(){
                dictApi.loadCatalog().then(res => {
                    this.catalog=res.data;
                }).catch()
            },
            searchDict(){
                let req = {};
                req.obj = this.dictSearch;
                req.page = this.page
                this.dictLoading = true;
                dictApi.searchDict(req).then(res => {
                    this.dictLoading = false;
                    this.dictData = res.data.list
                    this.total = parseInt(res.data.total,10)
                }).catch()
            },
            addDict(row){
                if(row){
                    this.dictId = row.id;
                }
                if(this.clickCatalogId){
                    this.dictDialogVisible = true;
                }else {
                    this.$message.error('请选择左侧目录');
                }
            },
            addDictItem(row){
                if(this.clickDictType === 'SQL'){
                    this.$message.error('sql字典表不允许添加明细');
                    return
                }

                if(row){
                    this.dictItemId = row.id;
                }
                if(this.clickDictId){
                    this.dictItemDialogVisible = true;
                }else {
                    this.$message.error('请选择中间具体字典');
                }
            },
            close(){
                this.dictDialogVisible = false
                setTimeout(() => {
                    this.searchDict()
                }, 300);
            },
            handleDictClick(row){
                this.clickDictId = row.id;
                this.clickDictCode = row.code;
                this.clickDictType = row.type;
                this.searchDictItem();
            },
            searchDictItem(){
                if(this.clickDictId){
                    this.dictItemLoading = true;
                    dictApi.searchDictItem(this.clickDictId).then(res => {
                        this.dictItemData = res.data
                        this.dictItemLoading = false;
                    }).catch()
                }
            },
            itemclose(){
                this.dictItemDialogVisible = false
                setTimeout(() => {
                    this.searchDictItem()
                }, 300);

            },
            // evictChache(code){
            //     dictApi.evictChache(code).then(()=>{
            //         this.$message.success('清楚成功！');
            //     }).catch()
            // },
            // evictAllChache(){
            //     dictApi.evictAllChache().then(res=>{
            //         this.$message.success(res.msg);
            //     }).catch()
            // },
        },
        mounted () {
            this.loadCatalog();
        }
    };
</script>

<style lang="less">

</style>
