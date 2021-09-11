package com.capgemini.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Entity
@Component
@Scope(value = "prototype")
@Table(name = "Pincode_table")
public class Pincode implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "Pincode_id")
	private int pincode;

	@Column(name = "constituency_Id", length = 3, nullable = false)
	private int constituencyId;

	@Column(name = "contituency_name", length = 30, nullable = false)
	private String constituencyName;

	@Column(name = "State", length = 30, nullable = false)
	private String state;

	public Pincode() {
		super();

	}

	public Pincode(int pincode, int constituencyId, String constituencyName, String state) {
		super();
		this.pincode = pincode;
		this.constituencyId = constituencyId;
		this.constituencyName = constituencyName;
		this.state = state;
	}

	public int getPincode() {
		return pincode;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
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

	@Override
	public String toString() {
		return "Pincode [pincode=" + pincode + ", constituencyId=" + constituencyId + ", constituencyName="
				+ constituencyName + ", state=" + state + "]";
	}

}
