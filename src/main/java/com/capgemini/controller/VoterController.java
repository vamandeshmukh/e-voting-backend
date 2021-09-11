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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.exception.AlreadyVotedException;
import com.capgemini.exception.InvalidEmailException;
import com.capgemini.exception.InvalidFieldException;
import com.capgemini.exception.InvalidGenderException;
import com.capgemini.exception.InvalidMobileNumberException;
import com.capgemini.exception.InvalidNameException;
import com.capgemini.exception.InvalidPincodeException;
import com.capgemini.exception.NoSuchRecordException;
import com.capgemini.exception.NotScheduledException;
import com.capgemini.exception.UnauthorisedVoterException;
import com.capgemini.exception.UnderAgeException;
import com.capgemini.model.Candidates;
import com.capgemini.model.Election;
import com.capgemini.model.Party;
import com.capgemini.model.Pincode;
import com.capgemini.model.Vote;
import com.capgemini.model.Voter;
import com.capgemini.service.VoterService;

@RestController
@CrossOrigin
@RequestMapping(path = "e-voting/voter")
public class VoterController {

	@Autowired
	@Qualifier("voterService")
	private VoterService voterService;

//	http://localhost:9090/election-api/e-voting/voter/castVote/
	@PostMapping(path = "/castVote")
	public ResponseEntity<String> castVote(@RequestBody Vote vote) throws NoSuchRecordException {
		ResponseEntity<String> response = null;
		String result = voterService.voteCasting(vote.getEpic(), vote.getCandidate());
		if (result != null) {
			response = new ResponseEntity<String>(result, HttpStatus.CREATED);
		}
		return response;
	}

// 	http://localhost:9090/election-api/e-voting/voter/-/addVoter
	@PostMapping(path = "/-/addVoter")
	public ResponseEntity<String> saveVoter(@RequestBody Voter voter) throws NoSuchRecordException {
		ResponseEntity<String> response = null;
		if (voterService.addVoter(voter)) {
			response = new ResponseEntity<String>(voter.toString(), HttpStatus.CREATED);
		}
		return response;
	}

//	http://localhost:9090/election-api/e-voting/voter/-/login
	@PostMapping(path = "/-/login")
	public ResponseEntity<Voter> voterLogin(@RequestBody Voter voter) throws NoSuchRecordException {
		Voter result = voterService.loginVoter(voter.getAadhaarId(), voter.getVoterPassword());
		ResponseEntity<Voter> response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}

//	http://localhost:9090/election-api/e-voting/voter/viewVoterDetailsById/{aadhaar}
	@GetMapping(path = "/viewVoterDetailsById/{aadhaar}")
	public ResponseEntity<Voter> getVoterId(@PathVariable("aadhaar") long aadhaar) throws NoSuchRecordException {
		Voter result = voterService.viewVoter(aadhaar);
		ResponseEntity<Voter> response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}

//	http://localhost:9090/election-api/e-voting/voter/viewElectionSchedule
	@GetMapping(path = "/viewElectionSchedule")
	public ResponseEntity<List<Election>> getSchedule() throws NoSuchRecordException {
		List<Election> result = voterService.viewSchedule();
		ResponseEntity<List<Election>> response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}

//	http://localhost:9090/election-api/e-voting/voter/viewParties
	@GetMapping(path = "/viewParties")
	public ResponseEntity<List<Party>> getParties() throws NoSuchRecordException {
		List<Party> result = voterService.viewParty();
		ResponseEntity<List<Party>> response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}

//	http://localhost:9090/election-api/e-voting/voter/viewCandidates
	@GetMapping(path = "/viewCandidates")
	public ResponseEntity<List<Candidates>> getCandidates() throws NoSuchRecordException {
		List<Candidates> result = voterService.viewCandidates();
		ResponseEntity<List<Candidates>> response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}

//	http://localhost:9090/election-api/e-voting/voter/viewCandidatesByConstituency/{aadhaar}
	@GetMapping(path = "/viewCandidatesByConstituency/{aadhaar}")
	public ResponseEntity<List<Candidates>> getCandidatesByConstituency(@PathVariable("aadhaar") long aadhaar)
			throws NoSuchRecordException {
		List<Candidates> result = voterService.viewCandidatesByConstituency(aadhaar);
		ResponseEntity<List<Candidates>> response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}

//	http://localhost:9090/election-api/e-voting/voter/results/viewAllCandidatesVoteCount
	@GetMapping(path = "/results/viewAllCandidatesVoteCount")
	public ResponseEntity<String> getCandidatesVoteCount() {
		String result = voterService.viewVoteForAllCandidate();
		ResponseEntity<String> response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}

//	http://localhost:9090/election-api/e-voting/voter/results/viewPartyVoteCount
	@GetMapping(path = "/results/viewPartyVoteCount")
	public ResponseEntity<String> getPartyVoteCount() {
		String result = voterService.viewVoteForAllParty();
		ResponseEntity<String> response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}

//	http://localhost:9090/election-api/e-voting/voter/results/viewPartyVoteConstituency/{constituency}
	@GetMapping(path = "/results/viewPartyVoteConstituency/{constituency}")
	public ResponseEntity<String> getPartyVoteConstituency(@PathVariable("constituency") int constituency)
			throws NoSuchRecordException {
		String result = voterService.viewVoteForConstituency(constituency);
		ResponseEntity<String> response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}

//	 http://localhost:9090/election-api/e-voting/admin/pincode/findPincodeByState/{state}
	@GetMapping(path = "/pincode/findPincodeByState/{state}")
	public ResponseEntity<List<Pincode>> viewPincodeByState(@PathVariable("state") String state)
			throws NoSuchRecordException {
		ResponseEntity<List<Pincode>> response = null;
		List<Pincode> list = voterService.findPincodeByState(state);
		response = new ResponseEntity<List<Pincode>>(list, HttpStatus.OK);
		return response;
	}

//Exception handlers

	@ResponseStatus(code = HttpStatus.NOT_FOUND,reason="Fields are empty")
	@ExceptionHandler(value = InvalidFieldException.class)
	public ResponseEntity<String> handleInvalidFiedsException() {
		return new ResponseEntity<String>("Fields are empty", HttpStatus.OK);
	}

	@ResponseStatus(code = HttpStatus.NOT_FOUND,reason="Invalid Email Address Or Email Address Already Exists")
	@ExceptionHandler(value = InvalidEmailException.class)
	public ResponseEntity<String> InvalidEmailException() {
		return new ResponseEntity<String>("Invalid Email Address Or Email Address Already Exists",
				HttpStatus.NOT_FOUND);
	}

	@ResponseStatus(code = HttpStatus.NOT_FOUND,reason="User Under Age Or Invalid Date of Birth")
	@ExceptionHandler(value = UnderAgeException.class)
	public ResponseEntity<String> UnderAgeException() {
		return new ResponseEntity<String>("User Under Age Or Invalid Date of Birth", HttpStatus.NOT_FOUND);
	}

	@ResponseStatus(code = HttpStatus.NOT_FOUND,reason="Invalid Username")
	@ExceptionHandler(value = InvalidNameException.class)
	public ResponseEntity<String> InvalidNameException() {
		return new ResponseEntity<String>("Invalid Username ", HttpStatus.NOT_FOUND);
	}

	
	@ExceptionHandler(value = InvalidGenderException.class)
	public ResponseEntity<String> InvalidGenderException() {
		return new ResponseEntity<String>("Invalid Gender ", HttpStatus.NOT_FOUND);
	}
	
	@ResponseStatus(code = HttpStatus.NOT_FOUND,reason="Invalid Pincode")
	@ExceptionHandler(value = InvalidPincodeException.class)
	public ResponseEntity<String> InvalidPincodeException() {
		return new ResponseEntity<String>("Invalid Pincode ", HttpStatus.NOT_FOUND);
	}

	@ResponseStatus(code = HttpStatus.NOT_FOUND,reason="Invalid Mobile Number Or Mobile Number Already Exists")
	@ExceptionHandler(value = InvalidMobileNumberException.class)
	public ResponseEntity<String> InvalidMobileNumberException() {
		return new ResponseEntity<String>("Invalid Mobile Number Or Mobile Number Already Exists ",
				HttpStatus.NOT_FOUND);
	}

	@ResponseStatus(code = HttpStatus.NOT_FOUND,reason="User Already Voted")
	@ExceptionHandler(value = AlreadyVotedException.class)
	public ResponseEntity<String> AlreadyVotedException() {
		return new ResponseEntity<String>("User Already Voted ", HttpStatus.NOT_FOUND);
	}

	@ResponseStatus(code = HttpStatus.NOT_FOUND,reason="Unauthorised Voter")
	@ExceptionHandler(value = UnauthorisedVoterException.class)
	public ResponseEntity<String> UnauthorisedVoterException() {
		return new ResponseEntity<String>("Unauthorised Voter ", HttpStatus.NOT_FOUND);
	}

	@ResponseStatus(code = HttpStatus.NOT_FOUND,reason="Election not Scheduled for Today ")
	@ExceptionHandler(value = NotScheduledException.class)
	public ResponseEntity<String> NotScheduledException() {
		return new ResponseEntity<String>("Election not Scheduled for Today ", HttpStatus.NOT_FOUND);
	}

}
