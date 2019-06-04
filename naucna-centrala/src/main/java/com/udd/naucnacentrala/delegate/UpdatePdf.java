package com.udd.naucnacentrala.delegate;

import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.udd.naucnacentrala.web.dto.FormSubmissionDto;

public class UpdatePdf implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("Ckecking updated pdf text");
		  
		@SuppressWarnings("unchecked")
		Map<String, FormSubmissionDto> formFields = (Map<String, FormSubmissionDto>) execution.getVariable("pdf_correction");
		
		Object newText = formFields.get("pdfCorrection");
		System.out.println("Old text is: " + execution.getVariable("pdfText"));
		System.out.println("New text is: " + newText.toString());
		
		execution.setVariable("pdfText", newText.toString());
	}

}
