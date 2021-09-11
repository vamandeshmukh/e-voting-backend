package com.capgemini.exception;

public class NoSuchVoterException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSuchVoterException(String message) {
		super(message);
	}
}
