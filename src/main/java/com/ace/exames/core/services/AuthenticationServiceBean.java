package com.ace.exames.core.services;

import java.sql.SQLException;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import com.ace.exames.core.models.Usuario;
import com.ace.exames.core.daos.UsuariosDao;
import com.ace.exames.core.dtos.AuthDetails;
import com.ace.exames.core.exceptions.LoginException;
import com.ace.exames.core.interfaces.AuthenticationService;

@Stateless(name = "AuthenticationService")
public class AuthenticationServiceBean implements AuthenticationService {
		
	@Resource
    private SessionContext context;
	
	private final UsuariosDao usuariosDao = new UsuariosDao();
	
	@Override
	public AuthDetails login(String login, String senha) throws LoginException {
		try {
			Usuario usuario = usuariosDao.getByNmLoginAndDsSenha(login, senha);
			
			if (usuario == null)
				throw new LoginException();
			
			return new AuthDetails(usuario.getNmLogin(), usuario.getQtTempoInatividade());
		} catch (SQLException e) {
        	throw new RuntimeException("Failed to retrieve data from server", e);
        }
	}
}