export default class Payment {

    constructor(userId, leaseId, date, amount) {
        this.userId = userId;
        this.leaseId = leaseId;
        this.date = date;
        this.amount = amount;
    }
}