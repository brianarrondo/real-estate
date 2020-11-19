package com.realstate.exceptions;

public class ApartmentDoesNotExistException extends Exception {
	public ApartmentDoesNotExistException() {
		super("El departamento con el ID especificado no existe.");
	}
}
