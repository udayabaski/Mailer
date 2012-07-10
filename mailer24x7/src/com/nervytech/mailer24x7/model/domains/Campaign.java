/**
 * 
 */
package com.nervytech.mailer24x7.model.domains;



/**
 * @author ADMIN
 *
 */
public class Campaign {

	private long campaignId;

	private long orgId;
	
	private String campaignName;
    
	private String createdEmailId;
    
	private String createdTime;
    
	private String lastModifiedTime;
    
	private String isPoweredBy;
    
	private String subject;

    private String imageLocation;
    
    private String campaignLink;
    
    private String unsubscribeLink;
    
    private String unsubscribeSubject;

    private long statusId;

	private long userId;
    
	private long subscriberListId;
    
	private String scheduledOn;
    
	private int status;
    
	private String latestSubscriberCount;

    private String totalSubscriberSent;
    
    private int opened;
    
    private int bounced;
    
    private int clicked;
    
	public long getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(long campaignId) {
		this.campaignId = campaignId;
	}

	public long getOrgId() {
		return orgId;
	}

	public void setOrgId(long orgId) {
		this.orgId = orgId;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public String getCreatedEmailId() {
		return createdEmailId;
	}

	public void setCreatedEmailId(String createdEmailId) {
		this.createdEmailId = createdEmailId;
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

	public String getIsPoweredBy() {
		return isPoweredBy;
	}

	public void setIsPoweredBy(String isPoweredBy) {
		this.isPoweredBy = isPoweredBy;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getImageLocation() {
		return imageLocation;
	}

	public void setImageLocation(String imageLocation) {
		this.imageLocation = imageLocation;
	}

	public String getCampaignLink() {
		return campaignLink;
	}

	public void setCampaignLink(String campaignLink) {
		this.campaignLink = campaignLink;
	}

	public String getUnsubscribeLink() {
		return unsubscribeLink;
	}

	public void setUnsubscribeLink(String unsubscribeLink) {
		this.unsubscribeLink = unsubscribeLink;
	}

	public String getUnsubscribeSubject() {
		return unsubscribeSubject;
	}

	public void setUnsubscribeSubject(String unsubscribeSubject) {
		this.unsubscribeSubject = unsubscribeSubject;
	}

	public long getStatusId() {
		return statusId;
	}

	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
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
