import cgSelect from './hh-select/index.vue'
import cgRadioGroup from './hh-radio-group/index.vue'
import cgCheckboxGroup from './hh-checkbox-group/index.vue'
import cgTableGrid from './hh-table-grid/index.vue'
import cgDictTableColumn from './hh-dict-table-column/index.vue'
import cgButton from './hh-button/index.vue'

export default {
    install: (Vue) => {
        Vue.component('hh-select',cgSelect)
        Vue.component('hh-checkbox-group',cgCheckboxGroup)
        Vue.component('hh-radio-group',cgRadioGroup)
        Vue.component('hh-table-grid',cgTableGrid)
        Vue.component('hh-dict-table-column',cgDictTableColumn)
        Vue.component('hh-button',cgButton)
    }
}