import cgAxios from '@/axios'

export default {
    getLessonList(data){
        return cgAxios.post(
            `/v1/course/getLessonList`,data
        )
    },

    deleteById(data){
        return cgAxios.post(
            `/v1/course/deleteById?id=`+data
        )
    },


    lessonInfo(data){
        return cgAxios.post(
            `/v1/course/getLessonById?id=`+data
        )
    },
    adoptLesson(data){
        return cgAxios.post(
            `/v1/course/adoptLesson?ids=`+data
        )
    },
    refuseLesson(data){
        return cgAxios.post(
            `/v1/course/refuseLesson?ids=`+data
        )
    },

}
