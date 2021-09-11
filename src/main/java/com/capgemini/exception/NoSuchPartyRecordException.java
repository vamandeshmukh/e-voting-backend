package com.capgemini.exception;

public class NoSuchPartyRecordException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoSuchPartyRecordException(String message) {
		super(message);
	}
}
