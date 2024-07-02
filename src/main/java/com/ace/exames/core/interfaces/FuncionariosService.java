package com.ace.exames.core.interfaces;

import java.util.List;

import javax.ejb.Remote;

import com.ace.exames.core.enums.ExameStatusEnum;
import com.ace.exames.core.models.Exame;

@Remote
public interface FuncionariosService {
	List<Exame> getExames(int page, int size);

	List<Exame> searchExames(Integer codigo, String nome, ExameStatusEnum status, int page, int size);

	Exame getExame(Integer id);
	
	void createExame(Exame exame);
	
	void updateExame(Exame exame);
	
	void deleteExame (Integer id);
}