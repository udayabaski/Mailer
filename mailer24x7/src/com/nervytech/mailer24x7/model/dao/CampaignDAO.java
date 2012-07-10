package com.nervytech.mailer24x7.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.nervytech.mailer24x7.model.domains.Campaign;
import com.nervytech.mailer24x7.model.domains.CampaignStatus;


/**
 * A data access object (DAO) providing persistence and search support for Users
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.nervytech.refermejob.hibernate.artifact.Users
 * @author MyEclipse Persistence Tools
 */

@Resource(mappedName="campaignDAO")
public class CampaignDAO extends JdbcDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(CampaignDAO.class);
	// property constants
	public static final String EMAIL_ID = "emailId";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";

	// TODO : It is assumed that CAMPAIGN_NAME is UNIQUE
	public long saveCampaign(Campaign cmpn){
		// ASSUMPTION - ORG_ID AND CAMPAIGN_NAME IS THE COMBINED UNIQUE ID
		String insertQuery = "INSERT INTO CAMPAIGN (ORG_ID,CAMPAIGN_NAME,CREATED_EMAILID,CREATED_TIME,LAST_MODIFIED_TIME," +
				"ISPOWEREDBY,SUBJECT,IMAGE_LOC,CAMPAIGN_LINK,UNSUBSCRIBE_LINK,UNSUBSCRIBE_SUBJECT) " +
				                " VALUES('"+cmpn.getOrgId()+"','"+cmpn.getCampaignName()+"','"+cmpn.getCreatedEmailId()+"','"+cmpn.getCreatedTime()+"'," +
				                		"'"+cmpn.getLastModifiedTime()+"','"+cmpn.getIsPoweredBy()+"','"+cmpn.getSubject()+"','"+cmpn.getImageLocation()+"'," +
				                				"'"+cmpn.getCampaignLink()+"','"+cmpn.getUnsubscribeLink()+"','"+cmpn.getUnsubscribeSubject()+"')";
		System.out.println("INSERT Query ===>>> "+insertQuery);
		getJdbcTemplate().execute(insertQuery);	
		
		String selectQuery = "SELECT CAMPAIGN_ID FROM CAMPAIGN WHERE ORG_ID='"+cmpn.getOrgId()+"' and CAMPAIGN_NAME='"+cmpn.getCampaignName()+"'";
		return getJdbcTemplate().queryForLong(selectQuery);
	}
	
	public void saveCampaignStatus(CampaignStatus cmpnStatus){
		// ASSUMPTION - ORG_ID AND CAMPAIGN_NAME IS THE COMBINED UNIQUE ID
		String insertQuery = "INSERT INTO CAMPAIGN_STATUS (ORG_ID,USER_ID,CAMPAIGN_ID,SUBSCRIBER_LIST_ID,SCHEDULED_ON,STATUS,LATEST_SUBSCRIBER_COUNT) " +
					                " VALUES('"+cmpnStatus.getOrgId()+"','"+cmpnStatus.getUserId()+"','"+cmpnStatus.getCampaignId()+"','"+cmpnStatus.getSubscriberListId()+"'," +
				                				"'"+cmpnStatus.getScheduledOn()+"','"+cmpnStatus.getStatus()+"','"+cmpnStatus.getLatestSubscriberCount()+"')";
		System.out.println("INSERT Query ===>>> "+insertQuery);
		getJdbcTemplate().execute(insertQuery);	
		
	}
	
	// Assumption : ONE TO ONE MAPPING B/W CAMPAIN AND CAMPAIGN_STATUS
	public void updateCampaignStatus(CampaignStatus cmpnStatus){
		// ASSUMPTION - ORG_ID AND CAMPAIGN_NAME IS THE COMBINED UNIQUE ID
		String insertQuery = "UPDATE CAMPAIGN_STATUS SET SUBSCRIBER_LIST_ID="+cmpnStatus.getSubscriberListId()+",SCHEDULED_ON='"+cmpnStatus.getScheduledOn()+"'," +
				"STATUS="+cmpnStatus.getStatus()+" WHERE CAMPAIGN_ID="+cmpnStatus.getCampaignId();
		System.out.println("INSERT Query ===>>> "+insertQuery);
		getJdbcTemplate().execute(insertQuery);	
		
	}
	
	public void updateCampaign(Campaign cmpn){
		// ASSUMPTION - ORG_ID AND CAMPAIGN_NAME IS THE COMBINED UNIQUE ID
		String insertQuery = "UPDATE CAMPAIGN SET CAMPAIGN_NAME='"+cmpn.getCampaignName()+"',LAST_MODIFIED_TIME='"+cmpn.getLastModifiedTime()+"'," +
				"SUBJECT='"+cmpn.getSubject()+"',IMAGE_LOC='"+cmpn.getImageLocation()+"'";
		System.out.println("INSERT Query ===>>> "+insertQuery);
		getJdbcTemplate().execute(insertQuery);	
	}
	
	/*public void updateCampaign(final Campaign cmpn) {
		
		getJdbcTemplate().update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException{
		    // ASSUMPTION - CAMPAIGN_NAME CAN NEVER BE MODIFIED 
		    // TODO : CHECK WITH ARUN IF UNSUBSCRIBE_SUBJECT CAN BE MODIFIED
			String updateSql = "UPDATE CAMPAIGN set ISPOWEREDBY=?,SUBJECT=?,UNSUBSCRIBE_SUBJECT=? WHERE ORG_ID=? AND CAMPAIGN_NAME=?";
				
			PreparedStatement ps;
			ps = con.prepareStatement(updateSql);
			
			System.out.println("UPDATEINNNNNNNNNNNNNNGGGGGG "+updateSql);
			
			ps.setString(1, cmpn.getIsPoweredBy());
			ps.setString(2, cmpn.getSubject());
			ps.setString(3, cmpn.getUnsubscribeSubject());
			ps.setLong(4, cmpn.getOrgId());
			ps.setString(5, cmpn.getCampaignName());
			
        	 return ps;
		  }
		});
		
	}*/
	
	 // TODO : Need to consider the performance. As per the current approach, we fetch drafted campaign when the user select DRAFT, COMPLETED when user select COMPLETED ....
	 // Check if we need to fecth everything once and process in the front end ....
     public List<Campaign> getCampaigns(long orgId,int status) {
		
		RowMapper mapper = new RowMapper() { 
	         public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	          Campaign cmpn = new Campaign();	
	          
	          cmpn.setCampaignId(rs.getLong("CAMPAIGN_ID"));
	          cmpn.setCampaignName(rs.getString("CAMPAIGN_NAME"));
	          cmpn.setCreatedEmailId(rs.getString("CREATED_EMAILID"));
	          cmpn.setLastModifiedTime(rs.getString("LAST_MODIFIED_TIME"));
	          cmpn.setSubject(rs.getString("SUBJECT"));
	          cmpn.setOpened(rs.getInt("OPENED"));
	          cmpn.setBounced(rs.getInt("BOUNCED"));
	          cmpn.setClicked(rs.getInt("CLICKED"));
	          cmpn.setScheduledOn(rs.getString("SCHEDULED_ON"));
	          
	          return cmpn;
	         }
	     };	  
		
		String selectCampaignQuery = "SELECT C.CAMPAIGN_ID,C.CAMPAIGN_NAME,C.CREATED_EMAILID,C.LAST_MODIFIED_TIME,C.SUBJECT, CS.OPENED,CS.BOUNCED,CS.CLICKED," +
				" CS.SCHEDULED_ON FROM CAMPAIGN C,CAMPAIGN_STATUS CS " +
				" WHERE C.CAMPAIGN_ID=CS.CAMPAIGN_ID AND CS.STATUS="+status+" AND C.ORG_ID="+orgId; 

		System.out.println("MAIL ID Query ===>>>> "+selectCampaignQuery);
		return getJdbcTemplate().query(selectCampaignQuery, mapper);		
	}
     
     public List<Campaign> getLaterAndNowScheduledCampaigns(long orgId,int scheduledNow,int scheduledLater) {
 		
 		RowMapper mapper = new RowMapper() { 
 	         public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
 	          Campaign cmpn = new Campaign();	
 	          
 	          cmpn.setCampaignId(rs.getLong("CAMPAIGN_ID"));
 	          cmpn.setCampaignName(rs.getString("CAMPAIGN_NAME"));
 	          cmpn.setCreatedEmailId(rs.getString("CREATED_EMAILID"));
 	          cmpn.setLastModifiedTime(rs.getString("LAST_MODIFIED_TIME"));
 	          cmpn.setSubject(rs.getString("SUBJECT"));
 	          cmpn.setOpened(rs.getInt("OPENED"));
 	          cmpn.setBounced(rs.getInt("BOUNCED"));
 	          cmpn.setClicked(rs.getInt("CLICKED"));
 	          cmpn.setScheduledOn(rs.getString("SCHEDULED_ON"));
 	          
 	          return cmpn;
 	         }
 	     };	  
 		
 		String selectCampaignQuery = "SELECT C.CAMPAIGN_ID,C.CAMPAIGN_NAME,C.CREATED_EMAILID,C.LAST_MODIFIED_TIME,C.SUBJECT, CS.OPENED,CS.BOUNCED,CS.CLICKED," +
 				" CS.SCHEDULED_ON FROM CAMPAIGN C,CAMPAIGN_STATUS CS " +
 				" WHERE C.CAMPAIGN_ID=CS.CAMPAIGN_ID AND CS.STATUS in ("+scheduledNow+","+scheduledLater+") AND C.ORG_ID="+orgId; 

 		System.out.println("MAIL ID Query ===>>>> "+selectCampaignQuery);
 		return getJdbcTemplate().query(selectCampaignQuery, mapper);		
 	}
     
     public List<Campaign> getScheduledCampaigns(int status,int rowCounts) {
 		
 		RowMapper mapper = new RowMapper() { 
 	         public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
 	          Campaign cmpn = new Campaign();	
 	          
 	          cmpn.setCampaignId(rs.getLong("CAMPAIGN_ID"));
 	          cmpn.setCampaignName(rs.getString("CAMPAIGN_NAME"));
 	          cmpn.setSubject(rs.getString("SUBJECT"));
 	          cmpn.setUnsubscribeLink(rs.getString("UNSUBSCRIBE_LINK"));
 	          cmpn.setUnsubscribeSubject(rs.getString("UNSUBSCRIBE_SUBJECT"));
 	          cmpn.setSubscriberListId(rs.getLong("SUBSCRIBER_LIST_ID"));
 	          cmpn.setStatusId(rs.getLong("STATUS_ID"));
 	          cmpn.setLatestSubscriberCount(rs.getString("LATEST_SUBSCRIBER_COUNT"));
 	          cmpn.setImageLocation(rs.getString("IMAGE_LOC"));
 	          
 	          return cmpn;
 	         }
 	     };	  
 		
 		String selectCampaignQuery = "SELECT C.CAMPAIGN_ID,C.CAMPAIGN_NAME,C.SUBJECT,C.UNSUBSCRIBE_LINK,C.UNSUBSCRIBE_SUBJECT," +
 				" CS.SUBSCRIBER_LIST_ID,CS.STATUS_ID,CS.LATEST_SUBSCRIBER_COUNT,C.IMAGE_LOC FROM CAMPAIGN C,CAMPAIGN_STATUS CS " +
 				" WHERE C.CAMPAIGN_ID=CS.CAMPAIGN_ID AND CS.STATUS="+status+" ORDER BY CS.SCHEDULED_ON ASC LIMIT "+rowCounts; 

 		System.out.println("MAIL ID Query ===>>>> "+selectCampaignQuery);
 		return getJdbcTemplate().query(selectCampaignQuery, mapper);		
 	}
     
     public List<Campaign> getScheduledCampaigns(int status,String toTime) {
  		
  		RowMapper mapper = new RowMapper() { 
  	         public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
  	          Campaign cmpn = new Campaign();	
  	          
  	          cmpn.setCampaignId(rs.getLong("CAMPAIGN_ID"));
  	          cmpn.setCampaignName(rs.getString("CAMPAIGN_NAME"));
  	          cmpn.setSubject(rs.getString("SUBJECT"));
  	          cmpn.setUnsubscribeLink(rs.getString("UNSUBSCRIBE_LINK"));
  	          cmpn.setUnsubscribeSubject(rs.getString("UNSUBSCRIBE_SUBJECT"));
  	          cmpn.setSubscriberListId(rs.getLong("SUBSCRIBER_LIST_ID"));
  	          cmpn.setStatusId(rs.getLong("STATUS_ID"));
  	          cmpn.setLatestSubscriberCount(rs.getString("LATEST_SUBSCRIBER_COUNT"));
  	          
  	          return cmpn;
  	         }
  	     };	  
  		
  		String selectCampaignQuery = "SELECT C.CAMPAIGN_ID,C.CAMPAIGN_NAME,C.SUBJECT,C.UNSUBSCRIBE_LINK,C.UNSUBSCRIBE_SUBJECT," +
  				" CS.SUBSCRIBER_LIST_ID,CS.STATUS_ID,CS.LATEST_SUBSCRIBER_COUNT FROM CAMPAIGN C,CAMPAIGN_STATUS CS " +
  				" WHERE C.CAMPAIGN_ID=CS.CAMPAIGN_ID AND CS.STATUS="+status+" AND CS.SCHEDULED_ON <='"+toTime+"' ORDER BY CS.SCHEDULED_ON ASC"; 

  		System.out.println("MAIL ID Query ===>>>> "+selectCampaignQuery);
  		return getJdbcTemplate().query(selectCampaignQuery, mapper);		
  	}
     
     public List<Campaign> getCampaign(final long campaignId) {
 		
 		RowMapper mapper = new RowMapper() { 
 	         public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
 	          Campaign cmpn = new Campaign();	
 	          
 	          cmpn.setCampaignId(campaignId);
 	          cmpn.setCampaignName(rs.getString("CAMPAIGN_NAME"));
 	          cmpn.setSubject(rs.getString("SUBJECT"));
 	          cmpn.setScheduledOn(rs.getString("SCHEDULED_ON"));
 	          cmpn.setImageLocation(rs.getString("IMAGE_LOC"));
 	          cmpn.setSubscriberListId(rs.getLong("SUBSCRIBER_LIST_ID"));
 	          cmpn.setStatus(rs.getInt("STATUS"));
 	          
 	          return cmpn;
 	         }
 	     };	  
 		
 		String selectCampaignQuery = "SELECT C.CAMPAIGN_NAME,C.SUBJECT,C.IMAGE_LOC,CS.SUBSCRIBER_LIST_ID,CS.STATUS," +
 				" CS.SCHEDULED_ON FROM CAMPAIGN C,CAMPAIGN_STATUS CS " +
 				" WHERE C.CAMPAIGN_ID=CS.CAMPAIGN_ID AND C.CAMPAIGN_ID="+campaignId; 

 		System.out.println("MAIL ID Query ===>>>> "+selectCampaignQuery);
 		return getJdbcTemplate().query(selectCampaignQuery, mapper);		
 	}

	
     public void deleteCampaign(long campaignId){
 		// ASSUMPTION - ORG_ID AND CAMPAIGN_NAME IS THE COMBINED UNIQUE ID
 		String deleteQuery = "DELETE FROM CAMPAIGN_STATUS WHERE CAMPAIGN_ID="+campaignId;
 		System.out.println("deleteQuery ===>>> "+deleteQuery);
 		getJdbcTemplate().execute(deleteQuery);	
 		
 		deleteQuery = "DELETE FROM CAMPAIGN WHERE CAMPAIGN_ID="+campaignId;
 		System.out.println("deleteQuery ===>>> "+deleteQuery);
 		getJdbcTemplate().execute(deleteQuery);
 	} 
     
     public String getCampaignImageLoc(long campaignId){
  		// ASSUMPTION - ORG_ID AND CAMPAIGN_NAME IS THE COMBINED UNIQUE ID
  		String slectQuery = "SELECT IMAGE_LOC FROM CAMPAIGN WHERE CAMPAIGN_ID="+campaignId;
  		System.out.println("slectQuery ===>>> "+slectQuery);
  		return getJdbcTemplate().queryForObject(slectQuery, String.class);	
  		
  	}  
		
	public static CampaignDAO getFromApplicationContext(ApplicationContext ctx) {
		return (CampaignDAO) ctx.getBean("CampaignDAO");
	}
}