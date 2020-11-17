package com.realstate.domains;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tenant")
public class Tenant implements Serializable {
	
	private static final long serialVersionUID = 42L;
	
	@Id
	private String tenantId;
	private String fullName;
	private String dni;
	private String phone;
	private String description;
	
	/* Constructors */
	public Tenant(String tenantId, String fullName, String dni, String phone, String description) {
		super();
		this.tenantId = tenantId;
		this.fullName = fullName;
		this.dni = dni;
		this.phone = phone;
		this.description = description;
	}

	/* Getters and Setters */
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((dni == null) ? 0 : dni.hashCode());
		result = prime * result + ((fullName == null) ? 0 : fullName.hashCode());
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
		result = prime * result + ((tenantId == null) ? 0 : tenantId.hashCode());
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
		Tenant other = (Tenant) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (dni == null) {
			if (other.dni != null)
				return false;
		} else if (!dni.equals(other.dni))
			return false;
		if (fullName == null) {
			if (other.fullName != null)
				return false;
		} else if (!fullName.equals(other.fullName))
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		if (tenantId == null) {
			if (other.tenantId != null)
				return false;
		} else if (!tenantId.equals(other.tenantId))
			return false;
		return true;
	}
	
}