package com.capgemini.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.exception.NoSuchRecordException;
import com.capgemini.exception.IncorrectLoginCredentialsException;
import com.capgemini.exception.InvalidFieldException;
import com.capgemini.model.Administrator;
import com.capgemini.model.Candidates;
import com.capgemini.model.Constituency;
import com.capgemini.model.Election;
import com.capgemini.model.Party;
import com.capgemini.model.Pincode;
import com.capgemini.model.Voter;
import com.capgemini.service.AdministratorService;

@RestController
@CrossOrigin
@RequestMapping(path = "e-voting/admin")
public class AdminController {

	@Autowired
	@Qualifier("adminService")
	private AdministratorService adminService;

	// http://localhost:9090/election-api/swagger-ui/

	// http://localhost:9090/election-api/e-voting/admin/-/addAdmin
	@PostMapping(path = "/-/addAdmin")
	public ResponseEntity<String> saveAdmin(@RequestBody Administrator admin) throws InvalidFieldException {
		ResponseEntity<String> response = null;
		if (adminService.addAdmin(admin)) {
			response = new ResponseEntity<String>(admin.toString(), HttpStatus.CREATED);
		}
		return response;
	}

	// http://localhost:9090/election-api/e-voting/admin/-/login
	@PostMapping(path = "/-/login")
	public ResponseEntity<Administrator> adminLogin(@RequestBody Administrator admin)
			throws IncorrectLoginCredentialsException {
		Administrator result = adminService.loginAdmin(admin.getAdminId(), admin.getAdminPassword());
		ResponseEntity<Administrator> response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}

	// http://localhost:9090/election-api/e-voting/admin/election/addElection
	@PostMapping(path = "/election/addElection")
	public ResponseEntity<String> saveElection(@RequestBody Election election) throws NoSuchRecordException {
		ResponseEntity<String> response = null;
		if (adminService.addElection(election)) {
			response = new ResponseEntity<String>("Election with id " + election.getElectionId() + " is added",
					HttpStatus.CREATED);
		}
		return response;
	}

	// http://localhost:9090/election-api/e-voting/admin/election/findAllElections
	@GetMapping(path = "/election/findAllElections")
	public List<Election> viewAllElectiones() throws NoSuchRecordException {
		List<Election> list = adminService.findAllElection();
		return list;
	}

	// http://localhost:9090/election-api/e-voting/admin/election/findElectionById:{electionId}
	@GetMapping(path = "/election/findElectionById:{electionId}")
	public ResponseEntity<Election> viewElectionById(@PathVariable("electionId") int electionId)
			throws NoSuchRecordException {
		Election result = adminService.findElectionById(electionId);
		ResponseEntity<Election> response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}

	// http://localhost:9090/election-api/e-voting/admin/election/findElectionByName:{electionName}
	@GetMapping(path = "/election/findElectionByName:{electionName}")
	public ResponseEntity<List<Election>> viewElectionByName(@PathVariable("electionName") String electionName)
			throws NoSuchRecordException {
		ResponseEntity<List<Election>> response = null;
		List<Election> list = adminService.findElectionByName(electionName);
		response = new ResponseEntity<List<Election>>(list, HttpStatus.OK);
		return response;
	}

	// http://localhost:9090/election-api/e-voting/admin/election/findElectionByType:{electionType}
	@GetMapping(path = "/election/findElectionByType:{electionType}")
	public ResponseEntity<List<Election>> viewElectionByType(@PathVariable("electionType") String electionType)
			throws NoSuchRecordException {
		ResponseEntity<List<Election>> response = null;
		List<Election> list = adminService.findElectionByType(electionType.toLowerCase());
		response = new ResponseEntity<List<Election>>(list, HttpStatus.OK);
		return response;
	}

	// http://localhost:9090/election-api/e-voting/admin/election/findElectionByDate/from:/to:
	@GetMapping(path = "/election/findElectionByDate/from:{from}/to:{to}")
	public ResponseEntity<List<Election>> viewElectionByDate(@PathVariable("from") String from,
			@PathVariable("to") String to) throws NoSuchRecordException {
		ResponseEntity<List<Election>> response = null;
		List<Election> list = adminService.findElectionByDate(from, to);
		response = new ResponseEntity<List<Election>>(list, HttpStatus.OK);
		return response;
	}

	// http://localhost:9090/election-api/e-voting/admin/election/editElectionName/election_ID:{electionId}/name:{electionName}
	@PutMapping(path = "/election/editElectionName/election_ID:{electionId}/name:{electionName}")
	public ResponseEntity<String> editElectionName(@PathVariable("electionId") int electionId,
			@PathVariable("electionName") String electionName) throws NoSuchRecordException {
		ResponseEntity<String> response = null;
		if (adminService.editElectionName(electionId, electionName)) {
			response = new ResponseEntity<String>("Updated: " + adminService.findElectionById(electionId).toString(),
					HttpStatus.OK);
		}
		return response;
	}

	// http://localhost:9090/election-api/e-voting/admin/election/editElectionType/election_ID:{electionId}/type:{electionType}
	@PutMapping(path = "/election/editElectionType/election_ID:{electionId}/type:{electionType}")
	public ResponseEntity<String> editElectionType(@PathVariable("electionId") int electionId,
			@PathVariable("electionType") String electionType) throws NoSuchRecordException {
		ResponseEntity<String> response = null;
		if (adminService.editElectionType(electionId, electionType)) {
			response = new ResponseEntity<String>("Updated: " + adminService.findElectionById(electionId).toString(),
					HttpStatus.OK);
		}
		return response;
	}

	// http://localhost:9090/election-api/e-voting/admin/election/editElectionDate/election_ID:{electionId}/electionDate:{electionDate}
	@PutMapping(path = "/election/editElectionDate/election_ID:{electionId}/electionDate:{electionDate}")
	public ResponseEntity<String> editElectionDate(@PathVariable("electionId") int electionId,
			@PathVariable("electionDate") String electionDate) throws NoSuchRecordException {
		ResponseEntity<String> response = null;
		if (adminService.editElectionDate(electionId, electionDate)) {
			response = new ResponseEntity<String>("Updated: " + adminService.findElectionById(electionId).toString(),
					HttpStatus.OK);
		}
		return response;
	}

	// http://localhost:9090/election-api/e-voting/admin/election/removeElection
	@DeleteMapping(path = "/election/removeElection:{electionId}")
	public ResponseEntity<String> removeElection(@PathVariable("electionId") int electionId)
			throws NoSuchRecordException {
		ResponseEntity<String> response = null;
		if (adminService.removeElection(electionId)) {
			response = new ResponseEntity<String>("Removed", HttpStatus.OK);
		}
		return response;
	}

	// http://localhost:9090/election-api/e-voting/admin/party/addParty
	@PostMapping(path = "/party/addParty")
	public ResponseEntity<String> addParty(@RequestBody Party party) throws NoSuchRecordException {
		ResponseEntity<String> response = null;
		if (adminService.addParty(party))
			response = new ResponseEntity<String>("party with id " + party.getRegId() + " is added",
					HttpStatus.CREATED);
		return response;
	}

	// http://localhost:9090/election-api/e-voting/admin/party/allParties
	@GetMapping(path = "/party/allParties")
	public ResponseEntity<List<Party>> viewAllParties() throws NoSuchRecordException {
		ResponseEntity<List<Party>> response = null;
		List<Party> list = adminService.readAllParties();
		response = new ResponseEntity<List<Party>>(list, HttpStatus.OK);
		return response;
	}

	// http://localhost:9090/election-api/e-voting/admin/party/searchById/partyId
	@GetMapping(path = "/party/searchByPartyId/{partyId}")
	public ResponseEntity<Party> viewPartyById(@PathVariable("partyId") String regId) throws NoSuchRecordException {
		ResponseEntity<Party> response = null;
		Party party = adminService.readByPartyRegId(regId);
		response = new ResponseEntity<Party>(party, HttpStatus.OK);
		return response;
	}

	// http://localhost:9090/election-api/e-voting/admin/party/searchByPartyName/partyName
	@GetMapping(path = "/party/searchByPartyName/{partyName}")
	public ResponseEntity<Party> viewPartyByName(@PathVariable("partyName") String partyName)
			throws NoSuchRecordException {
		ResponseEntity<Party> response = null;
		Party party = adminService.findByPartyName(partyName);
		response = new ResponseEntity<Party>(party, HttpStatus.OK);
		return response;
	}

	// http://localhost:9090/election-api/e-voting/admin/party/modifyLeader/partyId/leader
	@PutMapping(path = "/party/modifyLeader/{partyId}/{leader}")
	public ResponseEntity<String> modifyPartyRecord(@PathVariable("partyId") String regId,
			@PathVariable("leader") String leader) throws NoSuchRecordException {
		ResponseEntity<String> response = null;
		if (adminService.updateLeaderByRegdId(regId, leader)) {
			response = new ResponseEntity<String>("Given Party Record Updated", HttpStatus.OK);
		}
		return response;
	}

	// http://localhost:9090/election-api/e-voting/admin/party/deletePartyById/partyId
	@DeleteMapping(path = "/party/deletePartyById/{partyId}")
	public ResponseEntity<String> removeParty(@PathVariable("partyId") String regId) throws NoSuchRecordException {
		ResponseEntity<String> response = null;
		if (adminService.removePartyById(regId)) {
			response = new ResponseEntity<String>("party with the given Id deleted ", HttpStatus.OK);
		}
		return response;
	}

	// http://localhost:9090/election-api/e-voting/admin/candidate/viewAllCandidates/partyId
	@GetMapping(path = "/candidate/viewAllCandidates/{partyRegId}")
	public ResponseEntity<List<Candidates>> getAllCandidatesByPartyRegId(@PathVariable("partyRegId") String partyRegId)
			throws NoSuchRecordException {
		ResponseEntity<List<Candidates>> response = null;
		List<Candidates> list = adminService.viewCandidatesByPartyRegId(partyRegId);
		response = new ResponseEntity<List<Candidates>>(list, HttpStatus.OK);
		return response;
	}

	// http://localhost:9090/election-api/e-voting/admin/candidate/addCandidate
	@PostMapping(path = "/candidate/addCandidate")
	public ResponseEntity<String> saveCandidate(@RequestBody Candidates candidate) throws NoSuchRecordException {
		ResponseEntity<String> response = null;
		String result = adminService.addCandidate(candidate);
		if (result != null)
			response = new ResponseEntity<String>(result, HttpStatus.CREATED);
		return response;
	}

	// http://localhost:9090/election-api/e-voting/admin/candidate/viewAllCandidates
	@GetMapping(path = "/candidate/viewAllCandidates")
	public ResponseEntity<List<Candidates>> getAllCandidates() throws NoSuchRecordException {
		ResponseEntity<List<Candidates>> response = null;
		List<Candidates> list = adminService.viewCandidate();
		response = new ResponseEntity<List<Candidates>>(list, HttpStatus.OK);
		return response;
	}

	// http://localhost:9090/election-api/e-voting/admin/candidate/searchByCandidateId/CandidateId
	@GetMapping(path = "/candidate/searchByCandidateId/{CandidateId}")
	public ResponseEntity<Candidates> viewCandidateById(@PathVariable("CandidateId") int candidateId)
			throws NoSuchRecordException {
		ResponseEntity<Candidates> response = null;
		Candidates candidate = adminService.readCandidateId(candidateId);
		response = new ResponseEntity<Candidates>(candidate, HttpStatus.OK);
		return response;
	}

	// http://localhost:9090/election-api/e-voting/admin/candidate/searchByCandidateName/candidateName
	@GetMapping(path = "/candidate/searchByCandidateName/{candidateName}")
	public ResponseEntity<Candidates> viewCandidateByName(@PathVariable("candidateName") String candidateName)
			throws NoSuchRecordException {
		ResponseEntity<Candidates> response = null;
		Candidates candidates = adminService.findCandidateByName(candidateName.toLowerCase());
		response = new ResponseEntity<Candidates>(candidates, HttpStatus.OK);
		return response;
	}

	// http://localhost:9090/election-api/e-voting/admin/candidate/modifyCandidateName/candidateId/candidateName
	@PutMapping(path = "/candidate/modifyCandidateName/{candidateId}/{candidateName}")
	public ResponseEntity<String> modifyCandidateRecord(@PathVariable("candidateId") int candidateId,
			@PathVariable("candidateName") String candidateName) throws NoSuchRecordException {
		ResponseEntity<String> response = null;
		if (adminService.updateCandidateNameById(candidateId, candidateName)) {
			response = new ResponseEntity<String>("Given Candidate Record Updated", HttpStatus.OK);
		}
		return response;
	}

	// http://localhost:9090/election-api/e-voting/admin/candidate/deleteCandidateById/candidateId
	@DeleteMapping(path = "/candidate/deleteCandidateById/{candidateId}")
	public ResponseEntity<String> deleteCandidateById(@PathVariable("candidateId") int candidateId)
			throws NoSuchRecordException {
		ResponseEntity<String> response = null;
		if (adminService.removeCandidateById(candidateId))
			response = new ResponseEntity<String>("candidate with the given Id deleted ", HttpStatus.CREATED);
		return response;
	}

	// http://localhost:9090/election-api/e-voting/admin/constituency/addConstituency
	@PostMapping(path = "/constituency/addConstituency")
	public ResponseEntity<String> saveConstituency(@RequestBody Constituency constituency)
			throws NoSuchRecordException {
		ResponseEntity<String> response = null;
		if (adminService.addConstituency(constituency))
			response = new ResponseEntity<String>(
					"constituency with id " + constituency.getConstituencyId() + " is added", HttpStatus.CREATED);
		return response;
	}

	// http://localhost:9090/election-api/e-voting/admin/constituency/viewAllConstituency
	@GetMapping(path = "/constituency/viewAllConstituency")
	public ResponseEntity<List<Constituency>> getAllConstituency() throws NoSuchRecordException {
		ResponseEntity<List<Constituency>> response = null;
		List<Constituency> list = adminService.viewConstituency();
		response = new ResponseEntity<List<Constituency>>(list, HttpStatus.OK);
		return response;
	}

	// http://localhost:9090/election-api/e-voting/admin/constituency/searchByConstituencyId/constituencyId
	@GetMapping(path = "/constituency/searchByConstituencyId/{constituencyId}")
	public ResponseEntity<Constituency> viewConstituencyById(@PathVariable("constituencyId") int constituencyId)
			throws NoSuchRecordException {
		ResponseEntity<Constituency> response = null;
		Constituency constituency = adminService.readConstituencyById(constituencyId);
		response = new ResponseEntity<Constituency>(constituency, HttpStatus.OK);
		return response;
	}

	// http://localhost:9090/election-api/e-voting/admin/constituency/searchByConstituencyName/constituencyName
	@GetMapping(path = "/constituency/searchByConstituencyName/{constituencyName}")
	public ResponseEntity<Constituency> viewConstituencyByName(
			@PathVariable("constituencyName") String constituencyName) throws NoSuchRecordException {
		ResponseEntity<Constituency> response = null;
		Constituency constitunency = adminService.findConstituencyByName(constituencyName);
		response = new ResponseEntity<Constituency>(constitunency, HttpStatus.OK);
		return response;
	}

	// http://localhost:9090/election-api/e-voting/admin/constituency/modifyConstituencyName/constituencyId/constituencyName
	@PutMapping(path = "/constituency/modifyConstituencyName/{constituencyId}/{constituencyName}")
	public ResponseEntity<String> modifyConstituencyRecord(@PathVariable("constituencyId") int constituencyId,
			@PathVariable("constituencyName") String constituencyName) throws NoSuchRecordException {
		ResponseEntity<String> response = null;
		if (adminService.updateConstituencyNameById(constituencyId, constituencyName)) {
			response = new ResponseEntity<String>("Given Constituency Record Updated", HttpStatus.OK);
		}
		return response;
	}

	// http://localhost:9090/election-api/e-voting/admin/constituency/deleteconstituencybyConstituencyId/constituencyId
	@DeleteMapping(path = "/constituency/deleteconstituencybyConstituencyId/{constituencyId}")
	public ResponseEntity<String> deleteConstituencyById(@PathVariable("constituencyId") int constituencyId)
			throws NoSuchRecordException {
		ResponseEntity<String> response = null;
		if (adminService.removeConstituency(constituencyId)) {
			response = new ResponseEntity<String>("Constituency with the given Id deleted ", HttpStatus.CREATED);
		}
		return response;
	}

	// http://localhost:9090/election-api/e-voting/admin/voter/viewAllVoter
	@GetMapping(path = "/voter/viewAllVoter/")
	public ResponseEntity<List<Voter>> readAllvoter() throws NoSuchRecordException {
		List<Voter> result = adminService.readAllVoter();
		ResponseEntity<List<Voter>> response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}

	// http://localhost:9090/election-api/e-voting/admin/voter/findVoterByEpic/epic
	@GetMapping(path = "/voter/findVoterByEpic/{epic}")
	public ResponseEntity<Voter> findVoterByEpic(@PathVariable("epic") String epic) throws NoSuchRecordException {
		ResponseEntity<Voter> response = null;
		Voter result = adminService.findVoterByEpic(epic);
		response = new ResponseEntity<Voter>(result, HttpStatus.OK);
		return response;
	}

	// http://localhost:9090/election-api/e-voting/admin/voter/findVoterByName/voterName
	@GetMapping(path = "/voter/findVoterByName/{voterName}")
	public ResponseEntity<List<Voter>> viewVoterByName(@PathVariable("voterName") String voterName)
			throws NoSuchRecordException {
		ResponseEntity<List<Voter>> response = null;
		List<Voter> list = adminService.findVoterByName(voterName.toLowerCase());
		response = new ResponseEntity<List<Voter>>(list, HttpStatus.OK);
		return response;
	}

	// http://localhost:9090/election-api/e-voting/admin/voter/updateStatusOfVoter/epic
	@PutMapping(path = "/voter/updateStatusOfVoter/{epic}")
	public ResponseEntity<String> updateStatusOfVoter(@PathVariable("epic") String epic) throws NoSuchRecordException {
		ResponseEntity<String> response = null;
		if (adminService.updateStatusOfVoterByepic(epic)) {
			response = new ResponseEntity<String>("User approved with id: " + epic, HttpStatus.OK);
		}
		return response;
	}

	// http://localhost:9090/election-api/e-voting/admin/voter/viewAllRequests
	@GetMapping(path = "/voter/viewAllRequests")
	public ResponseEntity<List<Voter>> viewAllRequests() throws NoSuchRecordException {
		List<Voter> result = adminService.viewAllRequests();
		ResponseEntity<List<Voter>> response = new ResponseEntity<>(result, HttpStatus.OK);
		return response;
	}

	// http://localhost:9090/election-api/e-voting/admin/pincode/addPincode
	@PostMapping(path = "/pincode/addPincode")
	public ResponseEntity<String> savePincode(@RequestBody Pincode pincode) throws NoSuchRecordException {
		ResponseEntity<String> response = null;
		if (adminService.addPincode(pincode)) {
			response = new ResponseEntity<String>("pincode added with id: " + pincode.getPincode(), HttpStatus.CREATED);
		}
		return response;
	}

	@ResponseStatus(code = HttpStatus.NOT_FOUND,reason="Fields are empty")
	@ExceptionHandler(value = InvalidFieldException.class)
	public ResponseEntity<String> handleInvalidFiedsException() {
		return new ResponseEntity<String>("Fields are empty", HttpStatus.OK);
	}

}
