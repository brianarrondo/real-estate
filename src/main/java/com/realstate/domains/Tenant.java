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
}