package com.ace.exames.core.configs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ace.exames.core.commons.BaseModel;

public class Datasource {
	private final String connectionString = "jdbc:mysql://localhost:3306/avaliacao?user=root&password=root";
	
	public Datasource() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
	    	throw new RuntimeException("JDBC Driver Class not found", e);
	    }
	}
	
	private final void prepareStatementParams(PreparedStatement prepStatement, List<Object> params) throws SQLException {
		for (int i = 0; i < params.size(); i++)
			prepStatement.setObject(i+1, params.get(i));
	}
	
	public void execute(String statement) throws SQLException {
		this.execute(statement, Collections.emptyList());
	}
	
	public void execute(String statement, List<Object> params) throws SQLException {
		try (Connection connection = DriverManager.getConnection(connectionString)) {
			try (PreparedStatement prepStatement = connection.prepareStatement(statement)) {
				prepareStatementParams(prepStatement, params);
				prepStatement.execute();
			}
		}
	}
	
	public <T extends BaseModel> List<T> select(String statement, Class<T> clazz) throws SQLException {
		return this.select(statement, Collections.emptyList(), clazz);
	}
	
	public <T extends BaseModel> List<T> select(String statement, List<Object> parameters, Class<T> clazz) throws SQLException {
		try (Connection connection = DriverManager.getConnection(connectionString)) {
			try (PreparedStatement prepStatement = connection.prepareStatement(statement)) {
				prepareStatementParams(prepStatement, parameters);
				
				try (ResultSet rs = prepStatement.executeQuery()){
					List<T> items = new ArrayList<>();
					
					while (rs.next())
						items.add(BaseModel.fromResultSet(rs, clazz));
					
					return items;
				}
			}
		}
	}
	
	public <T extends BaseModel> T selectFirst(String statement, Class<T> clazz) throws SQLException {
		return this.selectFirst(statement, Collections.emptyList(), clazz);
	}
	
	public <T extends BaseModel> T selectFirst(String statement, List<Object> parameters, Class<T> clazz) throws SQLException {
		try (Connection connection = DriverManager.getConnection(connectionString)) {
			try (PreparedStatement prepStatement = connection.prepareStatement(statement)) {
				prepareStatementParams(prepStatement, parameters);
				
				try (ResultSet rs = prepStatement.executeQuery()){
					while (rs.next())
						return BaseModel.fromResultSet(rs, clazz);
					
					return null;
				}
			}
		}
	}
}