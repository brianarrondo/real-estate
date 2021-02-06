package com.realstate.exceptions;

public class LeaseIsNotActiveException extends Exception {
	public LeaseIsNotActiveException() {
		super("El contrato de alquiler no se encuentra activo.");
	}
}
