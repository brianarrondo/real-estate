import axios from 'axios';
import baseService from './BaseService';


export default class TenantService extends baseService {

    static resource = "/tenant";

    static getAllTenants(callback) {
        axios.get(this.baseURI + this.resource + "/all", {})
            .then((response) => {
                if (response.data.length > 0) {
                    callback(response);
                }
            })
            .catch((e) => {
                console.log(e)
            });
    }

    static createTenant(bodyParams, callback) {
        axios.post(this.baseURI + this.resource, bodyParams)
            .then((response) => {
                callback(response);
            })
            .catch((e) => {
               console.log(e)
            });
    }
}