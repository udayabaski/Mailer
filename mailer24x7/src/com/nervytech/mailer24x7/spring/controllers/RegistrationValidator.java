/**
 * 
 */
package com.nervytech.mailer24x7.spring.controllers;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.nervytech.mailer24x7.spring.form.RegForm;

/**
 * @author ADMIN
 *
 */

@Component("registrationValidator")
public class RegistrationValidator {
	
	public boolean supports(Class<?> klass) {
	    return RegForm.class.isAssignableFrom(klass);
	}
	
	public void validate(Object target, Errors errors) {
	    
		RegForm registrationForm = (RegForm) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullName", "NotEmpty.registrationForm.fullName","Full name must not be empty.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "organization", "NotEmpty.registrationForm.organization","Company must not be empty.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "website", "NotEmpty.registrationForm.website","Website must not be empty.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "country", "NotEmpty.registrationForm.country","Username must not be empty.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailId", "NotEmpty.registrationForm.emailId","Email must not be empty.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.registrationForm.password","Password must not be empty.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty.registrationForm.confirmPassword","Confirm password must not be empty.");
		
		if(registrationForm.getCountry().equals("-1")){
			errors.rejectValue("country", "NotEmpty.registrationForm.country", "Please select your country.");
		}
		
	    if (!(registrationForm.getPassword()).equals(registrationForm.getConfirmPassword())) {
	        errors.rejectValue("confirmPassword","matchingPassword.registrationForm.password","Password and Confirm Password does not match.");
	    }
	  }
}
