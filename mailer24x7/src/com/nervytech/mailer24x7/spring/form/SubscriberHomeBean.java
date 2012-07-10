/**
 * 
 */
package com.nervytech.mailer24x7.spring.form;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.nervytech.mailer24x7.model.domains.SubscriberIdStatus;
import com.nervytech.mailer24x7.model.domains.SubscriberList;

/**
 * @author ADMIN
 * 
 */
@Repository("subscriberForm")
public class SubscriberHomeBean {

    private List<SubscriberList> subscriberList;
    
    private SubscriberList subscriberGroup;
    
    private List<SubscriberIdStatus> activeSubscribers;
    
    private List<SubscriberIdStatus> unsubscribedSubscribers;
    
    private List<SubscriberIdStatus> bouncedSubscribers;

    private boolean subscriberListNotEmpty;
    
	public boolean isSubscriberListNotEmpty() {
		return subscriberListNotEmpty;
	}

	public void setSubscriberListNotEmpty(boolean subscriberListNotEmpty) {
		this.subscriberListNotEmpty = subscriberListNotEmpty;
	}

	public List<SubscriberList> getSubscriberList() {
		return subscriberList;
	}

	public void setSubscriberList(List<SubscriberList> subscriberList) {
		this.subscriberList = subscriberList;
	}

	public SubscriberList getSubscriberGroup() {
		return subscriberGroup;
	}

	public void setSubscriberGroup(SubscriberList subscriberGroup) {
		this.subscriberGroup = subscriberGroup;
	}

	public List<SubscriberIdStatus> getActiveSubscribers() {
		return activeSubscribers;
	}

	public void setActiveSubscribers(List<SubscriberIdStatus> activeSubscribers) {
		this.activeSubscribers = activeSubscribers;
	}

	public List<SubscriberIdStatus> getUnsubscribedSubscribers() {
		return unsubscribedSubscribers;
	}

	public void setUnsubscribedSubscribers(
			List<SubscriberIdStatus> unsubscribedSubscribers) {
		this.unsubscribedSubscribers = unsubscribedSubscribers;
	}

	public List<SubscriberIdStatus> getBouncedSubscribers() {
		return bouncedSubscribers;
	}

	public void setBouncedSubscribers(List<SubscriberIdStatus> bouncedSubscribers) {
		this.bouncedSubscribers = bouncedSubscribers;
	}
	
}
