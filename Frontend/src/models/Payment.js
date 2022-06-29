export default class Payment {

    constructor(userId, rentalBillId, date, amount) {
        this.userId = userId;
        this.rentalBillId = rentalBillId;
        this.date = date;
        this.amount = amount;
    }
}