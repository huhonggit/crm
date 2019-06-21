<template>
  <!--胡红 借助vue2.4 新特性 v-bind="$attrs" v-on="$listeners"-->
  <el-checkbox-group v-model="values" :value="value" @change="handleChange" v-bind="$attrs" v-on="$listeners">
    <el-checkbox v-for="item in items" :key="item.text" :label=item.value>{{item.text}}</el-checkbox>
  </el-checkbox-group>
</template>


<script>
  import hhApi from '../hhApi'

  export default {
    props: {
      dict: {
        type: String,
      },
      value: {
        type: Array,
      },
    },
    data: function () {
      return {
        items: [],
        values: [],
      }
    },
    methods: {
      handleChange (value) {
        this.$emit('input', value)
      },
    },
    watch: {
      value: function (val) {
        this.values = val
      },
    },
    mounted: function () {
      if (this.dict) {//字典处理
        hhApi.loadDictVoByCode(this.dict).then(res => {
          this.items = res.data.itemList
        }).catch()
      }

    },

  }
</script>