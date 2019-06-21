import cgAxios from '@/axios'

export default {

    searchUserList(data){
        return cgAxios.post(
            `/v1/userManagement/getUserList`,data
        )
    },

    searchUserDetail(data){
        return cgAxios.get(
            `/v1/userManagement/getUserInfo?userId=`+data
        )
    },

    searchOrderInfo(data){
        return cgAxios.get(
            `/v1/userManagement/getOrderInfo?userId=`+data
        )
    },

    searchCoinOperatingRecord(userId,coinType){
        return cgAxios.get(
            `/v1/userManagement/getCoinInfo?userId=`+userId+`&coinType=`+coinType
        )
    },

    searchLevelInfo(data){
        return cgAxios.get(
            `/v1/userManagement/getLevelInfo?userId=`+data
        )
    },

    searchLessonInfo(data){
        return cgAxios.get(
            `/v1/userManagement/getLessonInfo?userId=`+data
        )
    },

    searchPetDetail(data){
        return cgAxios.get(
            `/v1/userManagement/getPetInfo?userId=`+data
        )
    },


    // getSts(type,fileName){
    //     return cgAxios.get(
    //         `/v1/book/getStsBytype?type=`+type+`&fileName=`+fileName
    //     )
    // },
    //
    // saveInBook(id,fileName){
    //     let params = new URLSearchParams();
    //     params.append('id', id);
    //     params.append('fileName', fileName);
    //
    //     return cgAxios.post(
    //         `/v1/book/saveInBook`,params
    //     )
    // },

}
