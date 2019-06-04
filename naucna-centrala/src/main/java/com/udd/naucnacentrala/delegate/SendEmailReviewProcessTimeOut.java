
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
public class SendEmailReviewProcessTimeOut implements JavaDelegate {

	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Long mainEditorId = Long.parseLong((String) execution.getVariable("mainEditorId"));
		System.out.println("SendEmailAfterPaperSubmition sending email to main editor with ID: " + mainEditorId);
		User mainEditor = userService.findById(mainEditorId);
		
		EmailDTO emailDTO = new EmailDTO();
		emailDTO.setTo(mainEditor.getEmail());
		emailDTO.setSubject("\"Review process timed out notification email");
		emailDTO.setSubject("Dear Sir/Madam, The time has run out for reviewing the scientific paper.");

		emailService.sendMail(emailDTO);
	}
}
