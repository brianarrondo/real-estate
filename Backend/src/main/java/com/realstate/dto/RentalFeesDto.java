package com.realstate.dto;

import java.util.Date;

public class RentalFeesDto {
	private long id;
	private float fee;
	private Date startDate;
	private Date endDate;
	private long leaseId;

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
	public long getLeaseId() {
		return leaseId;
	}
	public void setLeaseId(long leaseId) {
		this.leaseId = leaseId;
	}
}
