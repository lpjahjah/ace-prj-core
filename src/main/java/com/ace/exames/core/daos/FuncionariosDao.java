package com.ace.exames.core.daos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import com.ace.exames.core.configs.Datasource;
import com.ace.exames.core.enums.ExameStatusEnum;
import com.ace.exames.core.models.Exame;

public class FuncionariosDao {

	private final Datasource datasource = new Datasource();
	
	private final String DEFAULT_SELECT = "SELECT * FROM exame WHERE dt_deletado IS NULL ";
	
	private final String SELECT_STATEMENT = DEFAULT_SELECT + "LIMIT ?";
	
	private final String SEARCH_STATEMENT = DEFAULT_SELECT + "%s ORDER BY cd_exame LIMIT ?";
	
	private final String SELECT_ONE = DEFAULT_SELECT + "AND cd_exame = ?";
	
	private final String DELETE_STATEMENT = "UPDATE exame SET dt_deletado = now() WHERE cd_exame = ?";
	
	private final String INSERT_STATEMENT = "INSERT INTO exame (nm_exame, ic_ativo, ds_detalhe_exame, ds_detalhe_exame1) VALUES (?, ?, ?, ?)";
	
	private final String UPDATE_STATEMENT = "UPDATE exame SET nm_exame = ?, ic_ativo = ?, ds_detalhe_exame = ?, ds_detalhe_exame1 = ? WHERE cd_exame = ?";

	
	public List<Exame> getAll(int page, int size) throws SQLException {
		List<Object> params = List.of(page * size);
		
		List<Exame> exames = datasource.select(SELECT_STATEMENT, params, Exame.class);
		
		return exames;
	}
	
	public List<Exame> search(Optional<Integer> codigo, Optional<String> nome, Optional<ExameStatusEnum> status, int page, int size) throws SQLException {
		
		List<Object> params = new ArrayList<>();
		StringJoiner filters = new StringJoiner(" AND ", " AND ", "");
				
		if (codigo.isPresent()) {
			filters.add("cd_exame = ?");
			params.add(codigo.get());
		}
		
		if (nome.isPresent()) {
			filters.add("nm_exame LIKE ?");
			params.add(nome.get() + "%");
		}
		
		if (status.isPresent()) {
			switch (status.get()) {
				case AMBOS:
					break;
				case ATIVO:
					filters.add("ic_ativo = ?");
					params.add(1);
					break;
				case INATIVO:
					filters.add("(ic_ativo = ? OR ic_ativo IS NULL)");
					params.add(0);
			}
		}
		
		String statement = 
				params.isEmpty() ? 
				String.format(SEARCH_STATEMENT, "") : 
				String.format(SEARCH_STATEMENT, filters.toString());
		
		System.out.print("QUERY: ");
		System.out.println(statement);
		
		params.add(page * size);
		
		List<Exame> exames = datasource.select(statement, params, Exame.class);
		
		return exames;
	}
	
	public Exame getOne(Integer id) throws SQLException {	
		return datasource.selectFirst(SELECT_ONE, List.of(id), Exame.class);
	}
	
	public void insert(Exame exame) throws SQLException {	
		List<Object> params = List.of(
				exame.getNmExame(), 
				exame.getIcAtivo(),
				exame.getDsDetalheExame(),
				exame.getDsDetalheExame1()
		);
		
		datasource.execute(INSERT_STATEMENT, params);
	}
	
	public void update(Exame exame) throws SQLException {	
		List<Object> params = List.of(
				exame.getNmExame(), 
				exame.getIcAtivo(),
				exame.getDsDetalheExame(),
				exame.getDsDetalheExame1(),
				exame.getCdExame()
		);
		
		datasource.execute(UPDATE_STATEMENT, params);
	}
	
	public void delete(Integer id) throws SQLException {	
		datasource.execute(DELETE_STATEMENT, List.of(id));
	}
	
}