import cgAxios from '@/axios'

export default {
    getNoticeInfo(){
        return cgAxios.post(`/v1/sys/getNoticeInfo`)
    },

    addNotice(data){
        return cgAxios.post(`/v1/sys/addNotice`,data)
    },
    chWhiteStatus(data){
        return cgAxios.post(`/v1/sys/chWhiteStatus`,data)
    },

    addDevice(data){
        return cgAxios.post(`/v1/sys/addDevice`,data)
    },
    deleteById(data){
        return cgAxios.post(`/v1/sys/deleteById?deviceId=`+data)
    },

    deleteWhiteDevice(data){
        return cgAxios.post(`/v1/sys/deleteWhiteDevice`,data)
    },



}
