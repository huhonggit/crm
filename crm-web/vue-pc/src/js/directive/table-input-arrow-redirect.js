/**
 * 表格中的input，按方向键可以跳转
 */

import $ from 'jquery'
import Vue from 'vue'

let cl
let sel = ':input:enabled:not(button)'

Vue.directive('table-input-arrow-redirect', (el) => {
  clearTimeout(cl)
  let $el = $(el)
  $el.off('keydown.tableInputArrowRedirect')
  $el.on('keydown.tableInputArrowRedirect', sel, (ev) => {
    clearTimeout(cl)
    cl = setTimeout(() => {
      let keyCode = ev.keyCode
      let isSelect = ev.target.nodeName === 'SELECT'
      let $target = $(ev.target)
      let $table = $target.parents('table').eq(0)
      let $tr = $target.parents('tr').eq(0)
      let $td = $target.parents('td').eq(0)
      if (!$td[0]) return
      let currentRowIndex = $tr[0].rowIndex
      let currentCellIndex = $td[0].cellIndex
      let $allInput = $tr.find(sel)
      let $allTableInput = $table.find(sel)
      let inputIndex = $allInput.index($target)

      // 禁止el-input切换，因为el-input一般伴随着自动搜索，上下键与el-input本身的行为相冲突
      if ($target.parents('.el-input').length > 0 &&
        (keyCode === 38 || keyCode === 40)) {
        return
      }

      let index = $allTableInput.index($allInput.eq(inputIndex))
      switch (keyCode) {
        // Left
        case 37: {
          $allTableInput.eq(index - 1).focus()
          break
        }
        // Right
        case 13:
        case 39:
          if ($allTableInput.eq(index + 1).length > 0) {
            $allTableInput.eq(index + 1).focus()
          } else {
            $allTableInput.eq(0).focus()
          }
          break
        // UP
        case 38: {
          if (isSelect) {
            break
          }
          let prevTR
          // 循环找上一个TR且匹配条件的:input
          do {
            currentRowIndex--
            prevTR = $table[0].rows[currentRowIndex]
            if (prevTR) {
              let input = $(prevTR.cells[currentCellIndex]).find(sel).eq(0)
              if (input[0]) {
                input.focus()
                break
              }
            }
          } while (prevTR)
          break
        }
        // DOWN
        case 40: {
          if (isSelect) {
            break
          }
          let nextTR
          do {
            currentRowIndex++
            nextTR = $table[0].rows[currentRowIndex]
            if (nextTR) {
              let input = $(nextTR.cells[currentCellIndex]).find(sel).eq(0)
              if (input[0]) {
                input.focus()
                break
              }
            }
          } while (nextTR)
          break
        }
      }
    }, 0)
  })
})
