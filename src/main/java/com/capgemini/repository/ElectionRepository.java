package com.capgemini.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capgemini.model.Election;

@Repository
public interface ElectionRepository extends JpaRepository<Election, Integer> {

	@Query(value = "Select e From Election e Where Lower(e.electionName) = :name")
	public List<Election> readAllElectionsByName(@Param("name") String electionName);

	@Query(value = "Select e From Election e Where Lower(e.electionType) = :type")
	public List<Election> readAllElectionsByType(@Param("type") String electionType);

	@Query(value = "Select e From Election e Where e.electionDate Between :from And :to")
	public List<Election> readAllElectionsByDate(@Param("from") String from, @Param("to") String to);

	@Transactional
	@Modifying
	@Query(value = "Update Election e Set e.electionName = :electionName Where e.electionId = :electionId")
	public void editElectionName(@Param("electionId") int electionId, @Param("electionName") String electionName);

	@Transactional
	@Modifying
	@Query(value = "Update Election e Set e.electionType = :electionType Where e.electionId = :electionId")
	public void editElectionType(@Param("electionId") int electionId, @Param("electionType") String electionType);

	@Transactional
	@Modifying
	@Query(value = "Update Election e Set e.electionDate = :electionDate Where e.electionId = :electionId")
	public void editElectionDate(@Param("electionId") int electionId, @Param("electionDate") String electionDate);

	@Query(value = "Select e From Election e Where Lower(e.electionName) = :name")
	public Election findElectionsByName(@Param("name") String electionName);
}
