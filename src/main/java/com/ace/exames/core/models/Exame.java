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
public class Exame extends BaseModel {
	private static final long serialVersionUID = 1L;

	private Integer cdExame;
	
	@NonNull
	private String nmExame;
	
	@NonNull
	private Boolean icAtivo = true;
	
	private String dsDetalheExame;
	
	private String dsDetalheExame1;

	@Override
	protected void loadFromResultSet(ResultSet resultSet) {
		try {
			this.cdExame = resultSet.getInt("cd_exame");
			this.nmExame = resultSet.getString("nm_exame");
			this.icAtivo = resultSet.getBoolean("ic_ativo");
			this.dsDetalheExame = resultSet.getString("ds_detalhe_exame");
			this.dsDetalheExame1 = resultSet.getString("ds_detalhe_exame1");
		} catch (Exception e) {
        	throw new RuntimeException("Failed to map model from ResultSet", e);
        }
	}
	
	
		
}