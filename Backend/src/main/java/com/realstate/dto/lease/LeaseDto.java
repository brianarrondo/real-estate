package com.realstate.dto.lease;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.realstate.dto.RentalFeesDto;
import com.realstate.entities.Tenant;

public class LeaseDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public long id;
	public String name;
	public List<Tenant> tenants;
	public long apartmentId;
	public Date startDate;
	public Date endDate;
	public boolean active;
	//private List<RentalFees> rentalFees = new ArrayList<RentalFees>();
	public String description;
	public List<RentalFeesDto> rentalFees = new ArrayList<RentalFeesDto>();

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public long getApartmentId() {
		return apartmentId;
	}
	public void setApartmentId(long apartmentId) {
		this.apartmentId = apartmentId;
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
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<RentalFeesDto> getRentalFees() {
		return rentalFees;
	}
	public void setRentalFees(List<RentalFeesDto> rentalFees) {
		this.rentalFees = rentalFees;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
