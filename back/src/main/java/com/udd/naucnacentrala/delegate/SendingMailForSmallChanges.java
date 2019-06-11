package com.udd.naucnacentrala.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

import com.udd.naucnacentrala.domain.User;
import com.udd.naucnacentrala.service.UserService;
import com.udd.naucnacentrala.service.impl.EmailService;
import com.udd.naucnacentrala.web.dto.EmailDTO;

public class SendingMailForSmallChanges implements JavaDelegate{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Long authorId = Long.parseLong((String) execution.getVariable("authorId"));
		System.out.println("SendingMailForSmallChanges sending correction email to author with ID: " + authorId);
		User author = userService.findById(authorId);
		
		EmailDTO emailDto = new EmailDTO();
		emailDto.setTo(author.getEmail());
		emailDto.setSubject("Korekcija");
		emailDto.setMessage("Postovani. Potrebne su male izmene kako bi Vas rad bio prihvacen. "
				+ "Objasnjenje : <b>"+execution.getVariable("explain_small_change").toString()+"</b>");
		
		emailService.sendMail(emailDto);
		
	}

}
