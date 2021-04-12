package com.realstate.entities;

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
	
	/* Constructors */
	public Apartment(String apartmentId, Estate estate, int rooms, String name, String description) {
		super();
		this.apartmentId = apartmentId;
		this.estate = estate;
		this.rooms = rooms;
		this.name = name;
		this.description = description;
	}

	/* Getters and Setters */
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apartmentId == null) ? 0 : apartmentId.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((estate == null) ? 0 : estate.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + rooms;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Apartment other = (Apartment) obj;
		if (apartmentId == null) {
			if (other.apartmentId != null)
				return false;
		} else if (!apartmentId.equals(other.apartmentId))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (estate == null) {
			if (other.estate != null)
				return false;
		} else if (!estate.equals(other.estate))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (rooms != other.rooms)
			return false;
		return true;
	}
	
	
}
