package com.udd.naucnacentrala.delegate;


import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.udd.naucnacentrala.domain.User;
import com.udd.naucnacentrala.service.UserService;
import com.udd.naucnacentrala.service.impl.EmailService;
import com.udd.naucnacentrala.web.dto.EmailDTO;

@Component
public class SendCorrectionEmail implements JavaDelegate {

	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Long authorId = Long.parseLong((String) execution.getVariable("authorId"));
		System.out.println("SendCorrectionEmail sending correction email to author with ID: " + authorId);
		User author = userService.findById(authorId);
		
		EmailDTO emailDto = new EmailDTO();
		emailDto.setTo(author.getEmail());
		emailDto.setSubject("Paper correction notification email");
		emailDto.setMessage("Dear Sir/Madam, Please correct the mistakes in your scientific paper.");
		
		emailService.sendMail(emailDto);

	}
}
