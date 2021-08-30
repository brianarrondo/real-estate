package com.realstate.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "lease")
public class Lease implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String leaseId;
	@NotNull(message = "cannot be null")
	private String name;
	@NotNull(message = "cannot be null")
	private List<Tenant> tenants = new ArrayList<Tenant>();
	@NotNull(message = "cannot be null")
	private Apartment apartment;
	@NotNull(message = "cannot be null")
	private Date startDate;
	private Date endDate;
	private boolean active;
	private List<RentalFees> rentalFees = new ArrayList<RentalFees>();
	private String description;
	
	/* Constructors */
	public Lease() {}
	
	public Lease(String leaseId, String name, List<Tenant> tenants, Apartment apartment, Date startDate, Date endDate, boolean active,
			String description) {
		super();
		this.leaseId = leaseId;
		this.name = name;
		this.tenants = tenants;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Tenant> getTenants() {
		return tenants;
	}
	public void setTenants(List<Tenant> tenants) {
		this.tenants = tenants;
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
}
