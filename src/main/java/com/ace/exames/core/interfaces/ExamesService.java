package com.ace.exames.core.interfaces;

import java.util.List;

import javax.ejb.Remote;

import com.ace.exames.core.models.Exame;

@Remote
public interface ExamesService {
	List<Exame> getExames();
}