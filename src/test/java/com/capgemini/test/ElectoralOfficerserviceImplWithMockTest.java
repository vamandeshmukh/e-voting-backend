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

import com.capgemini.model.ElectoralOfficer;
import com.capgemini.model.Voter;
import com.capgemini.repository.ElectoralOfficerRepository;
import com.capgemini.repository.VoterRepository;
import com.capgemini.service.ElectoralOfficerService;

@SpringBootTest
class ElectoralOfficerserviceImplWithMockTest {

	@Autowired
	private ElectoralOfficerService eoService;
	
	@Autowired
	private ApplicationContext context;
	
	@MockBean
	private ElectoralOfficerRepository eoRepository;
	
	@MockBean
	private VoterRepository voterRepository;

	@Test
	void testToAddEO() {
		ElectoralOfficer expected = context.getBean(ElectoralOfficer.class);
		expected.setElectoralOfficerId("EO-01");
		expected.setElectroalOfficerName("Sai");
		expected.setElectoralOfficerPassword("sai123");
		when(eoRepository.existsById(expected.getElectoralOfficerId())).thenReturn(true);
		Optional<ElectoralOfficer> expectation = Optional.of(expected);
		when(eoRepository.findById(expected.getElectoralOfficerId())).thenReturn(expectation);
		boolean result = eoService.addEO(expected);
		assertTrue(result);
	}
	
	@Test
	void testToViewtVoterRequestById() {
		Voter expected = context.getBean(Voter.class);
		expected.setMobile(9999999901L);
		Optional<Voter> expectation = Optional.of(expected);
		when(voterRepository.findById(expected.getMobile())).thenReturn(expectation);
		Voter actual = eoService.viewRequestById(expected.getMobile());
		assertEquals(expected.getMobile(), actual.getMobile());
	}
	
	@Test
	void testToRejectVoterRequestById() {
		Voter expected = context.getBean(Voter.class);
		expected.setMobile(9999999901L);
		when(voterRepository.existsById(expected.getMobile())).thenReturn(true);
		Optional<Voter> expectation = Optional.of(expected);
		when(voterRepository.findById(expected.getMobile())).thenReturn(expectation);
		boolean result = eoService.rejectVoterRequestById(expected.getMobile());
		assertTrue(result);
	}
	
	@Test
	public void testToViewAllVoter()
	{
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
		List<Voter> list = new ArrayList<Voter>();
		list.add(expected);
		when(voterRepository.findAll()).thenReturn(list);
		assertEquals(1 ,eoService.viewAllVoters().size());
	}
	
	@Test
	public void testToViewAllRequest()
	{
		Voter expected = context.getBean(Voter.class);
		expected.setAadhaarId(0);
		expected.setCity(null);
		expected.setConstituencyId(0);
		expected.setDistrict(null);
		expected.setDob(null);
		expected.setEpic(null);
		expected.setGender(null);
		expected.setHouseNo(null);
		expected.setLocality(null);
		expected.setMobile(0);
		expected.setState(null);
		expected.setStatus(null);
		expected.setStreet(null);		
		List<Voter> list = new ArrayList<Voter>();
		list.add(expected);
		when(voterRepository.findAll()).thenReturn(list);
		assertEquals(1 ,eoService.viewAllRequests().size());
	}
}
