package com.realstate.exceptions;

public class RentalBillHasAlreadyBeenPaidException extends Exception {
	public RentalBillHasAlreadyBeenPaidException() {
		super("La factura ya fue pagada.");
	}
}
