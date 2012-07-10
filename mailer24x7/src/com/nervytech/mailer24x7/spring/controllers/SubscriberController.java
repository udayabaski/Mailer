package com.nervytech.mailer24x7.spring.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.nervytech.mailer24x7.common.enums.SubscriberStatusEnum;
import com.nervytech.mailer24x7.common.util.MailerUtil;
import com.nervytech.mailer24x7.model.dao.SubscriberDAO;
import com.nervytech.mailer24x7.model.domains.SubscriberIdStatus;
import com.nervytech.mailer24x7.model.domains.SubscriberList;
import com.nervytech.mailer24x7.spring.auth.SessionUser;
import com.nervytech.mailer24x7.spring.form.SubscriberForm;
import com.nervytech.mailer24x7.spring.form.SubscriberHomeBean;

@Controller
@RequestMapping("/usr/subscriber.form")
@SessionAttributes("user")
public class SubscriberController {

	@Autowired
	private SubscriberDAO subscriberDAO;
	
	@RequestMapping(params = "action=new",method = RequestMethod.GET)
	public String newSubGroup(Map model, HttpServletRequest request) {
		
		SubscriberForm subForm = new SubscriberForm();
		
		subForm.setAddOption("CSV");
		subForm.setAction("save");
		
		model.put("subscriberForm", subForm);

		return "subscribergroup";
	}
	
	@RequestMapping(params = "action=save",method = RequestMethod.POST)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String saveSubscriberGroup(SubscriberForm subForm,BindingResult result,Map model, HttpServletRequest request) {
		
		if(subForm.getSubscriberName() ==null || subForm.getSubscriberName().trim().length() == 0) {
			result.rejectValue("subscriberName",
					"NotEmpty.subscriberForm.subscriberName",
					"Subscriber Name must not be empty.");
			
			return "subscribergroup";
		}
		
		SessionUser userDetails = UserDetailsServiceImpl.currentUserDetails();
		long orgId = userDetails.getOrgId();
		long userId = userDetails.getUserId();
		
		InputStream inputStream = null;
		String[] subscibersArray = null;
		if (subForm.getAddOption().equals("CSV")) {
			try {
				CommonsMultipartFile file = subForm.getFileData();

				if(file.getOriginalFilename() == null || file.getOriginalFilename().trim().length() == 0){
					result.rejectValue("fileData",
							"NotEmpty.subscriberForm.fileData",
							"File must be uploaded.");
					
				    return "subscribergroup";
				}
				if(file.getOriginalFilename().toLowerCase().lastIndexOf(".csv") == -1 && file.getOriginalFilename().toLowerCase().lastIndexOf(".txt") == -1 ){
					result.rejectValue("fileData",
							"NotEmpty.subscriberForm.fileData",
							"Unsupported file type.");
					
				    return "subscribergroup";
				}
				if (file.getSize() > 0) {
					inputStream = file.getInputStream();
					if (file.getSize() > MailerUtil.maxFileSize) {
						System.out.println("File Size:::" + file.getSize());
						result.rejectValue("fileData",
									"NotEmpty.subscriberForm.fileData",
									"File size can not be more than "+file.getSize()+" Bytes.");
							
						return "subscribergroup";
					}
					String subscribers = IOUtils.toString(inputStream);
					subscibersArray = subscribers
							.split(MailerUtil.subscriberSeparator);
				} else {
					result.rejectValue("fileData",
							"NotEmpty.subscriberForm.fileData",
							"File data is empty.");
					
				    return "subscribergroup";
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} else if(subForm.getAddOption().equals("Manual")) {
			String subscribers = subForm.getSubscribers();
			if(subscribers == null || subscribers.trim().length() == 0) {
				result.rejectValue("subscribers",
						"NotEmpty.subscriberForm.subscribers",
						"Subscribers must not be empty.");
				
			    return "subscribergroup";
			}
			subscibersArray = subscribers
					.split(MailerUtil.subscriberSeparator);
		}
		
		SubscriberList subList = new SubscriberList();
		subList.setCreatedTime(MailerUtil.formatter.format(new Date()));
		subList.setLastModifiedTime(MailerUtil.formatter.format(new Date()));
		subList.setOrgId(orgId);
		subList.setSubscriberListName(subForm.getSubscriberName());
		subList.setUserId(userId);
		
		subList.setActiveCount(subscibersArray.length); // TODO : Need to check with Arun as we are setting to Active by default ....
		
		long subscriberListId = subscriberDAO.addSubGroup(subList);
		
		if(subscibersArray.length >= 1)
		{
		 int status = SubscriberStatusEnum.ACTIVE.getStatus();
		 subscriberDAO.addBatchSubscriber(subscriberListId, userId, orgId, status, subscibersArray);
		}
		
		if(subForm.getToPage() !=null && subForm.getToPage().equals("campaign")) {
			return "redirect:campaign.form?action=new";
		}

		return "redirect:subscriber.form?action=viewSubGroups";
	}

	
	@RequestMapping(params = "action=viewSubGroups", method = RequestMethod.GET)
	public String viewSubGroups(Map model, HttpServletRequest request) {
		
		SessionUser userDetails = UserDetailsServiceImpl.currentUserDetails();
		long orgId = userDetails.getOrgId();
		long userId = userDetails.getUserId();
		
		SubscriberHomeBean subscriberHomeBean = new SubscriberHomeBean();
		
		List<SubscriberList> subscriberGroups = subscriberDAO.getSubscriberGroups(orgId+"");

		subscriberHomeBean.setSubscriberList(subscriberGroups);
		
		if(subscriberGroups != null && subscriberGroups.size() >0)
		{
			subscriberHomeBean.setSubscriberListNotEmpty(true);
		}
		
		model.put("subscriberHomeBean", subscriberHomeBean);

		return "viewsubgroups";
		
	}

	@RequestMapping(params = "action=deleteGroup", method = RequestMethod.GET)
	public String deleteSubGroups(@RequestParam String object,Map model, HttpServletRequest request) {
	
		subscriberDAO.deleteSubGroup(object);

		return "redirect:subscriber.form?action=viewSubGroups";  
	}
	
	
	// TODO : Improve the performance by converting it to a Single query ...
	@RequestMapping(params = "action=showGroup", method = RequestMethod.GET)
	public String showGroup(@RequestParam String object,Map model, HttpServletRequest request) {
		
		SubscriberHomeBean subscriberHomeBean = new SubscriberHomeBean();
		
		List<SubscriberList> subscriberGroups = subscriberDAO.getSubscriberGroup(object);
		
		int activeStatus = SubscriberStatusEnum.ACTIVE.getStatus(); 
		List<SubscriberIdStatus> activeSubscribers = subscriberDAO.getSubscribers(object, activeStatus);
		
		int unsubscribedStatus = SubscriberStatusEnum.UNSUBSCRIBED.getStatus();  
		List<SubscriberIdStatus> unsubscribedSubscribers = subscriberDAO.getSubscribers(object, unsubscribedStatus);

		int bouncedStatus = SubscriberStatusEnum.BOUNCED.getStatus();		
		List<SubscriberIdStatus> bouncedSubscribers = subscriberDAO.getSubscribers(object, bouncedStatus);


		subscriberHomeBean.setSubscriberGroup(subscriberGroups.get(0));
		
		subscriberHomeBean.setActiveSubscribers(activeSubscribers);
		subscriberHomeBean.setUnsubscribedSubscribers(unsubscribedSubscribers);
		subscriberHomeBean.setBouncedSubscribers(bouncedSubscribers);	
		
		if(activeSubscribers != null && activeSubscribers.size() >0)
		{
			subscriberHomeBean.setSubscriberListNotEmpty(true);
		}
		
		model.put("subscriberHomeBean", subscriberHomeBean);

		System.out.println("SENDING TO GROUPDETAILSSSSSSSSSSSSSSSSSSSSSS");
		return "viewgroupdetails";
	}
	
	@RequestMapping(params = "action=deleteSubscriber", method = RequestMethod.GET)
	public String deleteSubscriber(@RequestParam String object,@RequestParam String group,Map model, HttpServletRequest request) {
	
		subscriberDAO.deleteSubscriber(object); // TODO : Need to get the list of subscribers from the form and make it comma separated before passing and get the count.

		// TODO : Need to reduce the corresponding count from the subscriber_list table .....
		
		return "redirect:subscriber.form?action=showGroup&object="+group;  // TODO : Need to set org ID ...
	}

	@RequestMapping(params = "action=addSubscriber", method = RequestMethod.GET)
	public String addSubscriber(@RequestParam String object,Map model, HttpServletRequest request) {
	
		subscriberDAO.deleteSubscriber(object); // TODO : Need to get the list of subscribers from the form and make it comma separated before passing and get the count.

		// TODO : Need to reduce the corresponding count from the subscriber_list table .....
		
		return "redirect:/usr/profile.form?action=viewUsers&orgId=";  // TODO : Need to set org ID ...
	}

}
