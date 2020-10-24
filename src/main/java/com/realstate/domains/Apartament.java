package com.realstate.domains;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "apartament")
public class Apartament implements Serializable {
	
	@Id
	private String apartamentId;
	private Estate estate;
	private int rooms;
	private String name;
	private String description;

	
	public String getApartamentId() {
		return apartamentId;
	}
	public void setApartamentId(String apartamentId) {
		this.apartamentId = apartamentId;
	}
	public int getRooms() {
		return rooms;
	}
	public void setRooms(int rooms) {
		this.rooms = rooms;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Estate getEstateId() {
		return estate;
	}
	public void setEstateId(Estate estateId) {
		this.estate = estateId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
