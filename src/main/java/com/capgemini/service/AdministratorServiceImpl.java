package com.capgemini.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.exception.AlreadyExistsException;
import com.capgemini.exception.IncorrectLoginCredentialsException;
import com.capgemini.exception.InvalidFieldException;
import com.capgemini.exception.NoSuchCandidateRecordException;
import com.capgemini.exception.NoSuchConstituencyException;
import com.capgemini.exception.NoSuchElectionException;
import com.capgemini.exception.NoSuchPartyRecordException;
import com.capgemini.exception.NoSuchRecordException;
import com.capgemini.exception.NoSuchVoterException;
import com.capgemini.model.Administrator;
import com.capgemini.model.Candidates;
import com.capgemini.model.Constituency;
import com.capgemini.model.Election;
import com.capgemini.model.Party;
import com.capgemini.model.Pincode;
import com.capgemini.model.Voter;
import com.capgemini.repository.AdministratorRepository;
import com.capgemini.repository.CandidatesRepository;
import com.capgemini.repository.ConstituencyRepository;
import com.capgemini.repository.ElectionRepository;
import com.capgemini.repository.PartyRepository;
import com.capgemini.repository.PincodeRepository;
import com.capgemini.repository.VoterRepository;

import logging.GlobalResources;

@Service("adminService")
public class AdministratorServiceImpl implements AdministratorService {

	private Logger Logger = GlobalResources.getLogger(AdministratorServiceImpl.class);

	@Autowired
	private AdministratorRepository adminRepository;
	@Autowired
	private ElectionRepository electionRepository;
	@Autowired
	private CandidatesRepository candidatesRepository;
	@Autowired
	private ConstituencyRepository constituencyRepository;
	@Autowired
	private PartyRepository partyRepository;
	@Autowired
	private VoterRepository voterRepository;
	@Autowired
	private PincodeRepository pincodeRepository;

	// Administrator Functionalities

	@Override
	@Transactional
	public boolean addAdmin(Administrator admin) throws InvalidFieldException {
		if (admin.getAdminId() != null && admin.getAdminName() != null && admin.getAdminPassword() != null) {
			boolean result = false;
			String name = admin.getAdminName();
			String regex = "^[A-Za-z ]+";
			if (name.matches(regex)) {
				admin = adminRepository.save(admin);
				result = true;
				if (!admin.getAdminId().isEmpty()) {
					Logger.info("Admin is added successfully");
					return result;
				}
			}
			Logger.error("incorrect details");
			throw new InvalidFieldException("Not able to add admin record");
		}
		throw new InvalidFieldException("Fields are empty");
	}

	@Override
	public Administrator loginAdmin(String adminId, String password) throws IncorrectLoginCredentialsException {
		Administrator admin = null;

		if (adminRepository.existsById(adminId)
				&& adminRepository.findById(adminId).get().getAdminPassword().equals(password)) {
			admin = adminRepository.findById(adminId).get();
			Logger.info("Admin login is  successfull");
			return admin;
		}
		Logger.error("Admin details are incorrect");
		throw new IncorrectLoginCredentialsException("Invalid Credentials");
	}

	// Election Functionalities

	@Override
	@Transactional
	public boolean addElection(Election election)
			throws InvalidFieldException, NoSuchElectionException, AlreadyExistsException {
		boolean result = false;

		if (election.getElectionDate() != null && election.getElectionName() != null
				&& election.getElectionType() != null) {

			if (electionRepository.findElectionsByName(election.getElectionName().toLowerCase()) == null) {

				election = electionRepository.save(election);
				result = true;
				if (election.getElectionId() != 0) {
					Logger.info("Election is added");
					return result;
				}
				Logger.error("Not able to add Election record");
				throw new NoSuchElectionException("Not able to add Election record");
			}
			Logger.error("Election Name Already Exists");
			throw new AlreadyExistsException("Election Name Already Exists");
		}
		Logger.error("Field is Empty");
		throw new InvalidFieldException("Field is Empty");
	}

	@Override
	public Election findElectionById(int electionId) throws NoSuchElectionException {
		if (electionRepository.existsById(electionId)) {
			Logger.info("findElectionById");
			return electionRepository.findById(electionId).get();
		}
		Logger.error("Given Election id is not found");
		throw new NoSuchElectionException("Given Election id is not found");
	}

	@Override
	public List<Election> findElectionByName(String electionName) throws NoSuchElectionException {
		List<Election> list = electionRepository.readAllElectionsByName(electionName.toLowerCase());
		if (!list.isEmpty()) {
			Logger.info("findElectionByName");
			return list;
		}
		Logger.error("Given Election id is not found");
		throw new NoSuchElectionException("Given Election id is not found");
	}

	@Override
	public List<Election> findElectionByType(String electionType) throws NoSuchElectionException {
		List<Election> list = electionRepository.readAllElectionsByType(electionType.toLowerCase());
		if (!list.isEmpty()) {
			Logger.info("findElectionByType");
			return list;
		}
		Logger.error("No such election type exist");
		throw new NoSuchElectionException("No such election type exist");
	}

	@Override
	public List<Election> findElectionByDate(String from, String to) throws NoSuchRecordException {
		List<Election> list = electionRepository.readAllElectionsByDate(from, to);
		if (!list.isEmpty()) {
			Logger.info("findElectionByDate");
			return list;
		}
		Logger.error("No election Scheduled");
		throw new NoSuchRecordException("No election Scheduled");
	}

	@Override
	public List<Election> findAllElection() throws NoSuchRecordException {
		List<Election> list = electionRepository.findAll();
		if (!list.isEmpty()) {
			Logger.info("findAllElection");
			return list;
		}
		Logger.error("No List found");
		throw new NoSuchRecordException("No List found");
	}

	@Override
	@Transactional
	public boolean editElectionName(int electionId, String electionName) throws NoSuchElectionException {
		if (electionRepository.existsById(electionId)) {			
				electionRepository.editElectionName(electionId, electionName);
				Logger.info("editElectionName");
				return true;
		}
		Logger.error("Given id does not exist to update ElectionName");
		throw new NoSuchElectionException("Given id does not exist to update ElectionName");
	}

	@Override
	@Transactional
	public boolean editElectionType(int electionId, String electionType) throws NoSuchElectionException {
		if (electionRepository.existsById(electionId)) {
			electionRepository.editElectionType(electionId, electionType);
			Logger.info("editElectionType");
			return true;
		}
		Logger.error("Given id does not exist to update ElectionType");
		throw new NoSuchElectionException("Given id does not exist to update ElectionType");
	}

	@Override
	@Transactional
	public boolean editElectionDate(int electionId, String electionDate) throws NoSuchElectionException {
		if (electionRepository.existsById(electionId)) {
			electionRepository.editElectionDate(electionId, electionDate);
			Logger.info("editElectionDate");
			return true;
		}
		Logger.error("Given id does not exist to update electionDate");
		throw new NoSuchElectionException("Given id does not exist to update electionDate");
	}

	@Override
	@Transactional
	public boolean removeElection(int electionId) throws NoSuchElectionException {
		if (electionRepository.existsById(electionId)) {
			electionRepository.deleteById(electionId);
			Logger.info("removeElection");
			return true;
		}
		Logger.error("Given id does not exist to remove Election");
		throw new NoSuchElectionException("Given id does not exist to remove Election");
	}

	// Party Functionalities

	@Override
	@Transactional
	public boolean addParty(Party party) throws InvalidFieldException, NoSuchPartyRecordException {
		if (party.getRegId() != null && party.getPartyName() != null && party.getLeader() != null
				&& party.getSymbol() != null) {
			if (partyRepository.readPartyByName(party.getPartyName().toLowerCase()) == null) {
				party = partyRepository.save(party);
				if (!party.getRegId().isEmpty()) {
					Logger.info("addParty");
					return true;
				}
				Logger.error("Not able to add Party");
				throw new NoSuchPartyRecordException("Not able to add Party");
			}
			Logger.error("Party Already Exists");
			throw new AlreadyExistsException("Party Already Exists");
		}
		Logger.error("Field is Empty");
		throw new InvalidFieldException("Field is Empty");
	}

	@Override
	public List<Party> readAllParties() throws NoSuchRecordException {
		List<Party> list = partyRepository.findAll();
		if (!list.isEmpty()) {
			Logger.info("readAllParties");
			return list;
		}
		Logger.error("No Parties present");
		throw new NoSuchRecordException("No Parties present");
	}

	@Override
	public Party readByPartyRegId(String regId) throws NoSuchPartyRecordException {
		if (partyRepository.existsById(regId)) {
			Logger.info("readByPartyRegId");
			return partyRepository.findById(regId).get();
		}
		Logger.error("Party whith this id is not found");
		throw new NoSuchPartyRecordException("Party whith this id is not found");
	}

	@Override
	public Party findByPartyName(String partyName) throws NoSuchPartyRecordException {
		Party party = partyRepository.readPartyByName(partyName.toLowerCase());
		if (party != null) {
			Logger.info("findByPartyName");
			return party;
		}
		Logger.error("Party with party id not found");
		throw new NoSuchPartyRecordException("Party with this name is not found");
	}

	@Override
	@Transactional
	public boolean updateLeaderByRegdId(String regId, String leader) throws NoSuchPartyRecordException {
		if (partyRepository.existsById(regId)) {
			partyRepository.updatePartyLeaderByregId(leader, regId);
			Logger.info("updateLeaderByRegdId");
			return true;
		}
		Logger.error("Party with party name not found to update");
		throw new NoSuchPartyRecordException("Party with this id is not found");
	}

	@Override
	public boolean removePartyById(String regId) throws NoSuchPartyRecordException {
		if (partyRepository.existsById(regId)) {
			partyRepository.deleteById(regId);
			Logger.info("removePartyById");
			return true;
		}
		Logger.error("Party whith id is not found to delete");
		throw new NoSuchPartyRecordException("Party whith this id is not found to delete");
	}
	
	@Override
	public List<Candidates> viewCandidatesByPartyRegId(String partyRegId) throws NoSuchRecordException {
	List<Candidates> list = candidatesRepository.findCandidatesByPartyId(partyRegId);
	if (!list.isEmpty()) {
	Logger.info("viewCandidate");
	return list;
	}
	Logger.error("No Candidates found");
	throw new NoSuchRecordException("No Candidates found");
	}

	// Candidate Functionalities

	@Override
	@Transactional
	public String addCandidate(Candidates candidate)
			throws InvalidFieldException, NoSuchRecordException, AlreadyExistsException {
		String result = null;
		if (candidate.getCandidateName() != null && candidate.getConstituencyId() != 0
				&& candidate.getPartyRegId() != null) {
			if (partyRepository.existsById(candidate.getPartyRegId())
					&& constituencyRepository.existsById(candidate.getConstituencyId())) {
				if (candidatesRepository.findDuplicateCandidates(candidate.getPartyRegId(),
						candidate.getConstituencyId()) == null) {
					candidatesRepository.save(candidate);
					result = "Candidate Created";
					Logger.info("addCandidate");
					return result;
				}
				Logger.error("Duplicate Party Candidate for the Constituency");
				throw new AlreadyExistsException("Duplicate Party Candidate for the Constituency");
			}
			Logger.error("Incorrect Party or constituency");
			throw new NoSuchRecordException("Incorrect Party or constituency");
		}
		Logger.error("Field is Empty");
		throw new InvalidFieldException("Field is Empty");
	}

	@Override
	public List<Candidates> viewCandidate() throws NoSuchRecordException {
		List<Candidates> list = candidatesRepository.findAll();
		if (!list.isEmpty()) {
			Logger.info("viewCandidate");
			return list;
		}
		Logger.error("No Candidates found");
		throw new NoSuchRecordException("No Candidates found");
	}

	@Override
	public Candidates readCandidateId(int candidateId) throws NoSuchCandidateRecordException {
		Candidates result = null;
		if (candidatesRepository.existsById(candidateId)) {
			result = candidatesRepository.findById(candidateId).get();
			Logger.info("readCandidateId");
			return result;
		}
		Logger.error("Candidate with this id not found");
		throw new NoSuchCandidateRecordException("Candidate with this id not found");
	}

	@Override
	public Candidates findCandidateByName(String candidateName) throws NoSuchCandidateRecordException {
		Candidates candidate = candidatesRepository.readCandidateByName(candidateName);
		if (candidate != null) {
			Logger.info("findCandidateByName");
			return candidate;
		}
		Logger.error("No Candidates found");
		throw new NoSuchCandidateRecordException("No Candidates found");
	}

	@Override
	@Transactional
	public boolean updateCandidateNameById(int candidateId, String candidateName)
			throws NoSuchCandidateRecordException {
		if (candidatesRepository.existsById(candidateId)) {
			candidatesRepository.updateCandidateNameById(candidateName, candidateId);
			Logger.info("updateCandidateNameById");
			return true;
		}
		Logger.error("Given id does not exist to update CandidateName");
		throw new NoSuchCandidateRecordException("Given id does not exist to update CandidateName");
	}

	@Override
	public boolean removeCandidateById(int candidateId) throws NoSuchCandidateRecordException {
		if (candidatesRepository.existsById(candidateId)) {
			candidatesRepository.deleteById(candidateId);
			Logger.info("removeCandidateById");
			return true;
		}
		Logger.error("Candidate with this id not found to delete");
		throw new NoSuchCandidateRecordException("Candidate with this id not found to delete");
	}

	// Constituency Functionalities

	@Override
	@Transactional
	public boolean addConstituency(Constituency constituency)
			throws InvalidFieldException, NoSuchElectionException, NoSuchConstituencyException, AlreadyExistsException {
		if (constituency.getConstituencyName() != null && constituency.getElectionId() != 0
				&& constituency.getState() != null) {
			if (electionRepository.existsById(constituency.getElectionId())) {
				if (constituencyRepository
						.readConstituencyByName(constituency.getConstituencyName().toLowerCase()) == null) {
					constituency = constituencyRepository.save(constituency);
					if (constituency.getConstituencyId() != 0) {
						Logger.info("addConstituency");
						return true;
					}
					Logger.error("Not able to add constituency");
					throw new NoSuchConstituencyException("Not able to add constituency");
				}
				Logger.error("Constituency already exists");
				throw new AlreadyExistsException("Constituency already exists");
			}
			Logger.error("Election Id  not found");
			throw new NoSuchElectionException("Election Id  not found");
		}
		Logger.error("Field is Empty");
		throw new InvalidFieldException("Field is Empty");
	}

	@Override
	public List<Constituency> viewConstituency() throws NoSuchRecordException {
		List<Constituency> list = constituencyRepository.findAll();
		if (!list.isEmpty()) {
			Logger.info("viewConstituency");
			return list;
		}
		Logger.error("No Such Constituency exist");
		throw new NoSuchRecordException("No Such Constituency exist");
	}

	@Override
	public Constituency readConstituencyById(int constituencyId) throws NoSuchConstituencyException {
		if (constituencyRepository.existsById(constituencyId)) {
			Logger.info("readConstituencyById");
			return constituencyRepository.findById(constituencyId).get();
		}
		Logger.error("No Such id found");
		throw new NoSuchConstituencyException("No Such id found");
	}

	@Override
	public Constituency findConstituencyByName(String constituencyName) throws NoSuchConstituencyException {
		Constituency constituency = constituencyRepository.readConstituencyByName(constituencyName.toLowerCase());
		if (constituency != null) {
			Logger.info("findConstituencyByName");
			return constituency;
		} else {
			Logger.error("No Such Constituency exist");
			throw new NoSuchConstituencyException("No Such Constituency exist");
		}
	}

	@Override
	@Transactional
	public boolean updateConstituencyNameById(int constituencyId, String constituencyName)
			throws NoSuchConstituencyException {
		if (constituencyRepository.existsById(constituencyId)) {
			constituencyRepository.updateConstituencyNameById(constituencyName, constituencyId);
			Logger.info("updateConstituencyNameById");
			return true;
		}
		Logger.error("No Such id found to update constituencyName");
		throw new NoSuchConstituencyException("No Such id found to update constituencyName");
	}

	@Override
	public boolean removeConstituency(int constituencyId) throws NoSuchConstituencyException {
		if (constituencyRepository.existsById(constituencyId)) {
			constituencyRepository.deleteById(constituencyId);
			Logger.info("removeConstituency");
			return true;
		}
		Logger.error("No Such id exist");
		throw new NoSuchConstituencyException("No Such id exist");
	}

	// Voter Functionalities

	@Override
	public List<Voter> readAllVoter() throws NoSuchRecordException {
		List<Voter> result = voterRepository.findAll();
		if (!result.isEmpty()) {
			Logger.info("readAllVoter");
			return result;
		}
		Logger.error("No voter list is found.");
		throw new NoSuchRecordException("No voter list is found.");
	}

	@Override
	public List<Voter> viewAllRequests() throws NoSuchRecordException {
		List<Voter> result = voterRepository.readAllRequestsByStatus("validated");
		if (!result.isEmpty()) {
			Logger.info("voterRepository");
			return result;
		}
		Logger.error("No voter request list is found.");
		throw new NoSuchRecordException("No voter request list is found.");
	}

	@Override
	public Voter findVoterByEpic(String epic) throws NoSuchVoterException {
		Voter result = new Voter();
		result = voterRepository.findVoterByEpic(epic);
		if (result != null) {
			Logger.info("findVoterByEpic");
			return result;
		}
		Logger.error("voter by EPIC does not exists");
		throw new NoSuchVoterException("voter does not exists");
	}

	@Override
	public List<Voter> findVoterByName(String voterName) throws NoSuchVoterException {
		List<Voter> result = voterRepository.findAllVoterByName(voterName);
		if (!result.isEmpty()) {
			Logger.info("findVoterByName");
			return result;
		}
		Logger.error("voter by name does not exists");
		throw new NoSuchVoterException("voter does not exists");
	}

	@Override
	public boolean updateStatusOfVoterByepic(String epic) throws NoSuchVoterException {
		Voter result = new Voter();
		result = voterRepository.findVoterByEpic(epic);
		if (result != null) {
			voterRepository.handleRequestbyepic("approved", epic);
			Logger.info("updateStatusOfVoterByepic");
			return true;
		}
		Logger.error("voter does not exists for updation");
		throw new NoSuchVoterException("voter does not exists for updation");
	}

	@Override
	@Transactional
	public boolean addPincode(Pincode pincode) throws InvalidFieldException, AlreadyExistsException {
		if (pincode.getPincode() != 0 && pincode.getConstituencyId() != 0 && pincode.getConstituencyName() != null
				&& pincode.getState() != null) {
			if (pincodeRepository.existsById(pincode.getPincode()) == false) {
				pincodeRepository.save(pincode);
				Logger.info("addPincode");
				return true;
			}
			Logger.error("Pincode already exists");
			throw new AlreadyExistsException("Pincode already exists");
		}
		Logger.error("Field is Empty");
		throw new InvalidFieldException("Field is Empty");
	}

}
