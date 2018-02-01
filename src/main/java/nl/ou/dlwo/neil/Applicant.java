package nl.ou.dlwo.neil;

import java.io.Serializable;
import java.util.Date;

public class Applicant implements Serializable {

	private static final long serialVersionUID = 4661552363081858711L;

	private String city;
	private String comments;
	private Date dateOfBirth;
	private String emailAddress;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String postalCode;
	private Long provinceId;

	public String getCity() {
		return city;
	}

	public String getComments() {
		return comments;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public Long getProvinceId() {
		return provinceId;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}
}