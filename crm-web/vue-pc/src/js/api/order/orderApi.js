import axios from '@/axios'

export default {

    /**
     * 查询订单
     * @param data
     * @returns {*}
     */
    search(data) {
        return axios.post(
            `/v1/admin/order/search`, data
        )
    },
    /**
     *
     * @param id
     * @returns {*}
     */
    queryDetails(id) {
        return axios.get(
            `/v1/admin/order/` + id
        )
    },
    /**
     *
     * @param data
     * @returns {*}
     */
    searchCompensate(data) {
        return axios.post(
            `/v1/admin/order/compensate/search`, data
        )
    },


    compensateProcess(id) {
        return axios.post(
            `/v1/admin/order/compensate/process?id=` + id
        )
    },
}
