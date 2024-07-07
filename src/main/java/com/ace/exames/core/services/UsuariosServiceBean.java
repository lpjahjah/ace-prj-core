package com.ace.exames.core.services;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import com.ace.exames.core.models.Usuario;
import com.ace.exames.core.daos.UsuariosDao;
import com.ace.exames.core.exceptions.RequiredFieldsException;
import com.ace.exames.core.exceptions.UsuarioAlreadyExistsException;
import com.ace.exames.core.interfaces.UsuariosService;

@Stateless(name = "UsuariosService")
public class UsuariosServiceBean implements UsuariosService {
		
	@Resource
    private SessionContext context;
	
	private final UsuariosDao usuariosDao = new UsuariosDao();

	@Override
	public List<Usuario> getUsuarios(int page, int size) {
		try {
			return usuariosDao.getAll(page, size);
		} catch (SQLException e) {
        	throw new RuntimeException("Failed to retrieve data from server", e);
        }
	}
	
	@Override
	public List<Usuario> searchUsuarios(Integer codigo, String login, int page, int size) {
		try {
			return usuariosDao.search(
					Optional.ofNullable(codigo), 
					Optional.ofNullable(login).filter(Predicate.not(String::isBlank)),
					page,
					size
			);
		} catch (SQLException e) {
        	throw new RuntimeException("Failed to retrieve data from server", e);
        }
	}
	
	@Override
	public Usuario getUsuario(Integer id) {
		try {
			return  usuariosDao.getOne(id);
		} catch (SQLException e) {
        	throw new RuntimeException("Failed to retrieve data from server", e);
        }
	}
	
	@Override
	public void createUsuario(Usuario usuario) throws RequiredFieldsException, UsuarioAlreadyExistsException {
		try {
			usuario.validateFields();
			
			if (usuario.getDsSenha() == null)
				throw new RequiredFieldsException(Usuario.class.getName().toUpperCase(), List.of("dsSenha"));
			
			if (usuariosDao.getByNmLogin(usuario.getNmLogin()) != null)
				throw new UsuarioAlreadyExistsException(usuario);
			
			usuariosDao.insert(usuario);
		} catch (SQLException e) {
        	throw new RuntimeException("Failed to retrieve data from server", e);
        }
	}
	
	@Override
	public void updateUsuario(Usuario usuario) throws RequiredFieldsException, UsuarioAlreadyExistsException {
		try {
			usuario.validateFields();
			
			Usuario existingUsuario = usuariosDao.getByNmLogin(usuario.getNmLogin());
			
			if (existingUsuario != null && existingUsuario.getCdUsuario() != usuario.getCdUsuario())
				throw new UsuarioAlreadyExistsException(usuario);
			
			usuariosDao.update(usuario);
		} catch (SQLException e) {
        	throw new RuntimeException("Failed to retrieve data from server", e);
        }
	}
	
	@Override
	public void deleteUsuario(Integer id) {
		try {
			usuariosDao.delete(id);
		} catch (SQLException e) {
        	throw new RuntimeException("Failed to retrieve data from server", e);
        }
	}
}