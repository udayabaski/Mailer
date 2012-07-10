package com.nervytech.mailer24x7.spring.controllers;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nervytech.mailer24x7.common.enums.CampaignStatusEnum;
import com.nervytech.mailer24x7.model.dao.CampaignDAO;
import com.nervytech.mailer24x7.model.domains.Campaign;
import com.nervytech.mailer24x7.spring.auth.SessionUser;
import com.nervytech.mailer24x7.spring.form.HomeBean;

@Controller
@RequestMapping("/usr/home.form")
public class HomeController {

	private Map<String, String> countryMap;
	
	@Autowired
	private CampaignDAO campaignDAO;

	@RequestMapping(method = RequestMethod.GET)
	public String showCampaigns(Map model, HttpServletRequest request) {
		
		SessionUser userDetails = UserDetailsServiceImpl.currentUserDetails();
		String orgId = userDetails.getOrgId()+"";
		
		HomeBean homeBean = new HomeBean();
		
		int draftStatus = CampaignStatusEnum.DRAFT.getStatus();
		int scheduledStatus = CampaignStatusEnum.SCHEDULED.getStatus();
		int scheduledNow = CampaignStatusEnum.NOW.getStatus();
		int compaletedStatus = CampaignStatusEnum.COMPLETED.getStatus();
		
		// TODO : Need to alter the Query to make a single call instead of 3 .......
		List<Campaign> draftCampaigns = campaignDAO.getCampaigns(Long.parseLong(orgId),draftStatus);
		List<Campaign> scheduledCampaigns = campaignDAO.getLaterAndNowScheduledCampaigns(Long.parseLong(orgId),scheduledStatus,scheduledNow);
		List<Campaign> completedCampaigns = campaignDAO.getCampaigns(Long.parseLong(orgId),compaletedStatus);
		
		homeBean.setDraftCampaigns(draftCampaigns);
		homeBean.setScheduledCampaigns(scheduledCampaigns);
		homeBean.setCompletedCampaigns(completedCampaigns);
		
		if(draftCampaigns != null && draftCampaigns.size() >0)
		{
			homeBean.setDraftsNotEmpty(true);
		}
		
		homeBean.setOrgId(orgId);
		model.put("homeBean", homeBean);

		return "home";
	}

	
	@RequestMapping(params = "action=viewDrafts",method = RequestMethod.GET)
	public String showDraftCampaigns(@RequestParam String orgId,Map model,HttpServletRequest request) {
		
		HomeBean homeBean = new HomeBean();
		
		int status = CampaignStatusEnum.DRAFT.getStatus();
		
		List<Campaign> draftCampaigns = campaignDAO.getCampaigns(Long.parseLong(orgId),status);

		homeBean.setDraftCampaigns(draftCampaigns);
		model.put("homeBean", homeBean);

		return "home";
	}

	@RequestMapping(params = "action=viewScheduled",method = RequestMethod.GET)
	public String showScheduledCampaigns(@RequestParam String orgId,Map model, HttpServletRequest request) {
		
		HomeBean homeBean = new HomeBean();
		
		int status = CampaignStatusEnum.SCHEDULED.getStatus();
		
		List<Campaign> scheduledCampaigns = campaignDAO.getCampaigns(Long.parseLong(orgId),status);

		homeBean.setScheduledCampaigns(scheduledCampaigns);
		model.put("homeBean", homeBean);

		return "home";
	}
	
	@RequestMapping(params = "action=viewCompleted",method = RequestMethod.GET)
	public String showCompletedCampaigns(@RequestParam String orgId,Map model, HttpServletRequest request) {
		
		HomeBean homeBean = new HomeBean();
		
		int status = CampaignStatusEnum.COMPLETED.getStatus();
		
		List<Campaign> completedCampaigns = campaignDAO.getCampaigns(Long.parseLong(orgId),status);

		homeBean.setCompletedCampaigns(completedCampaigns);
		model.put("homeBean", homeBean);

		return "home";
	}	
	@RequestMapping(params = "action=viewCampaign",method = RequestMethod.GET)
	public String viewCampaign(@RequestParam String campaignId,Map model, HttpServletRequest request) {
		
		HomeBean homeBean = new HomeBean();
		
		String imagaLoc = campaignDAO.getCampaignImageLoc(Long.parseLong(campaignId));

		// TODO : Need to get the campaign file and open it in a new window ....
		
		return "home";
	}

	@RequestMapping(params = "action=deleteCampaign",method = RequestMethod.GET)
	public String deleteCampaign(@RequestParam String campaignId,Map model, HttpServletRequest request) {
		
		
		campaignDAO.deleteCampaign(Long.parseLong(campaignId));

		//model.put("homeBean", homeBean);

		return "redirect:/usr/home.form";
	}

}
