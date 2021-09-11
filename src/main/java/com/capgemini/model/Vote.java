package com.capgemini.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("Vote")
@Scope("prototype")
@Entity
@Table(name = "VOTE_TABLE")
public class Vote implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "VOTE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int voteId;

	@JoinColumn(name = "CANDIDATE_ID", nullable = false)
	private int candidate;

	@JoinColumn(name = "EPIC", nullable = false)
	private String epic;

	public Vote() {

	}

	public Vote(int voteId, int candidate, String epic) {
		super();
		this.voteId = voteId;
		this.candidate = candidate;
		this.epic = epic;
	}

	public int getVoteId() {
		return voteId;
	}

	public void setVoteId(int voteId) {
		this.voteId = voteId;
	}

	public int getCandidate() {
		return candidate;
	}

	public void setCandidate(int candidate) {
		this.candidate = candidate;
	}

	public String getEpic() {
		return epic;
	}

	public void setEpic(String epic) {
		this.epic = epic;
	}

	@Override
	public String toString() {
		return "Vote [voteId=" + voteId + ", candidate=" + candidate + ", epic=" + epic + "]";
	}

}
