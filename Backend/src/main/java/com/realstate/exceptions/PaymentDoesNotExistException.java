package com.realstate.exceptions;

public class PaymentDoesNotExistException extends Exception {
	public PaymentDoesNotExistException() {
		super("El pago con el ID especificado no existe.");
	}
}
