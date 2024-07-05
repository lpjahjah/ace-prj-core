package com.ace.exames.core.interfaces;

import java.util.List;

import javax.ejb.Remote;

import com.ace.exames.core.exceptions.RequiredFieldsException;
import com.ace.exames.core.models.Funcionario;

@Remote
public interface FuncionariosService {
	List<Funcionario> getFuncionarios(int page, int size);
	
	List<Funcionario> getAllFuncionariosOrderedByName();

	List<Funcionario> searchFuncionarios(Integer codigo, String nome, int page, int size);

	Funcionario getFuncionario(Integer id);
	
	void createFuncionario(Funcionario funcionario) throws RequiredFieldsException;
	
	void updateFuncionario(Funcionario funcionario) throws RequiredFieldsException;
	
	void deleteFuncionario(Integer id);
}