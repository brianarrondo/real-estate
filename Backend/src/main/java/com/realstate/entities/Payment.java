package com.realstate.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "payment")
public class Payment implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private float amount;
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private RentalBill rentalBill;
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	private User user;
	private Date date;
	private Date registerDate;

	/* Constructors */
	public Payment(long id, float amount, RentalBill rentalBill, User user, Date date) {
		super();
		this.id = id;
		this.amount = amount;
		this.rentalBill = rentalBill;
		this.date = date;
		this.registerDate = java.util.Date.from(Instant.now());
		this.user = user;
	}
	
	public Payment() {}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public RentalBill getRentalBill() {
		return rentalBill;
	}

	public void setRentalBill(RentalBill rentalBill) {
		this.rentalBill = rentalBill;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
