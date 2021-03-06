package com.udd.naucnacentrala.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.udd.naucnacentrala.domain.Magazine;
import com.udd.naucnacentrala.repository.MagazineRepository;

@Component
public class CheckIfReviewersAreAvailable implements JavaDelegate {

	@Autowired
	private MagazineRepository magazineRepository;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("CheckIfReviewersAreAvailable delegate");

		final Long magazineId = (Long) execution.getVariable("magazineId");
		final Magazine magazine = magazineRepository.findById(magazineId).get();
		
		if(magazine.getReviewers() == null) {
			System.out.println("No reviewers list found...");
			execution.setVariable("reviewersCount", 0);
		}else {
			execution.setVariable("reviewersCount", magazine.getReviewers().size());
		}
	}

}
