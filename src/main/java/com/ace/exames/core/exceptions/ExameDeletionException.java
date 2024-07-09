package com.ace.exames.core.exceptions;

public class ExameDeletionException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private static final String MESSAGE = "Could not delete exame of code \"%d\" because it has been registered by an user on ExameRealizado table. First delete all of it`s occurrences on ExameRealizado table to enable deletion.";
	
	public ExameDeletionException(Integer cdExame) {
		super(String.format(
				MESSAGE,
				cdExame
		));
	}
	
	public ExameDeletionException(String customMessage) {
		super(customMessage);
	}
}