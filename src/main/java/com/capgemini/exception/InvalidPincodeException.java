package com.capgemini.exception;

public class InvalidPincodeException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidPincodeException(String message) {
		super(message);
	}
}
