package com.udd.naucnacentrala.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.udd.naucnacentrala.domain.ScientificPaper;
import com.udd.naucnacentrala.repository.ScientificAreaRepository;
import com.udd.naucnacentrala.service.MagazineService;
import com.udd.naucnacentrala.service.ScientificPaperService;
import com.udd.naucnacentrala.service.UserService;

@Component
public class AddingPaperToDatabase implements JavaDelegate {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ScientificAreaRepository scientificAreaRepository;
	
	@Autowired
	private MagazineService magazineService;
	
	@Autowired
	private ScientificPaperService scientificPaperService;
	
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        System.out.println("Starting task IndexingWithDOI...");
        
        ScientificPaper scientificPaper = new ScientificPaper();
        scientificPaper.setTitle(execution.getVariable("title").toString());
        scientificPaper.setAbstractDescription(execution.getVariable("abstractDescription").toString());
        scientificPaper.setScientificArea(scientificAreaRepository.getOne(2l));
        scientificPaper.setKeywords(execution.getVariable("keywords").toString());
        scientificPaper.setPdf(execution.getVariable("pdfText").toString());
        
		scientificPaper.setMagazine(magazineService.findOne(Long.parseLong(execution.getVariable("magazineId").toString())));
		scientificPaper.setAuthor(userService.findById(Long.parseLong(execution.getVariable("authorId").toString())));
		
		scientificPaperService.save(scientificPaper);
        System.out.println("Ending task IndexingWithDOI...");	
	}

}
