package com.github.donkeyrit.twinkle.panels.content;

import com.github.donkeyrit.twinkle.dal.repositories.interfaces.CarRepository;
import com.github.donkeyrit.twinkle.dal.models.Car;
import com.github.donkeyrit.twinkle.utils.AssetsRetriever;
import com.github.donkeyrit.twinkle.DataBase;

import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.JPanel;
import java.util.Date;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CarPanel extends JPanel 
{	
	private String nameCountry;
	private String status;
	private int imagesNum;
	private Car car;

	public CarPanel(CarRepository carRepository, DataBase database, JPanel panel, int carId) 
	{
		setLayout(null);

		status = getStatus(database, carId);
		imagesNum = carId;

		this.car = carRepository.getById(carId);
		this.nameCountry = car.getModelOfCar().getMark().getCountry().getCountryName();

		Font font = new Font("Arial", Font.BOLD, 13);
		Font alterfont = new Font("Arial", Font.ITALIC, 13);

		JLabel modelLab = new JLabel("Model:");
		modelLab.setBounds(200, 10, 60, 15);
		modelLab.setFont(alterfont);
		add(modelLab);

		JLabel modelLabel = new JLabel(this.car.getModelOfCar().getModelName());
		modelLabel.setBounds(290, 10, 150, 15);
		modelLabel.setFont(font);
		add(modelLabel);

		JLabel markLab = new JLabel("Mark:");
		markLab.setBounds(200, 30, 60, 15);
		markLab.setFont(alterfont);
		add(markLab);

		JLabel markLabel = new JLabel(this.car.getModelOfCar().getMark().getName());
		markLabel.setBounds(290, 30, 150, 15);
		markLabel.setFont(font);
		add(markLabel);

		JLabel bodyTypeLab = new JLabel("Type:");
		bodyTypeLab.setBounds(200, 50, 70, 15);
		bodyTypeLab.setFont(alterfont);
		add(bodyTypeLab);

		JLabel bodyTypeLabel = new JLabel(car.getModelOfCar().getBodyType().getType());
		bodyTypeLabel.setBounds(290, 50, 150, 15);
		bodyTypeLabel.setFont(font);
		add(bodyTypeLabel);

		JButton moreButton = new JButton("More");
		moreButton.setBounds(200, 70, 100, 20);
		moreButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Component[] mas = panel.getComponents();
				ContentPanel temp = null;
				for (int i = 0; i < mas.length; i++) {
					if (mas[i].getClass().toString().indexOf("EntryPoint$ContentPanel") != -1) {
						temp = (ContentPanel) mas[i];
					}
				}

				if(temp == null)
				{
					return;
				}

				AboutCarPanel newPanel = new AboutCarPanel(carRepository, database, panel, car);
				newPanel.setFilter(temp.conditionPanel);
				newPanel.setNumPage(temp.numOfPage);
				newPanel.setStartBut(temp.startBut);
				newPanel.setBounds(250, 100, 605, 550);
				panel.remove(temp);
				panel.add(newPanel);
				panel.revalidate();
				panel.repaint();
			}
		});
		add(moreButton);

	}

	//TODO: Extract to separate repository
	private String getStatus(DataBase database, int num) 
	{
		ResultSet statusSet = database
				.select("SELECT * FROM rent WHERE id_car = " + num + " ORDER BY end_date, plan_date DESC LIMIT 1");
		String status = "open";
		try {
			while (statusSet.next()) {
				Date rentDate = statusSet.getDate("end_date");
				if (rentDate == null) {
					status = "lock";
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		return status;
	}

	@Override
	public void paintComponent(Graphics g) {
		Image image = AssetsRetriever.retrieveAssetImageFromResources("assets/cars/min/" + imagesNum + ".png");
		g.drawImage(image, 10, 10, this);

		Image country = AssetsRetriever.retrieveAssetImageFromResources("assets/flags/" + nameCountry + ".png");
		g.drawImage(country, 425, 0, this);

		Image statusImage = AssetsRetriever.retrieveAssetImageFromResources("assets/status/" + status + ".png");
		g.drawImage(statusImage, 10, 10, this);
	}
}
