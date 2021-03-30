import axios from 'axios';
import baseService from './BaseService';


export default class TenantService extends baseService {

    static resource = "/tenant";

    static getAllTenants(successCallback, errorCallback) {
        axios.get(this.baseURI + this.resource + "/all", {})
            .then((response) => {
                if (response.data.length > 0 && successCallback) {
                    successCallback(response);
                }
            })
            .catch((e) => {
                if (errorCallback) errorCallback(e);
            });
    }

    static createTenant(bodyParams, successCallback, errorCallback) {
        axios.post(this.baseURI + this.resource, bodyParams)
            .then((response) => {
                if (successCallback) successCallback(response);
            })
            .catch((e) => {
                if (errorCallback) errorCallback(e);
            });
    }

    static deleteTenant(bodyParams, successCallback, errorCallback) {
        const config = { data: bodyParams };
        axios.delete(this.baseURI + this.resource, config)
            .then((response) => {
                if (successCallback) successCallback(response);
            })
            .catch((e) => {
                if (errorCallback) errorCallback(e);
            });
    }

    static editTenant(bodyParams, successCallback, errorCallback) {
        axios.put(this.baseURI + this.resource, bodyParams)
            .then((response) => {
                if (successCallback) successCallback(response);
            })
            .catch((e) => {
                if (errorCallback) errorCallback(e);
            });
    }
}