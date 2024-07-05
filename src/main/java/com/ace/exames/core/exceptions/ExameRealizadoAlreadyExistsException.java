package com.ace.exames.core.exceptions;

import com.ace.exames.core.models.ExameRealizado;

public class ExameRealizadoAlreadyExistsException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private static final String MESSAGE = "ExameRealizado of ( cdFuncionario = %d, cdExame = %d, dtRealizacao = %s) already exists.";
	
	public ExameRealizadoAlreadyExistsException(ExameRealizado er) {
		super(String.format(
				MESSAGE, 
				er.getFuncionario().getCdFuncionario(),
				er.getExame().getCdExame(),
				er.getDtRealizacao().toString()
		));
	}
}