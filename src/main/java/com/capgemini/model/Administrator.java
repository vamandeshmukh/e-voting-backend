package com.capgemini.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("Administrator")
@Scope("prototype")
@Entity
@Table(name = "ADMINISTRATOR")
public class Administrator implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ADMIN_ID")
	private String adminId;

	@Column(name = "ADMIN_NAME", length = 50)
	private String adminName;

	@Column(name = "ADMIN_PASSWORD")
	private String adminPassword;

	public Administrator() {

	}

	public Administrator(String adminId, String adminName, String adminPassword) {
		super();
		this.adminId = adminId;
		this.adminName = adminName;
		this.adminPassword = adminPassword;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	@Override
	public String toString() {
		return "Administrator [adminId=" + adminId + ", adminName=" + adminName + ", adminPassword=" + adminPassword
				+ "]";
	}

}
