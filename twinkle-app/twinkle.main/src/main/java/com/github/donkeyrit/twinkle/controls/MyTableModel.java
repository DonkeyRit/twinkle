package com.github.donkeyrit.twinkle.controls;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import jakarta.persistence.EntityManager;

import org.hibernate.Session;

public class MyTableModel extends AbstractTableModel {

	private final EntityManager entityManager;
	private final String tableName;
	private final String query;

	public MyTableModel(EntityManager entityManager, String name)
	{
		this.entityManager = entityManager;
		this.tableName = name;
		this.query = "SELECT * FROM " + tableName;
	}

	@Override
	public int getRowCount() {
		return getRows().size();
	}

	@Override
	public int getColumnCount() {
		return getColumnNames().size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return getRows().get(rowIndex).get(columnIndex);
	}

	@Override
	public String getColumnName(int c) {
		return getColumnNames().get(c);
	}

	private ArrayList<ArrayList<Object>> getRows() {
		ArrayList<ArrayList<Object>> allRows = new ArrayList<ArrayList<Object>>();
		entityManager.unwrap(Session.class).doWork(connection -> {
			try (Statement statement = connection.createStatement();
					ResultSet rs = statement.executeQuery(query)) {
				int columnCount = rs.getMetaData().getColumnCount();
				while (rs.next()) {
					ArrayList<Object> oneRow = new ArrayList<Object>();
					for (int i = 1; i <= columnCount; i++) {
						oneRow.add(rs.getObject(i));
					}
					allRows.add(oneRow);
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		});
		return allRows;
	}

	private ArrayList<String> getColumnNames() {
		ArrayList<String> names = new ArrayList<String>();
		entityManager.unwrap(Session.class).doWork((Connection connection) -> {
			DatabaseMetaData dbmd = connection.getMetaData();
			try (ResultSet rs = dbmd.getColumns(null, null, tableName, null)) {
				while (rs.next()) {
					names.add(rs.getString("COLUMN_NAME"));
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		});
		return names;
	}

}
