package com.realstate.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.realstate.dto.payment.PaymentDto;

public class RentalBillDto {
	private long id;
	private long leaseId;
	private Date startDate;
	private Date endDate;
	private float amount;
	private List<PaymentDto> payments = new ArrayList<PaymentDto>();

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getLeaseId() {
		return leaseId;
	}
	public void setLeaseId(long leaseId) {
		this.leaseId = leaseId;
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
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}
	public List<PaymentDto> getPayments() {
		return payments;
	}
	public void setPayments(List<PaymentDto> payments) {
		this.payments = payments;
	}
}
