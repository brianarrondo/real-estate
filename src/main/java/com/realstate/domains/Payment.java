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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(amount);
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((paymentId == null) ? 0 : paymentId.hashCode());
		result = prime * result + ((rentalBillId == null) ? 0 : rentalBillId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Payment other = (Payment) obj;
		if (Float.floatToIntBits(amount) != Float.floatToIntBits(other.amount))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (paymentId == null) {
			if (other.paymentId != null)
				return false;
		} else if (!paymentId.equals(other.paymentId))
			return false;
		if (rentalBillId == null) {
			if (other.rentalBillId != null)
				return false;
		} else if (!rentalBillId.equals(other.rentalBillId))
			return false;
		return true;
	}
	
}
