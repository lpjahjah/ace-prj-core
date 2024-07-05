package com.ace.exames.core.interfaces;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import com.ace.exames.core.exceptions.RequiredFieldsException;
import com.ace.exames.core.models.ExameRealizado;

@Remote
public interface ExamesRealizadosService {
	List<ExameRealizado> getExamesRealizados(int page, int size);

	List<ExameRealizado> searchExamesRealizados(
			Integer cdExameRealizado,
			Integer cdFuncionario, 
			String nmFuncionario, 
			Integer cdExame, 
			String nmExame, 
			Date dtRealizacao,
			int page, 
			int size
	);

	ExameRealizado getExameRealizado(Integer id);
	
	void createExameRealizado(ExameRealizado exameRealizado) throws RequiredFieldsException;
	
	void updateExameRealizado(ExameRealizado exameRealizado) throws RequiredFieldsException;
	
	void deleteExameRealizado(Integer id);
}