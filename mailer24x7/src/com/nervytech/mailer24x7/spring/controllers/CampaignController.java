package com.nervytech.mailer24x7.spring.controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.nervytech.mailer24x7.client.exception.MailerException;
import com.nervytech.mailer24x7.common.enums.CampaignStatusEnum;
import com.nervytech.mailer24x7.common.util.MailerUtil;
import com.nervytech.mailer24x7.mail.util.MailSender;
import com.nervytech.mailer24x7.model.dao.CampaignDAO;
import com.nervytech.mailer24x7.model.dao.SubscriberDAO;
import com.nervytech.mailer24x7.model.domains.Campaign;
import com.nervytech.mailer24x7.model.domains.CampaignStatus;
import com.nervytech.mailer24x7.model.domains.SubscriberList;
import com.nervytech.mailer24x7.spring.auth.SessionUser;
import com.nervytech.mailer24x7.spring.form.CampaignForm;
import com.nervytech.mailer24x7.spring.form.HomeBean;
import com.nervytech.mailer24x7.spring.form.SubscriberForm;

@Controller
@RequestMapping("/usr/campaign.form")
public class CampaignController {

	@Autowired
	private CampaignValidator campaignValidation;
	
	//@Resource(name = "subscriberGroupDropDown")
	private Map<Long, String> subscriberGroupMap;
	
	@Autowired
	private CampaignDAO campaignDAO;
	
	@Autowired
	private SubscriberDAO subscriberDAO;
	
	@ModelAttribute("subscriberGroupMap")
	public Map<Long, String> populateSubscriberGroupMap() {
		//if (this.subscriberGroupMap == null) {
			
			SessionUser userDetails = UserDetailsServiceImpl.currentUserDetails();
			String orgId = userDetails.getOrgId()+"";
			
			subscriberGroupMap = new LinkedHashMap<Long, String>();
			subscriberGroupMap.put(-1l, "Select Subscriber");
			List<SubscriberList> subGroups = subscriberDAO.getSubscriberGroups(orgId);
			for(SubscriberList subList:subGroups) {
				subscriberGroupMap.put(subList.getSubscriberListId(), subList.getSubscriberListName());
			}
		//}
		return this.subscriberGroupMap;
	}
	
	@RequestMapping(params = "action=new",method = RequestMethod.GET)
	public String newCampaign(Map model, HttpServletRequest request) {
		
		SessionUser userDetails = UserDetailsServiceImpl.currentUserDetails();
		String orgId = userDetails.getOrgId()+"";
		
		if(subscriberGroupMap !=null && subscriberGroupMap.size() == 1) { // Size check is 1 since we add "Select Subscriber by default ..."
			
			SubscriberForm subForm = new SubscriberForm();
			
			subForm.setAddOption("CSV");
			subForm.setAction("save");
			subForm.setToPage("campaign");
			
			model.put("subscriberForm", subForm);

			return "subscribergroup";

		}
		
		CampaignForm campForm = new CampaignForm();
		
		campForm.setContentType("RTE");
		campForm.setSendingOption(CampaignStatusEnum.NOW.toString());
		campForm.setAction("saveorupdate");
		campForm.setCampaignId(-1);
		
		model.put("campaignForm", campForm);

		return "campaign";
	}
	
	// This is for AJAX ..........
	@RequestMapping(params = "action=sendTestMail", method = RequestMethod.GET)
	public @ResponseBody
	String sendTestMail1(@RequestParam String emailId) {
		return "Mail Sent";
	}

	@RequestMapping(params = "action=sendTestMail", method = RequestMethod.POST)
	String sendTestMail(CampaignForm campaignForm,BindingResult result,Map model, HttpServletRequest request) {

		campaignValidation.validate(campaignForm, result);

		if (result.hasErrors()) {
			return "campaign";
		}

		SessionUser userDetails = UserDetailsServiceImpl.currentUserDetails();
		long orgId = userDetails.getOrgId();
		long userId = userDetails.getUserId();
		
		String content = null;
		
		if(campaignForm.getContentType().equals("IHTML"))
		{	
			InputStream inputStream = null;
			try {
				CommonsMultipartFile file = campaignForm.getFileData();
				if(file.getOriginalFilename() == null || file.getOriginalFilename().trim().length() == 0){
					result.rejectValue("fileData",
							"NotEmpty.campaignForm.fileData",
							"File must be uploaded.");
					
				    return "campaign";
				}
				if(file.getOriginalFilename().toLowerCase().lastIndexOf(".html") == -1){
					result.rejectValue("fileData",
							"NotEmpty.campaignForm.fileData",
							"Unsupported file type.");
					
				    return "campaign";
				}
				if (file.getSize() > 0) {
					inputStream = file.getInputStream();
					if (file.getSize() > MailerUtil.maxFileSize) {
						result.rejectValue("fileData",
								"NotEmpty.campaignForm.fileData",
								"File size can not be more than "+file.getSize()+" Bytes.");
						
					    return "campaign";
					}
					System.out.println("size::" + file.getSize());
					
					BufferedReader br = new BufferedReader(
							new InputStreamReader(inputStream));

					StringBuilder sb = new StringBuilder();

					String line;
					while ((line = br.readLine()) != null) {
						sb.append(line);
					}
   	
					content = sb.toString();
					
				} else {
					result.rejectValue("fileData",
							"NotEmpty.campaignForm.fileData",
							"File data is empty.");
					
				    return "campaign";
				}

			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally {
			  if(inputStream !=null)
			  {
				 try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			  }
			}
		} else if(campaignForm.getContentType().equals("RTE")) {

			content = "<html><body>"+campaignForm.getEditorContent()+"<html><body>";
			
		}
		
		try {
		  MailSender.sendMail(MailerUtil.CONFIRMATION_MAIL_FROM, campaignForm.getTestMailId() , MailerUtil.CONFIRMATION_MAIL_SUBJECT, content);
		  campaignForm.setTestMailStatus("Test Mail was sent successfully.");
		  
		} catch (Exception e) {
			e.printStackTrace();
			campaignForm.setTestMailStatus("Unable to send the Test mail. "+e.getMessage());
		}

		return "campaign";
	}
	
	@RequestMapping(params = "action=saveorupdate",method = RequestMethod.POST)
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public String saveOrUpdateCampaign(CampaignForm campaignForm,BindingResult result,Map model, HttpServletRequest request) throws MailerException {
		
		campaignValidation.validate(campaignForm, result);

		if (result.hasErrors()) {
			return "campaign";
		}
		
		SessionUser userDetails = UserDetailsServiceImpl.currentUserDetails();
		long orgId = userDetails.getOrgId();
		long userId = userDetails.getUserId();
		String emailId = userDetails.getUsername();
		
		String fileName = "";
		
		fileName = campaignForm.getFilename();
		System.out.println("File Name is  "+fileName);
		
		if(fileName == null || fileName.trim().length() == 0) {
			fileName = MailerUtil.HTML_DIRECTORY+orgId+"\\\\"+userId;
			//File.separator+System.currentTimeMillis()+".html";
			System.out.println("File Name111111111 is  "+fileName);
			File f = new File(fileName);
			try {
				if (!f.exists()) {
					f.mkdirs();
				}
				fileName = fileName + "\\\\" + System.currentTimeMillis()
						+ ".html";
				f = new File(fileName);
				f.createNewFile();
				System.out.println("CreatedDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
			} catch (IOException e) {
				e.printStackTrace();
				throw new MailerException("Unable to create a file.", e);
			}
		}
		
		if(campaignForm.getContentType().equals("IHTML"))
		{	
			InputStream inputStream = null;
			OutputStream outputStream = null;
			try {
				CommonsMultipartFile file = campaignForm.getFileData();
				if(file.getOriginalFilename() == null || file.getOriginalFilename().trim().length() == 0){
					result.rejectValue("fileData",
							"NotEmpty.campaignForm.fileData",
							"File must be uploaded.");
					
				    return "campaign";
				}
				if(file.getOriginalFilename().toLowerCase().lastIndexOf(".html") == -1){
					result.rejectValue("fileData",
							"NotEmpty.campaignForm.fileData",
							"Unsupported file type.");
					
				    return "campaign";
				}
				if (file.getSize() > 0) {
					inputStream = file.getInputStream();
					if (file.getSize() > MailerUtil.maxFileSize) {
						result.rejectValue("fileData",
								"NotEmpty.campaignForm.fileData",
								"File size can not be more than "+file.getSize()+" Bytes.");
						
					    return "campaign";
					}
					System.out.println("size::" + file.getSize());
					
					outputStream = new FileOutputStream(fileName);

					int readBytes = 0;
					byte[] buffer = new byte[1024];
					while ((readBytes = inputStream.read(buffer, 0, 1024)) != -1) {
						outputStream.write(buffer, 0, readBytes);
					}
					
				} else {
					result.rejectValue("fileData",
							"NotEmpty.campaignForm.fileData",
							"File data is empty.");
					
				    return "campaign";
				}

			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally {
			  if(inputStream !=null)
			  {
				 try {
					inputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			  }
			  if(outputStream !=null)
			  {
				  try {
					outputStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
			}
		} else if(campaignForm.getContentType().equals("RTE")) {
			String editorContent = "<html><body>"+campaignForm.getEditorContent()+"<html><body>";
			
			FileWriter fstream = null;
			BufferedWriter out = null;
			try {
				// Create file
				fstream = new FileWriter(fileName);
				out = new BufferedWriter(fstream);
				out.write(editorContent);
				
			} catch (Exception e) {// Catch exception if any
				e.printStackTrace();
			} finally {
				if(out !=null)
					try {
						out.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		}

				
		Campaign cmpn = new Campaign();
		
		//cmpn.setCampaignLink(campaignLink);
		cmpn.setCampaignName(campaignForm.getCampaignName());
		cmpn.setCreatedEmailId(emailId);
		
		cmpn.setCreatedTime(MailerUtil.formatter.format(new Date()));
		cmpn.setLastModifiedTime(MailerUtil.formatter.format(new Date()));
		cmpn.setImageLocation(fileName); // TODO : Need to append campaign ID and must be unique ....
		//cmpn.setIsPoweredBy(isPoweredBy);
		//cmpn.setScheduledOn(scheduledOn);
		cmpn.setSubject(campaignForm.getSubject());
		cmpn.setSubscriberListId(Long.parseLong(campaignForm.getSubscriberGroup()));
		//cmpn.setUnsubscribeLink(unsubscribeLink);
		//cmpn.setUnsubscribeSubject(unsubscribeSubject);
		cmpn.setUserId(userId);
		cmpn.setOrgId(orgId);

		long campaignId = campaignForm.getCampaignId();
		System.out.println("CAMPAIGNIDDDDDDDDDDDDDDDD "+campaignForm.getCampaignId());
		
		CampaignStatus cmpnStatus = new CampaignStatus();
		cmpnStatus.setLatestSubscriberCount("0"); // Setting it to default ...
		cmpnStatus.setCampaignId(campaignId);
		cmpnStatus.setOrgId(orgId);
		cmpnStatus.setScheduledOn(MailerUtil.formatter.format(new Date())); // TODO : Need to handle ...
		if(campaignForm.getSendingOption().equals(CampaignStatusEnum.NOW.toString())){
		  cmpnStatus.setStatus(CampaignStatusEnum.NOW.getStatus());	
		} else if(campaignForm.getSendingOption().equals("LATER")){
		  cmpnStatus.setStatus(CampaignStatusEnum.SCHEDULED.getStatus());	
		} else if(campaignForm.getSendingOption().equals(CampaignStatusEnum.DRAFT.toString())) {
		  cmpnStatus.setStatus(CampaignStatusEnum.DRAFT.getStatus());
		}
		cmpnStatus.setSubscriberListId(Long.parseLong(campaignForm.getSubscriberGroup()));
		cmpnStatus.setUserId(userId);

		if(campaignId != -1)
		{
		  cmpn.setCampaignId(campaignForm.getCampaignId());
		  campaignDAO.updateCampaign(cmpn);
		  campaignDAO.updateCampaignStatus(cmpnStatus);
		} else {
		  cmpn.setImageLocation(fileName);
		  campaignId = campaignDAO.saveCampaign(cmpn);
		  cmpnStatus.setCampaignId(campaignId);
		  campaignDAO.saveCampaignStatus(cmpnStatus);
		}
		
						
		return "redirect:home.form";
	}

	
	@RequestMapping(params = "action=edit",method = RequestMethod.GET)
	public String editCampaign(@RequestParam String object,Map model, HttpServletRequest request) throws MailerException {
		
		List<Campaign> campaignList = campaignDAO.getCampaign(Long.parseLong(object)); // TODO : Handle exception
		Campaign cmpn = campaignList.get(0);
		
		CampaignForm campForm = new CampaignForm();
		
		campForm.setCampaignName(cmpn.getCampaignName());
		campForm.setSubject(cmpn.getSubject());
		campForm.setContentType("RTE");
		campForm.setSendingOption(CampaignStatusEnum.values()[cmpn.getStatus()].toString());
		campForm.setAction("saveorupdate");
		campForm.setCampaignId(cmpn.getCampaignId());
		campForm.setSubscriberGroup(cmpn.getSubscriberListId()+"");
		campForm.setFilename(cmpn.getImageLocation());
		
		try {
			String editorContent = FileUtils.readFileToString(new File(cmpn.getImageLocation()));
			campForm.setEditorContent(editorContent);
		} catch (IOException e) {
			throw new MailerException("Exception occured while reading the editor content.",e);
		}
		
		model.put("campaignForm", campForm);

		return "campaign";
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

	@RequestMapping(params = "action=delete",method = RequestMethod.GET)
	public String deleteCampaign(@RequestParam String object,Map model, HttpServletRequest request) {
		
		
		campaignDAO.deleteCampaign(Long.parseLong(object));

		//model.put("homeBean", homeBean);

		return "redirect:/usr/home.form";
	}

	/*@Override
	public ModelAndView resolveException(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception exception) {
		Map<Object, Object> model = new HashMap<Object, Object>();
			        if (exception instanceof MaxUploadSizeExceededException){
			            model.put("errors", "File size should be less then "+
			            ((MaxUploadSizeExceededException)exception).getMaxUploadSize()+" byte.");
			        } else{
			            model.put("errors", "Unexpected error: " + exception.getMessage());
			        }
			        model.put("FORM", new UploadForm());
			        return new ModelAndView("/FileUploadForm", (Map) model);
	}*/
	
	public static void main(String args[]) {
       String fileName = "";
		
		if(fileName == null || fileName.trim().length() == 0) {
			fileName = MailerUtil.HTML_DIRECTORY+"1"+"\\\\"+"1";
			//File.separator+System.currentTimeMillis()+".html";
			System.out.println("File Name111111111 is  "+fileName);
			  File f = new File(fileName);
			  if(!f.exists()){
				  try {
					f.mkdirs();
					fileName = fileName+File.separator+System.currentTimeMillis()+".html";
					f = new File(fileName);
					f.createNewFile();
					System.out.println("CreatedDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			  }
		}
	}
}

