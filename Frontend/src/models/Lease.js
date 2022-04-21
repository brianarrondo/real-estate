export default class Lease {
    constructor(id, name, tenants, apartment, startDate, endDate, active, rentalFees, description, estateName, isEnded) {
        this.id = id;
        this.name = name;
        this.tenants = tenants;
        this.apartment = apartment;
        this.estateName = estateName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = active;
        this.rentalFees = rentalFees;
        this.description = description;
        this.isEnded = isEnded;
    }
}