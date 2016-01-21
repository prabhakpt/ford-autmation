package com.ford.logs.automation.utilities;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class GmailSender {
	public static void main(String[] args) {
		final String userName = "prabha25k@gmail.com";
		final String password = "Prabha143kpt";
		Properties pro = new Properties();
		pro.put("mail.smtp.auth", "true");
		pro.put("mail.smtp.starttls.enable", "true");
		pro.put("mmail.smtp.host", "smtp.gmail.com");
		pro.put("mail.smtp.port", "587");
		Session session = Session.getInstance(pro, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication(userName, password);
			}
		});
		try {
			Message message =  new MimeMessage(session);
			message.setFrom(new InternetAddress("prabha25k@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("prabha.kpr25@gmail.com"));
			message.setSubject("Test message");
			message.setText("This is prabhekljlk");
			Transport.send(message);
			System.out.println("Done...");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
	}
}
