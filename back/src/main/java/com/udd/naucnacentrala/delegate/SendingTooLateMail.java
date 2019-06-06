package com.udd.naucnacentrala.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import com.udd.naucnacentrala.domain.User;
import com.udd.naucnacentrala.service.UserService;
import com.udd.naucnacentrala.service.impl.EmailService;
import com.udd.naucnacentrala.web.dto.EmailDTO;

public class SendingTooLateMail implements JavaDelegate{
	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Long authorId = Long.parseLong((String) execution.getVariable("authorId"));
		System.out.println("SendingTooLateMail to author with ID: " + authorId);
		User author = userService.findById(authorId);

		EmailDTO emailDTO = new EmailDTO();
		emailDTO.setTo(author.getEmail());
		emailDTO.setSubject("Kasno za korekciju");
		emailDTO.setMessage("Postovani. Isteklo je vreme za korekciju PDF teksta");

		emailService.sendMail(emailDTO);		
	}

}
