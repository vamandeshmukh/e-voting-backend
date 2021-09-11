package com.capgemini.exception;

public class UnderAgeException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnderAgeException(String message) {
		super(message);
	}
}
