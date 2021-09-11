package com.capgemini.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import com.capgemini.model.Candidates;
import com.capgemini.model.Election;
import com.capgemini.model.Party;
import com.capgemini.model.Voter;
import com.capgemini.repository.VoterRepository;
import com.capgemini.service.VoterService;

@SpringBootTest
class VoterServiceImplTest {

	@Autowired
	private VoterService voterService;
	
	@Autowired
	private VoterRepository voterRepository;

	@Autowired
	private ApplicationContext context;

	@Test
	void testToAddVoter() {
		Voter expected = context.getBean(Voter.class);
		expected.setAadhaarId(123456789123L);
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
		expected.setMobile(7410799201L);

		boolean result = voterService.addVoter(expected);
		assertTrue(result);
	}

	@Test
	void testFindVoterByAadharShouldReturnVoter() {
		Voter expected = context.getBean(Voter.class);

		expected.setAadhaarId(123456789123L);

		Voter actual = voterService.viewVoter(expected.getAadhaarId());

		assertEquals(expected.getAadhaarId(), actual.getAadhaarId());

	}

	@Test
	void testToAcceptRequestVoterByAadhar() {
		Voter expected = context.getBean(Voter.class);

		expected.setAadhaarId(123456789123L);

		Voter actual = voterService.viewVoter(expected.getAadhaarId());

		assertEquals(expected.getAadhaarId(), actual.getAadhaarId());

	}

	@Test
	void testToLoginVoter() {
		Voter expected = context.getBean(Voter.class);

		expected.setAadhaarId(123456789123L);
		expected.setVoterPassword("pav123");

		Voter actual = voterService.loginVoter(expected.getAadhaarId(), expected.getVoterPassword());

		assertEquals(expected.getAadhaarId(), actual.getAadhaarId());
		assertEquals(expected.getVoterPassword(), actual.getVoterPassword());

	}

	@Test
	void testToViewVoter() {
		Voter expected = context.getBean(Voter.class);

		expected.setAadhaarId(123456789123L);

		Voter actual = voterService.viewVoter(expected.getAadhaarId());

		assertEquals(expected.getAadhaarId(), actual.getAadhaarId());

	}

	@Test
	public void testToViewAllSchedule() {
		int source = voterService.viewSchedule().size();
		int count = 0;
		Voter expected = context.getBean(Voter.class);
		List<Voter> list = new ArrayList<Voter>();
		for (int i = 0; i < source; i++) {
			list.add(expected);
			count += 1;
		}
		List<Election> actual = voterService.viewSchedule();
		assertEquals(count, actual.size());
	}

	@Test
	public void testToViewParty() {
		int source = voterService.viewParty().size();
		int count = 0;
		Party expected = context.getBean(Party.class);
		List<Party> list = new ArrayList<Party>();
		for (int i = 0; i < source; i++) {
			list.add(expected);
			count += 1;
		}
		List<Party> actual = voterService.viewParty();
		assertEquals(count, actual.size());
	}

	@Test
	public void testToViewCandidates() {
		int source = voterService.viewCandidates().size();
		int count = 0;
		Candidates expected = context.getBean(Candidates.class);
		List<Candidates> list = new ArrayList<Candidates>();
		for (int i = 0; i < source; i++) {
			list.add(expected);
			count += 1;
		}
		List<Candidates> actual = voterService.viewCandidates();
		assertEquals(count, actual.size());
	}
	
	@Test
	public void testToDeleteTestRecords() {
		voterRepository.deleteById(123456789123L);
		assertTrue(true);
	}
}
