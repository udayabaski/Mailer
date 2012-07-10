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
@Repository("subscriberForm")
public class SubscriberForm {

	private String subscriberName;

	private String addOption;

	private CommonsMultipartFile fileData;
	
	private String subscribers;
	
    private String action;
    
    private String toPage;
    
	public String getToPage() {
		return toPage;
	}

	public void setToPage(String toPage) {
		this.toPage = toPage;
	}

	public String getSubscriberName() {
		return subscriberName;
	}

	public void setSubscriberName(String subscriberName) {
		this.subscriberName = subscriberName;
	}

	public String getAddOption() {
		return addOption;
	}

	public void setAddOption(String addOption) {
		this.addOption = addOption;
	}

	public CommonsMultipartFile getFileData() {
		return fileData;
	}

	public void setFileData(CommonsMultipartFile fileData) {
		this.fileData = fileData;
	}

	public String getSubscribers() {
		return subscribers;
	}

	public void setSubscribers(String subscribers) {
		this.subscribers = subscribers;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
    
}