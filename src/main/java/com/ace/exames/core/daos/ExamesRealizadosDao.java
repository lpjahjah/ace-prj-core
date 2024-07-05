package com.ace.exames.core.daos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import com.ace.exames.core.configs.Datasource;
import com.ace.exames.core.models.ExameRealizado;

public class ExamesRealizadosDao {

	private final Datasource datasource = new Datasource();
	
	private final String DEFAULT_SELECT = "SELECT * FROM exame_realizado er JOIN funcionario f ON (er.cd_funcionario = f.cd_funcionario) JOIN exame e ON (er.cd_exame = e.cd_exame) WHERE er.dt_deletado IS NULL ";
	
	private final String SELECT_STATEMENT = DEFAULT_SELECT + "LIMIT ?";
	
	private final String FIND_BY_CD_EXAME = DEFAULT_SELECT + "AND e.cd_exame = ?";
	
	private final String SEARCH_STATEMENT = DEFAULT_SELECT + "%s ORDER BY cd_exame_realizado LIMIT ?";
	
	private final String SELECT_ONE = DEFAULT_SELECT + "AND cd_exame_realizado = ?";
	
	private final String DELETE_STATEMENT = "UPDATE exame_realizado SET dt_deletado = now() WHERE cd_exame_realizado = ?";
	
	private final String DELETE_BY_CD_FUNCIONARIO = "UPDATE exame_realizado SET dt_deletado = now() WHERE cd_funcionario = ?";
	
	private final String INSERT_STATEMENT = "INSERT INTO exame_realizado (cd_funcionario, cd_exame, dt_realizacao) VALUES (?, ?, ?)";
	
	private final String UPDATE_STATEMENT = "UPDATE exame_realizado SET cd_funcionario = ?, cd_exame = ?, dt_realizacao = ? WHERE cd_exame_realizado = ?";

	
	public List<ExameRealizado> getAll(int page, int size) throws SQLException {
		List<Object> params = List.of(page * size);
		
		return datasource.select(SELECT_STATEMENT, params, ExameRealizado.class);
	}
	
	public ExameRealizado findByCdExame(Integer cdExame) throws SQLException {		
		return datasource.selectFirst(FIND_BY_CD_EXAME, List.of(cdExame), ExameRealizado.class);
	}
	
	public List<ExameRealizado> search(
			Optional<Integer> cdExameRealizado,
			Optional<Integer> cdFuncionario,
			Optional<String> nmFuncionario,
			Optional<Integer> cdExame,
			Optional<String> nmExame,
			Optional<Date> dtRealizacao,
			int page, 
			int size
	) throws SQLException {
		
		List<Object> params = new ArrayList<>();
		StringJoiner filters = new StringJoiner(" AND ", " AND ", "");
				
		if (cdExameRealizado.isPresent()) {
			filters.add("cd_exame_realizado = ?");
			params.add(cdExameRealizado.get());
		}
		
		if (cdFuncionario.isPresent()) {
			filters.add("f.cd_funcionario = ?");
			params.add(cdFuncionario.get());
		}
		
		if (nmFuncionario.isPresent()) {
			filters.add("f.nm_funcionario LIKE ?");
			params.add(nmFuncionario.get() + "%");
		}
		
		if (cdExame.isPresent()) {
			filters.add("e.cd_exame = ?");
			params.add(cdExame.get());
		}
		
		if (nmExame.isPresent()) {
			filters.add("e.nm_exame LIKE ?");
			params.add(nmExame.get() + "%");
		}
		
		if (dtRealizacao.isPresent()) {
			filters.add("er.dt_realizacao = ?");
			params.add(dtRealizacao.get());
		}
		
		String statement = 
				params.isEmpty() ? 
				String.format(SEARCH_STATEMENT, "") : 
				String.format(SEARCH_STATEMENT, filters.toString());
		
		System.out.print("QUERY: ");
		System.out.println(statement);
		
		params.add(page * size);
		
		return datasource.select(statement, params, ExameRealizado.class);
	}
	
	public ExameRealizado getOne(Integer id) throws SQLException {	
		return datasource.selectFirst(SELECT_ONE, List.of(id), ExameRealizado.class);
	}
	
	public void insert(ExameRealizado exameRealizado) throws SQLException {	
		List<Object> params = List.of(
				exameRealizado.getFuncionario().getCdFuncionario(), 
				exameRealizado.getExame().getCdExame(),
				exameRealizado.getDtRealizacao()
		);
		
		datasource.execute(INSERT_STATEMENT, params);
	}
	
	public void update(ExameRealizado exameRealizado) throws SQLException {	
		List<Object> params = List.of(
				exameRealizado.getFuncionario().getCdFuncionario(), 
				exameRealizado.getExame().getCdExame(),
				exameRealizado.getDtRealizacao(),
				exameRealizado.getCdExameRealizado()
		);
		
		datasource.execute(UPDATE_STATEMENT, params);
	}
	
	public void deleteByCdFuncionario(Integer cdFuncionario) throws SQLException {	
		datasource.execute(DELETE_BY_CD_FUNCIONARIO, List.of(cdFuncionario));
	}
	
	public void delete(Integer id) throws SQLException {	
		datasource.execute(DELETE_STATEMENT, List.of(id));
	}
	
}