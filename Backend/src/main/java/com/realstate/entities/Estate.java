package com.realstate.entities;

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
	private List<Apartment> apartments = new ArrayList<Apartment>();
	
	/* Constructors */
	public Estate(String estateId, String name, String address, String description, List<Apartment> apartments) {
		super();
		this.estateId = estateId;
		this.name = name;
		this.address = address;
		this.description = description;
		this.apartments = apartments;
	}
	
	/* Getters and Setters */
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
	public List<Apartment> getApartments() {
		return apartments;
	}
	public void setApartments(List<Apartment> apartments) {
		this.apartments = apartments;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((apartments == null) ? 0 : apartments.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((estateId == null) ? 0 : estateId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Estate other = (Estate) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (apartments == null) {
			if (other.apartments != null)
				return false;
		} else if (!apartments.equals(other.apartments))
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
		return true;
	}
}
