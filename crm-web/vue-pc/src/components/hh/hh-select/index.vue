<template>
    <el-select v-model="selected" :value="value" v-bind="$attrs" v-on="$listeners"
               :clearable="true"  >
        <el-option v-for="item in items" :key="item.value" :label="item.text" :value="item.value"></el-option>
    </el-select>
</template>

<script>
    import hhApi from "../hhApi"
    export default {
        props: {
            dict: {
                type: String
            },
            value:{
            }
        },
        data:function(){
            return {
                selected:this.value,
                items:[]
            }
        },
        watch: {
            value:function (val) {
                this.selected = val;
            }
        },
        methods: {
            change: function(val) {
                this.$emit('input', val);
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