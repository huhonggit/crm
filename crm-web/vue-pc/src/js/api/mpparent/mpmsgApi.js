import cgAxios from '@/axios'

export default {


    getMsgs(data) {
        return cgAxios.post(
            `/v1/mp_parent/msgs`, data
        )
    },

    loadMsg(id) {
        return cgAxios.get(
            `/v1/mp_parent/msg/` + id
        )
    },


    upload(fileFormData, requestConfig) {
        return cgAxios.post(
            `/v1/mp_parent/msg/upload_img/`, fileFormData, requestConfig
        )
    },

    addMsg(data) {
        return cgAxios.post(
            `/v1/mp_parent/msg`, data
        )
    },


    changeState(id, state) {
        return cgAxios.put(
            `/v1/mp_parent/msg/` + id + "/state/" + state
        )
    },


    saveAutoMsg(data) {
        return cgAxios.post(
            `/v1/mp_parent/auto/msg/`, data
        )
    },


    getAutoMsg() {
        return cgAxios.get(
            `/v1/mp_parent/auto/msg/`
        )
    },
}
