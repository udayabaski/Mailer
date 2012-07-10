/**
 * 
 */
package com.nervytech.mailer24x7.model.domains;

import java.util.Date;


/**
 * @author ADMIN
 *
 */
public class CampaignStatus {

	private long statusId;

	private long orgId;
	
	private long userId;
    
	private long campaignId;
    
	private long subscriberListId;
    
	private String scheduledOn;
    
	private int status;
    
	private String latestSubscriberCount;

    private String totalSubscriberSent;
    
    private int opened;
    
    private int bounced;
    
    private int clicked;

	public long getStatusId() {
		return statusId;
	}

	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(long campaignId) {
		this.campaignId = campaignId;
	}

	public long getSubscriberListId() {
		return subscriberListId;
	}

	public void setSubscriberListId(long subscriberListId) {
		this.subscriberListId = subscriberListId;
	}

	public String getScheduledOn() {
		return scheduledOn;
	}

	public void setScheduledOn(String scheduledOn) {
		this.scheduledOn = scheduledOn;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getLatestSubscriberCount() {
		return latestSubscriberCount;
	}

	public void setLatestSubscriberCount(String latestSubscriberCount) {
		this.latestSubscriberCount = latestSubscriberCount;
	}

	public String getTotalSubscriberSent() {
		return totalSubscriberSent;
	}

	public void setTotalSubscriberSent(String totalSubscriberSent) {
		this.totalSubscriberSent = totalSubscriberSent;
	}

	public int getOpened() {
		return opened;
	}

	public void setOpened(int opened) {
		this.opened = opened;
	}

	public int getBounced() {
		return bounced;
	}

	public void setBounced(int bounced) {
		this.bounced = bounced;
	}

	public int getClicked() {
		return clicked;
	}

	public void setClicked(int clicked) {
		this.clicked = clicked;
	}
    
}
