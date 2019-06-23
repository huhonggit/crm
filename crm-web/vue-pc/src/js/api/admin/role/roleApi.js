import cgAxios from '@/axios'

export default {

    loadRoles(data){
        return cgAxios.post(`/auth/role/loadRoles`,data)
    },
    saveRole(data){
        return cgAxios.post(`/auth/role/saveRole`,data)
    },
    getRoleWithMenu(data){
        return cgAxios.get(`/auth/role/getRoleVoById?id=`+data)
    },

}
