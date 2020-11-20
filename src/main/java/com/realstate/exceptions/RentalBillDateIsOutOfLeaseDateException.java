package com.realstate.exceptions;

public class RentalBillDateIsOutOfLeaseDateException extends Exception {
	public RentalBillDateIsOutOfLeaseDateException() {
		super("La fecha de la factura esta por fuera de las fechas del contrato.");
	}
}
