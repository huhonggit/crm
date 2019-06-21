import axios from '@/axios'

export default {

    /**
     * @param data
     * @returns {*}
     */
    search(data) {
        return axios.post(
            `/v1/inter-try/project/search`, data
        )
    },


    loadProjectGeneral(id) {
        return axios.get(
            `/v1/inter-try/project/` + id + "/general"
        )
    },

    addProject(data) {
        return axios.post(
            `/v1/inter-try/project`, data
        )
    },


    getProject(id) {
        return axios.get(
            `/v1/inter-try/project/` + id
        )
    },

    editProject(id, data) {
        return axios.put(
            `/v1/inter-try/project/` + id, data
        )
    },

    /**
     * @param data
     * @returns {*}
     */
    loadHosts(data) {
        return axios.post(
            `/v1/inter-try/host/search`, data
        )
    },


    saveHost(data) {
        return axios.post(
            `/v1/inter-try/host`, data
        )
    },


    loadHost(id) {
        return axios.get(
            `/v1/inter-try/host/` + id
        )
    },

    updateHostState(id, state) {
        return axios.put(
            `/v1/inter-try/host/` + id + "/state/" + state
        )
    },

    /**
     * @param data
     * @returns {*}
     */
    addInter(data) {
        return axios.post(
            `/v1/inter-try/inter`, data
        )
    },


    /**
     * @param data
     * @returns {*}
     */
    loadInter(id) {
        return axios.get(
            `/v1/inter-try/inter/` + id
        )
    },

    /**
     * @param data
     * @returns {*}
     */
    loadInters(data) {
        return axios.post(
            `/v1/inter-try/inter/search`, data
        )
    },

    updateInterState(id, state) {
        return axios.put(
            `/v1/inter-try/inter/` + id + "/state/" + state
        )
    },

    /**
     * @param data
     * @returns {*}
     */
    loadModules(isAll, projectId) {
        return axios.get(
            `/v1/inter-try/modules?isAll=` + isAll + "&projectId=" + projectId
        )
    },

    loadModule(id) {
        return axios.get(
            `/v1/inter-try/module/` + id
        )
    },
    saveModule(data) {
        return axios.post(
            `/v1/inter-try/module`, data
        )
    },
    updateModule(id, data) {
        return axios.put(
            `/v1/inter-try/module/` + id, data
        )
    },

    saveCase(data) {
        return axios.post(
            `/v1/inter-try/case`, data
        )
    },

    loadCase(id) {
        return axios.get(
            `/v1/inter-try/case/` + id
        )
    },

    updateCase(id, data) {
        return axios.put(
            `/v1/inter-try/case/` + id, data
        )
    },

    loadCases(isAll, projectId) {
        return axios.get(
            `/v1/inter-try/cases?isAll=` + isAll + "&projectId=" + projectId
        )
    },
    /**
     * @param data
     * @returns {*}
     */
    loadCaseInters(data) {
        return axios.post(
            `/v1/inter-try/case/inter/search`, data
        )
    },
    /**
     * @param data
     * @returns {*}
     */
    downCaseData(data) {
        return axios.get(
            `/v1/inter-try/case/export/data?checkedIds=` + data
        )
    },
    /**
     * @param data
     * @returns {*}
     */
    async exectorReturn(data) {
        var res = await axios.post(
            `/v1/inter-try/inter/exector_return`, data
        );
        return res;
    },

    loadCaseReportRecords(caseId, data) {
        return axios.post(
            `/v1/inter-try/record/case/` + caseId + "/report", data
        )
    },


    loadCaseRecords(caseId, data) {
        return axios.post(
            `/v1/inter-try/record/case/` + caseId, data
        )
    },


    loadRecordReports(recordId, data) {
        return axios.post(
            `/v1/inter-try/report/record/` + recordId, data
        )
    },

    /**
     * @param data
     * @returns {*}
     */
    loadReport(id) {
        return axios.get(
            `/v1/inter-try/report/` + id
        )
    },
}
