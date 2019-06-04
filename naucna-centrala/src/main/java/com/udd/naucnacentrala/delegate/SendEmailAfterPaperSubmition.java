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
public class SendEmailAfterPaperSubmition implements JavaDelegate {

	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		Long authorId = Long.parseLong((String) execution.getVariable("authorId"));
		System.out.println("SendEmailAfterPaperSubmition sending email to author with ID: " + authorId);
		User author = userService.findById(authorId);

		Long mainEditorId = Long.parseLong((String) execution.getVariable("mainEditorId"));
		System.out.println("SendEmailAfterPaperSubmition sending email to main editor with ID: " + mainEditorId);
		User mainEditor = userService.findById(mainEditorId);

		EmailDTO emailDTO = new EmailDTO();
		emailDTO.setTo(author.getEmail());
		emailDTO.setSubject("New article submission notification email");
		emailDTO.setMessage("Congratulations! You succesfully submitted an article. Please wait for a review.");

		emailService.sendMail(emailDTO);

		emailDTO.setTo(mainEditor.getEmail());
		emailDTO.setSubject("\"New article submission notification email");
		emailDTO.setMessage("There is a new article in the Naucna centrala. Please login to see it.");

		emailService.sendMail(emailDTO);

	}

}
