import cgAxios from '@/axios'

export default {
    saveCatalog (name) {
        var params = new URLSearchParams();
        params.append('name', name);
        return cgAxios.post(
            `/auth/config/dict/saveCatalog`,params
        )
    },
    computeCode (name) {
        var params = new URLSearchParams();
        params.append('name', name);
        return cgAxios.post(
            `/auth/config/dict/computeCode`,params
        )
    },
    loadCatalog(){
        return cgAxios.post(
            `/auth/config/dict/loadCatalog`
        )
    },
    saveDict(data){
        return cgAxios.post(
            `/auth/config/dict/saveDict`,data
        )
    },
    editDict(data){
        return cgAxios.post(
            `/auth/config/dict/editDict`,data
        )
    },
    getDictById(data){
        var params = new URLSearchParams();
        params.append('id', data);
        return cgAxios.post(
            `/auth/config/dict/getDictById`,params
        )
    },
    saveDictItem(data){
        return cgAxios.post(
            `/auth/config/dict/saveDictItem`,data
        )
    },
    editDictItem(data){
        return cgAxios.post(
            `/auth/config/dict/editDictItem`,data
        )
    },
    getDictItemById(data){
        var params = new URLSearchParams();
        params.append('id', data);
        return cgAxios.post(
            `/auth/config/dict/getDictItemById`,params
        )
    },
    searchDict(data){
        return cgAxios.post(
            `/auth/config/dict/searchDict`,data
        )
    },
    searchDictItem(dictId){
        var params = new URLSearchParams();
        params.append('dictId', dictId);
        return cgAxios.post(
            `/auth/config/dict/getItemsByDictId`,params
        )
    },
    loadDictVoByCode(code){
        var params = new URLSearchParams();
        params.append('code', code);
        return cgAxios.post(
            `/auth/config/dict/loadDictVoByCode`,params
        )
    },
    evictChache(code){
        var params = new URLSearchParams();
        params.append('code', code);
        return cgAxios.post(
            `/auth/config/dict/evictChache`,params
        )
    },
    evictAllChache(){
        return cgAxios.post(
            `/auth/config/dict/evictAllChache`
        )
    }
}
