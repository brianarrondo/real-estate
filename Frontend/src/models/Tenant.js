export default class Tenant {
    tenantId;
    fullName;
    dni;
    phone;
    description;

    constructor(tenantId, fullName, dni, phone, description) {
        this.tenantId = tenantId;
        this.fullName = fullName;
        this.dni = dni;
        this.phone = phone;
        this.description = description;
    }
}