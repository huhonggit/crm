import cgAxios from '@/axios'

export default {

    searchInbook(data){
        return cgAxios.post(
            `/v1/book/searchInbook`,data
        )
    },

    getSts(type,fileName){
        return cgAxios.get(
            `/v1/book/getStsBytype?type=`+type+`&fileName=`+fileName
        )
    },

    saveInBook(id,fileName){
        let params = new URLSearchParams();
        params.append('id', id);
        params.append('fileName', fileName);

        return cgAxios.post(
            `/v1/book/saveInBook`,params
        )
    },

    syncRedis(){
        return cgAxios.post(
            `/v1/book/syncRedis`
        )
    }

}
