/**
 * 
 */
package com.nervytech.mailer24x7.spring.form;

import java.util.List;

import com.nervytech.mailer24x7.model.domains.Campaign;

/**
 * @author bsikkaya
 *
 */
public class HomeBean {
   
	private List<Campaign> draftCampaigns;
	private List<Campaign> scheduledCampaigns;
	private List<Campaign> completedCampaigns;
	
	private boolean draftsNotEmpty;
	private boolean isScheduledNotEmpty;
	private boolean isCompletedNotEmpty;
	
	private String orgId;
	
	public List<Campaign> getDraftCampaigns() {
		return draftCampaigns;
	}
	public void setDraftCampaigns(List<Campaign> draftCampaigns) {
		this.draftCampaigns = draftCampaigns;
	}
	public List<Campaign> getScheduledCampaigns() {
		return scheduledCampaigns;
	}
	public void setScheduledCampaigns(List<Campaign> scheduledCampaigns) {
		this.scheduledCampaigns = scheduledCampaigns;
	}
	public List<Campaign> getCompletedCampaigns() {
		return completedCampaigns;
	}
	public void setCompletedCampaigns(List<Campaign> completedCampaigns) {
		this.completedCampaigns = completedCampaigns;
	}
	
	public boolean getDraftsNotEmpty() {
		return draftsNotEmpty;
	}
	public void setDraftsNotEmpty(boolean draftsNotEmpty) {
		this.draftsNotEmpty = draftsNotEmpty;
	}
	public boolean isScheduledNotEmpty() {
		return isScheduledNotEmpty;
	}
	public void setScheduledNotEmpty(boolean isScheduledNotEmpty) {
		this.isScheduledNotEmpty = isScheduledNotEmpty;
	}
	public boolean isCompletedNotEmpty() {
		return isCompletedNotEmpty;
	}
	public void setCompletedNotEmpty(boolean isCompletedNotEmpty) {
		this.isCompletedNotEmpty = isCompletedNotEmpty;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
}
