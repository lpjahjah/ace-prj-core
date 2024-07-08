package com.ace.exames.core.interfaces;

import javax.ejb.Remote;

import com.ace.exames.core.dtos.AuthDetails;
import com.ace.exames.core.exceptions.LoginException;

@Remote
public interface AuthenticationService {	
	AuthDetails login(String login, String senha) throws LoginException;
}