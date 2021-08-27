export default class Lease {
    constructor(leaseId, name, tenants, apartmentId, startDate, endDate, active, rentalFees, description) {
        this.leaseId = leaseId;
        this.name = name;
        this.tenants = tenants;
        this.apartmentId = apartmentId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = active;
        //this.rentalFees = rentalFees;
        this.description = description;
    }
}