package com.realstate.domains;

import java.util.Date;

public class RentalFees {
	private String rentalFeeId;
	private float fee;
	private Date startDate;
	private Date endDate;

	public String getRentalFeeId() {
		return rentalFeeId;
	}
	public void setRentalFeeId(String rentalFeeId) {
		this.rentalFeeId = rentalFeeId;
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
}
