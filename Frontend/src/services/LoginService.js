import baseService from "./BaseService";

export default class LoginService extends baseService {

    baseResourcePath = "/login";

    constructor() {
        super();
    }

    login(bodyParams, successCallback, errorCallback) {
        this.doPost(this.baseResourcePath, bodyParams, successCallback, errorCallback);
    }
}