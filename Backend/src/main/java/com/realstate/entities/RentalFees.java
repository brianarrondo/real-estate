package com.realstate.entities;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "rental_fees")
public class RentalFees {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private float fee;
	private Date startDate;
	private Date endDate;
	@ManyToOne (cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private Lease lease;

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public float getFee() {
		return fee;
	}
	public void setFee(float fee) {
		this.fee = fee;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Lease getLease() {
		return lease;
	}
	public void setLease(Lease lease) {
		this.lease = lease;
	}
}
