package com.capgemini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.model.Voter;

@Repository("voterRepository")
public interface VoterRepository extends JpaRepository<Voter, Long> {

	@Transactional
	@Modifying
	@Query(value = "UPDATE Voter v SET v.status = :validated WHERE v.aadhaarId = :id ")
	public void handleRequestbyId(@Param("validated") String status, @Param("id") long aadhaar);

	@Transactional
	@Modifying
	@Query(value = "UPDATE Voter v SET v.status = :validated WHERE v.epic = :epic ")
	public void handleRequestbyepic(@Param("validated") String status, @Param("epic") String epic);

	@Transactional
	@Modifying
	@Query(value = "UPDATE Voter v SET v.epic = :newId WHERE v.aadhaarId = :id ")
	public void generateVoterIdbyId(@Param("newId") String epic, @Param("id") long aadhaar);

	@Transactional
	@Modifying
	@Query(value = "UPDATE Voter v SET v.constituencyId = :newConstituency WHERE v.aadhaarId = :id ")
	public void generateConstituencybyId(@Param("newConstituency") int constituencyId, @Param("id") long aadhaar);

	@Query(value = "Select v From Voter v Where Lower(v.status) = :statusmessage")
	public List<Voter> readAllRequestsByStatus(@Param("statusmessage") String statusmessage);

	@Query(value = "Select v from Voter v where Lower(v.voterFirstName) Like %:name% Or Lower(v.voterMiddleName) Like %:name% Or Lower(v.voterLastName) Like %:name%")
	public List<Voter> findAllVoterByName(@Param("name") String voterName);

	@Query(value = "Select v from Voter v where Lower(v.status)= :status")
	public List<Voter> viewAllRequests(@Param("status") String status);

	@Query(value = "Select v from Voter v where v.epic= :epic")
	public Voter findVoterByEpic(@Param("epic") String epic);

	@Query(value = "Select v from Voter v where v.mobile= :mobile")
	public Voter findVoterByMobile(@Param("mobile") long mobile);

	@Query(value = "Select v from Voter v where v.voterEmail= :email")
	public Voter findVoterByEmail(@Param("email") String email);
}
