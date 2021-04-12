import axios from "axios";

export default class BaseService {

    static baseURI = "http://localhost:8080";

    static getConfig() {
        const token = sessionStorage.getItem('token');
        return {
            headers: {
                "Authorization": token,
                "Access-Control-Allow-Origin": "*"
            }
        };
    }

    static doGet(resource, queryParams, successCallback, errorCallback) {
        axios.get(this.baseURI + resource, this.getConfig())
            .then((response) => {
                if (response.data.length > 0 && successCallback) {
                    successCallback(response);
                }
            })
            .catch((e) => {
                if (errorCallback) errorCallback(e);
            });
    }

    static doPost(resource, bodyParams, successCallback, errorCallback) {
        axios.post(this.baseURI + resource, bodyParams, this.getConfig())
            .then((response) => {
                if (successCallback) successCallback(response);
            })
            .catch((e) => {
                if (errorCallback) errorCallback(e);
            });
    }

    static doDelete(resource, bodyParams, successCallback, errorCallback) {
        let config = this.getConfig();
        config.data = bodyParams;
        axios.delete(this.baseURI + resource, config)
            .then((response) => {
                if (successCallback) successCallback(response);
            })
            .catch((e) => {
                if (errorCallback) errorCallback(e);
            });
    }

    static doPut(resource, bodyParams, successCallback, errorCallback) {
        axios.put(this.baseURI + resource, bodyParams, this.getConfig())
            .then((response) => {
                if (successCallback) successCallback(response);
            })
            .catch((e) => {
                if (errorCallback) errorCallback(e);
            });
    }
}