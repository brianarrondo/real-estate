package com.realstate.exceptions;

public class LeaseDoesNotExistException extends Exception {
	public LeaseDoesNotExistException() {
		super("El contrato de alquiler con el ID especificado no existe.");
	}
}
