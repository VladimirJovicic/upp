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
public class SendConditionallyApprovedMail implements JavaDelegate{

	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Long authorId = Long.parseLong((String) execution.getVariable("authorId"));
		System.out.println("Sending mail that the paper is conditionally approved to author with ID: " + authorId);
		User author = userService.findById(authorId);
		
		EmailDTO emailDto = new EmailDTO();
		emailDto.setTo(author.getEmail());
		emailDto.setSubject("Paper is conditionally notification email");
		emailDto.setMessage("Dear Sir/Madam, Your paper has been conditionally approved.");
		
		emailService.sendMail(emailDto);
	}

}
