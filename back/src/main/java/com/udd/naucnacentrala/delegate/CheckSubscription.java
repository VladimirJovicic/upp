package com.udd.naucnacentrala.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.udd.naucnacentrala.domain.Magazine;
import com.udd.naucnacentrala.domain.User;
import com.udd.naucnacentrala.repository.MagazineRepository;


@Component
public class CheckSubscription implements JavaDelegate {
	
	@Autowired
	private MagazineRepository magazineRepository;
    
    @Override
    public void execute(DelegateExecution execution) throws Exception {
    	final Long magazineId = (Long)execution.getVariable("magazineId");
    	final Long authorId = Long.parseLong((String) execution.getVariable("authorId"));
        final Magazine magazine = magazineRepository.findById(magazineId).get();
        
        boolean isSubscribed = false;
        
        for(User user : magazine.getPayers()) {
        	if(user.getId() == authorId) {
        		isSubscribed = true;
        	}
        }
        
        execution.setVariable("subscriptionPayed", isSubscribed);
    }
}
