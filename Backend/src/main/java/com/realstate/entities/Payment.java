package com.realstate.entities;

import java.io.Serializable;
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
	private Date date;

	/* Constructors */
	public Payment(long paymentId, float amount, RentalBill rentalBill, Date date) {
		super();
		this.id = paymentId;
		this.amount = amount;
		this.rentalBill = rentalBill;
		this.date = date;
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
