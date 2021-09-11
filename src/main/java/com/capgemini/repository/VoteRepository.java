package com.capgemini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capgemini.model.Vote;

@Repository("voteRepository")
public interface VoteRepository extends JpaRepository<Vote, Integer> {

	@Query(value = "Select v From Vote v Where v.epic = :epic")
	public Vote existEpicInVote(@Param("epic") String epic);

	@Query(value = "Select v.candidate, Count(v) From Vote v Group By v.candidate")
	public List<Object[]> cadidateVoteCount();

	@Query(value = "Select p.regId , count(v) From Vote v, Party p, Candidates c Where v.candidate = c.candidateId And c.partyRegId = p.regId Group By p.regId")
	public List<Object[]> partyVoteCount();

	@Query(value = "Select v.candidate, Count(v) From Vote v, Candidates c Where c.constituencyId = :constituency And v.candidate = c.candidateId Group By v.candidate")
	public List<Object[]> partyVoteConstituency(@Param("constituency") int constituency);
}