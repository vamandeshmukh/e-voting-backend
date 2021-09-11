package com.capgemini.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capgemini.model.Party;

@Repository
public interface PartyRepository extends JpaRepository<Party, String> {

	@Transactional
	@Modifying
	@Query(value = "Update Party p Set p.leader = :leader Where p.regId = :id")
	public void updatePartyLeaderByregId(@Param("leader") String leader, @Param("id") String regId);

	@Query(value = "Select p From Party p Where Lower(p.partyName) = :name")
	public Party readPartyByName(@Param("name") String partyName);
}
