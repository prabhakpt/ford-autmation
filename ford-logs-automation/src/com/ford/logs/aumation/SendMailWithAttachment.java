package com.ford.logs.aumation;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMailWithAttachment {
	
public static void main(String[] args) {
	
	  String to="prabhakar@neevtech.com";//change accordingly  
	  String cc = "prabha25k@gmail.com,shiva@neevtech.com";
	  try { 
		InternetAddress ccAddress[] = InternetAddress.parse(cc);
	  
	  //Get the session object  
	  Properties props = new Properties();  
	  props.put("mail.smtp.host", "smtp.gmail.com");  
	  props.put("mail.smtp.socketFactory.port", "465");  
	  props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");  
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
	  
	   MimeMessage message = new MimeMessage(session);  
	   message.setFrom(new InternetAddress("testmail@gmail.com"));//change accordingly  
	   message.setRecipients(Message.RecipientType.CC, ccAddress);
	   message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
	   message.setSubject("This is subject"); 
	   
	   BodyPart messageBodyPart = new MimeBodyPart();
	   
	   messageBodyPart.setText("Hi Ke, \n\nScript Login attempts reached to three. Try to stop and re run the script. \n\nThank you,\nPrabhakar K.");
	   Multipart multiPart = new MimeMultipart();
	   multiPart.addBodyPart(messageBodyPart);
	   messageBodyPart= new MimeBodyPart();
	   
	   String filePath = System.getProperty("user.dir")+"\\resources\\ford-log-details.xls";
	   System.out.println("File path is : "+filePath);
	   DataSource dataSource = new FileDataSource(filePath);
	   messageBodyPart.setDataHandler(new DataHandler(dataSource));
	   messageBodyPart.setFileName(filePath);
	   multiPart.addBodyPart(messageBodyPart);
	   
	   message.setContent(multiPart);
	   
	   //send message  
	   Transport.send(message);  
	  
	   System.out.println("message sent successfully");  
	   
	  } catch (MessagingException e) {throw new RuntimeException(e);}  
	   
	 }  
}
