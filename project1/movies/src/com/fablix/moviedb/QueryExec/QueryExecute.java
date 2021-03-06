package com.fablix.moviedb.QueryExec;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import com.fablix.moviedb.db.dbConnection;

public class QueryExecute {
	private final static String SELECT = "SELECT";
	private final static String UPDATE = "UPDATE";
	private final static String INSERT = "INSERT";
	private final static String DELETE = "DELETE";
	
	private static Connection connection;
	private static ResultSet result;
	private static Statement stmt;
	
	/**
	 * 
	 * @param sqlStatement DML command.
	 * @throws Exception
	 */
	public static void exeuteQuery(String sqlStatement){
		String Statement = sqlStatement.toUpperCase();
		if (Statement.startsWith(SELECT)){
			
			selectQuery(sqlStatement);
		
		}else if (Statement.startsWith(UPDATE)){
			
			otherCommand(sqlStatement, UPDATE);
		
		}else if (Statement.startsWith(INSERT)){
			
			otherCommand(sqlStatement, INSERT);
		
		}else if (Statement.startsWith(DELETE)){
			
			otherCommand(sqlStatement, DELETE);
		
		}else{
			System.out.println("Invalid SQL command! not SELECT/UPDATE/INSERT/DELETE !");
		}
	}
	/**
	 * 
	 * @param sqlStatement
	 * @throws Exception
	 */
	private static void selectQuery(String sqlStatement){
		connection = dbConnection.getConnection();
		try{
			stmt = connection.createStatement();
	        result = stmt.executeQuery(sqlStatement);
	        
	        ResultSetMetaData metadata = result.getMetaData();
	        int totalCount = 0;
	        while (result.next()){
	        	for (int i = 1; i <= metadata.getColumnCount(); i++)
	                   System.out.println(metadata.getColumnName(i).toUpperCase()+" = "+result.getString(i));
	            System.out.println();
	            totalCount++;
	        }
	        System.out.println("Total " + totalCount + " records");
	       
		
		}catch (SQLException e){
			System.err.println(e.getMessage());
			System.out.println("SELECT command failed!");
		}finally{
			dbConnection.connectionClose(result, stmt, connection);
		}

	}
	
	/**
	 * 
	 * @param sqlStatement_ DML
	 * @param commandType 	 
	 */
	private static void otherCommand(String sqlStatement, String commandType ){
		connection = dbConnection.getConnection();
		try{
			stmt = connection.createStatement();
			int rowsChanged = stmt.executeUpdate(sqlStatement);
			
			System.out.println(commandType + " commmand is successful! The " 
					          + rowsChanged + " rows have been changed!");
		}catch (SQLException e){
			System.out.println(commandType + " command failed!");
		}finally{
			dbConnection.connectionClose(result, stmt, connection);
		}
	}
	
}
