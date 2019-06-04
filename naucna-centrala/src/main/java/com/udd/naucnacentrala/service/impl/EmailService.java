package com.udd.naucnacentrala.service.impl;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.udd.naucnacentrala.web.dto.EmailDTO;

@Service
public class EmailService {
	
	@Autowired
    private JavaMailSender mailSender;

	public void sendMail(String subject, String message, String to) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
            mimeMessage.setContent(message, "text/html");
            helper.setTo(to);
            helper.setSubject(subject);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}
	
	public void sendMail(EmailDTO dto) {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
            mimeMessage.setContent(dto.getMessage(), "text/html");
            helper.setTo(dto.getTo());
            helper.setSubject(dto.getSubject());
            mailSender.send(mimeMessage);
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}
}