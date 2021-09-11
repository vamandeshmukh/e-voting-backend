package com.capgemini.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("Party")
@Scope("prototype")
@Entity
@Table(name = "PARTY")
public class Party implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "REG_ID")
	private String regId;

	@Column(name = "PARTY_NAME", length = 50, nullable = false)
	private String partyName;

	@Column(name = "SYMBOL")
	private String symbol;

	@Column(name = "LEADER", length = 50, nullable = false)
	private String leader;

	public Party() {

	}

	public Party(String regId, String partyName, String symbol, String leader) {
		super();
		this.regId = regId;
		this.partyName = partyName;
		this.symbol = symbol;
		this.leader = leader;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getLeader() {
		return leader;
	}

	public void setLeader(String leader) {
		this.leader = leader;
	}

	@Override
	public String toString() {
		return "Party [regId=" + regId + ", partyName=" + partyName + ", symbol=" + symbol + ", leader=" + leader + "]";
	}

}
