package com.realstate.exceptions;

public class TenantDoesNotExistException extends Exception {
	public TenantDoesNotExistException() {
		super("El inquilino no existe o no fue encontrado.");
	}
}
