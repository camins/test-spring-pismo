package com.test.pismo.exceptions;

public class AmountException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public AmountException(String message) {
		super(message);
	}
}
