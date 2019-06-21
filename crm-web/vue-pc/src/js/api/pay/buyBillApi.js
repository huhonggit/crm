import axios from '@/axios'

export default {

    /**
     * 查询订单
     * @param data
     * @returns {*}
     */
    search() {
        return axios.get(
            `/v1/admin/buy/bill`
        )
    },


    insert(data) {
        return axios.post(
            `/v1/admin/buy/bill`, data
        )
    },


    getById(id) {
        return axios.get(
            `/v1/admin/buy/bill/` + id
    )
    },
}
