package com.ace.exames.core.commons;

import java.io.Serializable;

public interface BaseModel extends Serializable {
	String getTableName();
	
	String getIdentifier();
}