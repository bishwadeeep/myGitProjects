package com.myspringbootproject.myrestfulwebservices.demo;

public class MyWorldBean {

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public MyWorldBean(String message) {
		
		this.message = message;
		
	}

	@Override
	public String toString() {
		return "In my World Bean" + message;
	}
	
	

}
