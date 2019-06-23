
import Vue from 'vue'
import moment from 'moment'

Vue.filter('formatDate', function (value) {
  let date = new Date(value)
  return moment(date).format('YYYY-MM-DD HH:mm:ss')
})

