package com.realstate.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "rentall_bill")
public class RentalBill implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne (cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private Lease lease;
	private Date date;
	private float amount;
	@OneToMany (cascade = CascadeType.MERGE, fetch = FetchType.LAZY, mappedBy = "rentalBill")
	private List<Payment> payments = new ArrayList<Payment>();
		
	/* Constructors */
	public RentalBill() {}
	
	public RentalBill(long id, Lease lease, Date date, float amount) {
		this.id = id;
		this.lease = lease;
		this.date = date;
		this.amount = amount;
	}
	
	@Override
	public String toString() {
		return "RentalBill [rentalBillId=" + id + ", leaseId=" + lease.getId() + ", date=" + date + ", amount="
				+ amount + ", payments=" + payments + "]";
	}

	/* Getters and Setters */
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Lease getLease() {
		return lease;
	}

	public void setLease(Lease lease) {
		this.lease = lease;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
