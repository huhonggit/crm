import cgAxios from '@/axios'

export default {
//-------------menu-------------
    loadMenuTree(){
        return cgAxios.post(`/v1/admin/menu/loadMenuTree`)
    },
    handleModifyStatus(id,status){
        return cgAxios.post(`/v1/admin/menu/updateStatus?id=`+id+"&status="+status)
    },
    saveMenu(data){
        return cgAxios.post(`/v1/admin/menu/saveMenu`,data)
    },
    getMenu(data){
        return cgAxios.post(`/v1/admin/menu/getMenuById?id=`+data)
    },
    delMenu(data){
        return cgAxios.post(`/v1/admin/menu/delMenu?id=`+data)
    }


}
