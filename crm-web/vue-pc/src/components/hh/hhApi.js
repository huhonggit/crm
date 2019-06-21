import cgAxios from '@/axios'

export default {
    loadDictVoByCode(code){
        var params = new URLSearchParams();
        params.append('code', code);
        return cgAxios.post(
            `/v1/admin/config/dict/loadDictVoByCode`,params
        )
    },
    loadButtons(router){
        return cgAxios.get(
            `/v1/admin/menu/loadButtons?router=`+router
        )
    }
}