/**
 * 
 */
package com.nervytech.mailer24x7.spring.controllers;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.nervytech.mailer24x7.client.exception.MailerException;
import com.nervytech.mailer24x7.common.enums.CampaignStatusEnum;
import com.nervytech.mailer24x7.common.enums.SubscriberStatusEnum;
import com.nervytech.mailer24x7.common.util.MailerUtil;
import com.nervytech.mailer24x7.mail.util.MailSender;
import com.nervytech.mailer24x7.model.dao.CampaignDAO;
import com.nervytech.mailer24x7.model.dao.SubscriberDAO;
import com.nervytech.mailer24x7.model.domains.Campaign;
import com.nervytech.mailer24x7.model.domains.SubscriberIdStatus;

/**
 * @author bsikkaya
 * 
 */
public class CampaignNowJob {

	@Autowired
	private CampaignDAO campaignDAO;

	@Autowired
	private SubscriberDAO subscriberDAO;

	ExecutorService exec = Executors
			.newFixedThreadPool(MailerUtil.THREADES_COUNT);
	ThreadPoolExecutor executor = (ThreadPoolExecutor) exec;

	public void run() {
		
		try {
			List<Campaign> campaignsList = campaignDAO.getScheduledCampaigns(
					CampaignStatusEnum.NOW.getStatus(),
					MailerUtil.ROW_FETCH_SIZE);

			for (Campaign cmpn : campaignsList) {

				String content = null;
				try {
					content = FileUtils.readFileToString(new File(cmpn
							.getImageLocation()));

				} catch (IOException e) {
					throw new MailerException(
							"Exception occured while reading the editor content.",
							e);
				}

				Task task = new Task(Thread.currentThread().getName(), cmpn,
						content);
				System.out.println(Thread.currentThread().getName()
						+ " submitted " + task + ", queue size = "
						+ executor.getQueue().size());
				try {
					executor.execute(task);
					System.out.println("Added to QQQQQQQQQQQQQQQQ");
				} catch (RejectedExecutionException e) {
					// will be thrown if rejected execution handler
					// is not set with executor.setRejectedExecutionHandler
					e.printStackTrace();
				}
			}

			shutDownAndWaitToFinish(executor);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void shutDownAndWaitToFinish(ThreadPoolExecutor threadPool)throws Exception
	{
		try{
		    long keepAliveRecordThreadPool=MailerUtil.EXECUTOR_WAIT_TIME;
			threadPool.shutdown();
			while(!threadPool.isTerminated()){
				System.out.println(" --waiting ThreadPool's process to finish--");
				Thread.sleep(keepAliveRecordThreadPool);
			}	
		}
		catch(Exception e){
			throw e;
		}
	}

	private class Task implements Runnable {
		private SimpleDateFormat fmt = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm:ss.SSS");
		private String name;
		private Date created;
		private Campaign cmpn;
		private String content;

		public Task(String name, Campaign cmpn, String content) {
			this.name = name;
			this.cmpn = cmpn;
			this.content = content;
			this.created = new Date();
		}

		@Override
		public void run() {
			long campaignStatusId = cmpn.getStatusId();
			long campaignId = cmpn.getCampaignId();
			long subscriberListId = cmpn.getSubscriberListId();
			long latestSubscriberSent = Long.parseLong(cmpn.getLatestSubscriberCount());
			
			int totalSubscrierSent = 0;
			
			int bouncedCount = 0;

			List<SubscriberIdStatus> subscribers = subscriberDAO
					.getSubscribersByLatestCount(subscriberListId + "",
							SubscriberStatusEnum.ACTIVE.getStatus(),latestSubscriberSent);

			for (SubscriberIdStatus sub : subscribers) {
				long statusId = sub.getStatusId();

				try {
					MailSender.sendMail(MailerUtil.CONFIRMATION_MAIL_FROM,
							sub.getEmailId(), cmpn.getSubject(), content);
					
					totalSubscrierSent = totalSubscrierSent + 1;
					
					if(totalSubscrierSent % MailerUtil.LATEST_SUBSCRIBER_UPDATE_COUNT_INTERVAL == 0){
						updateLatestSubscrierCount(campaignStatusId,statusId, totalSubscrierSent);
					}
				} catch (Exception e) {
					e.printStackTrace();
					updateSubscriberStatus(statusId,
							SubscriberStatusEnum.BOUNCED.getStatus());
					
					bouncedCount = bouncedCount+1;
				}

			}
			
			updateBouncedCount(subscriberListId,bouncedCount);
			
			updateCampaignStatus(campaignStatusId,CampaignStatusEnum.COMPLETED.getStatus(),totalSubscrierSent);
		}

		private void updateLatestSubscrierCount(long campaignStatusId, long subscriber_id, int totalSubscrierSent) {
			try {
				subscriberDAO.updateLatestSubscriberSent(campaignStatusId, subscriber_id,totalSubscrierSent);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		private void updateCampaignStatus(long campaignStatusId, int status, int totalSubscrierSent) {
			try {
				subscriberDAO.updateCampaignStatus(campaignStatusId, status, totalSubscrierSent);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		private void updateBouncedCount(long subscriberListId,
				int bouncedCount) {
			try {
				subscriberDAO.updateBouncedCount(subscriberListId, bouncedCount);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}

		private void updateSubscriberStatus(long statusId, int status) {
			try {
				subscriberDAO.updateSubscriberStatus(statusId, status);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public String toString() {
			return name + ", created " + fmt.format(created);
		}

	}

}
