/**
 * 
 */
package com.nervytech.mailer24x7.spring.form;

import org.springframework.stereotype.Repository;

/**
 * @author ADMIN
 * 
 */
@Repository("regForm")
public class RegForm extends UserForm{

	private String organization;

	private String orgDisplayName;

	private String orgContactEmail;
	
	private String orgContactNumber;

	private String website;

	private String address;

	private String country;

	private String senderEmail;
	
	private String message;
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getOrganization() {
		return organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public String getOrgDisplayName() {
		return orgDisplayName;
	}

	public void setOrgDisplayName(String orgDisplayName) {
		this.orgDisplayName = orgDisplayName;
	}

	public String getOrgContactEmail() {
		return orgContactEmail;
	}

	public void setOrgContactEmail(String orgContactEmail) {
		this.orgContactEmail = orgContactEmail;
	}

	public String getOrgContactNumber() {
		return orgContactNumber;
	}

	public void setOrgContactNumber(String orgContactNumber) {
		this.orgContactNumber = orgContactNumber;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}
	
}
