package com.realstate.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "lease")
public class Lease implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotNull(message = "cannot be null")
	private String name;
	
	@NotNull(message = "cannot be null")
	@ManyToMany(fetch = FetchType.LAZY)
	private List<Tenant> tenants = new ArrayList<Tenant>();

	@NotNull(message = "cannot be null")
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private Apartment apartment;

	@NotNull(message = "cannot be null")
	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY, mappedBy = "lease")
	private List<RentalFees> rentalFees = new ArrayList<RentalFees>();

	private Date startDate;
	private Date endDate;
	private boolean active;
	
	private String description;
	
	/* Constructors */
	public Lease() {}
	
	public Lease(long id, String name, List<Tenant> tenants, Apartment apartment, Date startDate, Date endDate, boolean active,
			String description) {
		super();
		this.id = id;
		this.name = name;
		this.tenants = tenants;
		this.apartment = apartment;
		this.startDate = startDate;
		this.endDate = endDate;
		this.active = active;
		this.description = description;
	}

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

	public List<Tenant> getTenants() {
		return tenants;
	}

	public void setTenants(List<Tenant> tenants) {
		this.tenants = tenants;
	}

	public Apartment getApartment() {
		return apartment;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<RentalFees> getRentalFees() {
		return rentalFees;
	}

	public void setRentalFees(List<RentalFees> rentalFees) {
		this.rentalFees = rentalFees;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
