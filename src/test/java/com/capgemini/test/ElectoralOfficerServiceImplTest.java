package com.capgemini.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import com.capgemini.model.ElectoralOfficer;
import com.capgemini.model.Voter;
import com.capgemini.service.ElectoralOfficerService;

@SpringBootTest
class ElectoralOfficerServiceImplTest {
	@Autowired
	private ElectoralOfficerService eoService;

	@Autowired
	private ApplicationContext context;

	@Test
	void testToAddEO() {
		ElectoralOfficer expected = context.getBean(ElectoralOfficer.class);
		expected.setElectoralOfficerId("EO-01");
		expected.setElectroalOfficerName("Sai");
		expected.setElectoralOfficerPassword("sai123");
		boolean result = eoService.addEO(expected);
		assertTrue(result);
	}

	@Test
	void testToRejectVoterRequestById() {
		Voter expected = context.getBean(Voter.class);
		expected.setMobile(9999999901L);
		boolean result = eoService.rejectVoterRequestById(expected.getMobile());
		assertTrue(result);
	}

	@Test
	void testToDeactivateVoterRequestById() {
		Voter expected = context.getBean(Voter.class);
		expected.setMobile(9999999901L);
		boolean result = eoService.deactivateVoterById(expected.getMobile());
		assertTrue(result);
	}

	@Test
	void testToViewtVoterRequestById() {
		Voter expected = context.getBean(Voter.class);
		expected.setMobile(9999999901L);
		Voter actual = eoService.viewRequestById(expected.getMobile());
		assertEquals(expected.getMobile(), actual.getMobile());
	}

	@Test
	void testToLoginEO() {
		ElectoralOfficer expected = context.getBean(ElectoralOfficer.class);
		expected.setElectoralOfficerId("EO-02");
		expected.setElectoralOfficerPassword("vish123");
		expected.setElectroalOfficerName("Vishal");
		ElectoralOfficer actual = eoService.loginEO(expected.getElectoralOfficerId(),
				expected.getElectoralOfficerPassword());
		assertEquals(expected.getElectoralOfficerId(), actual.getElectoralOfficerId());
		assertEquals(expected.getElectoralOfficerPassword(), actual.getElectoralOfficerPassword());
	}

	@Test
	public void testToViewAllVoters() {
		int source = eoService.viewAllVoters().size();
		int count = 0;
		Voter expected = context.getBean(Voter.class);
		List<Voter> list = new ArrayList<Voter>();
		for (int i = 0; i < source; i++) {
			list.add(expected);
			count += 1;
		}
		List<Voter> actual = eoService.viewAllVoters();
		assertEquals(count, actual.size());
	}

	@Test
	public void testToViewAllRequest() {
		int source = eoService.viewAllRequests().size();
		int count = 0;
		Voter expected = context.getBean(Voter.class);
		List<Voter> list = new ArrayList<Voter>();
		for (int i = 0; i < source; i++) {
			list.add(expected);
			count += 1;
		}
		List<Voter> actual = eoService.viewAllRequests();
		assertEquals(count, actual.size());
	}

}
