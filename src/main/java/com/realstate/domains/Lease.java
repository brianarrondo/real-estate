package com.realstate.domains;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "lease")
public class Lease implements Serializable {
	@Id
	private String leaseId;
	private Tenant tenant;
	private Apartment apartment;
	private Date startDate;
	private Date endDate;
	private List<RentalFee> rentalsFee = new ArrayList<RentalFee>();
	private String description;

	public String getLeaseId() {
		return leaseId;
	}
	public void setLeaseId(String leaseId) {
		this.leaseId = leaseId;
	}
	public Tenant getTenant() {
		return tenant;
	}
	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}
	public Apartment getApartment() {
		return apartment;
	}
	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<RentalFee> getRentalsFee() {
		return rentalsFee;
	}
	public void setRentalsFee(List<RentalFee> rentalsFee) {
		this.rentalsFee = rentalsFee;
	}
}

