package com.udd.naucnacentrala.web.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.camunda.bpm.engine.FormService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.form.FormField;
import org.camunda.bpm.engine.form.TaskFormData;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udd.naucnacentrala.domain.Authority;
import com.udd.naucnacentrala.domain.Magazine;
import com.udd.naucnacentrala.domain.ScientificArea;
import com.udd.naucnacentrala.domain.User;
import com.udd.naucnacentrala.domain.UserRole;
import com.udd.naucnacentrala.repository.ScientificAreaRepository;
import com.udd.naucnacentrala.service.MagazineService;
import com.udd.naucnacentrala.service.UserService;
import com.udd.naucnacentrala.web.dto.FormFieldsDto;
import com.udd.naucnacentrala.web.dto.ReviewDTO;
import com.udd.naucnacentrala.web.dto.ScientificAreaDTO;
import com.udd.naucnacentrala.web.dto.ScientificPaperDTO;
import com.udd.naucnacentrala.web.dto.StringDTO;
import com.udd.naucnacentrala.web.dto.TaskDto;
import com.udd.naucnacentrala.web.dto.UserDTO;

@RestController
@RequestMapping(value = "/api/task")
public class TaskController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	TaskService taskService;

	@Autowired
	FormService formService;
	
	@Autowired
	ScientificAreaRepository scientificAreaRepository;
	
	@Autowired
	private MagazineService magazineService;

	@GetMapping("/getAll")
	public ResponseEntity<?> getAllTasks(Principal principal) {
		User user = userService.findByEmail(principal.getName());

		List<Task> tasks = taskService.createTaskQuery().active().taskAssignee(user.getId().toString()).list();

		List<TaskDto> result = new ArrayList<>();
		for (Task task : tasks) {
			result.add(new TaskDto(task.getId(), task.getName(),
					runtimeService.getVariable(task.getProcessInstanceId(), "magazineId").toString(),
					(String) runtimeService.getVariable(task.getProcessInstanceId(), "magazineTitle")));
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping(path = "/{taskId}")
	public ResponseEntity<?> getTask(@PathVariable String taskId) {
		System.out.println("TaskController.getTask...Getting the form data for the task with id: " + taskId);
		TaskFormData tfd = formService.getTaskFormData(taskId);

		List<FormField> properties = tfd.getFormFields();
		
		String taskName = taskService.createTaskQuery().taskId(taskId).list().get(0).getName().toLowerCase().replace(" ", "_");

		return new ResponseEntity<>(new FormFieldsDto(taskId, properties, "publishScientificPaper",taskName), HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	@PostMapping(path = "executeTask/{taskId}")
	public ResponseEntity<?> executeTask(@RequestBody Map<String, Object> form, @PathVariable String taskId) {
		System.out.println("TaskController.executeTask...Executing the task with id: " + taskId);
		System.out.println("TaskController.executeTask...Following form data received:");
		System.out.println(form);

		User author = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

		Task task = taskService.createTaskQuery().taskId(taskId).active().taskAssignee(author.getId().toString()).list().get(0);
		if (task == null) {
			System.out.println("TaskController.executeTask...Task does not exist.");
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		if (form.get("review") != null) {
			List<ReviewDTO> listOfReviews =  new ArrayList<ReviewDTO>();
			if(runtimeService.getVariable(task.getProcessInstanceId(), "reviews") == null) {
				listOfReviews.add(new ReviewDTO(author.getEmail(), form.get("review").toString()));
				runtimeService.setVariable(task.getProcessInstanceId(), "reviews", listOfReviews);
			}else {
				System.out.println("NIJE NULL I TREBA DODATI");
				listOfReviews =  (List<ReviewDTO>) runtimeService.getVariable(task.getProcessInstanceId(), "reviews");
				listOfReviews.add(new ReviewDTO(author.getEmail(), form.get("review").toString()));
				runtimeService.setVariable(task.getProcessInstanceId(), "reviews", listOfReviews);
			}
		}

		String processVariable = task.getName().toLowerCase().replace(" ", "_");
		runtimeService.setVariable(task.getProcessInstanceId(),processVariable ,form);
		runtimeService.setVariables(task.getProcessInstanceId() ,form);

		taskService.complete(taskId);

		System.out.println("TaskController.executeTask...Task completed succesfully.");

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping(value="/getScientificPaper/{taskId}")
	public ResponseEntity<ScientificPaperDTO> getScientificPaper(@PathVariable String taskId) {
		User author = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		Task task = taskService.createTaskQuery().taskId(taskId).active().taskAssignee(author.getId().toString()).list().get(0);
		Optional<ScientificArea> areaName = scientificAreaRepository.findById(Long.valueOf(runtimeService.getVariable(task.getExecutionId(), "scientificAreaId").toString()));
		return new ResponseEntity<ScientificPaperDTO>(
					new ScientificPaperDTO(
							runtimeService.getVariable(task.getExecutionId(), "title").toString(),
							runtimeService.getVariable(task.getExecutionId(), "abstractDescription").toString(),
							areaName.get().getScientificAreaName(),							
							runtimeService.getVariable(task.getExecutionId(), "keywords").toString(),
							runtimeService.getVariable(task.getExecutionId(), "coauthors").toString(),
							runtimeService.getVariable(task.getExecutionId(), "pdfText").toString()
							)
					,HttpStatus.OK
				);
	}
	
	@GetMapping(value="getBadFormattingMessage/{taskId}")
	public ResponseEntity<StringDTO> getBadFormattingMessage(@PathVariable String taskId) {
		User author = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		Task task = taskService.createTaskQuery().taskId(taskId).active().taskAssignee(author.getId().toString()).list().get(0);
		return new ResponseEntity<StringDTO>(new StringDTO(runtimeService.getVariable(task.getExecutionId(), 
				"badFormatingExplaining").toString()), HttpStatus.OK);
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping(value="getReviews/{taskId}")
	public ResponseEntity<List<ReviewDTO>> getReviews(@PathVariable String taskId) {
		User author = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		Task task = taskService.createTaskQuery().taskId(taskId).active().taskAssignee(author.getId().toString()).list().get(0);
		List<ReviewDTO> reviews = (List<ReviewDTO>) runtimeService.getVariable(task.getProcessInstanceId(), "reviews");
		return new ResponseEntity<List<ReviewDTO>>(reviews, HttpStatus.OK);
	}
	
	@GetMapping(value="getBigOrSmallChangeFeedback/{taskId}")
	public ResponseEntity<StringDTO> getBigOrSmallChangeFeedback(@PathVariable String taskId) {
		User author = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		Task task = taskService.createTaskQuery().taskId(taskId).active().taskAssignee(author.getId().toString()).list().get(0);
		
		if(runtimeService.getVariable(task.getProcessInstanceId(), "small_changes") == null) {
			System.out.println("Small changes je null");
		}else {
			System.out.println("Small changes nije null");
		}
		
		if(runtimeService.getVariable(task.getProcessInstanceId(), "big_changes") == null) {
			System.out.println("Big changes je null");
		}else {
			System.out.println("Big changes nije null");
		}
		
		String message = "";
		if(runtimeService.getVariable(task.getProcessInstanceId(), "small_changes") != null &&
			runtimeService.getVariable(task.getProcessInstanceId(), "small_changes").equals(true)) {
			message = "Small changes: " + runtimeService.getVariable(task.getProcessInstanceId(), "explain_small_change").toString();
		}
		if(runtimeService.getVariable(task.getProcessInstanceId(), "big_changes") != null &&
			runtimeService.getVariable(task.getProcessInstanceId(), "big_changes").equals(true)) {
			message = "Big changes: " + runtimeService.getVariable(task.getProcessInstanceId(), "explain_big_change").toString();
		}
		
		return new ResponseEntity<StringDTO>(new StringDTO(message), HttpStatus.OK);
	}
	
	@GetMapping(value="getPdfText/{taskId}")
	public ResponseEntity<StringDTO> getPdfText(@PathVariable String taskId) {
		User author = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		Task task = taskService.createTaskQuery().taskId(taskId).active().taskAssignee(author.getId().toString()).list().get(0);
		
		String message = runtimeService.getVariable(task.getProcessInstanceId(), "pdfText").toString();
		
		return new ResponseEntity<StringDTO>(new StringDTO(message), HttpStatus.OK);
	}
	
	@GetMapping(value="getScientificAreas")
	public ResponseEntity<List<ScientificAreaDTO>> getScientificAreas() {
		List<ScientificArea> listOfAreas = scientificAreaRepository.findAll();
		List<ScientificAreaDTO> listOfDtoAreas = new ArrayList<ScientificAreaDTO>();
		for(ScientificArea area : listOfAreas) {
			listOfDtoAreas.add(new ScientificAreaDTO(area.getId(), area.getScientificAreaName()));
		}
		return new ResponseEntity<List<ScientificAreaDTO>>(listOfDtoAreas, HttpStatus.OK);
	}
	
	@GetMapping(value="getReviewers/{taskId}")
	public ResponseEntity<List<UserDTO>> getReviewers(@PathVariable String taskId) {
		User author = userService.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		Task task = taskService.createTaskQuery().taskId(taskId).active().taskAssignee(author.getId().toString()).list().get(0);
		Magazine magazine = magazineService.findOne((Long)runtimeService.getVariable(task.getProcessInstanceId(), "magazineId"));
		List<UserDTO> retVal = new ArrayList<UserDTO>();
		
		for(User user: magazine.getReviewers()) {
			for(Authority a : user.getAuthorities()) {
				if(/*a.getName().equals(UserRole.EDITOR) ||*/ a.getName().equals(UserRole.REVIEWER)) {
					 UserDTO dto = new  UserDTO();
					 dto.setEmail(user.getEmail());
					 dto.setId(user.getId());
					 retVal.add(dto);
				}
			}
		}
		return new ResponseEntity<List<UserDTO>>(retVal, HttpStatus.OK);
	}
}
