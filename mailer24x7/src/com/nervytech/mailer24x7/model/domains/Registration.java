/**
 * 
 */
package com.nervytech.mailer24x7.model.domains;


/**
 * @author ADMIN
 *
 */
public class Registration {

	private long userId;

	private String confirmationId;
	
	private String createdTime;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getConfirmationId() {
		return confirmationId;
	}

	public void setConfirmationId(String confirmationId) {
		this.confirmationId = confirmationId;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

}
