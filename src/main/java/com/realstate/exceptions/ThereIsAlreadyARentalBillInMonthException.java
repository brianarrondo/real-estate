package com.realstate.exceptions;

public class ThereIsAlreadyARentalBillInMonthException extends Exception {
	public ThereIsAlreadyARentalBillInMonthException() {
		super("Ya existe una factura en el mes");
	}
}
