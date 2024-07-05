package com.ace.exames.core.commons;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ace.exames.core.exceptions.RequiredFieldsException;

import lombok.NonNull;

public abstract class BaseModel implements Serializable {	
	private static final long serialVersionUID = 1L;
	
    public BaseModel() {
        validateConstructor(getClass());
    }

    private void validateConstructor(Class<?> clazz) {
        try {
            clazz.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Subclasses of BaseModel must have a public no-arg constructor.", e);
        }
    }

    public static <T extends BaseModel> T fromResultSet(ResultSet resultSet, Class<T> clazz) {
    	try {
    		T instance = clazz.getDeclaredConstructor().newInstance();
    		instance.loadFromResultSet(resultSet);
            return instance;
        } catch (Exception e) {
        	throw new RuntimeException("Failed to create instance of model for ResultSet mapping", e);
        }
	}
    
   protected abstract void loadFromResultSet(ResultSet resultSet);
   
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
   };
}