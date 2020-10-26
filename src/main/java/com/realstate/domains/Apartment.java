package com.realstate.domains;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "apartment")
public class Apartment implements Serializable {
	
	@Id
	private String apartmentId;
	private Estate estate;
	private int rooms;
	private String name;
	private String description;

	public String getApartamentId() {
		return apartmentId;
	}
	public void setApartamentId(String apartmentId) {
		this.apartmentId = apartmentId;
	}
	public Estate getEstate() {
		return estate;
	}
	public void setEstate(Estate estate) {
		this.estate = estate;
	}
	public int getRooms() {
		return rooms;
	}
	public void setRooms(int rooms) {
		this.rooms = rooms;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
