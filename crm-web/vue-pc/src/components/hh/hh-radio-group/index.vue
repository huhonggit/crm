<template>
    <!--胡红 借助vue2.4 新特性 v-bind="$attrs" v-on="$listeners"-->
    <el-radio-group v-model="values" :value="value" @change="handleChange" v-bind="$attrs" v-on="$listeners">
        <el-radio-button :key="item.text" v-for="item in items" v-if="radioType === 'button'" :label=item.value>{{item.text}}</el-radio-button>
        <el-radio :key="item.text" v-for="item in items" v-if="radioType !== 'button'" :label=item.value>{{item.text}}</el-radio>
    </el-radio-group>
</template>


<script>
    import hhApi from "../hhApi"
    export default {
        props: {
            dict: {
                type: String
            },
            radioType: {
                type: String
            },
            value:{
                type: Object
            }
        },
        data:function(){
            return {
                items:[],
                values:undefined
            }
        },
        methods:{
            handleChange(value) {
                this.$emit("input", value);
            }
        },
        watch: {
            value:function (val) {
                this.values = val;
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