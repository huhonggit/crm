import cgAxios from '@/axios'

export default {
    getOutBookLimit(data){
        return cgAxios.post(
            `/v1/outbook/getLessonList`,data
        )
    },

}
