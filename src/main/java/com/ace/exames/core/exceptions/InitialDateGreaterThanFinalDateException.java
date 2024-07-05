package com.ace.exames.core.exceptions;

import java.util.Date;

public class InitialDateGreaterThanFinalDateException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private static final String MESSAGE = "Initial Date \"%s\" cannot be greater than the Final Date \"%s\".";
	
	public InitialDateGreaterThanFinalDateException(Date initialDate, Date finalDate) {
		super(String.format(
				MESSAGE,
				initialDate.toString(),
				finalDate.toString()
		));
	}
}