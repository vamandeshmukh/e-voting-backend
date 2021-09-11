package com.capgemini.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("Electoral_Officer")
@Scope("prototype")
@Entity
@Table(name = "ELECTORAL_OFFICER")
public class ElectoralOfficer implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ELECTORAL_OFFICER_ID")
	private String electoralOfficerId;

	@Column(name = "ELECTORAL_OFFICER_NAME", length = 50)
	private String electroalOfficerName;

	@Column(name = "ELECTORAL_OFFICER_PASSWORD")
	private String electoralOfficerPassword;

	public ElectoralOfficer() {

	}

	public String getElectoralOfficerId() {
		return electoralOfficerId;
	}

	public void setElectoralOfficerId(String electoralOfficerId) {
		this.electoralOfficerId = electoralOfficerId;
	}

	public String getElectroalOfficerName() {
		return electroalOfficerName;
	}

	public void setElectroalOfficerName(String electroalOfficerName) {
		this.electroalOfficerName = electroalOfficerName;
	}

	public String getElectoralOfficerPassword() {
		return electoralOfficerPassword;
	}

	public void setElectoralOfficerPassword(String electoralOfficerPassword) {
		this.electoralOfficerPassword = electoralOfficerPassword;
	}

	@Override
	public String toString() {
		return "ElectoralOfficer [electoralOfficerId=" + electoralOfficerId + ", electroalOfficerName="
				+ electroalOfficerName + ", electoralOfficerPassword=" + electoralOfficerPassword + "]";
	}

}
