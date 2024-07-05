package com.ace.exames.core.interfaces;

import java.util.List;

import javax.ejb.Remote;

import com.ace.exames.core.enums.ExameStatusEnum;
import com.ace.exames.core.exceptions.ExameDeletionException;
import com.ace.exames.core.exceptions.RequiredFieldsException;
import com.ace.exames.core.models.Exame;

@Remote
public interface ExamesService {
	List<Exame> getExames(int page, int size);
	
	List<Exame> getAllExamesAtivosOrderedByName();

	List<Exame> searchExames(Integer codigo, String nome, ExameStatusEnum status, int page, int size);

	Exame getExame(Integer id);
	
	void createExame(Exame exame) throws RequiredFieldsException;
	
	void updateExame(Exame exame) throws RequiredFieldsException;
	
	void deleteExame(Integer id) throws ExameDeletionException;
}