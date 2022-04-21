package com.realstate.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EstateDto implements Serializable {
	private long id;
	private String name;
	private String address;
	private String description;
	private List<ApartmentDto> apartments = new ArrayList<ApartmentDto>();
	private boolean active;

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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<ApartmentDto> getApartments() {
		return apartments;
	}
	public void setApartments(List<ApartmentDto> apartments) {
		this.apartments = apartments;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
}
