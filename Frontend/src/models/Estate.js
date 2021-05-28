export default class Estate {

    constructor(estateId, name, address, description, apartments) {
        this.estateId = estateId;
        this.name = name;
        this.address = address;
        this.description = description;
        this.apartments = apartments || [];
    }
}