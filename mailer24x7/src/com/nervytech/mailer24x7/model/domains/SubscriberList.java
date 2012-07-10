/**
 * 
 */
package com.nervytech.mailer24x7.model.domains;

import java.util.Date;


/**
 * @author ADMIN
 *
 */
public class SubscriberList {

	private long subscriberListId;

	private long orgId;
	
	private String subscriberListName;
    
	private long userId;
    
	private String createdTime;
	
	private String lastModifiedTime;
	
	private int activeCount;
	
	private int bouncedCount;
	
	private int unsubscriberCount;
	
	private long subscriberId;

	public long getSubscriberListId() {
		return subscriberListId;
	}

	public void setSubscriberListId(long subscriberListId) {
		this.subscriberListId = subscriberListId;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public String getSubscriberListName() {
		return subscriberListName;
	}

	public void setSubscriberListName(String subscriberListName) {
		this.subscriberListName = subscriberListName;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getLastModifiedTime() {
		return lastModifiedTime;
	}

	public void setLastModifiedTime(String lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}

	public int getActiveCount() {
		return activeCount;
	}

	public void setActiveCount(int activeCount) {
		this.activeCount = activeCount;
	}

	public int getBouncedCount() {
		return bouncedCount;
	}

	public void setBouncedCount(int bouncedCount) {
		this.bouncedCount = bouncedCount;
	}

	public int getUnsubscriberCount() {
		return unsubscriberCount;
	}

	public void setUnsubscriberCount(int unsubscriberCount) {
		this.unsubscriberCount = unsubscriberCount;
	}

	public long getSubscriberId() {
		return subscriberId;
	}

	public void setSubscriberId(long subscriberId) {
		this.subscriberId = subscriberId;
	}

}
