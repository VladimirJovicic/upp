package com.udd.naucnacentrala.delegate;

import java.util.Optional;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.udd.naucnacentrala.domain.Magazine;
import com.udd.naucnacentrala.domain.User;
import com.udd.naucnacentrala.repository.MagazineRepository;
import com.udd.naucnacentrala.repository.UserRepository;

@Component
public class PayForSubscription implements JavaDelegate{
	
	@Autowired
	private MagazineRepository magazineRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("PayForSubscription delegate is activated");
		final Long magazineId = (Long)execution.getVariable("magazineId");
    	final Long authorId = Long.parseLong((String) execution.getVariable("authorId"));
        final Magazine magazine = magazineRepository.findById(magazineId).get();
        Optional<User> userToPay = userRepository.findById(authorId);
        
        if(userToPay.isPresent()) {
        	magazine.getPayers().add(userToPay.get());
        }
        
        System.out.println("Payed for magazine : " + magazine.getName());
        magazineRepository.save(magazine);
	}

}
