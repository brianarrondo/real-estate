export default class Tenant {

    constructor(tenantId, fullName, dni, phone, description) {
        this.tenantId = tenantId;
        this.fullName = fullName;
        this.dni = dni;
        this.phone = phone;
        this.description = description;
    }
}