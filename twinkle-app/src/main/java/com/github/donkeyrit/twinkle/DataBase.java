package com.github.donkeyrit.twinkle;


import java.sql.*;
import java.util.*;

public class DataBase 
{
    private Connection connect;

    private String DB_CONNECTION = "jdbc:postgresql://127.0.0.1:5432/carrental";
    private String DB_USER = "twinkle_user";
    private String DB_PASSWORD = "Sr412Tqew!";

    public DataBase() 
    {
        try 
        {
            connect = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        }
    }

    public ResultSet select(String query) 
    {
        ResultSet rs = null;
        try 
        {
            Statement s = connect.createStatement();
            rs = s.executeQuery(query);
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return rs;
    }

    public int insert(String query) 
    {
        int resultCommand = 0;
        try 
        {
            Statement s = connect.createStatement();
            resultCommand = s.executeUpdate(query);
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return resultCommand;
    }

    public int update(String query) 
    {
        int resultCommand = 0;
        try 
        {
            Statement s = connect.createStatement();
            resultCommand = s.executeUpdate(query);
        } 
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        }
        return resultCommand;
    }

    public ArrayList<String> getTableNames()
    {
        ArrayList<String> tableNames = new ArrayList<String>();
        try 
        {
            DatabaseMetaData dbmd = connect.getMetaData();
            ResultSet rs = dbmd.getTables(null, null, null, null);
            while (rs.next()) 
            {
                tableNames.add(rs.getString(3));
            }
        } 
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        }
        return tableNames;
    }

    public ArrayList<String> getNameRows(String tableName) 
    {
        ArrayList<String> namesRows = new ArrayList<String>();
        try 
        {
            DatabaseMetaData dbmd = connect.getMetaData();
            ResultSet rs = dbmd.getColumns(null, null, tableName, null);
            while (rs.next()) 
            {
                namesRows.add(rs.getString("COLUMN_NAME"));
            }
        } 
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        }
        return namesRows;
    }
}
