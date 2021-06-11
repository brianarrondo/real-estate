import BaseService from "./BaseService";

class ApartmentService extends BaseService {
    baseResourcePath = "/apartment";

    constructor(unauthorizedCallback) {
        super(unauthorizedCallback);
    }

    getAllApartment(successCallback, errorCallback) {
        this.doGet(this.baseResourcePath + "/all", {}, successCallback, errorCallback);
    }

    getAllWithNoEstateAssigned(successCallback, errorCallback) {
        this.doGet(this.baseResourcePath + "/allWithNoEstateAssigned", {}, successCallback, errorCallback);
    }

    createApartment(apartment, successCallback, errorCallback) {
        this.doPost(this.baseResourcePath, apartment, successCallback, errorCallback);
    }

    deleteApartment(apartment, successCallback, errorCallback) {
        this.doDelete(this.baseResourcePath, apartment, successCallback, errorCallback);
    }

    editApartment(apartment, successCallback, errorCallback) {
        this.doPut(this.baseResourcePath, apartment, successCallback, errorCallback)
    }
}

export default ApartmentService;