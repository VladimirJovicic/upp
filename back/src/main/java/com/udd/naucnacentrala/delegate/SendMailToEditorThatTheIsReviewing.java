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
public class SendMailToEditorThatTheIsReviewing implements JavaDelegate {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EmailService emailService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Long mainEditorId = Long.parseLong((String) execution.getVariable("mainEditorId"));
		System.out.println("SendMailToEditorThatTheIsReviewing sending email to main editor with ID: " + mainEditorId);
		User mainEditor = userService.findById(mainEditorId);	
		
		EmailDTO emailDto = new EmailDTO();
		emailDto.setTo(mainEditor.getEmail());
		emailDto.setSubject("Izabrani ste");
		emailDto.setMessage("Postovani. S obzirom da nema dovoljno recezenata kao glavni urednih casopisa, morate da birate recezente za pristigli naucni rad."
				+ " Molimo Vas izvrsite ih u najkracem mogucem roku kako bi  se rad mogao dalje pregledati.");
		
		emailService.sendMail(emailDto);
	}

}
