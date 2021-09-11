package com.capgemini.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.exception.AlreadyExistsException;
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
import com.capgemini.model.Vote;
import com.capgemini.model.Voter;
import com.capgemini.repository.CandidatesRepository;
import com.capgemini.repository.ConstituencyRepository;
import com.capgemini.repository.ElectionRepository;
import com.capgemini.repository.PartyRepository;
import com.capgemini.repository.PincodeRepository;
import com.capgemini.repository.VoteRepository;
import com.capgemini.repository.VoterRepository;

import logging.GlobalResources;

@Service("voterService")
public class VoterServiceImpl implements VoterService {

	private Logger Logger = GlobalResources.getLogger(AdministratorServiceImpl.class);

	@Autowired
	private VoterRepository voterRepository;

	@Autowired
	private VoteRepository voteRepository;

	@Autowired
	private PartyRepository partyRepository;

	@Autowired
	private ElectionRepository electionRepository;

	@Autowired
	private CandidatesRepository candidatesRepository;

	@Autowired
	private ConstituencyRepository constituencyRepository;

	@Autowired
	private PincodeRepository pincodeRepository;

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	@Transactional
	public boolean addVoter(Voter voter)
			throws InvalidEmailException, UnderAgeException, InvalidNameException, InvalidPincodeException,
			InvalidMobileNumberException, InvalidAadhaarException, AlreadyExistsException, InvalidGenderException {
		boolean result = false;
		String regexName = "^[A-Za-z]+";
		String regexMobile = "^[0-9]{10}";
		String regexMail = "^[a-zA-Z.0-9_]+@[a-zA-Z]+.[a-zA-Z]+$";

		long aadhaarno = voter.getAadhaarId();
		long mobileNo = voter.getMobile();
		String fName = voter.getVoterFirstName();
		String mName = voter.getVoterMiddleName();
		String lName = voter.getVoterLastName();
		String email = voter.getVoterEmail();
		String dob = voter.getDob();
		voter.setEpic("N/A");
		voter.setStatus("requesting");
		voter.setConstituencyId(0);
		voter.setGender(voter.getGender().toLowerCase());

		int strLength = dob.length();
		String year = dob.substring(strLength - 4, strLength);
		int birthYear = Integer.parseInt(year);
		int electionYear = LocalDate.now().getYear();
		String mobile = String.valueOf(mobileNo);
		String aadhaar = String.valueOf(aadhaarno);

		if (aadhaar.matches("^[0-9]{12}")) {
			if (voterRepository.existsById(voter.getAadhaarId()) == false) {
				if (mobile.matches(regexMobile) && voterRepository.findVoterByMobile(mobileNo) == null) {
					if (pincodeRepository.existsById(voter.getPincode())) {
						if ((fName.matches(regexName)) && (mName.matches(regexName) || mName.isEmpty())
								&& (lName.matches(regexName))) {
							if ((voter.getDob().length() == 10) && (electionYear - birthYear) > 18) {
								if (voter.getGender().equals("male") || voter.getGender().equals("female")
										|| voter.getGender().equals("others")) {
									if (email.matches(regexMail) && voterRepository.findVoterByEmail(email) == null) {
										voter = voterRepository.save(voter);
										result = true;
										Logger.info("Voter is added successfully");
										return result;
									}
									Logger.error("Invalid Email");
									throw new InvalidEmailException("Invalid Email");
								}
								Logger.error("Invalid Gender");
								throw new InvalidGenderException("Invalid Gender");
							}
							Logger.error("Under-age user");
							throw new UnderAgeException("Age is insufficient to vote.");
						}
						Logger.error("User name is invalid");
						throw new InvalidNameException("User name is invalid");
					}
					Logger.error("Invalid Pincode");
					throw new InvalidPincodeException("Invalid Pincode");
				}
				Logger.error("Enter valid mobile number");
				throw new InvalidMobileNumberException("Enter valid mobile number");
			}
			Logger.error("Aadhaar already exists");
			throw new AlreadyExistsException("Aadhaar already exists");
		}
		Logger.error("Invalid aadhaar number");
		throw new InvalidAadhaarException("Invalid aadhaar number");
	}

	@Override
	public Voter loginVoter(long aadhaar, String password)
			throws IncorrectLoginCredentialsException, NoSuchVoterException, InvalidAadhaarException {
		String aadhaarStr = String.valueOf(aadhaar);
		String regex = "^[0-9]{12}";
		if (aadhaarStr.matches(regex)) {
			if (voterRepository.existsById(aadhaar)) {
				if (voterRepository.findById(aadhaar).get().getVoterPassword().equals(password)) {
					Logger.info("loginVoter");
					return voterRepository.findById(aadhaar).get();
				}
				Logger.error("Invalid password");
				throw new IncorrectLoginCredentialsException("Invalid password");
			}
			Logger.error("User with aadhaar number not found");
			throw new NoSuchVoterException("User with aadhaar number not found.");
		}
		Logger.error("Invalid aadhaar number used for login");
		throw new InvalidAadhaarException("Please enter a valid aadhaar number");
	}

	@Override
	public Voter viewVoter(long aadhaar) throws NoSuchVoterException, InvalidAadhaarException {
		String aadhaarStr = String.valueOf(aadhaar);
		String regex = "^[0-9]{12}";
		if (aadhaarStr.matches(regex)) {
			if (voterRepository.existsById(aadhaar)) {
				Logger.info("viewVoter");
				return voterRepository.findById(aadhaar).get();
			}
			Logger.error("Unregistered aadhaar number");
			throw new NoSuchVoterException("Voter with aadhaar number" + aadhaar + "is not registered.");
		}
		Logger.error("Invalid aadhaar number");
		throw new InvalidAadhaarException("Please enter a valid aadhaar number");
	}

	@Override
	public List<Election> viewSchedule() throws NoSuchRecordException {
		List<Election> result = null;
		result = electionRepository.findAll();
		if (!result.isEmpty()) {
			Logger.info("viewSchedule");
			return result;
		}
		Logger.error("No election schedule is found");
		throw new NoSuchRecordException("No election schedule is found.");
	}

	@Override
	public List<Party> viewParty() throws NoSuchRecordException {
		List<Party> result = null;
		result = partyRepository.findAll();
		if (!result.isEmpty()) {
			Logger.info("viewParty");
			return result;
		}
		Logger.error("No Party is found.");
		throw new NoSuchRecordException("No Party is found.");
	}

	@Override
	public List<Candidates> viewCandidates() throws NoSuchRecordException {
		List<Candidates> result = null;
		result = candidatesRepository.findAll();
		if (!result.isEmpty()) {
			Logger.info("viewCandidates");
			return result;
		}
		Logger.error("No Candidates are found.");
		throw new NoSuchRecordException("No Candidates are found.");
	}

	@Override
	public String voteCasting(String epic, int candidateId)
			throws NotScheduledException, NoSuchCandidateRecordException, AlreadyVotedException,
			UnauthorisedVoterException, NoSuchVoterException, NotScheduledException {
		String result = null;
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String strDate = formatter.format(date);

		if (voterRepository.findVoterByEpic(epic) != null) {
			if (electionRepository.findById(constituencyRepository
					.findById(voterRepository.findVoterByEpic(epic).getConstituencyId()).get().getElectionId()).get()
					.getElectionDate().equals(strDate)) {
				if (voterRepository.findVoterByEpic(epic).getStatus().toLowerCase().equals("approved")) {
					if (voteRepository.existEpicInVote(epic) == null) {
						if (candidatesRepository.findById(candidateId) != null) {
							if (voterRepository.findVoterByEpic(epic).getConstituencyId() == candidatesRepository
									.findById(candidateId).get().getConstituencyId()) {

								Vote vote = new Vote();
								vote.setEpic(epic);
								vote.setCandidate(candidateId);
								voteRepository.save(vote);
								result = "Thanks for Voting "
										+ candidatesRepository.findById(candidateId).get().getCandidateName();

								sendEmail(voterRepository.findVoterByEpic(epic).getVoterEmail(), "e-Voting Commission",
										"Dear " + voterRepository.findVoterByEpic(epic).getVoterFirstName() + " "
												+ voterRepository.findVoterByEpic(epic).getVoterLastName() + "," + "\n"
												+ "Thank you for casting your valuable vote." + "\n\n" + "Regards,"
												+ "\n" + "E-Voting Commission");
								Logger.info("castVote");
								return result;
							}
							Logger.error("Invalid ConstituencyId");
							throw new NoSuchConstituencyException("Invalid ConstituencyId");
						}
						Logger.error("Invalid Candidate");
						throw new NoSuchCandidateRecordException("Enter valid candidate");
					}
					Logger.error("Already Voted");
					throw new AlreadyVotedException("Already Voted");
				}
				Logger.error("Unauthorised voter");
				throw new UnauthorisedVoterException("Unauthorised voter");
			}
			Logger.error("No Election Scheduled for Today");
			throw new NotScheduledException("No Election Scheduled for Today");
		}
		Logger.error("No Voter found with given Epic");
		throw new NoSuchVoterException("No Voter found with this Epic");
	}

	@Override
	public List<Candidates> viewCandidatesByConstituency(long aadhaar)
			throws NoSuchRecordException, InvalidAadhaarException {
		if (voterRepository.existsById(aadhaar)) {
			List<Candidates> result = null;
			int voterConstituency = voterRepository.findById(aadhaar).get().getConstituencyId();
			result = candidatesRepository.findCandidatesByConstituency(voterConstituency);
			if (!result.isEmpty()) {
				Logger.info("viewCandidatesByConstituency");
				return result;
			}
			Logger.error("No candidates present in voter's constituency");
			throw new NoSuchRecordException("No record present");
		}
		Logger.error("Invalid aadhaar or Voter not registered");
		throw new InvalidAadhaarException("Invalid aadhaar or Voter not registered");
	}

	// View Results

	@Override
	public String viewVoteForAllCandidate() throws NoSuchRecordException {
		String result = "";
		List<Object[]> list = voteRepository.cadidateVoteCount();
		if (!list.isEmpty()) {
			for (Object[] obj : list) {
				int candidateId = Integer.parseInt(obj[0].toString());
				String candidateName = candidatesRepository.findById(candidateId).get().getCandidateName()+ ", " + partyRepository
						.findById(candidatesRepository.findById(candidateId).get().getPartyRegId()).get()
						.getPartyName();
				String votes = obj[1].toString();
				result = result + "\n" + candidateName + " has received " + votes + " vote(s)"+ ".";
			}
			Logger.info("viewVoteForAllCandidate");
			return result;
		}
		Logger.error("No results present");
		throw new NoSuchRecordException("No record present");
	}

	@Override
	public String viewVoteForAllParty() throws NoSuchRecordException {
		String result = "";
		List<Object[]> list = voteRepository.partyVoteCount();
		if (!list.isEmpty()) {
			for (Object[] obj : list) {
				String partyId = obj[0].toString();
				String partyName = partyRepository.findById(partyId).get().getPartyName();
				String votes = obj[1].toString();
				result = result + "\n" + partyName + " has received " + votes + " vote(s)"+ ".";
			}
			Logger.info("viewVoteForAllParty");
			return result;
		}
		Logger.error("INo results present");
		throw new NoSuchRecordException("No record present");
	}

	@Override
	public String viewVoteForConstituency(int constituency) throws NoSuchRecordException, NoSuchConstituencyException {
		if (constituencyRepository.existsById(constituency)) {
			String result = "";
			List<Object[]> list = voteRepository.partyVoteConstituency(constituency);
			if (!list.isEmpty()) {
				for (Object[] obj : list) {
					int candidateId = Integer.parseInt(obj[0].toString());
					String candidateName = candidatesRepository.findById(candidateId).get().getCandidateName();
					String votes = obj[1].toString();
					String party = partyRepository
							.findById(candidatesRepository.findById(candidateId).get().getPartyRegId()).get()
							.getPartyName();
					result = result + "\n" + candidateName + " has received " + votes + " vote(s) from " + party+ ".";
				}
				Logger.info("viewVoteForConstituency");
				return result;
			}
			Logger.error("No results present");
			throw new NoSuchRecordException("No record present");
		}
		Logger.error("Invalid ConstituencyId");
		throw new NoSuchConstituencyException("Invalid Constituency Id");
	}
	
	@Override
	public List<Pincode> findPincodeByState(String State) throws NoSuchRecordException {
		List<Pincode> pincodeList =pincodeRepository.readAllPincodeByState(State);
		if (!pincodeList .isEmpty()) {
			Logger.info("findByPartyName");
			return pincodeList;
		}
		Logger.error("No Pincodes in this state.");
		throw new NoSuchRecordException("No pincode available for this state.");
		
	}
	
	// Mail 

	@Override
	@Autowired
	public void MailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	@Override
	public void sendEmail(String to, String subject, String body) throws MailException {
		SimpleMailMessage simpleMail = new SimpleMailMessage();
		simpleMail.setTo(to);
		simpleMail.setSubject(subject);
		simpleMail.setText(body);
		javaMailSender.send(simpleMail);
	}

}
