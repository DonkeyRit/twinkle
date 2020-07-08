package com.github.donkeyrit.javaapp.database;

import com.github.donkeyrit.javaapp.database.DatabaseModelProviders.CarModelProvider;
import com.github.donkeyrit.javaapp.database.DatabaseModelProviders.ClientModelProvider;
import com.github.donkeyrit.javaapp.database.DatabaseModelProviders.InjuryModelProvider;
import com.github.donkeyrit.javaapp.database.DatabaseModelProviders.UserModelProvider;

import java.sql.*;
import java.util.ArrayList;

public abstract class DatabaseProvider {

    protected Connection connection;

    public UserModelProvider getUserModelProvider() {
        return new UserModelProvider(this);
    }
    public CarModelProvider getCarModelProvider() {
        return new CarModelProvider(this);
    }
    public ClientModelProvider getClientModelProvider() {return new ClientModelProvider(this);}
    public InjuryModelProvider getInjuryModelProvider() {return new InjuryModelProvider(this);}

    public DatabaseProvider() {
        initialize();
    }

    protected abstract void initialize();

    public ResultSet select(String query) {
        ResultSet rs = null;
        try {
            Statement s = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = s.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    public int insert(String query) {
        int resultCommand = 0;
        try {
            Statement s = connection.createStatement();
            resultCommand = s.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultCommand;
    }

    public int update(String query) {
        int resultCommand = 0;
        try {
            Statement s = connection.createStatement();
            resultCommand = s.executeUpdate(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultCommand;
    }

    public ArrayList<String> getTableNames() {
        ArrayList<String> tableNames = new ArrayList<>();
        try {
            DatabaseMetaData dbmd = connection.getMetaData();
            ResultSet rs = dbmd.getTables("carrental", null, null, null);
            while (rs.next()) {
                tableNames.add(rs.getString(3));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return tableNames;
    }

    public ArrayList<String> getNameRows(String tableName) {
        ArrayList<String> namesRows = new ArrayList<>();
        try {
            DatabaseMetaData dbmd = connection.getMetaData();
            ResultSet rs = dbmd.getColumns(null, null, tableName, null);
            while (rs.next()) {
                namesRows.add(rs.getString("COLUMN_NAME"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return namesRows;
    }
}
