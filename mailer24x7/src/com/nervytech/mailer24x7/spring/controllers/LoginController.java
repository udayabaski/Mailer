package com.nervytech.mailer24x7.spring.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.nervytech.mailer24x7.spring.form.LoginForm;

@Controller
@RequestMapping("/auth/loginform.form")
@SessionAttributes("user")
public class LoginController {

	@Autowired
	private LoginValidator loginValidation;

	/*@Autowired
	private UsersDAO usersDAO;*/

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Map model) {
		LoginForm loginForm = new LoginForm();
		//loginForm.setUserType("SEEKER"); // TODO : Validate if userType is valid(URL tampering may happen) and act upon ....
		model.put("loginForm", loginForm);
		return "login";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String authenticate(LoginForm loginForm, BindingResult result,
			ModelMap modelMap) {

		/*loginValidation.validate(loginForm, result);
		System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHHhh");
		if (result.hasErrors()) {
			return "login";
		}
		// loginForm = (LoginForm) model.get("loginForm");

		List<Users> users = null;
		Users user = null;
		UserTypes enumUserType = convertToEnumType(loginForm.getUserType());
		if (enumUserType == UserTypes.SEEKER) {
			users = usersDAO.checkAuthentication(loginForm.getUserName(),loginForm.getPassword());
			//users = usersDAO.findByUsername(loginForm.getUserName());
			if (users.size() <= 0){
				result.rejectValue("errorMessage", "invalid.loginForm.errorMessage","Oops!!! Invalid username or password.");
			  	return "login";
			}	
			user = (Users) users.get(0);
			modelMap.addAttribute("user", user);
			return "userhome";
		} else if (enumUserType == UserTypes.REFERRER) {
			List<Referrer> referrers = null;
			Referrer referrer = null;
			//referrers = referrerDAO.checkAuthentication(loginForm.getUserName(),loginForm.getPassword());
			if (referrers.size() <= 0) {
				result.rejectValue("errorMessage", "invalid.loginForm.errorMessage","Oops!!! Invalid username or password.");
				return "login";
			}	
			referrer = (Referrer) referrers.get(0);
			System.out.println("");
			modelMap.addAttribute("referrer", referrer);
			return "referrerhome";
		} else {
			return "welcome";
		}
*/
	return null;	
		}
	
}
