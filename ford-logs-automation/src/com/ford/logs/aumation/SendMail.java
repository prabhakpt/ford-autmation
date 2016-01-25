package com.ford.logs.aumation;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
	
public static void main(String[] args) {
	
	  String to="prabha25k@gmail.com";//change accordingly  
	  
	  //Get the session object  
	  Properties props = new Properties();  
	  props.put("mail.smtp.host", "smtp.gmail.com");  
	  props.put("mail.smtp.socketFactory.port", "465");  
	  props.put("mail.smtp.socketFactory.class",  
	            "javax.net.ssl.SSLSocketFactory");  
	  props.put("mail.smtp.auth", "true");  
	  props.put("mail.smtp.ssl", "true"); 
	  props.put("mail.smtp.port", "465");  
	   
	  Session session = Session.getDefaultInstance(props,  
	   new javax.mail.Authenticator() {  
	   protected PasswordAuthentication getPasswordAuthentication() {  
		   return new PasswordAuthentication("prabha.kpr25@gmail.com","Pra4@sho");//change accordingly  
	   }  
	  });  
	   
	  //compose message  
	  try {  
	   MimeMessage message = new MimeMessage(session);  
	   message.setFrom(new InternetAddress("testmail@gmail.com"));//change accordingly  
	   message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
	   message.setSubject("Hello"); 
	   message.setText("This is test mail...");
	   //send message  
	   Transport.send(message);  
	  
	   System.out.println("message sent successfully");  
	   
	  } catch (MessagingException e) {throw new RuntimeException(e);}  
	   
	 }  
}
