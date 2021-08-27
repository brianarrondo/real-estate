package com.realstate.dto;

import java.util.List;

import com.realstate.entities.Tenant;

public class LeaseCreationDto {
	public String leaseId;
	public String name;
	public List<Tenant> tenants;
	public String apartmentId;
	public String startDate;
	public String endDate;
	public boolean active;
	//private List<RentalFees> rentalFees = new ArrayList<RentalFees>();
	public String description;
}
