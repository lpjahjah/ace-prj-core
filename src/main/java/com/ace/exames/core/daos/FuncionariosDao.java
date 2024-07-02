package com.ace.exames.core.daos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import com.ace.exames.core.configs.Datasource;
import com.ace.exames.core.models.Funcionario;

public class FuncionariosDao {

	private final Datasource datasource = new Datasource();
	
	private final String DEFAULT_SELECT = "SELECT * FROM funcionario WHERE dt_deletado IS NULL ";
	
	private final String SELECT_STATEMENT = DEFAULT_SELECT + "LIMIT ?";
	
	private final String SEARCH_STATEMENT = DEFAULT_SELECT + "%s ORDER BY cd_funcionario LIMIT ?";
	
	private final String SELECT_ONE = DEFAULT_SELECT + "AND cd_funcionario = ?";
	
	private final String DELETE_STATEMENT = "UPDATE funcionario SET dt_deletado = now() WHERE cd_funcionario = ?";
	
	private final String INSERT_STATEMENT = "INSERT INTO funcionario (nm_funcionario) VALUES (?)";
	
	private final String UPDATE_STATEMENT = "UPDATE funcionario SET nm_funcionario = ? WHERE cd_funcionario = ?";

	
	public List<Funcionario> getAll(int page, int size) throws SQLException {
		List<Object> params = List.of(page * size);
		
		List<Funcionario> funcionarios = datasource.select(SELECT_STATEMENT, params, Funcionario.class);
		
		return funcionarios;
	}
	
	public List<Funcionario> search(Optional<Integer> codigo, Optional<String> nome, int page, int size) throws SQLException {
		
		List<Object> params = new ArrayList<>();
		StringJoiner filters = new StringJoiner(" AND ", " AND ", "");
				
		if (codigo.isPresent()) {
			filters.add("cd_funcionario = ?");
			params.add(codigo.get());
		}
		
		if (nome.isPresent()) {
			filters.add("nm_funcionario LIKE ?");
			params.add(nome.get() + "%");
		}
		
		String statement = 
				params.isEmpty() ? 
				String.format(SEARCH_STATEMENT, "") : 
				String.format(SEARCH_STATEMENT, filters.toString());
		
		System.out.print("QUERY: ");
		System.out.println(statement);
		
		params.add(page * size);
		
		return datasource.select(statement, params, Funcionario.class);
	}
	
	public Funcionario getOne(Integer id) throws SQLException {	
		return datasource.selectFirst(SELECT_ONE, List.of(id), Funcionario.class);
	}
	
	public void insert(Funcionario funcionario) throws SQLException {	
		List<Object> params = List.of(
				funcionario.getNmFuncionario()
		);
		
		datasource.execute(INSERT_STATEMENT, params);
	}
	
	public void update(Funcionario funcionario) throws SQLException {	
		List<Object> params = List.of(
				funcionario.getNmFuncionario(),
				funcionario.getCdFuncionario()
		);
		
		datasource.execute(UPDATE_STATEMENT, params);
	}
	
	public void delete(Integer id) throws SQLException {	
		datasource.execute(DELETE_STATEMENT, List.of(id));
	}
	
}