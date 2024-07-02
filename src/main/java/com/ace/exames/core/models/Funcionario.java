package com.ace.exames.core.models;

import java.sql.ResultSet;

import com.ace.exames.core.commons.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@EqualsAndHashCode(callSuper=false)
@Data
@NoArgsConstructor
public class Funcionario extends BaseModel {
	private static final long serialVersionUID = 1L;

	private Integer cdFuncionario;
	
	@NonNull
	private String nmFuncionario;

	@Override
	protected void loadFromResultSet(ResultSet resultSet) {
		try {
			this.cdFuncionario = resultSet.getInt("cd_funcionario");
			this.nmFuncionario = resultSet.getString("nm_funcionario");
		} catch (Exception e) {
        	throw new RuntimeException("Failed to map model from ResultSet", e);
        }
	}
	
	
		
}