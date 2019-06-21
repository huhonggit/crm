import cgAxios from '@/axios'

export default {


    getLevels(data) {
        return cgAxios.get(
            `/v1/mp_parent/course/levels`, data
        )
    },


    getWeeks(levelId) {
        return cgAxios.get(
            `/v1/mp_parent/course/weeks?levelId=` + levelId
        )
    },

    getUnits(levelId) {
        return cgAxios.get(
            `/v1/mp_parent/course/units?levelId=` + levelId
        )
    },


    getLessons(levelIncrId, week) {
        return cgAxios.get(
            `/v1/mp_parent/course/lessons?levelIncrId=` + levelIncrId + "&week=" + week
        )
    },

    getAllDatas(lessonId) {
        return cgAxios.get(
            `/v1/mp_parent/course/datas?lessonId=` + lessonId
        )
    },

    getGuides(lessonId, type) {
        return cgAxios.get(
            `/v1/mp_parent/course/guide/lesson/` + lessonId + "?type=" + type
        )
    },
    getGuide(id) {
        return cgAxios.get(
            `/v1/mp_parent/course/guide/` + id
        )
    },

    getGuide2(lessonId, type) {
        return cgAxios.get(
            `/v1/mp_parent/course/guide2/lesson/` + lessonId + "/?type=" + type
        )
    },

    saveGuide(data) {
        return cgAxios.post(
            `/v1/mp_parent/course/guide`, data
        )
    },
    delGuide(id) {
        return cgAxios.delete(
            `/v1/mp_parent/course/guide/` + id
        )
    },

    saveGuideVideo(data) {
        return cgAxios.post(
            `/v1/mp_parent/course/guideVideo`, data
        )
    },
    getGuideVideo(lessonId) {
        return cgAxios.get(
            `/v1/mp_parent/course/guideVideo/lesson/` + lessonId
        )
    },

    delGuideVideo(id) {
        return cgAxios.delete(
            `/v1/mp_parent/course/guideVideo/` + id
        )
    },

    saveGame(data) {
        return cgAxios.post(
            `/v1/mp_parent/course/game`, data
        )
    },

    getGame(lessonId) {
        return cgAxios.get(
            `/v1/mp_parent/course/game/lesson/` + lessonId
        )
    },

    saveParent(data) {
        return cgAxios.post(
            `/v1/mp_parent/course/parent`, data
        )
    },


    getParents(lessonId) {
        return cgAxios.get(
            `/v1/mp_parent/course/parent/lesson/` + lessonId
        )
    },

    getParent(id) {
        return cgAxios.get(
            `/v1/mp_parent/course/parent/` + id
        )
    },

    delParent(id) {
        return cgAxios.delete(
            `/v1/mp_parent/course/parent/` + id
        )
    },


    getReviews(lessonId) {
        return cgAxios.get(
            `/v1/mp_parent/course/review/lesson/` + lessonId
        )
    },

    saveReview(data) {
        return cgAxios.post(
            `/v1/mp_parent/course/review`, data
        )
    },

    getReview(id) {
        return cgAxios.get(
            `/v1/mp_parent/course/review/` + id
        )
    },

    delReview(id) {
        return cgAxios.delete(
            `/v1/mp_parent/course/review/` + id
        )
    },

    saveIdea(data) {
        return cgAxios.post(
            `/v1/mp_parent/course/idea`, data
        )
    },

    saveIdeaLesson(data) {
        return cgAxios.post(
            `/v1/mp_parent/course/idea_lesson`, data
        )
    },

    getIdeaLesson(lessonId) {
        return cgAxios.get(
            `/v1/mp_parent/course/idea_lesson?lessonId=` + lessonId
        )
    },


    getBindIdea(lessonId, type) {
        return cgAxios.get(
            `/v1/mp_parent/course/idea_bind?lessonId=` + lessonId + "&type=" + type
        )
    },

    loadIdea(id) {
        return cgAxios.get(
            `/v1/mp_parent/course/idea?id=` + id
        )
    },

    delIdea(id) {
        return cgAxios.delete(
            `/v1/mp_parent/course/idea?id=` + id
        )
    },


    getFileUrl(fileName, type) {
        return cgAxios.get(
            `/v1/mp_parent/course/getFileUrl?fileName=` + fileName + "&type=" + type
        )
    },


    savePicture(data) {
        return cgAxios.post(
            `/v1/mp_parent/course/picture`, data
        )
    },

    delPicture(id, picId) {
        return cgAxios.delete(
            `/v1/mp_parent/course/picture?id=` + id + "&picId=" + picId
        )
    },


    saveGameCourse(data) {
        return cgAxios.post(
            `/v1/mp_parent/course/gamecourse`, data
        )
    },

    delGameCourse(id, gameId) {
        return cgAxios.delete(
            `/v1/mp_parent/course/gamecourse?id=` + id + "&gameId=" + gameId
        )
    },
}
