package com.udd.naucnacentrala.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class ProcessFinishTime implements JavaDelegate{

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("Processing finish time to review");
		if(execution.getVariable("hoursOfFinishRev") == null) {
			execution.setVariable("hoursToFinishReview","PT24H");
		}else {
		execution.setVariable("hoursToFinishReview","PT"+execution.getVariable("hoursOfFinishRev").toString()+"H");
		}
	}

}
