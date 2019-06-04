package com.udd.naucnacentrala.delegate;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import com.udd.naucnacentrala.web.dto.FormSubmissionDto;

public class AfterReview implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		System.out.println("After every reviewer finished");

		@SuppressWarnings("unchecked")
		List<FormSubmissionDto> formFields = (List<FormSubmissionDto>) execution
				.getVariable("Task_1ggdged");

		for (FormSubmissionDto field : formFields) {
			execution.setVariable("publishingDecision", field.getFieldValue());
		}
	}

}
