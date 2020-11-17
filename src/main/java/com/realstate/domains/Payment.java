package com.realstate.domains;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "payment")
public class Payment implements Serializable {
	@Id
	private String paymentId;
	private float amount;
	private String rentalBillId;
	private Date date;

	/* Constructors */
	public Payment(String paymentId, float amount, String rentalBillId, Date date) {
		super();
		this.paymentId = paymentId;
		this.amount = amount;
		this.rentalBillId = rentalBillId;
		this.date = date;
	}
	
	/* Getters and Setters */
	public String getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(String paymentId) {
		this.paymentId = paymentId;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public String getRentalBillId() {
		return rentalBillId;
	}
	public void setRentalBillId(String leaseId) {
		this.rentalBillId = leaseId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
