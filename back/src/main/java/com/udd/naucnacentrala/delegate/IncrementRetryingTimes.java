package com.udd.naucnacentrala.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class IncrementRetryingTimes implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("IncrementRetryingTimes delegate");
		int retryingTimes = (int) execution.getVariable("retryingTimes");
		System.out.println("Old value is " + retryingTimes);
		retryingTimes++;
		System.out.println("New value is " + retryingTimes);
		execution.setVariable("retryingTimes", retryingTimes);
		
	}

	
}
