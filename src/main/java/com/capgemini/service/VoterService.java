package com.capgemini.service;

import java.util.List;

import org.springframework.mail.javamail.JavaMailSender;

import com.capgemini.exception.AlreadyVotedException;
import com.capgemini.exception.IncorrectLoginCredentialsException;
import com.capgemini.exception.InvalidAadhaarException;
import com.capgemini.exception.InvalidEmailException;
import com.capgemini.exception.InvalidGenderException;
import com.capgemini.exception.InvalidMobileNumberException;
import com.capgemini.exception.InvalidNameException;
import com.capgemini.exception.InvalidPincodeException;
import com.capgemini.exception.NoSuchCandidateRecordException;
import com.capgemini.exception.NoSuchConstituencyException;
import com.capgemini.exception.NoSuchRecordException;
import com.capgemini.exception.NoSuchVoterException;
import com.capgemini.exception.NotScheduledException;
import com.capgemini.exception.UnauthorisedVoterException;
import com.capgemini.exception.UnderAgeException;
import com.capgemini.model.Candidates;
import com.capgemini.model.Election;
import com.capgemini.model.Party;
import com.capgemini.model.Pincode;
import com.capgemini.model.Voter;

public interface VoterService {

	public boolean addVoter(Voter voter) throws InvalidEmailException, UnderAgeException, InvalidNameException,
			InvalidPincodeException, InvalidMobileNumberException, InvalidAadhaarException, InvalidGenderException;

	public Voter loginVoter(long aadhaar, String password)
			throws IncorrectLoginCredentialsException, NoSuchVoterException, InvalidAadhaarException;

	public Voter viewVoter(long aadhaar) throws NoSuchVoterException, InvalidAadhaarException;

	public List<Election> viewSchedule() throws NoSuchRecordException;

	public List<Party> viewParty() throws NoSuchRecordException;

	public List<Candidates> viewCandidates() throws NoSuchRecordException;

	public List<Candidates> viewCandidatesByConstituency(long aadhaar)
			throws NoSuchRecordException, InvalidAadhaarException;

	public String voteCasting(String epic, int candidateId)
			throws NotScheduledException, NoSuchCandidateRecordException, AlreadyVotedException,
			UnauthorisedVoterException, NoSuchVoterException, NotScheduledException;

	public String viewVoteForAllCandidate() throws NoSuchRecordException;

	public String viewVoteForAllParty() throws NoSuchRecordException;

	public String viewVoteForConstituency(int constituency) throws NoSuchRecordException, NoSuchConstituencyException;

	public List<Pincode> findPincodeByState(String State) throws NoSuchRecordException;
	
	public void MailService(JavaMailSender javaMailSender);

	public void sendEmail(String to, String subject, String body);
}
