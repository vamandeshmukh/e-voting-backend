package com.capgemini.exception;

public class NoSuchRecordException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSuchRecordException(String message) {
		super(message);
	}
}
