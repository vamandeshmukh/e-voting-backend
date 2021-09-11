package com.capgemini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.model.Candidates;

@Repository
public interface CandidatesRepository extends JpaRepository<Candidates, Integer> {

	@Transactional
	@Modifying
	@Query(value = "Update Candidates c Set c.candidateName = :candidateName Where c.candidateId = :id")
	public void updateCandidateNameById(@Param("candidateName") String candidateName, @Param("id") int candidateId);

	@Query(value = "Select c From Candidates c Where Lower(c.candidateName) Like %:name%")
	public Candidates readCandidateByName(@Param("name") String candidateName);

	@Query(value = "Select c From Candidates c Where c.constituencyId = :voterConstituency")
	public List<Candidates> findCandidatesByConstituency(@Param("voterConstituency") int voterConstituency);

	@Query(value = "select c From Candidates c Where c.partyRegId = :partyId And c.constituencyId = :constituency ")
	public Candidates findDuplicateCandidates(@Param("partyId") String partyId,
			@Param("constituency") int constituency);
	
	@Query(value = "Select c From Candidates c Where c.partyRegId = :partyRegId")
	public List<Candidates> findCandidatesByPartyId(@Param("partyRegId") String voterConstituency);
}
