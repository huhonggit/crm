import cgAxios from '@/axios'

export default {
//-------------menu-------------
    loadMenuTree(){
        return cgAxios.post(`/auth/menu/loadMenuTree`)
    },
    handleModifyStatus(id,status){
        return cgAxios.get(`/auth/menu/updateStatus?id=`+id+"&status="+status)
    },
    saveMenu(data){
        return cgAxios.post(`/auth/menu/saveMenu`,data)
    },
    getMenu(data){
        return cgAxios.get(`/auth/menu/getMenuById?id=`+data)
    },
    delMenu(data){
        return cgAxios.get(`/auth/menu/delMenu?id=`+data)
    }


}
