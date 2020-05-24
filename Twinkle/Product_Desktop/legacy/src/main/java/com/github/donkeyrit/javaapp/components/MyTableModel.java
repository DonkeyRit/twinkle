package com.github.donkeyrit.javaapp.components;

import com.github.donkeyrit.javaapp.database.DatabaseProvider;

import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MyTableModel extends AbstractTableModel {

    private String tableName;
    private String query;
    private ResultSet rs;
    private DatabaseProvider database;

    public MyTableModel(DatabaseProvider database, String name) {
        this.database = database;
        tableName = name;
        query = "SELECT * FROM " + tableName;
        rs = database.select(query);
    }


    @Override
    public int getRowCount() {
        int size = 0;
        try{
            rs.last();
            size = rs.getRow();
            rs.beforeFirst();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return size;
    }

    @Override
    public int getColumnCount() {
        int sizeRows = 0;
        try{
            sizeRows = rs.getMetaData().getColumnCount();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return sizeRows;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ArrayList<ArrayList<Object>> allRows = new ArrayList<ArrayList<Object>>();
        try{
            while(rs.next()){
                ArrayList<Object> oneRow = new ArrayList<Object>();
                int sizeRows = rs.getMetaData().getColumnCount();
                for(int i = 1; i < sizeRows+1; i++){
                    oneRow.add(rs.getObject(i));
                }
                allRows.add(oneRow);
            }
            rs.beforeFirst();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return allRows.get(rowIndex).get(columnIndex);
    }

    @Override
    public String getColumnName(int c) {
        ArrayList<String> names = database.getNameRows(tableName);
        return names.get(c);
    }

}
