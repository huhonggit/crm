import cgAxios from '@/axios'

export default {
    loadDictVoByCode(code){
        var params = new URLSearchParams();
        params.append('code', code);
        return cgAxios.post(
            `/auth/config/dict/loadDictVoByCode`,params
        )
    },
    loadButtons(router){
        return cgAxios.get(
            `/auth/menu/loadButtons?router=`+router
        )
    }
}