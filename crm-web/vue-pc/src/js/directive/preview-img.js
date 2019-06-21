import previewImage from './preview-image.vue'
import Vue from 'vue'
import $ from 'jquery'
const body = document.body
const id = 'previewImgNode'

Vue.directive('preview-img', (el, binding) => {
  el.onclick = function () {
    if (!document.getElementById(id)) {
      initNode(binding.value)
    }
  }
})

// 初始化节点
function initNode(src) {
  let div = document.createElement('div')
  div.setAttribute('id', id)
  body.appendChild(div)
  new Vue({
    data() {
      return {
        src
      }
    },
    template: '<previewImage @close="close" :src="src"></previewImage>',
    methods: {
      close() {
        this.$destroy()
        $(this.$el).remove()
      }
    },
    components: {
      previewImage
    }
  }).$mount('#' + id)
}
