<!--此style不用拷贝-->
<style lang="less">
  .global-unique-class div {
    margin: 5px;
  }
</style>
<!--上方style不用拷贝-->

<template>
  <!--
    注意：
    *. 拷贝时将 global-unique-class 替换为一个新的、全局唯一的名称，避免重名导致样式冲突
 -->
  <section class="global-unique-class flex-column content-padding">
    <div style="border: solid 2px #98ffae;">
      我来组成头部。
    </div>
    <div
      class="flex-row"
      style="border: solid 2px #8896ff;">
      <div style="overflow: auto; border: solid 2px #803353; flex:0 0 150px;">
        <p
          v-for="(_,i) in new Array(50)"
          :key="i">{{ i }}. 侧边栏</p>
      </div>
      <div
        class="flex-column"
        style="border: solid 2px #ff787b;">
        <div style="border-bottom: solid 2px #000;">
          患者姓名：xxxxxx
        </div>
        <div
          class="flex-column"
          style="overflow: auto;">
          <div style="background:#f2f2f2;">
            <p><b>1. 划重点：横着排，给父元素添加 flex-row</b></p>
            <p><b>2. 划重点：竖着排，给父元素添加 flex-column</b></p>
            <p><b>让<font color="red">自己</font>撑满剩余空间：添加 flex-column 或 flex-row 均可（会影响子元素布局，参考1和2）</b></p>
            <p>通过这两个css类组合，就基本可以实现大的布局，再配合下 el-row 可实现左右对齐、分散对齐等效果 。</p>
            <hr>
            <p
              v-for="(_,i) in new Array(50)"
              :key="i">{{ i }}. 我是主体</p>
          </div>
        </div>
        <el-row
          type="flex"
          justify="space-between"
          style="border-top: solid 2px #000;">
          <div>
            <el-button
              type="danger"
              size="small">左边
            </el-button>
          </div>
          <div>
            <el-button
              type="primary"
              size="small">右边
            </el-button>
          </div>
        </el-row>
      </div>
    </div>
    <div style="border: solid 2px red;">我是页脚</div>
  </section>
</template>
