package com.realstate.domains;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import com.realstate.services.RentalBillService;

@Document(collection = "rentall_bill")
public class RentalBill implements Serializable {
	@Id
	private String rentalBillId;
	private String leaseId;
	private Date date;
	private float amount;
	private List<Payment> payments = new ArrayList<Payment>();
	
	@Autowired
	private RentalBillService rentalBillService;
	
	/* Constructors */
	public RentalBill() {}
	
	@PersistenceConstructor
	public RentalBill(String rentalBillId) {
		this.rentalBillId = rentalBillId;
	}
	
	public RentalBill(String rentalBillId, String leaseId, Date date, float amount) {
		this.rentalBillId = rentalBillId;
		this.leaseId = leaseId;
		this.date = date;
		this.amount = amount;
	}
	
	/* Getters and Setters */
	public String getRentalBillId() {
		return rentalBillId;
	}
	public void setRentalBillId(String rentalBillId) {
		this.rentalBillId = rentalBillId;
	}
	public String getLeaseId() {
		return leaseId;
	}
	public void setLeaseId(String leaseId) {
		this.leaseId = leaseId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public List<Payment> getPayments() {
		return payments;
	}
	public void setPayments(List<Payment> payments) {
		this.payments = payments;
	}
	@Override
	public String toString() {
		return "RentalBill [rentalBillId=" + rentalBillId + ", leaseId=" + leaseId + ", date=" + date + ", amount="
				+ amount + ", payments=" + payments + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(amount);
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((leaseId == null) ? 0 : leaseId.hashCode());
		result = prime * result + ((payments == null) ? 0 : payments.hashCode());
		result = prime * result + ((rentalBillId == null) ? 0 : rentalBillId.hashCode());
		result = prime * result + ((rentalBillService == null) ? 0 : rentalBillService.hashCode());
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
		RentalBill other = (RentalBill) obj;
		if (Float.floatToIntBits(amount) != Float.floatToIntBits(other.amount))
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (leaseId == null) {
			if (other.leaseId != null)
				return false;
		} else if (!leaseId.equals(other.leaseId))
			return false;
		if (payments == null) {
			if (other.payments != null)
				return false;
		} else if (!payments.equals(other.payments))
			return false;
		if (rentalBillId == null) {
			if (other.rentalBillId != null)
				return false;
		} else if (!rentalBillId.equals(other.rentalBillId))
			return false;
		if (rentalBillService == null) {
			if (other.rentalBillService != null)
				return false;
		} else if (!rentalBillService.equals(other.rentalBillService))
			return false;
		return true;
	}
	
}
