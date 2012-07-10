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

import com.nervytech.mailer24x7.common.enums.UserRoleEnum;
import com.nervytech.mailer24x7.common.enums.UserStatusEnum;
import com.nervytech.mailer24x7.model.domains.Organization;
import com.nervytech.mailer24x7.model.domains.Registration;
import com.nervytech.mailer24x7.model.domains.User;


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

@Resource(mappedName="usersDAO")
public class UsersDAO extends JdbcDaoSupport {
	private static final Logger log = LoggerFactory.getLogger(UsersDAO.class);
	// property constants
	public static final String EMAIL_ID = "emailId";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";

	
	public List<Organization> findByOrgName(String orgName) {		
		RowMapper mapper = new RowMapper() { 
	         public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	          Organization org = new Organization();	 
	          org.setOrgName(rs.getString("ORG_NAME"));
	          org.setContactEmail(rs.getString("CONTACT_EMAIL"));
	          return org;
	         }
	     };	  
	    String orgSQL = "SELECT * FROM ORGANIZATION WHERE ORG_NAME='"+orgName+"'";
	    System.out.println("Org SQL is ====>>> "+orgSQL);
	    return getJdbcTemplate().query(orgSQL, mapper);
	}
	
	public List<User> findByEmailId(String emailId) {		
		RowMapper mapper = new RowMapper() { 
	         public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	          User usr = new User();	 
	          usr.setEmailId(rs.getString("EMAIL_ID"));
	          usr.setPassword(rs.getString("PASSWORD"));
	          //usr.setRoleName(rs.getString("role_name"));
	          return usr;
	         }
	     };	  
	    String userSQL = "SELECT * FROM USER WHERE EMAIL_ID='"+emailId+"'";
	    System.out.println("Login SQL is ====>>> "+userSQL);
	    return getJdbcTemplate().query(userSQL, mapper);
	}
	
	public List<User> getUser(String emailId) {		
		RowMapper mapper = new RowMapper() { 
	         public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	          User usr = new User();
	          usr.setUserId(rs.getInt("USER_ID"));
	          usr.setOrgId(rs.getInt("ORG_ID"));
	          usr.setEmailId(rs.getString("EMAIL_ID"));
	          usr.setPassword(rs.getString("PASSWORD"));
	          usr.setRole(rs.getInt("ROLE"));
	          usr.setStatus(rs.getInt("STATUS"));
	          
	          return usr;
	         }
	     };	  
	    String userSQL = "SELECT * FROM USER WHERE EMAIL_ID='"+emailId+"'";
	    System.out.println("Login SQL is ====>>> "+userSQL);
	    return getJdbcTemplate().query(userSQL, mapper);
	}
	
	public List<User> getUserByUserId(String userId) {		
		RowMapper mapper = new RowMapper() { 
	         public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	          User usr = new User();
	          usr.setUserId(rs.getInt("USER_ID"));
	          usr.setOrgId(rs.getInt("ORG_ID"));
	          usr.setEmailId(rs.getString("EMAIL_ID"));
	          usr.setPassword(rs.getString("PASSWORD"));
	          usr.setDisplayName(rs.getString("DISPLAYNAME"));
	          usr.setRole(rs.getInt("ROLE"));
	          usr.setStatus(rs.getInt("STATUS"));
	          usr.setContactNumber(rs.getString("CONTACT_NO"));
	          usr.setFullName(rs.getString("FULL_NAME"));
	          usr.setLanguage(rs.getString("LANGUAGE"));
	          usr.setTimeZone(rs.getString("TIMEZONE"));
	          
	          return usr;
	         }
	     };	  
	    String userSQL = "SELECT * FROM USER WHERE USER_ID="+userId;
	    System.out.println("Login SQL is ====>>> "+userSQL);
	    return getJdbcTemplate().query(userSQL, mapper);
	}
	
	public List<User> getUsers(String orgId) {		
		RowMapper mapper = new RowMapper() { 
	         public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
	          User usr = new User();
	          usr.setUserId(rs.getInt("USER_ID"));
	          usr.setOrgId(rs.getInt("ORG_ID"));
	          usr.setEmailId(rs.getString("EMAIL_ID"));
	          usr.setRole(rs.getInt("ROLE"));
	          usr.setFullName(rs.getString("FULL_NAME"));
	          usr.setDisplayName(rs.getString("DISPLAYNAME"));
	          usr.setRoleName(UserRoleEnum.values()[rs.getInt("ROLE")].toString());
	          
	          return usr;
	         }
	     };	  
	    String userSQL = "SELECT * FROM USER WHERE ORG_ID='"+orgId+"'";
	    System.out.println("Login SQL is ====>>> "+userSQL);
	    return getJdbcTemplate().query(userSQL, mapper);
	}
	
	public void updateEncodedPassword(User user){
		String updateQuery = "UPDATE USER SET PASSWORD='"+user.getPassword()+"' where EMAIL_ID='"+user.getEmailId()+"'";
		System.out.println("UPDATE Query ===>>> "+updateQuery);
		getJdbcTemplate().execute(updateQuery);	
	}
	
	
	public void deleteFromRegistration(String userId){
		String updateQuery = "DELETE FROM REGISTRATION WHERE USER_ID='"+userId+"'";
		System.out.println("UPDATE Query ===>>> "+updateQuery);
		getJdbcTemplate().execute(updateQuery);	
	}
	
	public void enableUser(String userId){
		String updateQuery = "UPDATE USER SET STATUS='"+UserStatusEnum.ENABLED.getStatus()+"' where USER_ID='"+userId+"'";
		System.out.println("UPDATE Query ===>>> "+updateQuery);
		getJdbcTemplate().execute(updateQuery);	
	}
	
	public String getConfirmationId(String userId){
		String insertQuery = "SELECT CONFIRMATION_ID FROM REGISTRATION WHERE USER_ID="+userId;
		System.out.println("INSERT Query ===>>> "+insertQuery);
		return getJdbcTemplate().queryForObject(insertQuery, String.class);	
	}
	
	public String getUserId(String confirmationId){
		String insertQuery = "SELECT USER_ID FROM REGISTRATION WHERE CONFIRMATION_ID='"+confirmationId+"'";
		System.out.println("INSERT Query ===>>> "+insertQuery);
		return getJdbcTemplate().queryForObject(insertQuery, String.class);	
	}
	
	public void insertRegistration(Registration reg){
		String insertQuery = "INSERT INTO REGISTRATION VALUES('"+reg.getUserId()+"','"+reg.getConfirmationId()+"','"+reg.getCreatedTime()+"')";
		System.out.println("INSERT Query ===>>> "+insertQuery);
		getJdbcTemplate().execute(insertQuery);	
	}
	
	// TODO : Need to check orgid as well while deleting, editing and so an .....
	// This is to prevent URL tampering ......
	public void deleteUser(String userId){
		String deleteQuery = "DELETE FROM USER where USER_ID="+userId;
		System.out.println("UPDATE Query ===>>> "+deleteQuery);
		getJdbcTemplate().execute(deleteQuery);	
	}
	
	public void updateUser(User user){
		String updateQuery = "UPDATE USER SET EMAIL_ID='"+user.getEmailId()+"',DISPLAYNAME='"+user.getDisplayName()+"',FULL_NAME='"+user.getFullName()+"' " +
				",CONTACT_NO='"+user.getContactNumber()+"',ROLE='"+user.getRole()+"',LANGUAGE='"+user.getLanguage()+"'" +
						",TIMEZONE='"+user.getTimeZone()+"' where EMAIL_ID='"+user.getEmailId()+"'";
		System.out.println("UPDATE Query ===>>> "+updateQuery);
		getJdbcTemplate().execute(updateQuery);	
	}
	
	public void updatePassword(User user){
		String updateQuery = "UPDATE USER SET PASSWORD='"+user.getPassword()+"' where USER_ID='"+user.getUserId()+"'";
		System.out.println("UPDATE Query ===>>> "+updateQuery);
		getJdbcTemplate().execute(updateQuery);	
	}
	
	public long saveUser(User user){
		String insertQuery = "INSERT INTO USER (ORG_ID,EMAIL_ID,PASSWORD,DISPLAYNAME,ROLE,STATUS,CONTACT_NO,FULL_NAME,LANGUAGE,TIMEZONE,CREATED_TIME) " +
				                " VALUES('"+user.getOrgId()+"','"+user.getEmailId()+"','"+user.getPassword()+"','"+user.getDisplayName()+"'," +
				                		"'"+user.getRole()+"','"+user.getStatus()+"','"+user.getContactNumber()+"','"+user.getFullName()+"'," +
				                				"'"+user.getLanguage()+"','"+user.getTimeZone()+"','"+user.getCreatedTime()+"')";
		System.out.println("INSERT Query ===>>> "+insertQuery);
		getJdbcTemplate().execute(insertQuery);	
		
		String selectQuery = "SELECT USER_ID FROM USER WHERE EMAIL_ID='"+user.getEmailId()+"'";
		return getJdbcTemplate().queryForLong(selectQuery);
	}
	
	
	public long saveOrganization(Organization org){
		String insertQuery = "INSERT INTO ORGANIZATION (ORG_NAME,DISPLAYNAME,STATUS,CREATED_TIME,CREATED_EMAILID," +
				"CONTACT_EMAIL,CONTACT_NO,WEBSITE,ADDRESS,COUNTRY,TIMEZONE,SENDER_EMAIL) " +
				                " VALUES('"+org.getOrgName()+"','"+org.getDisplayName()+"','"+org.getStatus()+"','"+org.getCreatedTime()+"'," +
				                		"'"+org.getCreatedEmail()+"','"+org.getContactEmail()+"','"+org.getContactNumber()+"','"+org.getWebsite()+"','"+org.getAddress()+"'," +
				                				"'"+org.getCountry()+"','"+org.getTimeZone()+"','"+org.getSenderEmail()+"')";
		System.out.println("INSERT Query ===>>> "+insertQuery);
		getJdbcTemplate().execute(insertQuery);	
		
		String selectQuery = "SELECT ORG_ID FROM ORGANIZATION WHERE ORG_NAME='"+org.getOrgName()+"'";
		return getJdbcTemplate().queryForLong(selectQuery);
	}
	
	
	public static UsersDAO getFromApplicationContext(ApplicationContext ctx) {
		return (UsersDAO) ctx.getBean("UsersDAO");
	}
}