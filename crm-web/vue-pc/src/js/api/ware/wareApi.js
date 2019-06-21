import axios from '@/axios'

export default {

    /**
     * 查询订单
     * @param data
     * @returns {*}
     */
    searchWares(params) {
        return axios.post(
            `/v1/admin/ware/search`, params
        )
    },


    searchWareItems(params) {
        return axios.post(
            `/v1/admin/ware/item/search`, params
        )
    },


    searchRebates(params) {
        return axios.post(
            `/v1/admin/ware/rebates/search`, params
        )
    },


    searchChannels(params) {
        return axios.post(
            `/v1/admin/ware/channel/search`, params
        )
    },

    searchPages(params) {
        return axios.post(
            `/v1/admin/ware/page/search`, params
        )
    },


    searchPageWares(params) {
        return axios.post(
            `/v1/admin/ware/pageware/search`, params
        )
    },
    searchLevelPages(params) {
        return axios.post(
            `/v1/admin/ware/level/page/search`, params
        )
    },
    searchReals(params) {
        return axios.post(
            `/v1/admin/ware/real/search`, params
        )
    },
}
