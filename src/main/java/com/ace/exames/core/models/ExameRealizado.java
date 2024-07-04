package com.ace.exames.core.models;

import java.sql.ResultSet;
import java.util.Date;

import com.ace.exames.core.commons.BaseModel;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper=true)
@Data
@NoArgsConstructor
public class ExameRealizado extends BaseModel {
	private static final long serialVersionUID = 1L;

	private Integer cdExameRealizado;
	
	private Funcionario funcionario;
	
	private Exame exame;
	
	private Date dtRealizacao;

	@Override
	protected void loadFromResultSet(ResultSet resultSet) {
		try {
			this.cdExameRealizado = resultSet.getInt("cd_exame_realizado");
			this.exame = Exame.fromResultSet(resultSet, Exame.class);
			this.funcionario = Funcionario.fromResultSet(resultSet, Funcionario.class);
			this.dtRealizacao = resultSet.getDate("dt_realizacao");
		} catch (Exception e) {
        	throw new RuntimeException("Failed to map model from ResultSet", e);
        }
	}		
}