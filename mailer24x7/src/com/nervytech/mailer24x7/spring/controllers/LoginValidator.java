/**
 * 
 */
package com.nervytech.mailer24x7.spring.controllers;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

import com.nervytech.mailer24x7.spring.form.LoginForm;

/**
 * @author ADMIN
 * 
 */

@Component("loginValidator")
public class LoginValidator {

	public boolean supports(Class<?> klass) {
		return LoginForm.class.isAssignableFrom(klass);
	}

	public void validate(Object target, Errors errors) {

		LoginForm loginForm = (LoginForm) target;

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName",
				"NotEmpty.loginForm.userName",
				"User name must not be empty.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
				"NotEmpty.loginForm.password",
				"Password must not be empty.");

		if (loginForm.getUserName().trim().length() > 30) {
			errors.rejectValue("userName", "length.loginForm.userName",
					"Username length can not be more than 30.");
		}
		if (loginForm.getPassword().trim().length() > 20) {
			errors.rejectValue("password", "length.loginForm.password",
					"Password length can not be more than 20.");
		}
	}
}
