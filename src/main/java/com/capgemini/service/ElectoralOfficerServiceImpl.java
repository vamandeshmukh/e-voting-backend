package com.capgemini.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.exception.IncorrectLoginCredentialsException;
import com.capgemini.exception.InvalidAadhaarException;
import com.capgemini.exception.InvalidFieldException;
import com.capgemini.exception.NoSuchRecordException;
import com.capgemini.exception.NoSuchVoterException;
import com.capgemini.model.ElectoralOfficer;
import com.capgemini.model.Voter;
import com.capgemini.repository.ElectoralOfficerRepository;
import com.capgemini.repository.PincodeRepository;
import com.capgemini.repository.VoterRepository;

import logging.GlobalResources;

@Service("eoService")
public class ElectoralOfficerServiceImpl implements ElectoralOfficerService {
	
	private Logger Logger = GlobalResources.getLogger(AdministratorServiceImpl.class);
	
	@Autowired
	private ElectoralOfficerRepository eoRepository;

	@Autowired
	private VoterRepository voterRepository;

	@Autowired
	private PincodeRepository pincodeRepository;

	@Override
	@Transactional
	public boolean addEO(ElectoralOfficer eo) throws InvalidFieldException, NoSuchRecordException {
		if (eo.getElectoralOfficerId() != null && eo.getElectroalOfficerName() != null
				&& eo.getElectoralOfficerPassword() != null) {
			boolean result = false;
			String name = eo.getElectroalOfficerName();
			String regex1 = "^[A-Za-z ]+";
			if (name.matches(regex1)) {
				eo = eoRepository.save(eo);
				result = true;
				Logger.info("EO is added successfully");
				return result;
			}
			Logger.error("Unable to add EO");
			throw new NoSuchRecordException("Unable to add EO");
		}
		Logger.error("Field is Empty");
		throw new InvalidFieldException("Field is Empty");
	}

	@Override
	public ElectoralOfficer loginEO(String eoId, String password) throws IncorrectLoginCredentialsException {
		ElectoralOfficer eo = null;
		if (eoRepository.existsById(eoId)
				&& eoRepository.findById(eoId).get().getElectoralOfficerPassword().equals(password)) {
			eo = eoRepository.findById(eoId).get();
			Logger.info("loginEO");
			return eo;
		}
		Logger.error("Invalid Credentials");
		throw new IncorrectLoginCredentialsException("Invalid Credentials");
	}

	@Override
	public List<Voter> viewAllRequests() throws NoSuchRecordException {
		List<Voter> result = voterRepository.readAllRequestsByStatus("requesting");
		if (!result.isEmpty()) {			
			Logger.info("viewAllRequests");
			return result;
		}
		Logger.error("No voter request list is found.");
		throw new NoSuchRecordException("No voter request list is found.");
	}

	@Transactional
	@Override
	public boolean acceptVoterRequestById(long aadhaar) throws NoSuchVoterException, InvalidAadhaarException {
		String regex = "^[0-9]{12}";
		String aadhaarStr = String.valueOf(aadhaar);
		if ((aadhaarStr.matches(regex))) {
			if (voterRepository.existsById(aadhaar)) {
				Voter voter = voterRepository.findById(aadhaar).get();
				String epic = pincodeRepository.findById(voter.getPincode()).get().getConstituencyName().substring(0, 2)
						.toUpperCase() + voter.getVoterFirstName().substring(0, 2).toUpperCase()
						+ voter.getDob().substring(0, 2) + voter.getDob().substring(3, 5) + voter.getDob().substring(6)
						+ voter.getVoterLastName().substring(0, 2).toUpperCase();
				int constituencyid = pincodeRepository.findById(voter.getPincode()).get().getConstituencyId();
				voterRepository.handleRequestbyId("validated", aadhaar);
				voterRepository.generateVoterIdbyId(epic, aadhaar);
				voterRepository.generateConstituencybyId(constituencyid, aadhaar);
				Logger.info("acceptVoterRequestById");
				return true;
			}
			Logger.error("Voter not found");
			throw new NoSuchVoterException("Voter not found");
		}
		Logger.error("Please enter valid aadhaar number");
		throw new InvalidAadhaarException("Please enter valid aadhaar number");
	}

	@Override
	public boolean rejectVoterRequestById(long aadhaar) throws NoSuchVoterException, InvalidAadhaarException {
		String regex = "^[0-9]{12}";
		String aadhaarStr = String.valueOf(aadhaar);
		if ((aadhaarStr.matches(regex))) {
			if (voterRepository.existsById(aadhaar)) {
				voterRepository.handleRequestbyId("rejected", aadhaar);
				Logger.info("rejectVoterRequestById");
				return true;
			}
			Logger.error("Voter not found");
			throw new NoSuchVoterException("Voter not found");
		}
		Logger.error("Please enter valid aadhaar number");
		throw new InvalidAadhaarException("Please enter valid aadhaar number");
	}

	@Override
	public boolean deactivateVoterById(long aadhaar) throws NoSuchVoterException, InvalidAadhaarException {
		String regex = "^[0-9]{12}";
		String aadhaarStr = String.valueOf(aadhaar);

		if (voterRepository.existsById(aadhaar)) {
			if ((aadhaarStr.matches(regex))) {
				voterRepository.handleRequestbyId("deactivated", aadhaar);
				Logger.info("deactivateVoterById");
				return true;
			}
			Logger.error("Voter not found");
			throw new NoSuchVoterException("Voter is not found");
		}
		Logger.error("Please enter valid aadhaar number");
		throw new InvalidAadhaarException("Please enter valid aadhaar number");
	}

	@Override
	public Voter viewRequestById(long aadhaar) throws NoSuchVoterException, InvalidAadhaarException {
		String regex = "^[0-9]{12}";
		String aadhaarStr = String.valueOf(aadhaar);
		if ((aadhaarStr.matches(regex))) {
			if (voterRepository.existsById(aadhaar)) {
				Voter voter = voterRepository.findById(aadhaar).get();
				Logger.info("viewRequestById");
				return voter;
			}
			Logger.error("Voter with Id not found");
			throw new NoSuchVoterException("Voter with Id is not found");
		}
		Logger.error("Voter not found");
		throw new InvalidAadhaarException("Please enter valid aadhaar number");
	}

	@Override
	public List<Voter> viewAllVoters() throws NoSuchRecordException {
		List<Voter> result = voterRepository.findAll();
		if (!result.isEmpty()) {
			Logger.info("viewAllVoters");
			return result;
		}
		Logger.error("No voter list is found");
		throw new NoSuchRecordException("No voter list is found.");
	}

}
