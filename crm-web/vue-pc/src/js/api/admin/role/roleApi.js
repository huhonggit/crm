import cgAxios from '@/axios'

export default {

    loadRoles(data){
        return cgAxios.post(`/v1/admin/role/loadRoles`,data)
    },
    saveRole(data){
        return cgAxios.post(`/v1/admin/role/saveRole`,data)
    },
    getRoleWithMenu(data){
        return cgAxios.post(`/v1/admin/role/getRoleVoById?id=`+data)
    },

}
