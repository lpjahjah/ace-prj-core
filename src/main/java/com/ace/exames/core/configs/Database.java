package com.ace.exames.core.configs;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ejb.Local;
import javax.ejb.Stateful;

@Stateful
@Local
public class Database {
	private final String connectionString = "jdbc:mysql://localhost:3306/avaliacao?user=root&password=root";
	
	public Database() throws ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
	}
	
	public ResultSet execute(String statement) throws SQLException {
		try (Connection connection = DriverManager.getConnection(connectionString)) {
			try (PreparedStatement prepStatement = connection.prepareStatement(statement)) {
//				pstmt.setString(1, "lead developer");
//				pstmt.setInt(2, 1);
				
				prepStatement.execute();
				
				return prepStatement.getResultSet();
			}
		}
	}
}