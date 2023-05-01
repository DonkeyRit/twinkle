package com.github.donkeyrit.twinkle.panels.sidebar.listeners;

import com.github.donkeyrit.twinkle.DataBase;
import com.github.donkeyrit.twinkle.dal.models.MarkOfCar;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class ModelComboBoxUpdateActionListener implements ActionListener 
{
	private final JComboBox<MarkOfCar> markComboBox;
	private final JComboBox<String> modelComboBox;
	private final DataBase database;

	public ModelComboBoxUpdateActionListener(JComboBox<MarkOfCar> markComboBox, JComboBox<String> modelComboBox, DataBase dataBase) 
	{
		this.markComboBox = markComboBox;
		this.modelComboBox = modelComboBox;
		this.database = dataBase;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		MarkOfCar markSelected = (MarkOfCar) markComboBox.getSelectedItem();
		List<Integer> idMarkList = new ArrayList<Integer>();
		List<String> modelList = new ArrayList<String>();

		if (!markSelected.getName().equals("All marks")) {
			ResultSet idMarkSet = database.select("SELECT id_mark FROM mark WHERE mark_name = '" + markSelected + "'");

			try {
				while (idMarkSet.next()) {
					idMarkList.add(idMarkSet.getInt("id_mark"));
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

			String queryModel = "SELECT model_name FROM model WHERE id_mark in (";
			for (int i = 0; i < idMarkList.size(); i++) {
				queryModel += idMarkList.get(i);
				if (i == idMarkList.size() - 1) {
					queryModel += ")";
				} else {
					queryModel += ",";
				}
			}

			ResultSet modelSet = database.select(queryModel);
			try {
				while (modelSet.next()) {
					modelList.add(modelSet.getString("model_name"));
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		modelComboBox.removeAllItems();
		modelComboBox.addItem("All models");
		for (int i = 0; i < modelList.size(); i++) {
			modelComboBox.addItem(modelList.get(i));
		}
	}

}
