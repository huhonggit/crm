import cgAxios from '@/axios'

export default {

    search(data){
        return cgAxios.post(
            `/v1/demo/search`,data
        )
    },



    uploadImages(data){
        return cgAxios.post(
            `/v1/mp_parent/course/upload_img`,data
        )
    },

}
