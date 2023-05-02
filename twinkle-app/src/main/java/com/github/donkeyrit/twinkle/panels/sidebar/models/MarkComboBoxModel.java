package com.github.donkeyrit.twinkle.panels.sidebar.models;

import com.github.donkeyrit.twinkle.dal.models.MarkOfCar;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import java.util.List;

public class MarkComboBoxModel extends AbstractListModel<MarkOfCar> implements ComboBoxModel<MarkOfCar> 
{
	private List<MarkOfCar> marks;
	private MarkOfCar current;
	
	public MarkComboBoxModel(List<MarkOfCar> marks, String defaultValue) 
	{
		MarkOfCar defaultMark = new MarkOfCar();
		defaultMark.setId(-1);
		defaultMark.setName(defaultValue);

		marks.add(0, defaultMark);
		this.marks = marks;
		this.current = defaultMark;
	}

	@Override
	public void setSelectedItem(Object anItem) 
	{
		current = (MarkOfCar) anItem;
	}

	@Override
	public Object getSelectedItem() 
	{
		return current;
	}

	@Override
	public int getSize() 
	{
		return this.marks.size();
	}

	@Override
	public MarkOfCar getElementAt(int index) 
	{
		return this.marks.get(index);
	}
}
