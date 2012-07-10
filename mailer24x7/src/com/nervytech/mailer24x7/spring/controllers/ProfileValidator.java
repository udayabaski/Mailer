/**
 * 
 */
package com.nervytech.mailer24x7.spring.controllers;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.nervytech.mailer24x7.spring.form.RegForm;
import com.nervytech.mailer24x7.spring.form.UserForm;

/**
 * @author ADMIN
 *
 */

@Component("profileValidator")
public class ProfileValidator {
	
	public boolean supports(Class<?> klass) {
	    return RegForm.class.isAssignableFrom(klass);
	}
	
	public void validate(Object target, Errors errors) {
	    
		UserForm profileForm = (UserForm) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailId", "NotEmpty.profileForm.emailId","Email must not be empty.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty.profileForm.password","Password must not be empty.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "NotEmpty.profileForm.confirmPassword","Confirm password must not be empty.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "fullName", "NotEmpty.profileForm.fullName","Full name must not be empty.");

		if(profileForm.getRole().equals("-1")){
			errors.rejectValue("role", "NotEmpty.profileForm.role", "Please select the role.");
		}
		
	    if (!(profileForm.getPassword()).equals(profileForm.getConfirmPassword())) {
	        errors.rejectValue("confirmPassword","matchingPassword.profileForm.password","Password and Confirm Password does not match.");
	    }
	  }
}
