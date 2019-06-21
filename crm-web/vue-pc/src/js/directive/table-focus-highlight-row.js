
import $ from 'jquery'
import Vue from 'vue'
let sel = ':input:enabled:not(button)'

Vue.directive('table-focus-highlight-row', {
  bind: function (el) {
    $(el).on('focus', sel, (event) => {
      $(event.target).parents('tr').eq(0).addClass('focus-highlight')
    }).on('blur', sel, (event) => {
      $(event.target).parents('tr').eq(0).removeClass('focus-highlight')
    })
  },
})
