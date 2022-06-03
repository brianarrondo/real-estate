export default class CreationLeaseDto {
    constructor(id, name, tenants, apartmentId, startDate, endDate, active, rentalFees, description, baseAmount) {
        this.id = id;
        this.name = name;
        this.tenants = tenants;
        this.apartmentId = apartmentId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = active;
        this.rentalFees = rentalFees;
        this.description = description;
        this.baseAmount = baseAmount;
    }
}