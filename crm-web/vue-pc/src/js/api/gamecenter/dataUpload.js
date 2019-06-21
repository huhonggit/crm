import cgAxios from '@/axios'

export default {

    importData(data){
        return cgAxios.post(
            `/v1/gamecenter/import`,data
        )
    },
    queryObjectList(clzName,tableName){
        return cgAxios.get(
            `/v1/gamecenter/queryObjectList?clzName=`+clzName+`&tableName=`+tableName
        )
    }

}