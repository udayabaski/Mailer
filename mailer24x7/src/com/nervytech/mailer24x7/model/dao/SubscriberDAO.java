package com.nervytech.mailer24x7.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.nervytech.mailer24x7.model.domains.Campaign;
import com.nervytech.mailer24x7.model.domains.SubscriberIdStatus;
import com.nervytech.mailer24x7.model.domains.SubscriberList;

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

@Resource(mappedName = "campaignDAO")
public class SubscriberDAO extends JdbcDaoSupport {
	private static final Logger log = LoggerFactory
			.getLogger(SubscriberDAO.class);
	// property constants
	public static final String EMAIL_ID = "emailId";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";

	public List<SubscriberList> getSubscriberGroups(String orgId) {

		RowMapper mapper = new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				SubscriberList subList = new SubscriberList();

				subList.setSubscriberListId(rs.getInt("SUBSCRIBER_LIST_ID"));
				subList.setSubscriberListName(rs
						.getString("SUBSCRIBER_LIST_NAME"));
				subList.setActiveCount(rs.getInt("ACTIVE_COUNT"));
				subList.setBouncedCount(rs.getInt("BOUNCE_COUNT"));
				subList.setUnsubscriberCount(rs.getInt("UNSUBSCRIBER_COUNT"));

				return subList;
			}
		};

		String selectSubscriberQuery = "SELECT SL.SUBSCRIBER_LIST_ID,SL.SUBSCRIBER_LIST_NAME,SL.ACTIVE_COUNT,SL.BOUNCE_COUNT,SL.UNSUBSCRIBER_COUNT "
				+ " FROM SUBSCRIBER_LIST SL " + " WHERE SL.ORG_ID=" + orgId;

		System.out.println("MAIL ID Query ===>>>> " + selectSubscriberQuery);
		return getJdbcTemplate().query(selectSubscriberQuery, mapper);
	}

	public List<SubscriberList> getSubscriberGroup(String subListId) {

		RowMapper mapper = new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				SubscriberList subList = new SubscriberList();

				subList.setSubscriberListId(rs.getInt("SUBSCRIBER_LIST_ID"));
				subList.setSubscriberListName(rs
						.getString("SUBSCRIBER_LIST_NAME"));
				subList.setActiveCount(rs.getInt("ACTIVE_COUNT"));
				subList.setBouncedCount(rs.getInt("BOUNCE_COUNT"));
				subList.setUnsubscriberCount(rs.getInt("UNSUBSCRIBER_COUNT"));

				return subList;
			}
		};

		String selectSubscriberQuery = "SELECT SL.SUBSCRIBER_LIST_ID,SL.SUBSCRIBER_LIST_NAME,SL.ACTIVE_COUNT,SL.BOUNCE_COUNT,SL.UNSUBSCRIBER_COUNT "
				+ " FROM SUBSCRIBER_LIST SL "
				+ " WHERE SL.SUBSCRIBER_LIST_ID="
				+ subListId;

		System.out.println("MAIL ID Query ===>>>> " + selectSubscriberQuery);
		return getJdbcTemplate().query(selectSubscriberQuery, mapper);
	}

	public List<SubscriberIdStatus> getSubscribers(String subListId, int status) {

		RowMapper mapper = new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				SubscriberIdStatus subStatus = new SubscriberIdStatus();

				subStatus.setEmailId(rs.getString("EMAIL_ID"));
				subStatus.setStatusId(rs.getLong("STATUS_ID"));

				return subStatus;
			}
		};

		String selectSubscriberQuery = "SELECT * FROM SUBSCRIBERID_STATUS WHERE SUBSCRIBER_LIST_ID="
				+ subListId + " AND STATUS=" + status+" ORDER BY STATUS_ID ASC";

		System.out.println("MAIL ID Query ===>>>> " + selectSubscriberQuery);
		return getJdbcTemplate().query(selectSubscriberQuery, mapper);
	}
	
	public List<SubscriberIdStatus> getSubscribersByLatestCount(String subListId, int status,long latestSubscriberSent) {

		RowMapper mapper = new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				SubscriberIdStatus subStatus = new SubscriberIdStatus();

				subStatus.setEmailId(rs.getString("EMAIL_ID"));
				subStatus.setStatusId(rs.getLong("STATUS_ID"));

				return subStatus;
			}
		};

		String selectSubscriberQuery = "SELECT * FROM SUBSCRIBERID_STATUS WHERE SUBSCRIBER_LIST_ID="
				+ subListId + " AND STATUS_ID > "+latestSubscriberSent+" AND STATUS=" + status+" ORDER BY STATUS_ID ASC";

		System.out.println("MAIL ID Query ===>>>> " + selectSubscriberQuery);
		return getJdbcTemplate().query(selectSubscriberQuery, mapper);
	}

	public void deleteSubGroup(String subListId) {
		String deleteQuery = "DELETE FROM SUBSCRIBERID_STATUS WHERE SUBSCRIBER_LIST_ID="
				+ subListId;
		System.out.println("deleteQuery ===>>> " + deleteQuery);
		getJdbcTemplate().execute(deleteQuery);

		deleteQuery = "DELETE FROM SUBSCRIBER_LIST WHERE SUBSCRIBER_LIST_ID="
				+ subListId;
		System.out.println("deleteQuery ===>>> " + deleteQuery);
		getJdbcTemplate().execute(deleteQuery);
	}

	public void deleteSubscriber(String statusIds) {
		String deleteQuery = "DELETE FROM SUBSCRIBERID_STATUS WHERE STATUS_ID IN ("
				+ statusIds + ")";
		System.out.println("deleteQuery ===>>> " + deleteQuery);
		getJdbcTemplate().execute(deleteQuery);
	}
	
	public void updateLatestSubscriberSent(long campaignStatusId, long latestSubscriberSent, int totalCount) {
		String udateQuery = "update CAMPAIGN_STATUS set LATEST_SUBSCRIBER_COUNT='"
				+ latestSubscriberSent + "',TOTAL_SUBSCRIBER_SENT="+totalCount+" WHERE STATUS_ID =" + campaignStatusId;
		System.out.println("udateQuery ===>>> " + udateQuery);
		getJdbcTemplate().execute(udateQuery);
	}
	
	public void updateCampaignStatus(long campaignStatusId, int status, int totalCount) {
		String udateQuery = "update CAMPAIGN_STATUS set STATUS='"
				+ status + "',TOTAL_SUBSCRIBER_SENT="+totalCount+" WHERE STATUS_ID =" + campaignStatusId;
		System.out.println("udateQuery ===>>> " + udateQuery);
		getJdbcTemplate().execute(udateQuery);
	}

	public void updateBouncedCount(long subscriberListId, int bouncedCount) {
		String udateQuery = "update SUBSCRIBER_LIST set BOUNCE_COUNT='"
				+ bouncedCount + "',ACTIVE_COUNT= ACTIVE_COUNT-" + bouncedCount
				+ " WHERE SUBSCRIBER_LIST_ID =" + subscriberListId;
		System.out.println("udateQuery ===>>> " + udateQuery);
		getJdbcTemplate().execute(udateQuery);
	}

	public void updateSubscriberStatus(long statusId, int status) {
		String udateQuery = "update SUBSCRIBERID_STATUS set status='" + status
				+ "' WHERE STATUS_ID =" + statusId;
		System.out.println("udateQuery ===>>> " + udateQuery);
		getJdbcTemplate().execute(udateQuery);
	}

	public void addSubscriber(SubscriberIdStatus subStatus) {
		String insertQuery = "INSERT INTO SUBSCRIBERID_STATUS(SUBSCRIBER_LIST_ID,USER_ID,ORG_ID,STATUS,EMAIL_ID) "
				+ " VALUES('"
				+ subStatus.getSubscriberListId()
				+ "','"
				+ subStatus.getUserId()
				+ "','"
				+ subStatus.getOrgId()
				+ "','"
				+ subStatus.getStatus() + "','" + subStatus.getEmailId() + "')";
		System.out.println("insertQuery ===>>> " + insertQuery);
		getJdbcTemplate().execute(insertQuery);
	}

	public void addBatchSubscriber(final long subscriberListId,
			final long userId, final long orgId, final int status,
			final String[] subscribers) {
		String insertQuery = "INSERT INTO SUBSCRIBERID_STATUS(SUBSCRIBER_LIST_ID,USER_ID,ORG_ID,STATUS,EMAIL_ID) "
				+ " VALUES(?,?,?,?,?)";
		System.out.println("insertQuery ===>>> " + insertQuery);

		getJdbcTemplate().batchUpdate(insertQuery,
				new BatchPreparedStatementSetter() {

					@Override
					public void setValues(PreparedStatement ps, int count)
							throws SQLException {
						ps.setLong(1, subscriberListId);
						ps.setLong(2, userId);
						ps.setLong(3, orgId);
						ps.setInt(4, status);
						ps.setString(5, subscribers[count]);
					}

					@Override
					public int getBatchSize() {
						return subscribers.length;
					}
				});

	}

	public long addSubGroup(SubscriberList subList) {
		String insertQuery = "INSERT INTO SUBSCRIBER_LIST(ORG_ID,SUBSCRIBER_LIST_NAME,USER_ID,CREATED_TIME,LAST_MODIFIED_TIME,ACTIVE_COUNT) "
				+ " VALUES('"
				+ subList.getOrgId()
				+ "','"
				+ subList.getSubscriberListName()
				+ "','"
				+ subList.getUserId()
				+ "','"
				+ subList.getCreatedTime()
				+ "',"
				+ "'"
				+ subList.getLastModifiedTime()
				+ "','"
				+ subList.getActiveCount() + "')";
		System.out.println("insertQuery ===>>> " + insertQuery);
		getJdbcTemplate().execute(insertQuery);

		// TODO : Assumption : Subscriber List name is unique ...
		String selectQuery = "SELECT SUBSCRIBER_LIST_ID FROM SUBSCRIBER_LIST WHERE SUBSCRIBER_LIST_NAME='"
				+ subList.getSubscriberListName() + "'";
		return getJdbcTemplate().queryForLong(selectQuery);
	}

	public static SubscriberDAO getFromApplicationContext(ApplicationContext ctx) {
		return (SubscriberDAO) ctx.getBean("CampaignDAO");
	}
}