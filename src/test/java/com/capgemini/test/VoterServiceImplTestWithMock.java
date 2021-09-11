package com.capgemini.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import com.capgemini.model.Candidates;
import com.capgemini.model.Election;
import com.capgemini.model.Party;
import com.capgemini.model.Voter;
import com.capgemini.repository.CandidatesRepository;
import com.capgemini.repository.ElectionRepository;
import com.capgemini.repository.PartyRepository;
import com.capgemini.repository.VoterRepository;
import com.capgemini.service.VoterService;

@SpringBootTest
class VoterServiceImplTestWithMock {

	@Autowired
	private ApplicationContext context;

	@Autowired
	private VoterService voterService;

	@MockBean
	private VoterRepository voterRepository;

	@MockBean
	private PartyRepository partyRepository;

	@MockBean
	private ElectionRepository electionRepository;

	@MockBean
	private CandidatesRepository candidateRepository;

	@Test
	void testToAddVoter() {
		Voter expected = context.getBean(Voter.class);
		expected.setMobile(9999999901L);
		expected.setVoterFirstName("Pavani");
		expected.setVoterMiddleName("P");
		expected.setVoterLastName("V");
		expected.setVoterEmail("pavani@gmail.com");
		expected.setVoterPassword("pav123");
		expected.setCity("Hyderabad");
		expected.setDistrict("Hyderabad");
		expected.setHouseNo("PAV201");
		expected.setLocality("near tech Park");
		expected.setState("Telagana");
		expected.setStreet("Whitefields");
		expected.setPincode(490020);
		expected.setGender("female");
		expected.setStatus("Approved");
		expected.setDob("12-03-1999");
		when(voterRepository.existsById(expected.getMobile())).thenReturn(true);
		Optional<Voter> expectation = Optional.of(expected);
		when(voterRepository.findById(expected.getMobile())).thenReturn(expectation);
		boolean actual = voterService.addVoter(expected);
		assertTrue(actual);
	}

	@Test
	void testFindVoterByMobileShouldReturnVoter() {
		Voter expected = context.getBean(Voter.class);
		expected.setMobile(9999999999L);
		when(voterRepository.existsById(expected.getMobile())).thenReturn(true);
		Optional<Voter> expectation = Optional.of(expected);
		when(voterRepository.findById(expected.getMobile())).thenReturn(expectation);
		Voter actual = voterService.viewVoter(expected.getMobile());
		assertEquals(expected.getMobile(), actual.getMobile());
	}

	@Test
	void testToAcceptRequestVoterByMobile() {
		Voter expected = context.getBean(Voter.class);
		expected.setMobile(9999999999L);
		when(voterRepository.existsById(expected.getMobile())).thenReturn(true);
		Optional<Voter> expectation = Optional.of(expected);
		when(voterRepository.findById(expected.getMobile())).thenReturn(expectation);
		Voter actual = voterService.viewVoter(expected.getMobile());
		assertEquals(expected.getMobile(), actual.getMobile());
	}

	@Test
	void testToLoginVoter() {
		Voter expected = context.getBean(Voter.class);
		expected.setMobile(9999999901L);
		expected.setVoterPassword("pav123");
		when(voterRepository.existsById(expected.getMobile())).thenReturn(true);
		Optional<Voter> expectation = Optional.of(expected);
		when(voterRepository.findById(expected.getMobile())).thenReturn(expectation);
		Voter actual = voterService.loginVoter(expected.getMobile(), expected.getVoterPassword());
		assertEquals(expected.getMobile(), actual.getMobile());
		assertEquals(expected.getVoterPassword(), actual.getVoterPassword());
	}

	@Test
	void testToViewVoter() {
		Voter expected = context.getBean(Voter.class);
		expected.setMobile(9999999999L);
		when(voterRepository.existsById(expected.getMobile())).thenReturn(true);
		Optional<Voter> expectation = Optional.of(expected);
		when(voterRepository.findById(expected.getMobile())).thenReturn(expectation);
		Voter actual = voterService.viewVoter(expected.getMobile());
		assertEquals(expected.getMobile(), actual.getMobile());
	}
	
	@Test
	public void testToViewSchedule() {
		Election expected = context.getBean(Election.class);
		expected.setElectionDate("20-2-2020");
		expected.setElectionId(2);
		expected.setElectionName("Region");
		expected.setElectionType("state");
		List<Election> list = new ArrayList<Election>();
		list.add(expected);
		when(electionRepository.findAll()).thenReturn(list);
		assertEquals(1, voterService.viewSchedule().size());
	}

	@Test
	public void testToViewParty() {
		Party expected = context.getBean(Party.class);
		expected.setLeader("Amit");
		expected.setPartyName("BJP");
		expected.setRegId("B01");
		expected.setSymbol("Lotus");
		List<Party> list = new ArrayList<Party>();
		list.add(expected);
		when(partyRepository.findAll()).thenReturn(list);
		assertEquals(1, voterService.viewParty().size());
	}

	@Test
	public void testToViewCandidates() {
		Candidates expected = context.getBean(Candidates.class);
		expected.setCandidateId(1);
		expected.setCandidateName("Vishal");
		expected.setConstituencyId(2);
		expected.setPartyRegId("A20");
		List<Candidates> list = new ArrayList<Candidates>();
		list.add(expected);
		when(candidateRepository.findAll()).thenReturn(list);
		assertEquals(1, voterService.viewParty().size());
	}

}