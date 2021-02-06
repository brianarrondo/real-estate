package com.realstate.exceptions;

public class AmountToPaidIsZeroException extends Exception {
	public AmountToPaidIsZeroException() {
		super("El monto a pagar es 0.");
	}
}
