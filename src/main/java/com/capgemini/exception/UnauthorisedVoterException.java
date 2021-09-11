package com.capgemini.exception;

public class UnauthorisedVoterException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnauthorisedVoterException(String message) {
		super(message);
	}
}
