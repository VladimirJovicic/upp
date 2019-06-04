package com.udd.naucnacentrala.web.dto;

import org.camunda.bpm.engine.form.FormField;

import java.util.List;

public class FormFieldsDto {
    String taskId;
    List<FormField> formFields;
    String processInstanceId;
    String taskName;

    public FormFieldsDto(String taskId, List<FormField> formFields, String processInstanceId, String taskName) {
		super();
		this.taskId = taskId;
		this.formFields = formFields;
		this.processInstanceId = processInstanceId;
		this.taskName = taskName;
	}

	public FormFieldsDto() {}

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<FormField> getFormFields() {
        return formFields;
    }

    public void setFormFields(List<FormField> formFields) {
        this.formFields = formFields;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

	@Override
	public String toString() {
		return "FormFieldsDto [taskId=" + taskId + ", formFields=" + formFields + ", processInstanceId="
				+ processInstanceId + ", taskName=" + taskName + "]";
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
    
    

}
