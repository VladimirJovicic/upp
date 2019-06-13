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
public class SendMailThatHeIsTheChooser implements JavaDelegate {
	
	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		Long scientificAreaEditorId = Long.parseLong((String) execution.getVariable("scientificAreaEditorId"));
		System.out.println("SendMailThatHeIsTheChooser sending correction email to author with ID: " + scientificAreaEditorId);
		User scientificAreaEditor = userService.findById(scientificAreaEditorId);
		
		EmailDTO emailDto = new EmailDTO();
		emailDto.setTo(scientificAreaEditor.getEmail());
		emailDto.setSubject("Izabrani ste");
		emailDto.setMessage("Postovani. Kao glavni urednih naucne oblasti casopisa, morate da birate recezente za pristigli naucni rad."
				+ " Molimo Vas izvrsite ih u najkracem mogucem roku kako bi  se rad mogao dalje pregledati.");
		
		emailService.sendMail(emailDto);
	}

}
