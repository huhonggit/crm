# 基础环境

参见package.json中的依赖。

# 打包

在 `.gitlab-ci.yml` 中对应处添加：`sh pc/deploy_vue.sh`：

# 关于SVG

不要用 `<img>` 来加载svg图标，而应采用下方方式。

```html
<template>
  <div>
    <!-- 别忘记添加 svg- 前缀 -->
    <svg><use xlink:href="#svg-abcdef"></use></svg>
  </div>
</template>

<script>
  import '@/assets/abcdef.svg'
</script>


```
