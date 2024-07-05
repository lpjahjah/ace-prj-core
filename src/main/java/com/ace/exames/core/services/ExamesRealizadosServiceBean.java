package com.ace.exames.core.services;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import com.ace.exames.core.models.ExameRealizado;
import com.ace.exames.core.daos.ExamesRealizadosDao;
import com.ace.exames.core.exceptions.ExameRealizadoAlreadyExistsException;
import com.ace.exames.core.exceptions.InitialDateGreaterThanFinalDateException;
import com.ace.exames.core.exceptions.RequiredFieldsException;
import com.ace.exames.core.interfaces.ExamesRealizadosService;

@Stateless(name = "ExamesRealizadosService")
public class ExamesRealizadosServiceBean implements ExamesRealizadosService {
		
	@Resource
    private SessionContext context;
	
	private final ExamesRealizadosDao examesRealizadosDao = new ExamesRealizadosDao();
	
	@Override
	public List<ExameRealizado> getExamesRealizados(int page, int size) {
		try {
			return examesRealizadosDao.getAll(page, size);
		} catch (SQLException e) {
        	throw new RuntimeException("Failed to retrieve data from server", e);
        }
	}
	
	@Override
	public List<ExameRealizado> getExamesRealizadosBetween(Date initialDate, Date finalDate) throws InitialDateGreaterThanFinalDateException {
		try {
			if (initialDate.compareTo(finalDate) > 0)
				throw new InitialDateGreaterThanFinalDateException(initialDate, finalDate);
			
			return examesRealizadosDao.findByDtRealizacaoBetween(initialDate, finalDate);
		} catch (SQLException e) {
        	throw new RuntimeException("Failed to retrieve data from server", e);
        }
	}
		
	@Override
	public List<ExameRealizado> searchExamesRealizados(
			Integer cdExameRealizado,
			Integer cdFuncionario, 
			String nmFuncionario, 
			Integer cdExame, 
			String nmExame, 
			Date dtRealizacao,
			int page, 
			int size
	) {
		try {
			List<ExameRealizado> exames = examesRealizadosDao.search(
					Optional.ofNullable(cdExameRealizado),
					Optional.ofNullable(cdFuncionario),
					Optional.ofNullable(nmFuncionario).filter(Predicate.not(String::isBlank)), 
					Optional.ofNullable(cdExame), 
					Optional.ofNullable(nmExame).filter(Predicate.not(String::isBlank)), 
					Optional.ofNullable(dtRealizacao), 
					page,
					size
			);
			
			return exames;
		} catch (SQLException e) {
        	throw new RuntimeException("Failed to retrieve data from server", e);
        }
	}
	
	@Override
	public ExameRealizado getExameRealizado(Integer id) {
		try {
			return  examesRealizadosDao.getOne(id);
		} catch (SQLException e) {
        	throw new RuntimeException("Failed to retrieve data from server", e);
        }
	}
	
	@Override
	public void createExameRealizado(ExameRealizado exame) throws RequiredFieldsException, ExameRealizadoAlreadyExistsException {
		try {
			exame.validateFields();
			
			if (examesRealizadosDao.findByCdFuncionarioAndCdExameAndDtRealizacao(exame.getFuncionario().getCdFuncionario(), exame.getExame().getCdExame(), exame.getDtRealizacao()) != null)
				throw new ExameRealizadoAlreadyExistsException(exame);
			
			examesRealizadosDao.insert(exame);
		} catch (SQLException e) {
        	throw new RuntimeException("Failed to retrieve data from server", e);
        }
	}
	
	@Override
	public void updateExameRealizado(ExameRealizado exame) throws RequiredFieldsException, ExameRealizadoAlreadyExistsException {
		try {
			exame.validateFields();
			
			if (examesRealizadosDao.findByCdFuncionarioAndCdExameAndDtRealizacao(exame.getFuncionario().getCdFuncionario(), exame.getExame().getCdExame(), exame.getDtRealizacao()) != null)
				throw new ExameRealizadoAlreadyExistsException(exame);
			
			examesRealizadosDao.update(exame);
		} catch (SQLException e) {
        	throw new RuntimeException("Failed to retrieve data from server", e);
        }
	}
	
	@Override
	public void deleteExameRealizado (Integer id) {
		try {
			examesRealizadosDao.delete(id);
		} catch (SQLException e) {
        	throw new RuntimeException("Failed to retrieve data from server", e);
        }
	}
}