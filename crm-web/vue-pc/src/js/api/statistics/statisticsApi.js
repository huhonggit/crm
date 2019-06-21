import cgAxios from '@/axios'

export default {


    loadRetention(data){
        return cgAxios.post(`/v1/statistics/user/loadRetention`,data)
    },
    loadUserBase(data){
        return cgAxios.post(`/v1/statistics/user/loadUserBase`,data)
    },
    loadUserDevice(data){
        return cgAxios.post(`/v1/statistics/user/loadUserDevice`,data)
    },
    loadNewLost(data){
        return cgAxios.post(`/v1/statistics/user/loadNewLost`,data)
    },

    loadActive(data){
        return cgAxios.post(`/v1/statistics/user/loadActive`,data)
    },

    loadTrans(data){
        return cgAxios.post(`/v1/statistics/pay/loadTrans`,data)
    },
    loadNews(data){
        return cgAxios.post(`/v1/statistics/pay/loadNews`,data)
    },
    loadTypes(data){
        return cgAxios.post(`/v1/statistics/pay/loadTypes`,data)
    },
    loadContents(data){
        return cgAxios.post(`/v1/statistics/pay/loadContents`,data)
    },
    loadInterval(data){
        return cgAxios.post(`/v1/statistics/pay/loadInterval`,data)
    },



}
