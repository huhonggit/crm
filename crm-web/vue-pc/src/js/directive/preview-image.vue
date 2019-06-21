<template>
  <div class="preview-image-wrapper">
    <div class="node" :class={anim:this.anim} ref="node">
      <img @load="initPosition" :src="realSrc" ref="img" class="J-preview-image"/>
    </div>
    <div class="control">
      <a href="javascript:void(0)" @click="reset">
        <b class="icon">
          <i class="el-icon-refresh"></i>
        </b>
        <span class="text">局中</span>
      </a>
      <a href="javascript:void(0)" @click="changeTurn(-0.25)">
        <b class="icon">
          <svg version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px" viewBox="0 0 530.6 476.3" style="enable-background:new 0 0 530.6 476.3;" xml:space="preserve">
<path fill="currentColor" d="M65.5,245.7c-0.4-25.3,3.2-50.6,10.7-75.3c38.5-126,169-197.9,291.6-160.4s190.6,170,152,296
	c-38.5,126-169,197.9-291.6,160.4c-36.4-11.1-68.7-30.9-95-57.5c-6.2-6.3-6.1-16.5,0.1-22.8c6.2-6.3,16.3-6.4,22.5-0.1
	c22.7,23,50.5,40,81.9,49.6c105.7,32.3,218.4-29.8,251.7-138.7S464,73.2,358.4,40.9S139.9,70.6,106.6,179.6
	c-6.8,22.1-9.8,44.7-9.3,67.2l39.5-39.5c6-6,15.9-5.9,22.1,0.3c6.2,6.2,6.4,16.1,0.3,22.1l-61.8,61.8c-0.7,1.7-1.8,3.3-3.2,4.7
	c-6,6-15.9,5.9-22.1-0.3L4.8,228.6c-6.2-6.2-6.4-16.1-0.3-22.1c6-6,15.9-5.9,22.1,0.3L65.5,245.7z"/>
</svg>
        </b>
        <span class="text">向左旋转</span>
      </a>
      <a href="javascript:void(0)" class="clockwise" @click="changeTurn(0.25)">
        <b class="icon">
          <svg version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" x="0px" y="0px" viewBox="0 0 530.6 476.3" style="enable-background:new 0 0 530.6 476.3;" xml:space="preserve">
<path fill="currentColor" d="M65.5,245.7c-0.4-25.3,3.2-50.6,10.7-75.3c38.5-126,169-197.9,291.6-160.4s190.6,170,152,296
	c-38.5,126-169,197.9-291.6,160.4c-36.4-11.1-68.7-30.9-95-57.5c-6.2-6.3-6.1-16.5,0.1-22.8c6.2-6.3,16.3-6.4,22.5-0.1
	c22.7,23,50.5,40,81.9,49.6c105.7,32.3,218.4-29.8,251.7-138.7S464,73.2,358.4,40.9S139.9,70.6,106.6,179.6
	c-6.8,22.1-9.8,44.7-9.3,67.2l39.5-39.5c6-6,15.9-5.9,22.1,0.3c6.2,6.2,6.4,16.1,0.3,22.1l-61.8,61.8c-0.7,1.7-1.8,3.3-3.2,4.7
	c-6,6-15.9,5.9-22.1-0.3L4.8,228.6c-6.2-6.2-6.4-16.1-0.3-22.1c6-6,15.9-5.9,22.1,0.3L65.5,245.7z"/>
</svg>
        </b>
        <span class="text">向右旋转</span>
      </a>
      <a href="javascript:void(0)" @click="changeScale(0.25)">
        <b class="icon"><i class="el-icon-zoom-in"></i></b>
        <span class="text">放大</span>
      </a>
      <a href="javascript:void(0)" @click="changeScale(-0.25)">
        <b class="icon"><i class="el-icon-zoom-out"></i></b>
        <span class="text">缩小</span>
      </a>
      <a :href="src" target="_blank">
        <b class="icon"><i class="el-icon-view"></i></b>
        <span class="text">查看原始文件</span>
      </a>
    </div>
    <a class="close" @click="close">
      <i class="el-icon-close"></i>
    </a>
  </div>
</template>

<style lang="less">
  .preview-image-wrapper {
    user-select: none;
    position: fixed;
    text-align: center;
    left: 0;
    top: 0;
    right: 0;
    bottom: 0;
    overflow: auto;
    background: rgba(0, 0, 0, 0.6);
    z-index: 99999;
    .node {
      position: absolute;
      transform: translateX(0) translateY(0) rotate(0) scale(1, 1);
      padding: 1em;
      &.anim {
        transition: transform 0.3s;
      }
    }
    img {
      transition: transform 0.3s;
    }
    .control {
      position: fixed;
      left: 50%;
      bottom: 1em;
      transform: translateX(-50%);
      background: rgba(0, 0, 0, 0.5);
      text-align: center;
      color: #000;
      border-radius: 0.5em;
      display: flex;
      overflow: hidden;

      a {
        color: #fff;
        padding: 1.1em 1.8em 0.8em 1.8em;
        display: inline-block;
        &:hover {
          background: rgba(255, 255, 255, 0.2);
        }
        &.clockwise svg {
          transform: scaleX(-1);
        }
      }
      .icon {
        display: block;
        height: 28px;
        text-align: center;
        i {
          font-size: 28px;
          display: block;
        }
        svg {
          width: 26px;
          height: 26px;
        }
      }
      span.text {
        display: block;
        text-align: center;
        padding-top: .5em;
      }
    }
    .close {
      position: fixed;
      right: 0;
      top: 0;
      background: rgba(0, 0, 0, 0.2);
      color: #fff;
      font-size: 60px;
      width: 100px;
      height: 80px;
      display: flex;
      justify-content: center;
      align-items: center;
      text-shadow: 0 0 1px #000;
    }
  }
</style>

<script>
  import $ from 'jquery'
  const $window = $(window)
  let initX, initY, initPageX, initPageY

  export default {
    props: {
      src: {
        type: String,
        require: true,
      },
    },
    data () {
      return {
        turn: 0,
        x: 0,
        y: 0,
        scale: 1,
        anim: false,
      }
    },
    computed: {
      realSrc () {
        let src = this.src + '?x-oss-process=image/resize,m_mfit,w_1280'
        // 如果是谷歌浏览器，则使用更小体积的webp
        if (/Chrome/i.testModel(navigator.userAgent)) {
          return src + '/format,webp'
        }
        return src
      },
    },
    methods: {
      changeTurn (turn) {
        this.turn += turn
        //旋转的时候增加动画
        this.anim = true
        this.$nextTick(() => {
          this.position()
        })
      },
      reset () {
        this.turn = 0
        this.x = 0
        this.y = 0
        this.scale = 1
        this.anim = true
        this.$nextTick(() => {
          this.initPosition()
        })
      },
      changeScale (scale) {
        this.scale += scale + (this.scale * scale)
        if (this.scale <= 0.25) {
          this.scale = 0.25
        }
        if (this.scale > 5) {
          this.scale = 5
        }
        this.anim = true
        this.$nextTick(() => {
          this.position()
        })
      },
      initPosition () {
        let $img = $(this.$refs.img)
        this.x = $window.width() / 2 - $img.width() / 2
        this.position()
      },
      position () {
        if (!this.$refs.node) {
          return
        }
        let $node = $(this.$refs.node)
        $node[0].style.transform = `translateX(${this.x}px) translateY(${
          this.y
          }px) rotate(${this.turn}turn) scale(${this.scale}, ${this.scale})`
      },
      close () {
        this.unbind()
        this.$refs.node.removeEventListener('mousedown', this.bind)
        this.$emit('close')
      },
      bind (ev) {
        if (ev.target !== this.$refs.node && ev.target !== this.$refs.img) {
          return
        }
        let $node = $(this.$refs.node)
        this.anim = false
        let TRANSFORM = $node[0].style.transform.match(/((?:-)?\d+)/g)

        if (!Array.isArray(TRANSFORM)) {
          return
        }

        let unit = TRANSFORM.map(num => parseInt(num, 10))

        //记录初始点击的坐标
        initX = unit[0]
        initY = unit[1]

        //记录初始的pageX，pageY
        initPageX = ev.pageX
        initPageY = ev.pageY
        window.addEventListener('mousemove', this.mousemove)
        window.addEventListener('mouseup', this.unbind)
      },
      unbind () {
        window.removeEventListener('mouseup', this.unbind)
        window.removeEventListener('mousemove', this.mousemove)
      },
      mousemove (ev) {
        this.x = initX + (ev.pageX - initPageX)
        this.y = initY + (ev.pageY - initPageY)
        this.position()
      },
    },
    mounted () {
      this.$refs.img.addEventListener(
        'dragstart',
        ev => {
          ev.preventDefault()
        },
        true,
      )
      // 当鼠标按下时
      this.$refs.node.addEventListener('mousedown', this.bind, {capture: true})
    },
  }
</script>
