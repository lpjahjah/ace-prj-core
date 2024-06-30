package com.ace.exames.core.models;

import com.ace.exames.core.commons.BaseModel;

import lombok.Data;
import lombok.NonNull;

@Data
public class Exame implements BaseModel {
	
	private static final long serialVersionUID = -3631309734944846674L;

	private Integer cdExame;
	
	@NonNull
	private String nmExame;
	
	@NonNull
	private Boolean icAtivo = true;
	
	private String dsDetalheExame;
	
	private String dsDetalheExame1;
	
	@Override
	public String getTableName() {
		return "exame";
	}

	@Override
	public String getIdentifier() {
		return "cdExame";
	}
	
	
	
}