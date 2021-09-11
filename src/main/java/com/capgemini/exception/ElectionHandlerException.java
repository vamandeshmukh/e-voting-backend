package com.capgemini.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ElectionHandlerException {
	@ResponseStatus(code = HttpStatus.NOT_FOUND,reason="NO such candidate record found")
	@ExceptionHandler(value = NoSuchCandidateRecordException.class)
	
	public ResponseEntity<String> handleException() {
		return new ResponseEntity<String>("NO such candidate record found", HttpStatus.NOT_FOUND);
	}

	@ResponseStatus(code = HttpStatus.NOT_FOUND,reason="Incorrect Login Credentials")
	@ExceptionHandler(value = IncorrectLoginCredentialsException.class)
	public ResponseEntity<String> handleLoginException() {
		return new ResponseEntity<String>("Incorrect Login Credentials", HttpStatus.NOT_FOUND);
	}

	@ResponseStatus(code = HttpStatus.NOT_FOUND,reason="NO such party record found")
	@ExceptionHandler(value = NoSuchPartyRecordException.class)
	public ResponseEntity<String> handlePartyRecordsException() {
		return new ResponseEntity<String>("NO such party record found", HttpStatus.NOT_FOUND);
	}

	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR,reason="NO such election record found ")
	@ExceptionHandler(value = NoSuchElectionException.class)
	public  ResponseEntity<String> handleElectionRecordsException() {
		return new ResponseEntity<String>("NO such election record found", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ResponseStatus(code = HttpStatus.NOT_FOUND,reason="No constituency record found")
	@ExceptionHandler(value = NoSuchConstituencyException.class)
	public ResponseEntity<String> handleConstituencyRecordsException() {
		return new ResponseEntity<String>("No constituency record found", HttpStatus.NOT_FOUND);
	}

	@ResponseStatus(code = HttpStatus.NOT_FOUND,reason="Record Already Exists")
	@ExceptionHandler(value = AlreadyExistsException.class)
	public ResponseEntity<String> AlreadyExistsException() {
		return new ResponseEntity<String>("Record Already Exists", HttpStatus.NOT_FOUND);
	}

	@ResponseStatus(code = HttpStatus.NOT_FOUND,reason="No Such Record Found")
	@ExceptionHandler(value = NoSuchRecordException.class)
	public ResponseEntity<String> NoRecordFoundException() {
		return new ResponseEntity<String>("No Such Record Found", HttpStatus.NOT_FOUND);
	}

	@ResponseStatus(code = HttpStatus.NOT_FOUND,reason="No Such Record Found")
	@ExceptionHandler(value = NoSuchVoterException.class)
	public ResponseEntity<String> NoSuchVoterException() {
		return new ResponseEntity<String>("No Such Voter Found", HttpStatus.NOT_FOUND);
	}

	@ResponseStatus(code = HttpStatus.NOT_FOUND,reason="Invalid Aadhaar Number")
	@ExceptionHandler(value = InvalidAadhaarException.class)
	public ResponseEntity<String> InvalidAadhaarException() {
		return new ResponseEntity<String>("Invalid Aadhaar Number", HttpStatus.NOT_FOUND);
	}
}