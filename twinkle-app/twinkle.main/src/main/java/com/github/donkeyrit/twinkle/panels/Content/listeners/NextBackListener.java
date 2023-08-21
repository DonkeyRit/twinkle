package com.github.donkeyrit.twinkle.panels.content.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.github.donkeyrit.twinkle.panels.content.ContentPanel;
import com.github.donkeyrit.twinkle.DataBase;
import com.github.donkeyrit.twinkle.dal.models.filters.Paging;
import com.github.donkeyrit.twinkle.dal.repositories.filters.CarQueryFilter;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.CarRepository;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.RentRepository;

public class NextBackListener implements ActionListener {
	private RentRepository rentRepository;
	private CarRepository carRepository;
	private DataBase dataBase;
	private JPanel panel;

	public NextBackListener(JPanel panel, CarRepository carRepository, RentRepository rentRepository,
			DataBase dataBase) {
		this.panel = panel;
		this.dataBase = dataBase;
		this.carRepository = carRepository;
		this.rentRepository = rentRepository;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton selectedBut = (JButton) e.getSource();
		ContentPanel outerPanel = (ContentPanel) selectedBut.getParent().getParent();

		String nameBut = selectedBut.getIcon().toString();
		int indexName = nameBut.lastIndexOf("/");

		panel.remove(outerPanel);

		JPanel addPanel = null;
		CarQueryFilter carQueryFilter = outerPanel.getFilter();
		if (nameBut.substring(indexName + 1, indexName + 5).equals("next")) {
			outerPanel.getFilter().setPaging(outerPanel.getFilter().getPaging().next());
		} else {
			outerPanel.getFilter().setPaging(outerPanel.getFilter().getPaging().previous());
		}
		addPanel = new ContentPanel(this.panel, this.carRepository, this.rentRepository, this.dataBase, carQueryFilter);
		panel.add(addPanel);
		addPanel.setBounds(250, 100, 605, 550);
		panel.revalidate();
		panel.repaint();
	}

}
