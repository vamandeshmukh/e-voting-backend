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

@Component("Constituency")
@Scope("prototype")
@Entity
@Table(name = "CONSTITUENCY")
public class Constituency implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CONSTITUENCY_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int constituencyId;

	@Column(name = "CONSTITUENCY_NAME", length = 30, nullable = false, unique = true)
	private String constituencyName;

	@Column(name = "STATE", length = 30, nullable = false)
	private String state;

	@Column(name = "ELECTION_ID", length = 3, nullable = false)
	private int electionId;

	public Constituency() {

	}

	public Constituency(int constituencyId, String constituencyName, String state, int electionId) {
		super();
		this.constituencyId = constituencyId;
		this.constituencyName = constituencyName;
		this.state = state;
		this.electionId = electionId;
	}

	public int getConstituencyId() {
		return constituencyId;
	}

	public void setConstituencyId(int constituencyId) {
		this.constituencyId = constituencyId;
	}

	public String getConstituencyName() {
		return constituencyName;
	}

	public void setConstituencyName(String constituencyName) {
		this.constituencyName = constituencyName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getElectionId() {
		return electionId;
	}

	public void setElectionId(int electionId) {
		this.electionId = electionId;
	}

	@Override
	public String toString() {
		return "Constituency [constituencyId=" + constituencyId + ", constituencyName=" + constituencyName + ", state="
				+ state + ", electionId=" + electionId + "]";
	}

}
