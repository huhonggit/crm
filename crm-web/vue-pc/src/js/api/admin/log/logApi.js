import cgAxios from '@/axios'

export default {
//-------------menu-------------
    loadLogs(data){
        return cgAxios.post(`/v1/admin/log/loadLogs`,data)
    },

}
