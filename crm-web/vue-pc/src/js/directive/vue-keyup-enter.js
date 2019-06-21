
import Vue from 'vue'
import $ from 'jquery'

Vue.directive('keyup-enter', {
    bind: function (el, binding) {
        $(el).on('keyup', (ev)=> {
            if (ev.keyCode === 13) {
                binding.value();
            }
        })
    }
});