/**
 * 
 */
package com.nervytech.mailer24x7.spring.form;

import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @author ADMIN
 * 
 */
@Repository("campaignForm")
public class CampaignForm {

	private String campaignName;

	private String senderName;

	private String senderEmail;
	
	private String subject;

	private String emailTemplateType;

	private String editorContent;

	private String subscriberGroup;

	private String sendingOption;
	
	private String scheduledTime;
	
	private String testMailId;
	
    private String senderMail;
	    
    private String contentType;
	    
    private String filename;
	    
    private CommonsMultipartFile fileData;
	    
    private String action;
    
    private long campaignId;
    
    private String testMailStatus;
    
	public String getTestMailStatus() {
		return testMailStatus;
	}

	public void setTestMailStatus(String testMailStatus) {
		this.testMailStatus = testMailStatus;
	}

	public long getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(long campaignId) {
		this.campaignId = campaignId;
	}

	public String getSenderMail() {
		return senderMail;
	}

	public void setSenderMail(String senderMail) {
		this.senderMail = senderMail;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public CommonsMultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getEmailTemplateType() {
		return emailTemplateType;
	}

	public void setEmailTemplateType(String emailTemplateType) {
		this.emailTemplateType = emailTemplateType;
	}

	public String getEditorContent() {
		return editorContent;
	}

	public void setEditorContent(String editorContent) {
		this.editorContent = editorContent;
	}

	public String getSubscriberGroup() {
		return subscriberGroup;
	}

	public void setSubscriberGroup(String subscriberGroup) {
		this.subscriberGroup = subscriberGroup;
	}

	public String getSendingOption() {
		return sendingOption;
	}

	public void setSendingOption(String sendingOption) {
		this.sendingOption = sendingOption;
	}

	public String getScheduledTime() {
		return scheduledTime;
	}

	public void setScheduledTime(String scheduledTime) {
		this.scheduledTime = scheduledTime;
	}

	public String getTestMailId() {
		return testMailId;
	}

	public void setTestMailId(String testMailId) {
		this.testMailId = testMailId;
	}

}