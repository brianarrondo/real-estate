import baseService from './BaseService';

export default class TenantService extends baseService {

    baseResourcePath = "/tenant";

    constructor(unauthorizedCallback) {
        super(unauthorizedCallback);
    }

    getAllTenants(successCallback, errorCallback) {
        this.doGet(this.baseResourcePath + "/all", {}, successCallback, errorCallback);
    }

    getAllWithoutLease(successCallback, errorCallback) {
        this.doGet(this.baseResourcePath + "/allWithoutLease", {}, successCallback, errorCallback);
    }

    createTenant(tenant, successCallback, errorCallback) {
        this.doPost(this.baseResourcePath, tenant, successCallback, errorCallback);
    }

    deleteTenant(tenant, successCallback, errorCallback) {
        this.doDelete(this.baseResourcePath, tenant, successCallback, errorCallback);
    }

    editTenant(tenant, successCallback, errorCallback) {
        this.doPut(this.baseResourcePath, tenant, successCallback, errorCallback)
    }
}