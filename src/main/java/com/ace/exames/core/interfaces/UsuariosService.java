package com.ace.exames.core.interfaces;

import java.util.List;

import javax.ejb.Remote;

import com.ace.exames.core.exceptions.RequiredFieldsException;
import com.ace.exames.core.exceptions.UsuarioAlreadyExistsException;
import com.ace.exames.core.models.Usuario;

@Remote
public interface UsuariosService {
	List<Usuario> getUsuarios(int page, int size);

	List<Usuario> searchUsuarios(Integer codigo, String login, int page, int size);

	Usuario getUsuario(Integer id);
	
	void createUsuario(Usuario usuario) throws RequiredFieldsException, UsuarioAlreadyExistsException;
	
	void updateUsuario(Usuario usuario) throws RequiredFieldsException, UsuarioAlreadyExistsException;
	
	void deleteUsuario(Integer id);
}