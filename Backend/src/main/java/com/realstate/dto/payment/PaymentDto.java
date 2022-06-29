package com.realstate.dto.payment;

import java.util.Date;

import com.realstate.entities.User;

public class PaymentDto {
	public long id;
	public float amount;
	public long rentalBillId;
	public long userId;
	public Date date;
	public Date registerDate;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public long getRentalBillId() {
		return rentalBillId;
	}
	public void setRentalBillId(long rentalBillId) {
		this.rentalBillId = rentalBillId;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long user) {
		this.userId = user;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
}
