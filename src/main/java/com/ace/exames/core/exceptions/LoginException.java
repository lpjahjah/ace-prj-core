package com.ace.exames.core.exceptions;

public class LoginException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private static final String MESSAGE = "Invalid username or password.";
	
	public LoginException() {
		super(MESSAGE);
	}
}