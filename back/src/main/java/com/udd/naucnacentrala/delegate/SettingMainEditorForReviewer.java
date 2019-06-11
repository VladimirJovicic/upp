package com.udd.naucnacentrala.delegate;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.udd.naucnacentrala.domain.Magazine;
import com.udd.naucnacentrala.repository.MagazineRepository;

@Component
public class SettingMainEditorForReviewer implements JavaDelegate {
	
	@Autowired
	private MagazineRepository magazineRepository;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("SettingMainEditorForReviewer delegate");
		 final Long magazineId = (Long)execution.getVariable("magazineId");
	     final Magazine magazine = magazineRepository.findById(magazineId).get();
	     
	     String id = magazine.getMainEditor().getId().toString();
	     List<String> hugeListOfMillionRecords = new ArrayList<String>();
	     hugeListOfMillionRecords.add(id);
	     
	     execution.setVariable("hoursToFinishReview", "PT24H");
	     execution.setVariable("reviewers", hugeListOfMillionRecords);
	}

}
