package com.ace.exames.core.exceptions;

import com.ace.exames.core.models.Usuario;

public class UsuarioAlreadyExistsException extends Exception {
	private static final long serialVersionUID = 1L;
	
	private static final String MESSAGE = "Usuario of ( cdUsuario = %d, cdLogin = %s) already exists.";
	
	public UsuarioAlreadyExistsException(Usuario usuario) {
		super(String.format(
				MESSAGE, 
				usuario.getCdUsuario(),
				usuario.getNmLogin()
		));
	}
}