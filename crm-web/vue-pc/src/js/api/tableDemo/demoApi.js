import cgAxios from '@/axios'

export default {

    search(data){
        return cgAxios.post(
            `/v1/demo/search`,data
        )
    },

}
