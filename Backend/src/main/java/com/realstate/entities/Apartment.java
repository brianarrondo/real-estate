package com.realstate.entities;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "apartment")
public class Apartment implements Serializable {
	
	@Id
	private String apartmentId;
	private String estateId;
	private int rooms;
	private String name;
	private String description;
	
	/* Constructors */
	public Apartment(String apartmentId, String estateId, int rooms, String name, String description) {
		super();
		this.apartmentId = apartmentId;
		this.estateId = estateId;
		this.rooms = rooms;
		this.name = name;
		this.description = description;
	}

	/* Getters and Setters */
	public String getApartmentId() {
		return apartmentId;
	}
	public void setApartmentId(String apartmentId) {
		this.apartmentId = apartmentId;
	}
	public String getEstateId() {
		return estateId;
	}
	public void setEstateId(String estateId) {
		this.estateId = estateId;
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
		result = prime * result + ((estateId == null) ? 0 : estateId.hashCode());
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
		if (estateId == null) {
			if (other.estateId != null)
				return false;
		} else if (!estateId.equals(other.estateId))
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
