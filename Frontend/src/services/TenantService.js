import baseService from './BaseService';

export default class TenantService extends baseService {

    baseResourcePath = "/tenant";

    constructor(unauthorizedCallback) {
        super(unauthorizedCallback);
    }

    getAllTenants(successCallback, errorCallback) {
        this.doGet(this.baseResourcePath + "/all", {}, successCallback, errorCallback);
    }

    createTenant(bodyParams, successCallback, errorCallback) {
        this.doPost(this.baseResourcePath, bodyParams, successCallback, errorCallback);
    }

    deleteTenant(bodyParams, successCallback, errorCallback) {
        this.doDelete(this.baseResourcePath, bodyParams, successCallback, errorCallback);
    }

    editTenant(bodyParams, successCallback, errorCallback) {
        this.doPut(this.baseResourcePath, bodyParams, successCallback, errorCallback)
    }
}