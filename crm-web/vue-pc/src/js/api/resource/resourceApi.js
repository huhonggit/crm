import cgAxios from '@/axios'

export default {
    getBucket(data,data2){
        return cgAxios.post(
            '/resource/getBuketBytype?fileType='+data+'&type='+data2
        )
    },
    getBucketNoSts(data,data2){
        return cgAxios.post(
            '/resource/getBuketNoStsBytype?fileType='+data+'&type='+data2
        )
    },
    uploadResource(data){
        return cgAxios.post(
            `/resource/upload`,data
        )
    },
    uploadCourse(data){
        return cgAxios.post(
            `/resource/uploadCourse`,data
        )
    },
    uploadExcel(data){
        return cgAxios.post(
            `/resource/uploadExcel`,data
        )
    },

    search(data){
        return cgAxios.post(
            `/resource/list`,data
        )
    },
    searchStaticResource(data){
        return cgAxios.post(
            `/resource/staticResourceList`,data
        )
    },
    del(data){
        return cgAxios.post(
            `/resource/del?id=`+data
        )
    },
    chStable(data){
        return cgAxios.post(
            `/resource/chResourceToStable?id=`+data
        )
    },
    uploadLessonZip(data){
        return cgAxios.post(
            `/v1/course/uploadLessonZip`,data
        )
    },
    getLessonResList(data){
        return cgAxios.post(
            `/v1/course/getLessonResList`,data
        )
    },
    uploadLessonCheck(excelFile){
        return cgAxios.post(
            `/v1/course/uploadLessonCheck`,excelFile
        )
    },
}
