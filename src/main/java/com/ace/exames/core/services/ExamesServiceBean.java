package com.ace.exames.core.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import com.ace.exames.core.models.Exame;
import com.ace.exames.core.daos.ExamesDao;
import com.ace.exames.core.daos.ExamesRealizadosDao;
import com.ace.exames.core.enums.ExameStatusEnum;
import com.ace.exames.core.exceptions.ExameDeletionException;
import com.ace.exames.core.exceptions.RequiredFieldsException;
import com.ace.exames.core.interfaces.ExamesService;

@Stateless(name = "ExamesService")
public class ExamesServiceBean implements ExamesService {
		
	@Resource
    private SessionContext context;
	
	private final ExamesDao examesDao = new ExamesDao();

	private final ExamesRealizadosDao examesRealizadosDao = new ExamesRealizadosDao();
	
	@Override
	public List<Exame> getExames(int page, int size) {
		try {
			List<Exame> exames = examesDao.getAll(page, size);
			
			return exames;
		} catch (SQLException e) {
        	throw new RuntimeException("Failed to retrieve data from server", e);
        }
	}
	
	@Override
	public List<Exame> getAllExamesAtivosOrderedByName() {
		try {
			return  examesDao.getAllAtivosOrderByName();
		} catch (SQLException e) {
        	throw new RuntimeException("Failed to retrieve data from server", e);
        }
	}
	
	@Override
	public List<Exame> searchExames(Integer codigo, String nome, ExameStatusEnum status, int page, int size) {
		try {
			List<Exame> exames = examesDao.search(
					Optional.ofNullable(codigo), 
					Optional.ofNullable(nome).filter(Predicate.not(String::isBlank)), 
					Optional.ofNullable(status), 
					page,
					size
			);
			
			return exames;
		} catch (SQLException e) {
        	throw new RuntimeException("Failed to retrieve data from server", e);
        }
	}
	
	@Override
	public Exame getExame(Integer id) {
		try {
			return  examesDao.getOne(id);
		} catch (SQLException e) {
        	throw new RuntimeException("Failed to retrieve data from server", e);
        }
	}
	
	@Override
	public void createExame(Exame exame) throws RequiredFieldsException {
		try {
			exame.validateFields();
			examesDao.insert(exame);
		} catch (SQLException e) {
        	throw new RuntimeException("Failed to retrieve data from server", e);
        }
	}
	
	@Override
	public void updateExame(Exame exame) throws RequiredFieldsException {
		try {
			exame.validateFields();
			examesDao.update(exame);
		} catch (SQLException e) {
        	throw new RuntimeException("Failed to retrieve data from server", e);
        }
	}
	
	@Override
	public void deleteExame(Integer id) throws ExameDeletionException {
		try {
			if (examesRealizadosDao.findByCdExame(id) != null)
				throw new ExameDeletionException(id);
			
			examesDao.delete(id);
		} catch (SQLException e) {
        	throw new RuntimeException("Failed to retrieve data from server", e);
        }
	}
}