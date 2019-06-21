<template>
    <!--胡红 借助vue2.4 新特性 v-bind="$attrs" v-on="$listeners"-->
    <el-table-column :prop="prop" v-bind="$attrs">
        <template slot-scope="scope">
                <span :key="it.text" v-for="it in items" v-if="scope.row[prop]===it.value">{{it.text}}</span>
        </template>
    </el-table-column>
</template>

<script>
    import hhApi from "../hhApi"
    export default {
        props: {
            prop: {
                type: String
            },
            dict: {
                type: String
            },
        },
        data:function(){
            return {
                items:[]
            }
        },
        mounted:function(){
            if(this.dict){//字典处理
                hhApi.loadDictVoByCode(this.dict).then(res =>{
                    this.items = res.data.itemList;
                }).catch();
            }

        }


    }
</script>