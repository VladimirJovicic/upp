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
public class SendEmailBadFormating implements JavaDelegate {

	@Autowired
	private UserService userService;

	@Autowired
	private EmailService emailService;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		execution.removeVariable("scientificAreaId");

		Long authorId = Long.parseLong((String) execution.getVariable("authorId"));
		System.out.println("SendEmailBadFormating sending bad formating error email to author with ID: " + authorId);
		User author = userService.findById(authorId);

		EmailDTO emailDto = new EmailDTO();
		emailDto.setTo(author.getEmail());
		emailDto.setSubject("Lose formatiran rad");
		emailDto.setMessage("Postovani, Vas rad je lose formatiran. Molimo vas popravite to kako bi se rad mogao recenzirati. " 
				+ "U nastavku mejla sledi obrazlozenje  : <b>" + execution.getVariable("badFormatingExplaining").toString() + "</b>");

		emailService.sendMail(emailDto);
	}
}
