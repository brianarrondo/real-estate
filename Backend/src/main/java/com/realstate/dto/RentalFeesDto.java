package com.realstate.dto;

import java.util.Date;

public class RentalFeesDto {
	private long id;
	private float fee;
	private String startDate;
	private String endDate;
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
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public long getLeaseId() {
		return leaseId;
	}
	public void setLeaseId(long leaseId) {
		this.leaseId = leaseId;
	}
}
