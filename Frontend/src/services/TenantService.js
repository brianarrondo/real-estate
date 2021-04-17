import baseService from './BaseService';

export default class TenantService extends baseService {

    static baseResourcePath = "/tenant";

    static getAllTenants(successCallback, errorCallback) {
        this.doGet(this.baseResourcePath + "/all", {}, successCallback, errorCallback);
    }

    static createTenant(bodyParams, successCallback, errorCallback) {
        this.doPost(this.baseResourcePath, bodyParams, successCallback, errorCallback);
    }

    static deleteTenant(bodyParams, successCallback, errorCallback) {
        this.doDelete(this.baseResourcePath, bodyParams, successCallback, errorCallback);
    }

    static editTenant(bodyParams, successCallback, errorCallback) {
        this.doPut(this.baseResourcePath, bodyParams, successCallback, errorCallback)
    }
}