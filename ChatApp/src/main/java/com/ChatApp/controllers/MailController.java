package com.ChatApp.controllers;

import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;


public class MailController {
	
	public static JavaMailSender getJavaMailSender() {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost("smtp.gmail.com");
	    mailSender.setPort(587);
	     
	    mailSender.setUsername("chatapp72@gmail.com");
	    mailSender.setPassword("chatapp72@chatapp72");
	     
	    Properties props = mailSender.getJavaMailProperties();
	    props.put("mail.transport.protocol", "smtp");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.debug", "true");
	     
	    return mailSender;
	}
	
	public static boolean sendMail(String mailTo, String fname) {
		String msg = "<h6>Hello "+fname+"</h6>"
				+ "<a href='http://192.168.43.101:8080/verifymail?email="+mailTo+"'>Click here </a> to verify your email";
		
		JavaMailSender sender = getJavaMailSender();
		MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            message.setContent(msg,"text/html; charset=utf-8");
            helper.setTo(mailTo);
            helper.setSubject("Mail Verification From ChatApp");
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
        sender.send(message);
        return true;
	}
	
	
}
