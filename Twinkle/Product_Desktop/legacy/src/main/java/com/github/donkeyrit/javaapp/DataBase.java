package com.github.donkeyrit.javaapp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dima
 */
import java.sql.*;
import java.util.*;

public class DataBase{
	private Connection connect;
	
	
	private String DB_DRIVER = "com.mysql.jdbc.Driver";
	private String DB_CONNECTION = "jdbc:mysql://localhost:3306/project";
	private String DB_USER = "root";
	private String DB_PASSWORD = "root";
	
	
	public DataBase(){
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your JDBC Driver?");
			e.printStackTrace();
		}
		
		try {
			connect = DriverManager.getConnection(DB_CONNECTION, DB_USER,DB_PASSWORD);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
        
        public ResultSet select(String query){
            ResultSet rs = null;
            try{
                Statement s = connect.createStatement();
                rs = s.executeQuery(query);
            }catch(SQLException e){
                e.printStackTrace();
            }
            return rs;
        }
        
       public int insert(String query){
           int resultCommand = 0;
           try{
               Statement s = connect.createStatement();
               resultCommand = s.executeUpdate(query);
           }catch(SQLException e){
               e.printStackTrace();
           }
           return resultCommand;
       }
	
       public int update(String query){
           int resultCommand = 0;
           try{
               Statement s = connect.createStatement();
               resultCommand = s.executeUpdate(query);
           }catch(SQLException ex){
               ex.printStackTrace();
           }
           return resultCommand;
       }
       
       public ArrayList<String> getTableNames(){
           ArrayList<String> tableNames = new ArrayList<String>();
           try{
                DatabaseMetaData dbmd = connect.getMetaData();
		ResultSet rs = dbmd.getTables(null, null, null, null);
		while (rs.next()) {
                    tableNames.add(rs.getString(3));
		}
           }catch(SQLException ex){
               ex.printStackTrace();
           }
           return tableNames;
       }
       
       public ArrayList<String> getNameRows(String tableName){
           ArrayList<String> namesRows = new ArrayList<String>();
           try{
               DatabaseMetaData dbmd = connect.getMetaData();
	       ResultSet rs = dbmd.getColumns(null, null, tableName, null);
               while (rs.next()) {
                    namesRows.add(rs.getString("COLUMN_NAME"));
		}
           }catch(SQLException ex){
               ex.printStackTrace();
           }
           return namesRows;
       }
}
