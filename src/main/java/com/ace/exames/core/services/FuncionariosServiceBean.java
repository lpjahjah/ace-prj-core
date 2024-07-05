package com.ace.exames.core.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import com.ace.exames.core.models.Funcionario;
import com.ace.exames.core.daos.ExamesRealizadosDao;
import com.ace.exames.core.daos.FuncionariosDao;
import com.ace.exames.core.exceptions.RequiredFieldsException;
import com.ace.exames.core.interfaces.FuncionariosService;

@Stateless(name = "FuncionariosService")
public class FuncionariosServiceBean implements FuncionariosService {
		
	@Resource
    private SessionContext context;
	
	private final FuncionariosDao funcionariosDao = new FuncionariosDao();
	
	private final ExamesRealizadosDao examesRealizadosDao = new ExamesRealizadosDao();
	
	@Override
	public List<Funcionario> getFuncionarios(int page, int size) {
		try {
			return funcionariosDao.getAll(page, size);
		} catch (SQLException e) {
        	throw new RuntimeException("Failed to retrieve data from server", e);
        }
	}
	
	@Override
	public List<Funcionario> getAllFuncionariosOrderedByName() {
		try {
			return funcionariosDao.getAllOrderedByName();
		} catch (SQLException e) {
        	throw new RuntimeException("Failed to retrieve data from server", e);
        }
	}
	
	@Override
	public List<Funcionario> searchFuncionarios(Integer codigo, String nome, int page, int size) {
		try {
			return funcionariosDao.search(
					Optional.ofNullable(codigo), 
					Optional.ofNullable(nome).filter(Predicate.not(String::isBlank)), 
					page,
					size
			);
		} catch (SQLException e) {
        	throw new RuntimeException("Failed to retrieve data from server", e);
        }
	}
	
	@Override
	public Funcionario getFuncionario(Integer id) {
		try {
			return  funcionariosDao.getOne(id);
		} catch (SQLException e) {
        	throw new RuntimeException("Failed to retrieve data from server", e);
        }
	}
	
	@Override
	public void createFuncionario(Funcionario funcionario) throws RequiredFieldsException {
		try {
			funcionario.validateFields();
			funcionariosDao.insert(funcionario);
		} catch (SQLException e) {
        	throw new RuntimeException("Failed to retrieve data from server", e);
        }
	}
	
	@Override
	public void updateFuncionario(Funcionario funcionario) throws RequiredFieldsException {
		try {
			funcionario.validateFields();
			funcionariosDao.update(funcionario);
		} catch (SQLException e) {
        	throw new RuntimeException("Failed to retrieve data from server", e);
        }
	}
	
	@Override
	public void deleteFuncionario(Integer id) {
		try {
			examesRealizadosDao.deleteByCdFuncionario(id);
			funcionariosDao.delete(id);
		} catch (SQLException e) {
        	throw new RuntimeException("Failed to retrieve data from server", e);
        }
	}
}