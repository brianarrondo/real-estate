package com.realstate.exceptions;

public class AmountPaymentHigherThanRentalBillException extends Exception {
	public AmountPaymentHigherThanRentalBillException() {
		super("El monto a pagar es superior al de la factura.");
	}
}
