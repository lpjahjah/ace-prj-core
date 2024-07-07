package com.ace.exames.core.models;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.ace.exames.core.commons.BaseModel;
import com.ace.exames.core.exceptions.RequiredFieldsException;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@EqualsAndHashCode(callSuper=true)
@Data
@NoArgsConstructor
public class Usuario extends BaseModel {
	private static final long serialVersionUID = 1L;

	private Integer cdUsuario;
	
	@NonNull
	private String nmLogin;
	
	private String dsSenha;	
	
	@NonNull
	private Integer qtTempoInatividade;

	@Override
	protected void loadFromResultSet(ResultSet resultSet) {
		try {
			this.cdUsuario = resultSet.getInt("cd_usuario");
			this.nmLogin = resultSet.getString("nm_login");
			this.qtTempoInatividade = resultSet.getInt("qt_tempo_inatividade");
		} catch (Exception e) {
        	throw new RuntimeException("Failed to map model from ResultSet", e);
        }
	}
	
	@Override
	public void validateFields() throws RequiredFieldsException {
		   Class<?> clazz = this.getClass();
		   
		   List<Field> annotatedFields = new ArrayList<>();
		   boolean hasNullField = false;
		   
		   for (Field field: clazz.getFields()) {
			   if (field.isAnnotationPresent(NonNull.class)) {
				   annotatedFields.add(field);
				   
				   if (field.equals(null))
					   hasNullField = true;
			   }
		   }
		   
		   if (hasNullField)
			   throw new RequiredFieldsException(clazz.getName(), annotatedFields.stream().map(Field::getName).collect(Collectors.toList()));
		   
		   if (this.getQtTempoInatividade() < 1 || this.getQtTempoInatividade() > 90)
			   throw new RequiredFieldsException("Field (qtTempoInatividade) of object Usuario needs to be a number between 1 and 90.");
		   
		   if (!StringUtils.isAlphanumeric(this.getNmLogin()))
			   throw new RequiredFieldsException("Field (nmLogin) of object Usuario must contain only alphanumeric characters.");

	};
}