<template>
    <!--胡红 借助vue2.4 新特性 v-bind="$attrs" v-on="$listeners"-->
    <div class="flex-column">
        <div class="flex-column">
            <el-table :data="tableData" style="width: 100%" height="100%" v-loading="loading"
                      fit highlight-current-row v-bind="$attrs" v-on="$listeners">
                <slot></slot>
            </el-table>
        </div>
        <el-pagination
          v-if="showPagination" size="small" background @size-change="handleSizeChange"
                       @current-change="handleCurrentChange" :current-page="page.pageNum"
                       :page-sizes="[10,20,50]" :page-size="page.pageSize"
                       layout="total, sizes, prev, pager, next, jumper" :total="total">
        </el-pagination>
    </div>

</template>

<script>
    import hhApi from "../hhApi"
    export default {
        props: {
            sql: { //sql代码
                type: String
            },
            showPagination:{//是否分页
                type: Boolean,
                default: true
            },
            queryParam:{//查询参数
                type: Object
            },
            autoQuery:{
                type: Boolean,
                default: true
            },
            appendOrgId:{
                type: Boolean,
                default: false
            }
        },
        data:function(){
            return {
                page: {
                    pageNum: 1,
                    pageSize: 20
                },//分页信息
                tableData:[],//表格数据
                total: null, //总条数
                loading:false
            }
        },
        methods: {
            handleSizeChange(val) {
                this.page.pageSize = val
                this.search()
            },
            handleCurrentChange(val) {
                this.page.pageNum = val
                this.search()
            },
            search:function(){
                this.loading = true;
                hhApi.querySQL(this.sql,this.queryParam,this.page,this.appendOrgId,this.showPagination).then(res =>{
                    if(res.status === 200){
                        this.tableData = res.data.list;
                        this.total = parseInt(res.data.total,10) ;
                    }else {
                        console.log(res.errorMsg)
                    }
                    this.loading = false;
                }).catch()

            }
        },
        mounted:function(){
            if(this.sql){//sql处理
                if(this.autoQuery){
                    this.search();
                }
            }

        }


    }
</script>