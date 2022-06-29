package com.realstate.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.realstate.dto.payment.PaymentDto;
import com.realstate.entities.RentalBill;
import com.realstate.utils.Utils;

public class RentalBillDto {
	private long id;
	private long leaseId;
	private Date startDate;
	private Date endDate;
	private float amount;
	private List<PaymentDto> payments = new ArrayList<PaymentDto>();
	private float unpaidAmount; 

	public RentalBillDto() {}
	
	public RentalBillDto (RentalBill rentalBill) {
		id = rentalBill.getId();
		leaseId = rentalBill.getLease().getId();
		startDate = rentalBill.getStartDate();
		endDate = rentalBill.getEndDate();
		amount = rentalBill.getAmount();
		payments = rentalBill.getPayments().stream().map(s -> Utils.map(s, PaymentDto.class)).collect(Collectors.toList());
		unpaidAmount = (float) (rentalBill.getAmount() - rentalBill.getPayments().stream().mapToDouble(p -> p.getAmount()).sum());
	}

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
	public float getUnpaidAmount() {
		return unpaidAmount;
	}
	public void setUnpaidAmount(float unpaidAmount) {
		this.unpaidAmount = unpaidAmount;
	}
}
