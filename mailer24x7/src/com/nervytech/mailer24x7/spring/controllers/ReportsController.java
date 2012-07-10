package com.nervytech.mailer24x7.spring.controllers;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.nervytech.mailer24x7.spring.auth.SessionUser;

@Controller
@RequestMapping("/usr/reports.form")
public class ReportsController {

	@RequestMapping(params = "action=view",method = RequestMethod.GET)
	public String viewUsers(Map model, HttpServletRequest request) {
		
		SessionUser userDetails = UserDetailsServiceImpl.currentUserDetails();
		String orgId = userDetails.getOrgId()+"";
		long userId = userDetails.getUserId();
		
		return "viewreport";
	}
	
}
