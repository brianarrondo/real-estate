import BaseService from "./BaseService";

class EstateService extends BaseService{
    baseResourcePath = "/estate";

    constructor(unauthorizedCallback) {
        super(unauthorizedCallback);
    }

    getAllEstates(successCallback, errorCallback) {
        this.doGet(this.baseResourcePath + "/all", {}, successCallback, errorCallback);
    }

    createEstate(estate, successCallback, errorCallback) {
        this.doPost(this.baseResourcePath, estate, successCallback, errorCallback);
    }

    deleteEstate(estate, successCallback, errorCallback) {
        this.doDelete(this.baseResourcePath, estate, successCallback, errorCallback);
    }

    editEstate(estate, successCallback, errorCallback) {
        this.doPut(this.baseResourcePath, estate, successCallback, errorCallback)
    }
}

export default EstateService;