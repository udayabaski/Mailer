package com.nervytech.mailer24x7.spring.controllers;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nervytech.mailer24x7.model.dao.UsersDAO;
import com.nervytech.mailer24x7.model.domains.User;
import com.nervytech.mailer24x7.spring.auth.SessionUser;
import com.nervytech.mailer24x7.spring.form.UserBean;
import com.nervytech.mailer24x7.spring.form.UserForm;

@Controller
@RequestMapping("/usr/profile.form")
public class ProfileController {

	@Autowired
	private ProfileValidator profileValidation;
	
	private Map<String, String> countryMap;
	
	@Autowired
	private UsersDAO usersDAO;
	
	@Resource(name = "roleDropDown")
	private Map<Integer, String> roleMap;

	@ModelAttribute("roleMap")
	public Map<Integer, String> roleMap() {
		return this.roleMap;
	}
	
	@RequestMapping(params = "action=view",method = RequestMethod.GET)
	public String viewUsers(Map model, HttpServletRequest request) {
		
		SessionUser userDetails = UserDetailsServiceImpl.currentUserDetails();
		String orgId = userDetails.getOrgId()+"";
		long userId = userDetails.getUserId();
		
		UserBean userBean = new UserBean();
		
		List<User> users = usersDAO.getUsers(orgId);

		userBean.setUsers(users);
		
		model.put("userBean", userBean);

		return "viewusers";
	}
	
	
	@RequestMapping(params = "action=save",method = RequestMethod.POST)
	public String saveUser(UserForm userForm,BindingResult result,Map model, HttpServletRequest request) {
		
		profileValidation.validate(userForm, result);

		if (result.hasErrors()) {
			return "newuser";
		}

		
		SessionUser userDetails = UserDetailsServiceImpl.currentUserDetails();
		long orgId = userDetails.getOrgId();
		
		User usr = new User();
		usr.setContactNumber(userForm.getContactNumber());
		usr.setDisplayName(userForm.getDisplayName());
		usr.setEmailId(userForm.getEmailId());
		usr.setFullName(userForm.getFullName());
		usr.setLanguage(userForm.getLanguage());
		usr.setOrgId(orgId);
		usr.setPassword(userForm.getPassword());
		usr.setRole(Integer.parseInt(userForm.getRole()));
		//usr.setStatus();
		usr.setTimeZone(userForm.getTimeZone());
		
		usersDAO.saveUser(usr);

		return "redirect:profile.form?action=view";
	}

	@Secured("ADMIN")
	@RequestMapping(params = "action=new",method = RequestMethod.GET)
	public String newUser(Map model, HttpServletRequest request) {
		
		UserForm usrForm = new UserForm();
		
		usrForm.setAction("save");
		
		model.put("profileForm", usrForm);

		return "newuser";
	}


	@RequestMapping(params = "action=delete",method = RequestMethod.GET)
	public String deleteUser(@RequestParam String object,Map model, HttpServletRequest request) {
		
		usersDAO.deleteUser(object);

		return "redirect:profile.form?action=view";  // TODO : Need to set org ID ...
	}
	
	@RequestMapping(params = "action=edit",method = RequestMethod.GET)
	public String editUser(@RequestParam String object,Map model, HttpServletRequest request) {

		List<User> users = usersDAO.getUserByUserId(object);

		// TODO : Could be a URL tampering ..... Also need to check ORGID
		if(users == null || users.size() == 0)
		{
			return "redirect:home.form";
		}
		else
		{
			User usr = users.get(0);
			UserForm usrForm = new UserForm();

			usrForm.setContactNumber(usr.getContactNumber());
			usrForm.setDisplayName(usr.getDisplayName());
			usrForm.setEmailId(usr.getEmailId());
			usrForm.setFullName(usr.getFullName());
			usrForm.setLanguage(usr.getLanguage());
			usrForm.setRole(usr.getRole()+"");
			usrForm.setTimeZone(usr.getTimeZone());
			usrForm.setUserId(object);
			
			usrForm.setAction("update");
			
			model.put("profileForm", usrForm);
			
			return "newuser";	
		}

	}
	
	@RequestMapping(params = "action=update",method = RequestMethod.POST)
	public String updateUser(@Validated UserForm userForm,
			BindingResult result, Map model,ModelMap modelMap) {
		
		profileValidation.validate(userForm, result);

		if (result.hasErrors()) {
			return "newuser";
		}
	
		User usr = new User();
		usr.setUserId(Long.parseLong(userForm.getUserId()));
		usr.setEmailId(userForm.getEmailId());
		usr.setDisplayName(userForm.getDisplayName());
		usr.setRole(Integer.parseInt(userForm.getRole()));
		usr.setContactNumber(userForm.getContactNumber());
		usr.setFullName(userForm.getFullName());
		usr.setLanguage(userForm.getLanguage());
		usr.setTimeZone(userForm.getTimeZone());

		usersDAO.updateUser(usr);
		
		return "redirect:profile.form?action=view"; // TODO : Need to set .....
	}

	@RequestMapping(params = "action=updatePassword",method = RequestMethod.GET)
	public String updatePassword(@Validated UserForm userForm,
			BindingResult result, Map model,ModelMap modelMap) {
		
		if (result.hasErrors()) {
			return "home";
		}
		
		// TODO : Need to validate the old password ...
		User usr = new User();
		usr.setUserId(Long.parseLong(userForm.getUserId()));
		usr.setOrgId(Long.parseLong(userForm.getOrgId()));
		usr.setPassword(userForm.getPassword());

		usersDAO.updateUser(usr);
		
		return "redirect:/usr/profile.form?userId="; // TODO : Need to set .....
	}
	
}
