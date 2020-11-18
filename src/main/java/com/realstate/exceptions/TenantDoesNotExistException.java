package com.realstate.exceptions;

public class TenantDoesNotExistException extends Exception {
	public TenantDoesNotExistException() {
		super("El inquilino con el ID especificado no existe.");
	}
}
