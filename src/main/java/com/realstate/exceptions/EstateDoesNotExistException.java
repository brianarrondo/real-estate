package com.realstate.exceptions;

public class EstateDoesNotExistException extends Exception {
	public EstateDoesNotExistException() {
		super("La propiedad con el ID especificado no existe.");
	}
}
