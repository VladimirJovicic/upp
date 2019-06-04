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
		emailDTO.setSubject("Too late for pdf correction");
		emailDTO.setMessage("Dear Sir/Madam, We are sorry, the time for your PDF correction is over!");

		emailService.sendMail(emailDTO);		
	}

}
