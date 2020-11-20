package com.realstate.exceptions;

public class InvalidParametersException extends Exception{
	public InvalidParametersException() {
		super("Los parametros del json del request son invalidos.");
	}
}
