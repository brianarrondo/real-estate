package com.realstate.domains;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "lease")
public class Lease implements Serializable {
	@Id
	private String leaseId;
	@NotNull(message = "cannot be null")
	private Tenant tenant;
	@NotNull(message = "cannot be null")
	private Apartment apartment;
	@NotNull(message = "cannot be null")
	private Date startDate;
	@NotNull(message = "cannot be null")
	private Date endDate;
	private boolean active = true;
	private List<RentalFees> rentalFees = new ArrayList<RentalFees>();
	private List<RentalBill> rentalBills = new ArrayList<RentalBill>();
	private String description;
	
	/* Constructors */
	public Lease() {}
	
	public Lease(String leaseId, Tenant tenant, Apartment apartment, Date startDate, Date endDate, boolean active,
			String description) {
		super();
		this.leaseId = leaseId;
		this.tenant = tenant;
		this.apartment = apartment;
		this.startDate = startDate;
		this.endDate = endDate;
		this.active = active;
		this.description = description;
	}

	/* Getters and Setters */
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
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public List<RentalFees> getRentalFees() {
		return rentalFees;
	}
	public void setRentalFees(List<RentalFees> rentalFees) {
		this.rentalFees = rentalFees;
	}
	public List<RentalBill> getRentalBills() {
		return rentalBills;
	}
	public void setRentalBills(List<RentalBill> rentalBills) {
		this.rentalBills = rentalBills;
	}
}
