package com.capgemini.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("Candidate")
@Scope(scopeName = "prototype")
@Entity
@Table(name = "CANDIDATE")
public class Candidates implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CANDIDATE_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int candidateId;

	@Column(name = "CANDIDATE_NAME", length = 50, nullable = false)
	private String candidateName;

	@Column(name = "PARTY_REG_ID", length = 10, nullable = false)
	private String partyRegId;

	@Column(name = "CONSTITUENCY_ID", length = 3, nullable = false)
	private int constituencyId;

	public Candidates() {

	}

	public Candidates(int candidateId, String candidateName, String partyRegId, int constituencyId) {
		super();
		this.candidateId = candidateId;
		this.candidateName = candidateName;
		this.partyRegId = partyRegId;
		this.constituencyId = constituencyId;
	}

	public int getCandidateId() {
		return candidateId;
	}

	public void setCandidateId(int candidateId) {
		this.candidateId = candidateId;
	}

	public String getCandidateName() {
		return candidateName;
	}

	public void setCandidateName(String candidateName) {
		this.candidateName = candidateName;
	}

	public String getPartyRegId() {
		return partyRegId;
	}

	public void setPartyRegId(String partyRegId) {
		this.partyRegId = partyRegId;
	}

	public int getConstituencyId() {
		return constituencyId;
	}

	public void setConstituencyId(int constituencyId) {
		this.constituencyId = constituencyId;
	}

	@Override
	public String toString() {
		return "Candidates [candidateId=" + candidateId + ", candidateName=" + candidateName + ", partyRegId="
				+ partyRegId + ", constituencyId=" + constituencyId + "]";
	}

}
