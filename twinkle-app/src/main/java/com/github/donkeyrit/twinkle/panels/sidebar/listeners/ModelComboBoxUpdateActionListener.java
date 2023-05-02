package com.github.donkeyrit.twinkle.panels.sidebar.listeners;

import com.github.donkeyrit.twinkle.dal.repositories.interfaces.ModelOfCarRepository;
import com.github.donkeyrit.twinkle.dal.models.MarkOfCar;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import java.util.ArrayList;
import java.util.List;

public class ModelComboBoxUpdateActionListener implements ActionListener 
{
	private ModelOfCarRepository modelOfCarRepository;
	private final JComboBox<MarkOfCar> markComboBox;
	private final JComboBox<String> modelComboBox;

	public ModelComboBoxUpdateActionListener(
		JComboBox<MarkOfCar> markComboBox, 
		JComboBox<String> modelComboBox, 
		ModelOfCarRepository modelOfCarRepository) 
	{
		this.markComboBox = markComboBox;
		this.modelComboBox = modelComboBox;
		this.modelOfCarRepository = modelOfCarRepository;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		MarkOfCar markSelected = (MarkOfCar) markComboBox.getSelectedItem();
		List<String> modelList = new ArrayList<String>();

		if(markSelected.getId() > -1)
		{
			int markId = markSelected.getId();
			modelList = modelOfCarRepository
				.getListByMark(markId)
				.map(model -> model.getModelName())
				.toList();
		}

		modelComboBox.removeAllItems();
		modelComboBox.addItem("All models");
		for (int i = 0; i < modelList.size(); i++) {
			modelComboBox.addItem(modelList.get(i));
		}
	}
}
