package com.capgemini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.exception.InvalidFieldException;
import com.capgemini.exception.NoSuchRecordException;
import com.capgemini.model.ElectoralOfficer;
import com.capgemini.model.Voter;
import com.capgemini.service.ElectoralOfficerService;

@RestController
@CrossOrigin
@RequestMapping(path = "e-voting/eo")
public class ElectoralOfficerController {

	@Autowired
	@Qualifier("eoService")
	private ElectoralOfficerService eoService;

//	http://localhost:9090/election-api/e-voting/eo/-/addEO
	@PostMapping(path = "/-/addEO")
	public ResponseEntity<String> saveEO(@RequestBody ElectoralOfficer eo) throws NoSuchRecordException {
		ResponseEntity<String> response = null;
		if (eoService.addEO(eo)) {
			response = new ResponseEntity<String>(eo.toString(), HttpStatus.CREATED);
		}
		return response;
	}

//	http://localhost:9090/election-api/e-voting/eo/-/login
	@PostMapping(path = "/-/login")
	public ResponseEntity<ElectoralOfficer> eoLogin(@RequestBody ElectoralOfficer eo)
		throws NoSuchRecordException {
		ElectoralOfficer result = eoService.loginEO(eo.getElectoralOfficerId(), eo.getElectoralOfficerPassword());
		ResponseEntity<ElectoralOfficer> response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}

//	http://localhost:9090/election-api/e-voting/eo/viewAllRequests
	@GetMapping(path = "/viewAllRequests")
	public ResponseEntity<List<Voter>> viewRequests() throws NoSuchRecordException {
		List<Voter> result = eoService.viewAllRequests();
		ResponseEntity<List<Voter>> response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}

//	http://localhost:9090/election-api/e-voting/eo/updateVoterStatus/{updatedstatus}/{adhaar}
	@PutMapping(path = "/updateVoterStatus/{updatedstatus}/{aadhaar}")
	public ResponseEntity<String> handleRequestsById(@PathVariable("aadhaar") long aadhaar,
			@PathVariable("updatedstatus") String updatedstatus) throws NoSuchRecordException {
		updatedstatus = updatedstatus.toLowerCase();
		ResponseEntity<String> response = null;
		if (updatedstatus.equals("accept")) {
			if (eoService.acceptVoterRequestById(aadhaar)) {
				response = new ResponseEntity<String>("voter is validated with id " + aadhaar, HttpStatus.OK);
			}
			return response;
		} else if (updatedstatus.equals("reject")) {
			if (eoService.rejectVoterRequestById(aadhaar)) {
				response = new ResponseEntity<String>("voter is rejected with id " + aadhaar, HttpStatus.OK);
			}
			return response;
		} else if (updatedstatus.equals("deactivate")) {
			if (eoService.deactivateVoterById(aadhaar)) {
				response = new ResponseEntity<String>("voter is deactivated with id " + aadhaar, HttpStatus.OK);
			}
			return response;
		} else {
			response = new ResponseEntity<String>("Invalid Status", HttpStatus.OK);
		}
		return response;
	}

//	http://localhost:9090/election-api/e-voting/eo/viewVoterRequestById/{aadhaar}
	@GetMapping(path = "/viewVoterRequestById/{aadhaar}")
	public ResponseEntity<Voter> viewRequestsbyId(@PathVariable("aadhaar") long aadhaar) throws NoSuchRecordException {
		Voter result = eoService.viewRequestById(aadhaar);
		ResponseEntity<Voter> response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}

//	http://localhost:9090/election-api/e-voting/eo/viewAllVoters
	@GetMapping(path = "/viewAllVoters")
	public ResponseEntity<List<Voter>> viewAllVoters() throws NoSuchRecordException {
		List<Voter> result = eoService.viewAllVoters();
		ResponseEntity<List<Voter>> response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}

	@ResponseStatus(code = HttpStatus.NOT_FOUND,reason="Fields are empty")
	@ExceptionHandler(value = InvalidFieldException.class)
	public ResponseEntity<String> handleInvalidFiedsException() {
		return new ResponseEntity<String>("Fields are empty", HttpStatus.OK);
	}
}
