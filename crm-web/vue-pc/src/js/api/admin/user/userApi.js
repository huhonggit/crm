import cgAxios from '@/axios'

export default {

    saveUser(data){
        return cgAxios.post(`/v1/admin/user/saveUser`,data)
    },
    queryUser(data){
        return cgAxios.post(`/v1/admin/user/queryUser`,data)
    },
    getUserById(data){
        return cgAxios.post(`/v1/admin/user/getUserById?id=`+data)
    },
    loadMenusByOnlineUser(){
        return cgAxios.post(`/v1/admin/user/loadMenusByOnlineUser`)
    },
    updatePwd(oldPwd,newPwd){
        return cgAxios.post(`/v1/admin/user/updatePwd?oldPwd=`+oldPwd+"&newPwd="+newPwd)
    },
    editPassword(data){
        return cgAxios.post(`/v1/admin/user/editPassword`,data)
    },
    handleModifyStatus(id,status){
        return cgAxios.post(`/v1/admin/user/updateStatus?id=`+id+"&status="+status)
    },



}
