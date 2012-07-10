/**
 * 
 */
package com.nervytech.mailer24x7.common.util;

import java.text.SimpleDateFormat;

/**
 * @author bsikkaya
 *
 */
public class MailerUtil {
  
	public static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	public static long maxFileSize = 102400; // 100 MB
	public static final String subscriberSeparator = ",";
	public static final String CONFIRMATION_MAIL_FROM = "baskar.sks@gmail.com";
	public static final String CONFIRMATION_MAIL_SUBJECT = "Welcome to Mailer24x7 - Confirmation";
	public static final String CONFIRMATION_MAIL_URL = "http://localhost:8080/mailer24x7/comn/regform.form?action=confirm&id=CONFIRM_ID";
	public static final String HTML_DIRECTORY = "D:\\\\nervymail\\\\html\\\\";
	
	public static int ROW_FETCH_SIZE = 100; // 100 MB
	public static int THREADES_COUNT = 6; // 100 MB
	public static long EXECUTOR_WAIT_TIME = 60000; // 100 MB
	public static long LATEST_SUBSCRIBER_UPDATE_COUNT_INTERVAL = 100; // 100 MB
	
}
