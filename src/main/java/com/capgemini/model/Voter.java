package com.capgemini.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("Voter")
@Scope("prototype")
@Entity
@Table(name = "VOTER_TABLE")

public class Voter implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "AADHAAR", length = 12, nullable = false, unique = true)
	private long aadhaarId;

	@Column(name = "EPIC")
	private String epic;

	@Column(name = "VOTER_FIRSTNAME", length = 50, nullable = false)
	private String voterFirstName;

	@Column(name = "VOTER_MIDDLENAME", length = 50, nullable = true)
	private String voterMiddleName;

	@Column(name = "VOTER_LASTNAME", length = 50, nullable = false)
	private String voterLastName;

	@Column(name = "DOB", length = 10, nullable = false)
	private String dob;

	@Column(name = "GENDER", nullable = false, columnDefinition = "varchar(6) Check(gender IN ('male','female','others'))")
	private String gender;

	@Column(name = "HouseNo")
	private String houseNo;

	@Column(name = "Street")
	private String street;

	@Column(name = "locality")
	private String locality;

	@Column(name = "City")
	private String city;

	@Column(name = "District")
	private String district;

	@Column(name = "State", length = 30, nullable = false)
	private String state;

	@Column(name = "PINCODE", length = 6, nullable = false)
	private int pincode;

	@Column(name = "MOBILE", length = 10, nullable = false, unique = true)
	private long mobile;

	@Column(name = "VOTER_PASSWORD", length = 8, nullable = false)
	private String voterPassword;

	@Column(name = "Email", length = 50, nullable = false, unique = true)
	private String voterEmail;

	@Column(name = "STATUS", nullable = false, columnDefinition = "varchar(15) Check(status IN ('requesting','validated','approved','rejected','deactivated'))")
	private String status;

	@Column(name = "CONSTITUENCY_ID", length = 3, nullable = false)
	private int constituencyId;

	public Voter() {

	}

	public Voter(long aadhaarId, String epic, String voterFirstName, String voterMiddleName, String voterLastName,
			String dob, String gender, String houseNo, String street, String locality, String city, String district,
			String state, int pincode, long mobile, String voterPassword, String voterEmail, String status,
			int constituencyId) {
		super();
		this.aadhaarId = aadhaarId;
		this.epic = epic;
		this.voterFirstName = voterFirstName;
		this.voterMiddleName = voterMiddleName;
		this.voterLastName = voterLastName;
		this.dob = dob;
		this.gender = gender;
		this.houseNo = houseNo;
		this.street = street;
		this.locality = locality;
		this.city = city;
		this.district = district;
		this.state = state;
		this.pincode = pincode;
		this.mobile = mobile;
		this.voterPassword = voterPassword;
		this.voterEmail = voterEmail;
		this.status = status;
		this.constituencyId = constituencyId;
	}

	public long getAadhaarId() {
		return aadhaarId;
	}

	public void setAadhaarId(long aadhaarId) {
		this.aadhaarId = aadhaarId;
	}

	public String getEpic() {
		return epic;
	}

	public void setEpic(String epic) {
		this.epic = epic;
	}

	public String getVoterFirstName() {
		return voterFirstName;
	}

	public void setVoterFirstName(String voterFirstName) {
		this.voterFirstName = voterFirstName;
	}

	public String getVoterMiddleName() {
		return voterMiddleName;
	}

	public void setVoterMiddleName(String voterMiddleName) {
		this.voterMiddleName = voterMiddleName;
	}

	public String getVoterLastName() {
		return voterLastName;
	}

	public void setVoterLastName(String voterLastName) {
		this.voterLastName = voterLastName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHouseNo() {
		return houseNo;
	}

	public void setHouseNo(String houseNo) {
		this.houseNo = houseNo;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public int getPincode() {
		return pincode;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
	}

	public long getMobile() {
		return mobile;
	}

	public void setMobile(long mobile) {
		this.mobile = mobile;
	}

	public String getVoterPassword() {
		return voterPassword;
	}

	public void setVoterPassword(String voterPassword) {
		this.voterPassword = voterPassword;
	}

	public String getVoterEmail() {
		return voterEmail;
	}

	public void setVoterEmail(String voterEmail) {
		this.voterEmail = voterEmail;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getConstituencyId() {
		return constituencyId;
	}

	public void setConstituencyId(int constituencyId) {
		this.constituencyId = constituencyId;
	}

	@Override
	public String toString() {
		return "Voter [aadhaarId=" + aadhaarId + ", epic=" + epic + ", voterFirstName=" + voterFirstName
				+ ", voterMiddleName=" + voterMiddleName + ", voterLastName=" + voterLastName + ", dob=" + dob
				+ ", gender=" + gender + ", houseNo=" + houseNo + ", street=" + street + ", locality=" + locality
				+ ", city=" + city + ", district=" + district + ", state=" + state + ", pincode=" + pincode
				+ ", mobile=" + mobile + ", voterPassword=" + voterPassword + ", voterEmail=" + voterEmail + ", status="
				+ status + ", constituencyId=" + constituencyId + "]";
	}

}
