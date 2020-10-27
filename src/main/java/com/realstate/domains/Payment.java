package com.realstate.domains;

import java.util.Date;

public class Payment {
	private String paymentId;
	private float amount;
	private String rentalBillId;
	private Date date;

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
	public String getLeaseId() {
		return rentalBillId;
	}
	public void setLeaseId(String leaseId) {
		this.rentalBillId = leaseId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
