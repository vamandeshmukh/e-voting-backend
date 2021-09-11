package com.capgemini.exception;

public class AlreadyVotedException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AlreadyVotedException(String message) {
		super(message);
	}
}
