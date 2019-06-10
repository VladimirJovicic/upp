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
public class SendEmailTooManyTimesTried implements JavaDelegate{

	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Long authorId = Long.parseLong((String) execution.getVariable("authorId"));
		System.out.println("SendEmailAfterPaperSubmition sending email to main editor with ID: " + authorId);
		User author = userService.findById(authorId);
		
		EmailDTO emailDTO = new EmailDTO();
		emailDTO.setTo(author.getEmail());
		emailDTO.setSubject("Prekoracili ste granicu slanja");
		emailDTO.setMessage("Postovani, zao nam je ali iskoristile ste broj ponavljanja za ponovno slanje rada.");

		emailService.sendMail(emailDTO);
	}
}
