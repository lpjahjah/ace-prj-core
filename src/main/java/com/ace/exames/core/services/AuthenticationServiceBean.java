package com.ace.exames.core.services;

import java.sql.SQLException;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import com.ace.exames.core.models.Usuario;
import com.ace.exames.core.daos.UsuariosDao;
import com.ace.exames.core.interfaces.AuthenticationService;

@Stateless(name = "AuthenticationService")
public class AuthenticationServiceBean implements AuthenticationService {
		
	@Resource
    private SessionContext context;
	
	private final UsuariosDao usuariosDao = new UsuariosDao();
	
	@Override
	public Usuario login(String login, String senha) {
		try {
			return  usuariosDao.getByNmLoginAndDsSenha(login, senha);
		} catch (SQLException e) {
        	throw new RuntimeException("Failed to retrieve data from server", e);
        }
	}
}