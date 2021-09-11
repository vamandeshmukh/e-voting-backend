package com.capgemini.test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import com.capgemini.model.Administrator;
import com.capgemini.model.Candidates;
import com.capgemini.model.Constituency;
import com.capgemini.model.Election;
import com.capgemini.model.Party;
import com.capgemini.model.Pincode;
import com.capgemini.repository.AdministratorRepository;
import com.capgemini.repository.CandidatesRepository;
import com.capgemini.repository.ConstituencyRepository;
import com.capgemini.repository.ElectionRepository;
import com.capgemini.repository.PartyRepository;
import com.capgemini.repository.PincodeRepository;
import com.capgemini.service.AdministratorService;

@SpringBootTest
class AdministratorServiceImplWithMockTest {

	@Autowired
	private AdministratorService adminService;

	@Autowired
	private ApplicationContext context;

	@MockBean
	private AdministratorRepository adminRepository;

	@MockBean
	private ElectionRepository electionRepository;

	@MockBean
	private ConstituencyRepository constituencyRepository;

	@MockBean
	private CandidatesRepository candidatesRepository;
	
	@MockBean
	private PartyRepository partyRepository;
	
	@MockBean
	private PincodeRepository pincodeRepository;

	// ADMIN-TESTING
	
	@Test
    void TestToAddAdmin() {
        Administrator expected = context.getBean(Administrator.class);        
        expected.setAdminId("A05");
        expected.setAdminName("Apurva");
        expected.setAdminPassword("apu123");        
        when(adminRepository.existsById(expected.getAdminId())).thenReturn(true);
        Optional<Administrator> expectation = Optional.of(expected);
        when(adminRepository.findById(expected.getAdminId())).thenReturn(expectation);
        boolean actual = adminService.addAdmin(expected);
        assertTrue(actual);
    }

	// ELECTION - TESTING
	
	@Test
    void TestToAddElection() {
        Election expected = context.getBean(Election.class);
        expected.setElectionId(1);
        expected.setElectionName("StateElection");
        expected.setElectionType("StateWise");
        expected.setElectionDate("20-07-2021");
        when(electionRepository.existsById(expected.getElectionId())).thenReturn(true);
        Optional<Election> expectation = Optional.of(expected);
        when(electionRepository.findById(expected.getElectionId())).thenReturn(expectation);
        boolean actual = adminService.addElection(expected);
        assertTrue(actual);
    }	

	@Test
	void testFindElectionByIdShouldReturnElection() {
		Election expected = context.getBean(Election.class);
		expected.setElectionId(1);
		when(electionRepository.existsById(expected.getElectionId())).thenReturn(true); 																						
		Optional<Election> expectation = Optional.of(expected);
		when(electionRepository.findById(expected.getElectionId())).thenReturn(expectation);
		Election actual = adminService.findElectionById(expected.getElectionId());
		assertEquals(expected.getElectionId(), actual.getElectionId());
	}
	
	@Test
	void testfindElectionByName() {
		Election expected = context.getBean(Election.class);
		expected.setElectionName("statelection");
		when(electionRepository.readAllElectionsByName("election_name")).thenReturn(
		Stream.of(new Election(2, "National", "NationWise", "20-07-2021")).collect(Collectors.toList()));
		assertEquals(1, adminService.findElectionByName("election_name").size());
	}
	
	@Test
	void testfindElectionByType() {
		Election expected = context.getBean(Election.class);
		expected.setElectionName("StateWise");
		when(electionRepository.readAllElectionsByName("election_name")).thenReturn(
		Stream.of(new Election(2, "National", "NationWise", "20-07-2021")).collect(Collectors.toList()));
		assertEquals(1, adminService.findElectionByName("election_name").size());
	}
	
	@Test
	void testToUpdateLeaderByRegId() {
		Party expected = context.getBean(Party.class);
		expected.setRegId("C01");
		expected.setLeader("Amit Shah");
		when(partyRepository.existsById(expected.getRegId())).thenReturn(true);
		boolean result = adminService.updateLeaderByRegdId(expected.getRegId(), expected.getLeader());
		assertTrue(result);
	}
	
	@Test
	void testToRemoveElectionById() {
		Election expected = context.getBean(Election.class);
		expected.setElectionId(1);
		when(electionRepository.existsById(expected.getElectionId())).thenReturn(true);
		boolean result = adminService.removeElection(expected.getElectionId());
		assertTrue(result);
	}

	// CONSTITUENCY - TESTING
	
	@Test
    void TestToAddConstituency() {
        Constituency expected = context.getBean(Constituency.class);
        expected.setConstituencyId(1);;
        expected.setConstituencyName("Apurva");
        expected.setElectionId(2);
        expected.setState("Gujrat");
        when(constituencyRepository.existsById(expected.getConstituencyId())).thenReturn(true);
        Optional<Constituency> expectation = Optional.of(expected);
        when(constituencyRepository.findById(expected.getConstituencyId())).thenReturn(expectation);
        boolean actual = adminService.addConstituency(expected);
        assertTrue(actual);
    }

	@Test
	void testFindConstituencyById() {
		Constituency expected = context.getBean(Constituency.class);
		expected.setConstituencyId(2);		when(constituencyRepository.existsById(expected.getConstituencyId())).thenReturn(true);
		Optional<Constituency> expectation = Optional.of(expected);
		when(constituencyRepository.findById(expected.getConstituencyId())).thenReturn(expectation);
		Constituency actual = adminService.readConstituencyById(expected.getConstituencyId());
		assertEquals(expected.getConstituencyId(), actual.getConstituencyId());
	}
	
	@Test
    void testupdateConstituencyNameById() {
        Constituency expected = context.getBean(Constituency.class);
        expected.setConstituencyId(1);
        expected.setConstituencyName("Amit Shah");
        when(constituencyRepository.existsById(expected.getConstituencyId())).thenReturn(true);
        boolean result = adminService.updateConstituencyNameById(expected.getConstituencyId(), expected.getConstituencyName());
        assertTrue(result);
    }
	
	@Test
	public void testToViewAllConstituency()
	{
		Constituency expected = context.getBean(Constituency.class);
		expected.setConstituencyName("Palghar");
		expected.setConstituencyId(2);
		expected.setState("MH");
		expected.setElectionId(2);		
		List<Constituency> list = new ArrayList<Constituency>();
		list.add(expected);
		when(constituencyRepository.findAll()).thenReturn(list);
		assertEquals(1 ,adminService.viewConstituency().size());
	}	

	// Candidate - Testing

	@Test
	void testFindCandidateByIdShouldReturnCandidate() {
		Candidates expected = context.getBean(Candidates.class);
		expected.setCandidateId(1);
		when(candidatesRepository.existsById(expected.getCandidateId())).thenReturn(true);
		Optional<Candidates> expectation = Optional.of(expected);
		when(candidatesRepository.findById(expected.getCandidateId())).thenReturn(expectation);
		Candidates actual = adminService.readCandidateId(expected.getCandidateId());
		assertEquals(expected.getCandidateId(), actual.getCandidateId());
	}
	
	 @Test
	    void testToRemoveCandidateById() {
	        Candidates expected = context.getBean(Candidates.class);
	        expected.setCandidateId(1);
	        when(candidatesRepository.existsById(expected.getCandidateId())).thenReturn(true);
	        boolean result = adminService.removeCandidateById(expected.getCandidateId());
	        assertTrue(result);
	    }
	 
	 @Test
		public void testToViewAllCandidate()
		{
		 Candidates expected = context.getBean(Candidates.class);
			expected.setCandidateId(1);
			expected.setCandidateName("Rohit");
			expected.setConstituencyId(2);
			expected.setPartyRegId("A20");
			List<Candidates> list = new ArrayList<Candidates>();
			list.add(expected);
			when(candidatesRepository.findAll()).thenReturn(list);
			assertEquals(1 ,adminService.viewCandidate().size());
		}   
	
	// PARTY-TESTING
	
	@Test
	void testToAddParty() {
		Party expected = context.getBean(Party.class);
		expected.setRegId("vv");
		expected.setPartyName("Bjp");
		expected.setLeader("true");
		expected.setSymbol("666");
		when(partyRepository.existsById(expected.getRegId())).thenReturn(true);
		boolean actual = adminService.addParty(expected);
		assertTrue(actual);
	}
	
	@Test
	void testToFindReadByPartyId() {
		Party expected = context.getBean(Party.class);
		expected.setRegId("vv");
		when(partyRepository.existsById(expected.getRegId())).thenReturn(true);
		Optional<Party> expectation = Optional.of(expected);
		when(partyRepository.findById(expected.getRegId())).thenReturn(expectation);
		Party actual = adminService.readByPartyRegId(expected.getRegId());
		assertEquals(expected.getRegId(), actual.getRegId());
	}
	
	@Test
	void testToEditElectionNameById() {
		Election expected = context.getBean(Election.class);
		expected.setElectionId(1);
		expected.setElectionName("StateElection");
		when(electionRepository.existsById(expected.getElectionId())).thenReturn(true);
		boolean result = adminService.editElectionName(expected.getElectionId(), expected.getElectionName());
		assertTrue(result);
	}
	
	@Test
	void testToEditElectionTypeById() {
		Election expected = context.getBean(Election.class);
		expected.setElectionId(1);
		expected.setElectionType("StateWise");
		when(electionRepository.existsById(expected.getElectionId())).thenReturn(true);
		boolean result = adminService.editElectionName(expected.getElectionId(), expected.getElectionType());
		assertTrue(result);
	}	
	
	@Test
	public void testToViewAllParty()
	{
		Party expected = context.getBean(Party.class);
		expected.setPartyName("Congress");
		expected.setLeader("Rahul");
		expected.setRegId("C01");
		expected.setSymbol("Palm");
		List<Party> list = new ArrayList<Party>();
		list.add(expected);
		when(partyRepository.findAll()).thenReturn(list);
		assertEquals(1 ,adminService.readAllParties().size());
	}
	
	@Test
	void testToRemovePartyById() {
		Party expected = context.getBean(Party.class);
		expected.setRegId("C02");
		when(partyRepository.existsById(expected.getRegId())).thenReturn(true);
		boolean result = adminService.removePartyById(expected.getRegId());
		assertTrue(result);
	}	
	
	// PINCODE-MOCK-TESTING
	
	@Test
    void TestToAddPincode() {
        Pincode expected = context.getBean(Pincode.class);        
        expected.setPincode(491441);      
        when(pincodeRepository.existsById(expected.getPincode())).thenReturn(true);
        Optional<Pincode> expectation = Optional.of(expected);
        when(pincodeRepository.findById(expected.getPincode())).thenReturn(expectation);
        boolean actual = adminService.addPincode(expected);
        assertTrue(actual);
    }	

}
