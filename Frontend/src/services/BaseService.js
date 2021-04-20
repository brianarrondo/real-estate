import axios from "axios";

export default class BaseService {

    baseURI = "http://localhost:8080";

    constructor(unauthorizedCallback) {
        // Interceptamos los requests que retornan 401 (acceso no autorizado)
        axios.interceptors.response.use(
            (response) => {
                return response;
            },
            (error) => {
                if (error && error.response && error.response.status === 401) {
                    if(unauthorizedCallback) unauthorizedCallback();
                    error.message = "La sesión expiró. Debe loguearse nuevamente";
                }
                return Promise.reject(error);
            }
        );
        // Seteamos el token de autenticacion en todos los requests
        axios.interceptors.request.use(
            (config) => {
                const token = localStorage.getItem("token");
                if (token) config.headers.Authorization = token;
                return config;
            },
            (error) => {
                return Promise.reject(error);
            }
        );
    }

    doGet(resource, queryParams, successCallback, errorCallback) {
        axios.get(this.baseURI + resource)
            .then((response) => {
                if (response.data.length > 0 && successCallback) {
                    successCallback(response);
                }
            })
            .catch((e) => {
                if (errorCallback) errorCallback(e);
            });
    }

    doPost(resource, bodyParams, successCallback, errorCallback) {
        axios.post(this.baseURI + resource, bodyParams)
            .then((response) => {
                if (successCallback) successCallback(response);
            })
            .catch((e) => {
                if (errorCallback) errorCallback(e);
            });
    }

    doDelete(resource, bodyParams, successCallback, errorCallback) {
        const config = { data: bodyParams };
        axios.delete(this.baseURI + resource, config)
            .then((response) => {
                if (successCallback) successCallback(response);
            })
            .catch((e) => {
                if (errorCallback) errorCallback(e);
            });
    }

    doPut(resource, bodyParams, successCallback, errorCallback) {
        axios.put(this.baseURI + resource, bodyParams)
            .then((response) => {
                if (successCallback) successCallback(response);
            })
            .catch((e) => {
                if (errorCallback) errorCallback(e);
            });
    }
}