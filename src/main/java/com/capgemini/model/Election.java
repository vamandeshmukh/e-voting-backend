
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

@Component("Election")
@Scope("prototype")
@Entity
@Table(name = "ELECTION")
public class Election implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ELECTION_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int electionId;

	@Column(name = "ELECTION_NAME", length = 30, nullable = false)
	private String electionName;

	@Column(name = "ELECTION_TYPE", length = 30, nullable = false)
	private String electionType;

	@Column(name = "ELECTION_DATE", length = 10, nullable = false)
	private String electionDate;

	public Election() {

	}

	public Election(int electionId, String electionName, String electionType, String electionDate) {
		super();
		this.electionId = electionId;
		this.electionName = electionName;
		this.electionType = electionType;
		this.electionDate = electionDate;

	}

	public int getElectionId() {
		return electionId;
	}

	public void setElectionId(int electionId) {
		this.electionId = electionId;
	}

	public String getElectionName() {
		return electionName;
	}

	public void setElectionName(String electionName) {
		this.electionName = electionName;
	}

	public String getElectionType() {
		return electionType;
	}

	public void setElectionType(String electionType) {
		this.electionType = electionType;
	}

	public String getElectionDate() {
		return electionDate;
	}

	public void setElectionDate(String electionDate) {
		this.electionDate = electionDate;
	}

	@Override
	public String toString() {
		return "Election [electionId=" + electionId + ", electionName=" + electionName + ", electionType="
				+ electionType + ", electionDate=" + electionDate + "]";
	}

}
