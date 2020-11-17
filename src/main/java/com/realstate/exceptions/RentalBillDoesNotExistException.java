package com.realstate.exceptions;

public class RentalBillDoesNotExistException extends Exception {
	public RentalBillDoesNotExistException() {
		super("La factura con el ID especificado no existe.");
	}
}
