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
		emailDTO.setSubject("Vas rad je prihvacen");
		emailDTO.setMessage("Postovani, Vas rad je prihvacen i spreman za recenziju. Molim Vas da sacekate da se proces recenziranja zavrsi.");

		emailService.sendMail(emailDTO);

		emailDTO.setTo(mainEditor.getEmail());
		emailDTO.setSubject("Nov rad je objavljen u vasem casoposu");
		emailDTO.setMessage("Postovani, nov rad je objavljen u casopisu gde ste vi glavni urednik. Molimo Vas pogledajte.");

		emailService.sendMail(emailDTO);

	}

}
