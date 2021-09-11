package com.capgemini.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import com.capgemini.model.Administrator;
import com.capgemini.model.Candidates;
import com.capgemini.model.Constituency;
import com.capgemini.model.Election;
import com.capgemini.model.Party;
import com.capgemini.model.Pincode;
import com.capgemini.service.AdministratorService;

@SpringBootTest
class AdministratorServiceImplTest {

	@Autowired
	private AdministratorService adminService;

	@Autowired
	private ApplicationContext context;

	// ADMIN-TESTING

	@Test
	void testToAddAdmin() {
		Administrator expected = context.getBean(Administrator.class);
		expected.setAdminId("A01");
		expected.setAdminName("Vanshika");
		expected.setAdminPassword("van123");
		boolean result = adminService.addAdmin(expected);
		assertTrue(result);
	}

	@Test
	void testToLoginAdmin() {
		Administrator expected = context.getBean(Administrator.class);
		expected.setAdminId("A01");
		expected.setAdminPassword("van123");
		Administrator actual = adminService.loginAdmin(expected.getAdminId(), expected.getAdminPassword());
		assertEquals(expected.getAdminId(), actual.getAdminId());
		assertEquals(expected.getAdminPassword(), actual.getAdminPassword());
	}

	// ELECTION TESTING

	@Test
	void testToAddElection() {
		Election expected = context.getBean(Election.class);
		expected.setElectionName("Election");
		expected.setElectionType("Type-01");
		expected.setElectionDate("21-07-2021");
		boolean result = adminService.addElection(expected);
		assertTrue(result);

	}

	@Test
	void testFindElectionByIdShouldReturnElection() {
		Election expected = context.getBean(Election.class);
		expected.setElectionId(7);
		Election actual = adminService.findElectionById(expected.getElectionId());
		assertEquals(expected.getElectionId(), actual.getElectionId());

	}

	@Test
	void testFindElectionByDateShouldReturnElection() {
		List<Election> actual = adminService.findElectionByDate("20-02-2020", "30-08-2021");
		assertTrue(actual.size() > 0);
	}

	@Test
	void testEditElectionName() {
		Election expected = context.getBean(Election.class);
		expected.setElectionId(7);
		expected.setElectionName("National");
		boolean result = adminService.editElectionName(expected.getElectionId(), expected.getElectionName());
		assertTrue(result);
	}

	@Test
	void testEditElectionType() {
		Election expected = context.getBean(Election.class);
		expected.setElectionId(7);
		expected.setElectionType("Regionals");
		boolean result = adminService.editElectionType(expected.getElectionId(), expected.getElectionType());
		assertTrue(result);
	}

	@Test
	void testEditElectionDate() {
		Election expected = context.getBean(Election.class);
		expected.setElectionId(7);
		expected.setElectionDate("20-07-2021");
		boolean result = adminService.editElectionDate(expected.getElectionId(), expected.getElectionDate());
		assertTrue(result);
	}

	@Test
	void testFindElectionByNameShouldReturnElection() {
		List<Election> actual = adminService.findElectionByName("National");
		assertTrue(actual.size() > 0);
	}

	@Test
	void testToRemoveElection() {
		Election expected = context.getBean(Election.class);
		expected.setElectionName("Election");
		expected.setElectionType("Type-01");
		expected.setElectionDate("21-07-2021");
		adminService.addElection(expected);
		boolean result = adminService.removeElection(expected.getElectionId());
		assertTrue(result);
	}

	// PARTY TESTING

	@Test
	void testToAddParty() {
		Party expected = context.getBean(Party.class);
		expected.setRegId("BJP01");
		expected.setPartyName("BJP");
		expected.setLeader("Amit Shah");
		expected.setSymbol("Lotus");
		boolean result = adminService.addParty(expected);
		assertTrue(result);
	}

	@Test
	void testFindPartyByRegIdShouldReturnParty() {
		Party expected = context.getBean(Party.class);
		expected.setRegId("BJP01");
		Party actual = adminService.readByPartyRegId(expected.getRegId());
		assertEquals(expected.getRegId(), actual.getRegId());
	}

	@Test
	void testFindPartyByNameShouldReturnParty() {
		Party expected = context.getBean(Party.class);
		expected.setPartyName("BJP");
		Party actual = adminService.findByPartyName(expected.getPartyName());
		assertEquals(expected.getPartyName(), actual.getPartyName());

	}

	@Test
	void testToUpdateLeaderByRegId() {
		Party expected = context.getBean(Party.class);
		expected.setRegId("BJP01");
		expected.setLeader("Rajnath Singh");
		boolean result = adminService.updateLeaderByRegdId(expected.getRegId(), expected.getLeader());
		assertTrue(result);
	}

	@Test
	public void testToViewAllParty() {
		int source = adminService.readAllParties().size();
		int count = 0;
		Party expected = context.getBean(Party.class);
		List<Party> list = new ArrayList<Party>();
		for (int i = 0; i < source; i++) {
			list.add(expected);
			count += 1;
		}
		List<Party> actual = adminService.readAllParties();
		assertEquals(count, actual.size());
	}

	@Test
	void testToRemovePartyById() {
		Party expected = context.getBean(Party.class);
		expected.setRegId("TMC-01");
		expected.setPartyName("TMC");
		expected.setLeader("Mamta Banerjee");
		expected.setSymbol("Lotus");
		adminService.addParty(expected);
		boolean result = adminService.removePartyById(expected.getRegId());
		assertTrue(result);
	}

	// CANDIDATE TESTING

	@Test
	void testToAddCandidates() {
		Candidates expected = context.getBean(Candidates.class);
		expected.setCandidateName("Rohit");
		expected.setConstituencyId(44);
		expected.setPartyRegId("C-01");
		String res = adminService.addCandidate(expected);
		assertThat(res);
		adminService.removeCandidateById(expected.getCandidateId());
	}

	@Test
	void testFindCandidatesByNameShouldReturnCandidates() {
		Candidates expected = context.getBean(Candidates.class);
		expected.setCandidateName("Virat");
		Candidates actual = adminService.findCandidateByName(expected.getCandidateName());
		assertThat(actual.equals(expected));
	}

	@Test
	void testFindCandidatesByIdShouldReturnCandidates() {
		Candidates expected = context.getBean(Candidates.class);
		expected.setCandidateId(49);
		Candidates actual = adminService.readCandidateId(expected.getCandidateId());
		assertEquals(expected.getCandidateId(), actual.getCandidateId());
	}

	@Test
	void testToUpdateCandidateNameById() {
		Candidates expected = context.getBean(Candidates.class);
		expected.setCandidateId(49);
		expected.setCandidateName("Virat");
		boolean result = adminService.updateCandidateNameById(expected.getCandidateId(), expected.getCandidateName());
		assertTrue(result);

	}

	@Test
	public void testToViewAllCandidates() {
		int source = adminService.viewCandidate().size();
		int count = 0;
		Candidates expected = context.getBean(Candidates.class);
		List<Candidates> list = new ArrayList<Candidates>();
		for (int i = 0; i < source; i++) {
			list.add(expected);
			count += 1;
		}
		List<Candidates> actual = adminService.viewCandidate();
		assertEquals(count, actual.size());
	}

	@Test
	void testToRemoveCandidatesById() {
		Candidates expected = context.getBean(Candidates.class);
		expected.setCandidateName("Rohit");
		expected.setConstituencyId(44);
		expected.setPartyRegId("C-01");
		adminService.addCandidate(expected);
		boolean result = adminService.removeCandidateById(expected.getCandidateId());
		assertTrue(result);
	}

	// CONSTITUENCY TESTING

	@Test
	void testToAddConstituency() {
		Constituency expected = context.getBean(Constituency.class);
		expected.setConstituencyName("Bhilai-C");
		expected.setElectionId(7);
		expected.setState("CG");
		boolean result = adminService.addConstituency(expected);
		assertTrue(result);
		adminService.removeConstituency(expected.getConstituencyId());
	}

	@Test
	void testFindConstituencyByIdShouldReturnConstituency() {
		Constituency expected = context.getBean(Constituency.class);
		expected.setConstituencyId(1);
		Constituency actual = adminService.readConstituencyById(expected.getConstituencyId());
		assertEquals(expected.getConstituencyId(), actual.getConstituencyId());

	}

	@Test
	void testFindConstituencyByNameShouldReturnConstituency() {
		Constituency expected = context.getBean(Constituency.class);
		expected.setConstituencyName("Dadar-C");
		Constituency actual = adminService.findConstituencyByName(expected.getConstituencyName());
		assertEquals(expected.getConstituencyName(), actual.getConstituencyName());
	}

	@Test
	void testToUpdateConstituencyNameById() {
		Constituency expected = context.getBean(Constituency.class);
		expected.setConstituencyId(1);
		expected.setConstituencyName("Dadar-C");
		boolean result = adminService.updateConstituencyNameById(expected.getConstituencyId(),
				expected.getConstituencyName());
		assertTrue(result);

	}

	@Test
	public void testToViewAllConstituency() {
		int source = adminService.viewConstituency().size();
		int count = 0;
		Constituency expected = context.getBean(Constituency.class);
		List<Constituency> list = new ArrayList<Constituency>();
		for (int i = 0; i < source; i++) {
			list.add(expected);
			count += 1;
		}
		List<Constituency> actual = adminService.viewConstituency();
		assertEquals(count, actual.size());
	}

	@Test
	void testToRemoveConstituencyById() {
		Constituency expected = context.getBean(Constituency.class);
		expected.setConstituencyName("Bhilai-C");
		expected.setElectionId(7);
		expected.setState("CG");
		adminService.addConstituency(expected);

		boolean result = adminService.removeConstituency(expected.getConstituencyId());
		assertTrue(result);
	}

	// PINCODE-TESTING

	@Test
	void testToAddPincode() {
		Pincode expected = context.getBean(Pincode.class);
		expected.setPincode(490020);
		expected.setConstituencyId(1);
		expected.setConstituencyName("Dadar-C");
		expected.setState("CG");
		boolean result = adminService.addPincode(expected);
		assertTrue(result);
	}

}
