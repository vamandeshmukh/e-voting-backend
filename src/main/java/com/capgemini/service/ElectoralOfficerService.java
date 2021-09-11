package com.capgemini.service;

import java.util.List;

import com.capgemini.exception.IncorrectLoginCredentialsException;
import com.capgemini.exception.InvalidAadhaarException;
import com.capgemini.exception.InvalidFieldException;
import com.capgemini.exception.NoSuchRecordException;
import com.capgemini.exception.NoSuchVoterException;
import com.capgemini.model.ElectoralOfficer;
import com.capgemini.model.Voter;

public interface ElectoralOfficerService {

	public boolean addEO(ElectoralOfficer eo) throws InvalidFieldException, NoSuchRecordException;

	public ElectoralOfficer loginEO(String adminId, String password) throws IncorrectLoginCredentialsException;

	public List<Voter> viewAllRequests() throws NoSuchRecordException;

	public Voter viewRequestById(long aadhaar) throws NoSuchRecordException;

	public boolean acceptVoterRequestById(long aadhaar) throws NoSuchVoterException, InvalidAadhaarException;

	public boolean rejectVoterRequestById(long aadhaar) throws NoSuchVoterException, InvalidAadhaarException;

	public boolean deactivateVoterById(long aadhaar) throws NoSuchVoterException, InvalidAadhaarException;

	public List<Voter> viewAllVoters() throws NoSuchRecordException;
}
