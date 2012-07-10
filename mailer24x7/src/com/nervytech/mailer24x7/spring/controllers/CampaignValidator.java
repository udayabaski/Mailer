/**
 * 
 */
package com.nervytech.mailer24x7.spring.controllers;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.nervytech.mailer24x7.spring.form.CampaignForm;
import com.nervytech.mailer24x7.spring.form.RegForm;

/**
 * @author ADMIN
 *
 */

@Component("campaignValidator")
public class CampaignValidator {
	
	public boolean supports(Class<?> klass) {
	    return RegForm.class.isAssignableFrom(klass);
	}
	
	public void validate(Object target, Errors errors) {
	    
		CampaignForm campaignForm = (CampaignForm) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "campaignName", "NotEmpty.campaignForm.campaignName","Campaign name must not be empty.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "senderName", "NotEmpty.campaignForm.senderName","Sender name must not be empty.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "senderEmail", "NotEmpty.campaignForm.senderEmail","Sender email must not be empty.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subject", "NotEmpty.campaignForm.subject","Subject must not be empty.");
		
		if(campaignForm.getSubscriberGroup().equals("-1")){
			errors.rejectValue("subscriberGroup", "NotEmpty.campaignForm.subscriberGroup", "Please select a subscriber group.");
		}
		
	  }
}
