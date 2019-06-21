import cgAxios from '@/axios'

export default {
    query(data){
        return cgAxios.post(`/v1/app/register/query`,data)
    },
    handleModifyStatus(id,status){
        return cgAxios.get(`/v1/app/register/updateStatus?id=`+id+"&status="+status)
    },
    handleModifySuperStatus(id,status){
        return cgAxios.get(`/v1/app/register/updateSuperStatus?id=`+id+"&status="+status)
    },
    loadAddressDict(data){
        return cgAxios.post(`/v1/app/address/loadAddressDict`,data)
    },
    syncAddress(){
        return cgAxios.get(`/v1/app/address/syncAddress`)
    }

}
