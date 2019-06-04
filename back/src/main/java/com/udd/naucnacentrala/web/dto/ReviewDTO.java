package com.udd.naucnacentrala.web.dto;

import java.io.Serializable;

public class ReviewDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String user;
	private String comment;
	
	ReviewDTO(){
		
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public ReviewDTO(String user, String comment) {
		super();
		this.user = user;
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "ReviewDTO [user=" + user + ", comment=" + comment + "]";
	}
	
}
