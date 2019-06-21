import cgAxios from '@/axios'

export default {
    addFrame(data){
        return cgAxios.post(
            `/v1/frame/addVersion`,data
        )
    },
    frameList(data){
        return cgAxios.post(
            `/v1/frame/frameList`,data
        )
    },
    getFrameNotice(data){
        return cgAxios.post(
            `/v1/frame/getFrameNotice?id=`+data
        )
    },
    updateAppNotice(data){
        return cgAxios.post(
            `/v1/frame/updateAppNotice`,data
        )
    },
    updateResourceNotice(data){
        return cgAxios.post(
            `/v1/frame/updateResourceNotice`,data
        )
    },
    resourceList(data){
        return cgAxios.post(
            `/v1/frame/resourceList`,data
        )
    },
    deleteById(data){
        return cgAxios.post(
            `/v1/frame/deleteById?id=`+data
        )
    },
    deployById(data){
        return cgAxios.post(
            `/v1/frame/deployById?id=`+data
        )
    },
    getVersionListByType(data){
        return cgAxios.post(
            `/v1/frame/getVersionListByType?type=`+data
        )
    },

}
