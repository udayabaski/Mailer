package com.nervytech.mailer24x7.spring.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nervytech.mailer24x7.common.enums.OrgStatusEnum;
import com.nervytech.mailer24x7.common.enums.UserRoleEnum;
import com.nervytech.mailer24x7.common.enums.UserStatusEnum;
import com.nervytech.mailer24x7.common.util.MailerUtil;
import com.nervytech.mailer24x7.mail.util.MailSender;
import com.nervytech.mailer24x7.model.dao.UsersDAO;
import com.nervytech.mailer24x7.model.domains.Organization;
import com.nervytech.mailer24x7.model.domains.Registration;
import com.nervytech.mailer24x7.model.domains.User;
import com.nervytech.mailer24x7.spring.auth.SessionUser;
import com.nervytech.mailer24x7.spring.form.LoginForm;
import com.nervytech.mailer24x7.spring.form.RegForm;

@Controller
@RequestMapping("/comn/regform.form")
public class RegistrationController {

	@Autowired
	private RegistrationValidator registrationValidation;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private SaltSource saltSource;

	@Autowired
	private UserDetailsService userDetailsService;

	@Resource(name = "countryDropDown")
	private Map<String, String> countryMap;
	
	@Autowired
	private UsersDAO usersDAO;
	
	@ModelAttribute("countryMap")
	public Map<String, String> populateCountryMap() {
		return this.countryMap;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String showForm(Map model, HttpServletRequest request) {
		//String userCountry = getCountryFromIpAddress(request.getRemoteAddr());
		RegForm regForm = new RegForm();
		//regForm.setCountry(userCountry);

		model.put("regForm", regForm);

		return "register";
	}
	
	@RequestMapping(params = "action=confirm",method = RequestMethod.GET)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String confirmUser(@RequestParam String id,Map model, HttpServletRequest request) {
		
		String userId = usersDAO.getUserId(id);
		
		usersDAO.enableUser(userId);
		
		usersDAO.deleteFromRegistration(userId);
		
		LoginForm loginForm = new LoginForm();
		loginForm.setGeneralMessage("The user is activated. You can login now.");
		model.put("loginForm", loginForm);
		return "login";
	}


	@RequestMapping(params = "action=send",method = RequestMethod.GET)
	public String sendConfirmationMail(@RequestParam String object,Map model, HttpServletRequest request) {
		
		String confirmationId = usersDAO.getConfirmationId(object);
		
		String confirmationUrl = MailerUtil.CONFIRMATION_MAIL_URL;
		confirmationUrl.replaceAll("<CONFIRM_ID>", confirmationId);
		
		List<User> users = usersDAO.getUserByUserId(object);
		
		User usr = users.get(0);
		
		String content = "Hi "+usr.getFullName()+"\n Please click the following link for activating your account.\n "+confirmationUrl;
		
		MailSender.sendMail(MailerUtil.CONFIRMATION_MAIL_FROM, usr.getEmailId() , MailerUtil.CONFIRMATION_MAIL_SUBJECT, content);
		
		RegForm regForm = new RegForm();
		
		regForm.setUserId(object);
		
		model.put("regForm", regForm);

		return "register";
	}

	
	// This is for AJAX ..........
	@RequestMapping(params = "action=checkOrg", method = RequestMethod.GET)
	public @ResponseBody
	String checkIfOrgExist(@RequestParam String orgName) {
	  List<Organization> checkOrg = usersDAO.findByOrgName(orgName);
	  if (checkOrg.size() > 0) {
			return "This company is already registered.";
	   }	
	  return "This company is not yet registered.";
	}

	@RequestMapping(method = RequestMethod.POST)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String registerUser(RegForm regForm,
			BindingResult result, Map model) {
		
		registrationValidation.validate(regForm, result);

		if (result.hasErrors()) {
			return "register";
		}

		List<Organization> checkOrg = usersDAO.findByOrgName(regForm.getOrganization());
		if (checkOrg.size() > 0) {
			result.rejectValue("organization",
					"NotEmpty.registrationForm.organization",
					"This Organization is already registered.");
			return "register";
		}
		
		List<User> users = usersDAO.getUser(regForm.getEmailId());
		if(users.size() > 0) {
			result.rejectValue("emailId",
					"NotEmpty.registrationForm.emailId",
					"This User is already exist.");
			return "register";
		}

		// seekerRegForm = (SeekerRegistrationForm)
		// model.get("seekerRegistrationForm");
		
		Date currentDate = new Date();
		
		Organization org = new Organization();
		//org.setAddress(regForm.getAddress());
		//org.setContactEmail(regForm.getOrgContactEmail());
		//org.setContactNumber(regForm.getOrgContactNumber());
		org.setCountry(regForm.getCountry());
		org.setCreatedEmail(regForm.getEmailId());
		
		org.setCreatedTime(MailerUtil.formatter.format(currentDate));
		
		org.setDisplayName(regForm.getOrganization());
		org.setOrgName(regForm.getOrganization());
		org.setSenderEmail(regForm.getSenderEmail());
		org.setStatus(OrgStatusEnum.ENABLED.ordinal());
		//org.setTimeZone(regForm.getTimeZone());
		org.setWebsite(regForm.getWebsite());  // TODO : Need to check if it is valid

		long orgId = usersDAO.saveOrganization(org);
		
		User user = new User();
		
		//user.setContactNumber(regForm.getContactNumber());
		user.setDisplayName(regForm.getFullName());
		user.setEmailId(regForm.getEmailId());
		user.setFullName(regForm.getFullName());
		//user.setLanguage(regForm.getLanguage());
		user.setOrgId(orgId);
		user.setPassword(regForm.getPassword());
		user.setRole(UserRoleEnum.ADMIN.getRole());   
		user.setStatus(UserStatusEnum.DISABLED.getStatus());  
		//user.setTimeZone(regForm.getTimeZone());
		user.setCreatedTime(MailerUtil.formatter.format(currentDate));
		
		// SHA Password ........

		long userId = usersDAO.saveUser(user);
		
		user.setUserId(userId);
		
		System.out.println("User Saved ===========>>>>>>>>");
		
		UserDetails userDetails =
	          userDetailsService.loadUserByUsername(regForm.getEmailId());
		
		user.setPassword(passwordEncoder.encodePassword(regForm.getPassword(),saltSource.getSalt(userDetails)));
		
		usersDAO.updateEncodedPassword(user);
		
		Registration reg = new Registration();
		reg.setUserId(userId);
		reg.setConfirmationId(UUID.randomUUID().toString()+System.currentTimeMillis());
		reg.setCreatedTime(MailerUtil.formatter.format(currentDate));
		
		usersDAO.insertRegistration(reg);
		
		String confirmationUrl = MailerUtil.CONFIRMATION_MAIL_URL;
		confirmationUrl = confirmationUrl.replaceAll("CONFIRM_ID", reg.getConfirmationId());
		
		String content = "Hi "+regForm.getFullName()+"\n Please click the following link for activating your account.\n "+confirmationUrl;
		
		MailSender.sendMail(MailerUtil.CONFIRMATION_MAIL_FROM, regForm.getEmailId() , MailerUtil.CONFIRMATION_MAIL_SUBJECT, content);
		
        /* autoLogin(regForm.getEmailId(),regForm.getPassword(),user);  

		return "redirect:/usr/home.form"; */
		regForm = new RegForm();
		
		regForm.setUserId(userId+"");
		
		model.put("regForm", regForm);

		return "register";
	}

	/**
	 * Automatic login after successful registration.
	 * 
	 * @param password
	 * @param user
	 * 
	 * @param username
	 * @param users
	 */
	//@Transactional(propagation = Propagation.REQUIRES_NEW)
	private boolean autoLogin(String username,String password,User user) {
		try {
			Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			authorities.add(new GrantedAuthorityImpl("ADMIN"));

			//org.springframework.security.core.userdetails.User springUser = new org.springframework.security.core.userdetails.User(username, password, true, true, true, true, authorities);
			
			SessionUser springUser = new SessionUser(username,user.getPassword(),authorities,user.getOrgId(),user.getUserId(),true);
			
			Authentication authentication = new UsernamePasswordAuthenticationToken(
					springUser, springUser.getPassword(),
					springUser.getAuthorities());

			// Place the new Authentication object in the security context.
			SecurityContextHolder.getContext()
					.setAuthentication(authentication);
		} catch (Exception e) {
			e.printStackTrace();
			SecurityContextHolder.getContext().setAuthentication(null);
			// logger.error("Exception", e);
			return false;
		}
		return true;
	}

	public static void main(String args[]) {
		System.out.println("2222222222222 "+(UUID.randomUUID().toString()+System.currentTimeMillis()).length());
	}
}
