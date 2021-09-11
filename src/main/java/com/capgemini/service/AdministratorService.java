package com.capgemini.service;

import java.util.List;

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

public interface AdministratorService {

	public Administrator loginAdmin(String adminId, String password) throws IncorrectLoginCredentialsException;

	public boolean addAdmin(Administrator admin) throws InvalidFieldException;
	
	// Election 

	public boolean addElection(Election election)
			throws InvalidFieldException, NoSuchElectionException, AlreadyExistsException;

	public List<Election> findAllElection() throws NoSuchRecordException;

	public Election findElectionById(int electionId) throws NoSuchElectionException;

	public List<Election> findElectionByName(String electionName) throws NoSuchElectionException;

	public List<Election> findElectionByType(String electionType) throws NoSuchElectionException;

	public List<Election> findElectionByDate(String from, String to) throws NoSuchElectionException;

	public boolean editElectionName(int electionId, String electionName) throws NoSuchElectionException;

	public boolean editElectionType(int electionId, String electionType) throws NoSuchElectionException;

	public boolean editElectionDate(int electionId, String electionDate) throws NoSuchElectionException;

	public boolean removeElection(int electionId) throws NoSuchElectionException;
	
	// Party

	public boolean addParty(Party party) throws InvalidFieldException, NoSuchPartyRecordException;

	public List<Party> readAllParties() throws NoSuchRecordException;

	public Party readByPartyRegId(String regId) throws NoSuchPartyRecordException;

	public Party findByPartyName(String partyName) throws NoSuchPartyRecordException;

	public boolean updateLeaderByRegdId(String regId, String leader) throws NoSuchPartyRecordException;

	public boolean removePartyById(String regId) throws NoSuchPartyRecordException;
	
	public List<Candidates> viewCandidatesByPartyRegId(String partyRegId) throws NoSuchRecordException;

	// Candidates
	
	public String addCandidate(Candidates candidate)
			throws NoSuchRecordException, InvalidFieldException, AlreadyExistsException;

	public List<Candidates> viewCandidate() throws NoSuchRecordException;

	public Candidates readCandidateId(int candidateId) throws NoSuchCandidateRecordException;

	public Candidates findCandidateByName(String candidateName) throws NoSuchCandidateRecordException;

	public boolean updateCandidateNameById(int candidateId, String candidateName) throws NoSuchCandidateRecordException;

	public boolean removeCandidateById(int candidateId) throws NoSuchCandidateRecordException;

	public boolean addConstituency(Constituency constituency)
			throws InvalidFieldException, NoSuchElectionException, NoSuchConstituencyException;
	
	// Constituency

	public List<Constituency> viewConstituency() throws NoSuchRecordException;

	public Constituency readConstituencyById(int constituencyId) throws NoSuchConstituencyException;

	public Constituency findConstituencyByName(String constituencyName) throws NoSuchConstituencyException;

	public boolean updateConstituencyNameById(int constituencyId, String constituencyName)
			throws NoSuchConstituencyException;

	public boolean removeConstituency(int constituencyId) throws NoSuchConstituencyException;
	
	// Voter

	public List<Voter> readAllVoter() throws NoSuchRecordException;

	public List<Voter> viewAllRequests() throws NoSuchRecordException;

	public Voter findVoterByEpic(String epic) throws NoSuchVoterException;

	public List<Voter> findVoterByName(String voterName) throws NoSuchVoterException;

	public boolean updateStatusOfVoterByepic(String epic) throws NoSuchVoterException;
	
	// Pincode

	public boolean addPincode(Pincode pincode) throws InvalidFieldException;
}
