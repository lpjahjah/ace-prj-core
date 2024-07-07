package com.ace.exames.core.daos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

import com.ace.exames.core.configs.Datasource;
import com.ace.exames.core.models.Usuario;
import com.ace.exames.core.utils.HashUtils;

public class UsuariosDao {

	private final Datasource datasource = new Datasource();
	
	private final String DEFAULT_SELECT = "SELECT * FROM usuario WHERE dt_deletado IS NULL ";
	
	private final String SELECT_STATEMENT = DEFAULT_SELECT + "LIMIT ?";
		
	private final String SEARCH_STATEMENT = DEFAULT_SELECT + "%s ORDER BY cd_usuario LIMIT ?";
	
	private final String SELECT_ONE = DEFAULT_SELECT + "AND cd_usuario = ?";
	
	private final String SELECT_BY_NM_LOGIN = DEFAULT_SELECT + "AND nm_login = ?";
	
	private final String SELECT_BY_NM_LOGIN_AND_DS_SENHA = DEFAULT_SELECT + "AND nm_login = ? AND ds_senha = ?";
	
	private final String DELETE_STATEMENT = "UPDATE usuario SET dt_deletado = now() WHERE cd_usuario = ?";
	
	private final String INSERT_STATEMENT = "INSERT INTO usuario (nm_login, ds_senha, qt_tempo_inatividade) VALUES (?, ?, ?)";
	
	private final String UPDATE_STATEMENT = "UPDATE usuario SET nm_login = ?, qt_tempo_inatividade = ? WHERE cd_usuario = ?";
	
	private final String UPDATE_STATEMENT_WITH_SENHA = "UPDATE usuario SET nm_login = ?, qt_tempo_inatividade = ?, ds_senha = ? WHERE cd_usuario = ?";
	
	public List<Usuario> getAll(int page, int size) throws SQLException {
		List<Object> params = List.of(page * size);
		
		List<Usuario> usuarios = datasource.select(SELECT_STATEMENT, params, Usuario.class);
		
		return usuarios;
	}
	
	public List<Usuario> search(Optional<Integer> codigo, Optional<String> login, int page, int size) throws SQLException {
		
		List<Object> params = new ArrayList<>();
		StringJoiner filters = new StringJoiner(" AND ", " AND ", "");
				
		if (codigo.isPresent()) {
			filters.add("cd_usuario = ?");
			params.add(codigo.get());
		}
		
		if (login.isPresent()) {
			filters.add("nm_login LIKE ?");
			params.add(login.get() + "%");
		}
		
		String statement = 
				params.isEmpty() ? 
				String.format(SEARCH_STATEMENT, "") : 
				String.format(SEARCH_STATEMENT, filters.toString());
		
		System.out.print("QUERY: ");
		System.out.println(statement);
		
		params.add(page * size);
		
		List<Usuario> usuarios = datasource.select(statement, params, Usuario.class);
		
		return usuarios;
	}
	
	public Usuario getOne(Integer id) throws SQLException {	
		return datasource.selectFirst(SELECT_ONE, List.of(id), Usuario.class);
	}
	
	public Usuario getByNmLogin(String login) throws SQLException {	
		List<Object> params = List.of(login);
		
		return datasource.selectFirst(SELECT_BY_NM_LOGIN, params, Usuario.class);
	}
	
	public Usuario getByNmLoginAndDsSenha(String login, String senha) throws SQLException {	
		List<Object> params = List.of(login, HashUtils.toMD5(senha));
		
		return datasource.selectFirst(SELECT_BY_NM_LOGIN_AND_DS_SENHA, params, Usuario.class);
	}
	
	public void insert(Usuario usuario) throws SQLException {	
		List<Object> params = List.of(
				usuario.getNmLogin(),
				HashUtils.toMD5(usuario.getDsSenha()),
				usuario.getQtTempoInatividade()

		);
		
		datasource.execute(INSERT_STATEMENT, params);
	}
	
	public void update(Usuario usuario) throws SQLException {	
		List<Object> params = new ArrayList<>();
		
		params.add(usuario.getNmLogin());
		params.add(usuario.getQtTempoInatividade());
		
		if (usuario.getDsSenha() == null || usuario.getDsSenha().isBlank()) {
			params.add(usuario.getCdUsuario());
			datasource.execute(UPDATE_STATEMENT, params);	
		}
		else {
			params.add(HashUtils.toMD5(usuario.getDsSenha()));
			params.add(usuario.getCdUsuario());
			datasource.execute(UPDATE_STATEMENT_WITH_SENHA, params);
		}		
	}
	
	public void delete(Integer id) throws SQLException {	
		datasource.execute(DELETE_STATEMENT, List.of(id));
	}
	
}