package com.udd.naucnacentrala.web.dto;

public class StringDTO {

	private String message;
	
	public StringDTO()
	{}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public StringDTO(String message) {
		super();
		this.message = message;
	}	
	
	
}
