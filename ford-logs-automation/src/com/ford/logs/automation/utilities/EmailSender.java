package com.ford.logs.automation.utilities;

import java.util.Properties;

import javax.activation.*;
import javax.mail.*;
import javax.mail.internet.*;


public class EmailSender {
	public static void sendEmail(){
		  
		      String to = "prabha.kpr25@gmail.com";//change accordingly  
		      String from = "prabha.kpt25@yahoo.com";//change accordingly  
		      String host = "localhost";//or IP address  
		  
		     //Get the session object  
		      Properties properties = System.getProperties();  
		      properties.setProperty("mail.smtp.host", host);  
		      properties.setProperty("mail.smtp.auth", "true");
		      Session session = Session.getDefaultInstance(properties);  
		  
		     //compose the message  
		      try{  
		         MimeMessage message = new MimeMessage(session);  
		         message.setFrom(new InternetAddress(from));  
		         message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));  
		         message.setSubject("Ping");  
		         message.setText("Hello, this is example of sending email  ");  
		  
		         // Send message  
		         Transport.send(message);  
		         System.out.println("message sent successfully....");  
		  
		      }catch (MessagingException mex) {mex.printStackTrace();}  
		   }  

	public static void main(String[] args) {
		sendEmail();
	}

}
