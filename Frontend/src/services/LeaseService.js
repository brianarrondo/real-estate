import BaseService from "./BaseService";

class LeaseService extends BaseService {
    baseResourcePath = "/lease";

    constructor(unauthorizedCallback) {
        super(unauthorizedCallback);
    }
    /*
    getAllEstates(successCallback, errorCallback) {
        this.doGet(this.baseResourcePath + "/all", {}, successCallback, errorCallback);
    }*/

    createLease(lease, successCallback, errorCallback) {
        this.doPost(this.baseResourcePath, lease, successCallback, errorCallback);
    }
/*
    deleteEstate(estate, successCallback, errorCallback) {
        this.doDelete(this.baseResourcePath, estate, successCallback, errorCallback);
    }

    editEstate(estate, successCallback, errorCallback) {
        this.doPut(this.baseResourcePath, estate, successCallback, errorCallback)
    }*/
}

export default LeaseService;