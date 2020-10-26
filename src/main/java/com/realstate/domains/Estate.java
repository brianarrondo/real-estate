package com.realstate.domains;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "estate")
public class Estate implements Serializable {
	
	private static final long serialVersionUID = 42L;
	
	@Id
	private String estateId;
	private String name;
	private String address;
	private String description;
	private List<Apartment> apartaments = new ArrayList<Apartment>();

	public String getEstateId() {
		return estateId;
	}
	public void setEstateId(String estateId) {
		this.estateId = estateId;
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
	public List<Apartment> getApartaments() {
		return apartaments;
	}
	public void setApartaments(List<Apartment> apartaments) {
		this.apartaments = apartaments;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
