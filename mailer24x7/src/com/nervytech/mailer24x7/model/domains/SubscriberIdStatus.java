/**
 * 
 */
package com.nervytech.mailer24x7.model.domains;



/**
 * @author ADMIN
 *
 */
public class SubscriberIdStatus {

	private long statusId;

	private long subscriberListId;
	
	private long userId;
    
	private long orgId;
    
	private int status;
	
	private String emailId;

	public long getStatusId() {
		return statusId;
	}

	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}

	public long getSubscriberListId() {
		return subscriberListId;
	}

	public void setSubscriberListId(long subscriberListId) {
		this.subscriberListId = subscriberListId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

}
