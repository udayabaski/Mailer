/**
 * 
 */
package com.nervytech.mailer24x7.mail.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
 
public class MailSender {
 
	public static void sendMail(String fromAddr,String toAddr,String subject,String content) {
		Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
 
        Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("baskar.sks","JayaMalika1982");
                }
            });
 
        try {
 
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromAddr));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toAddr));
            message.setSubject(subject);
            message.setText(content);
             
            Transport.send(message);
            
            System.out.println("Sent Mail "+toAddr);
 
        }
        catch (MessagingException e) {
        	System.out.println("Exception occured here .........");
        	e.printStackTrace();
            throw new RuntimeException(e);
        }  catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	/*public static ClientResponse PostMessage() {
	       Client client = Client.create();
	       client.addFilter(new HTTPBasicAuthFilter("api",
	                       "key-3ax6xnjp29jd6fds4gc373sgvjxteol0"));
	       WebResource webResource =
	               client.resource("https://api.mailgun.net/v2/samples.mailgun.org" +
	                               "/messages");
	       MultivaluedMapImpl formData = new MultivaluedMapImpl();
	       formData.add("from", "Excited User <me@samples.mailgun.org>");
	       formData.add("to", "sergeyo@profista.com");
	       formData.add("to", "serobnic@mail.ru");
	       formData.add("subject", "Hello");
	       formData.add("text", "Testing some Mailgun awesomness!");
	       return webResource.type(MediaType.APPLICATION_FORM_URLENCODED).
	               post(ClientResponse.class, formData);
	}*/
	
	public static void main(String[] args) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
 
        Session session = Session.getInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("baskar.sks","JayaMalika1982");
                }
            });
 
        try {
 
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("baskar.sks@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("baskar.sks@gmail.com"));
            message.setSubject("Testing Subject");
            message.setText("Dear Mail Crawler," +
                    "\n\n No spam to my email, please!");
 
            Transport.send(message);
 
            System.out.println("Done");
 
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
	
    public static void main1(String[] args) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "email-smtp.us-east-1.amazonaws.com");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
 
        Session session = Session.getDefaultInstance(props,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("AKIAIXOMF4RBLMBRSCLQ","AlidyV+DO9mIWCtx2L8yulq9t6MNXHG2LPeBA/0Fci/S");
                }
            });
 
        try {
 
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("baskar.sks@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse("baskar.sks@gmail.com"));
            message.setSubject("Testing Subject");
            String content = "<html><body><p><span style=\"font-size:72px;\"><span style=\"font-family: tahoma,geneva,sans-serif;\"><em><u><strong><span style=\"background-color: rgb(0, 255, 0);\">sadfsjadfkjlsdaf</span></strong></u></em></span></span></p></body></html>";
            message.setContent(content, "text/html");
 
            Transport.send(message);
 
            System.out.println("Done");
 
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}