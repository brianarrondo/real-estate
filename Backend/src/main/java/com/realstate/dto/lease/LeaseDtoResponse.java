package com.realstate.dto.lease;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.realstate.dto.ApartmentDto;
import com.realstate.dto.RentalFeesDto;
import com.realstate.entities.Lease;
import com.realstate.entities.Tenant;
import com.realstate.utils.Utils;

public class LeaseDtoResponse {
	public long id;
	public String name;
	public List<Tenant> tenants;
	public ApartmentDto apartment;
	public String estateName;
	public Date startDate;
	public Date endDate;
	public boolean active;
	public boolean isEnded;
	public String description;
	public List<RentalFeesDto> rentalFees = new ArrayList<RentalFeesDto>();
	
	public LeaseDtoResponse(Lease lease) {
		Date currentDate = new Date();
		this.id = lease.getId();
		this.name = lease.getName();
		this.tenants = lease.getTenants();
		this.apartment = Utils.map(lease.getApartment(), ApartmentDto.class);
		this.estateName = lease.getApartment().getEstate().getName();
		this.startDate = lease.getStartDate();
		this.endDate = lease.getEndDate();
		this.active = lease.isActive();
		this.isEnded = currentDate.after(endDate);
		this.description = lease.getDescription();
		this.rentalFees = rentalFees.stream().map(s -> Utils.map(s, RentalFeesDto.class)).collect(Collectors.toList());
	}
}
