import baseService from "./BaseService";

export default class TenantService extends baseService {

    static baseResourcePath = "/login";

    static login(bodyParams, successCallback, errorCallback) {
        this.doPost(this.baseResourcePath, bodyParams, successCallback, errorCallback);
    }
}