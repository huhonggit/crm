import cgAxios from '@/axios'

export default {

    saveUser(data){
        return cgAxios.post(`/auth/user/saveUser`,data)
    },
    queryUser(data){
        return cgAxios.post(`/auth/user/queryUser`,data)
    },
    getUserById(data){
        return cgAxios.get(`/auth/user/getUserById?id=`+data)
    },
    loadMenusByOnlineUser(){
        return cgAxios.post(`/auth/user/loadMenusByOnlineUser`)
    },
    updatePwd(oldPwd,newPwd){
        return cgAxios.get(`/auth/user/updatePwd?oldPwd=`+oldPwd+"&newPwd="+newPwd)
    },
    editPassword(data){
        return cgAxios.post(`/auth/user/editPassword`,data)
    },
    handleModifyStatus(id,status){
        return cgAxios.get(`/auth/user/updateStatus?id=`+id+"&status="+status)
    },



}
