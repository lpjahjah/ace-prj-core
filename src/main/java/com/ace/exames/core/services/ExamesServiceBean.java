package com.ace.exames.core.services;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import com.ace.exames.core.models.Exame;
import com.ace.exames.core.interfaces.ExamesService;

@Stateless(name = "ExamesService")
public class ExamesServiceBean implements ExamesService {
	
	@Resource
    private SessionContext context;
	
	@Override
	public List<Exame> getExames() {
		return List.of(
				new Exame("Exame 1"),
				new Exame("Exame 2"),
				new Exame("Exame 3"),
				new Exame("Exame 4")
		);
	}
}