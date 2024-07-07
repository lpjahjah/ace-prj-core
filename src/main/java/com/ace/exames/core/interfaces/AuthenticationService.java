package com.ace.exames.core.interfaces;

import javax.ejb.Remote;

import com.ace.exames.core.models.Usuario;

@Remote
public interface AuthenticationService {	
	Usuario login(String login, String senha);
}