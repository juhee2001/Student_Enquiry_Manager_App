package com.studentenquiryapp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.internet.MimeMessage;

@Component
public class EmailUtil {

	@Autowired
	private JavaMailSender mailSender;
	
	public boolean sendEmail(String to, String subject, String body) {
		boolean isSent=false;
		try {
			MimeMessage msg=mailSender.createMimeMessage();
			
			MimeMessageHelper helper=new MimeMessageHelper(msg);
			
			helper.setSubject(subject);
			helper.setTo(to);
			helper.setText(body, true);
			
			mailSender.send(msg);
			isSent=true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return isSent;
	}
}
