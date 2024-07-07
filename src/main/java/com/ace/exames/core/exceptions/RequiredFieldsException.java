package com.ace.exames.core.exceptions;

public class RequiredFieldsException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private static final String MESSAGE = "Fields (%s) of object \"%s\" are required.";
	
	public RequiredFieldsException(String objectName, Iterable<? extends CharSequence> fieldNames) {
		super(String.format(
				MESSAGE, 
				String.join(", ", fieldNames), 
				objectName.toUpperCase()
		));
	}
	
	public RequiredFieldsException(String customMessage) {
		super(customMessage);
	}
}