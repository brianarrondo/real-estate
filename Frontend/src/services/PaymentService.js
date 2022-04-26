import BaseService from "./BaseService";

class PaymentService extends BaseService {
    baseResourcePath = "/billmanager";

    constructor(unauthorizedCallback) {
        super(unauthorizedCallback);
    }

    getAllPayments(successCallback, errorCallback) {
        this.doGet(this.baseResourcePath + "/all", {}, successCallback, errorCallback);
    }

    create(payment, successCallback, errorCallback) {
        this.doPost(this.baseResourcePath + "/generate_payment", payment, successCallback, errorCallback);
    }
    /*
        deleteEstate(estate, successCallback, errorCallback) {
            this.doDelete(this.baseResourcePath, estate, successCallback, errorCallback);
        }

        editEstate(estate, successCallback, errorCallback) {
            this.doPut(this.baseResourcePath, estate, successCallback, errorCallback)
        }*/
}

export default PaymentService;