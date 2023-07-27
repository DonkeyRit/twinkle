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
	private Car car;

	private int imagesNum;
	private String nameCountry;
	private String bodyTypeName;
	private String status;

	public CarPanel(CarRepository carRepository, DataBase database, JPanel panel, int num) 
	{
		setLayout(null);

		status = getStatus(database, num);
		imagesNum = num;
		
		String query = "SELECT id,model_year,info,cost,model_name,mark_name,country_name,body_type_name FROM\n"
				+ "(SELECT id,model_year,info,cost,model_name,id_body_type,mark_name,country_name FROM\n"
				+ "(SELECT id,model_year,image,info,cost,model_name,id_body_type,mark_name,id_country FROM \n"
				+ "(SELECT id,model_year,image,info,cost,model_name,id_mark,id_body_type FROM car \n"
				+ "INNER JOIN model ON car.id_model = model.id_model) as join1\n"
				+ "INNER JOIN mark ON join1.id_mark = mark.id_mark) as join2\n"
				+ "INNER JOIN country ON join2.id_country = country.id_country) as join3\n"
				+ "INNER JOIN body_type ON join3.id_body_type = body_type.id_body_type WHERE id = " + imagesNum;

		ResultSet carSet = database.select(query);
		try {
			while (carSet.next()) {
				nameCountry = carSet.getString("country_name");
				bodyTypeName = carSet.getString("body_type_name");
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		this.car = carRepository.getById(num);


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

		JLabel bodyTypeLabel = new JLabel(bodyTypeName);
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

				AboutCarPanel newPanel = new AboutCarPanel(
					carRepository,
					database,
					panel,
					imagesNum, 
					car.getModelYear(), 
					car.getCost(), 
					car.getModelOfCar().getModelName(), 
					car.getModelOfCar().getMark().getName(), 
					nameCountry,
					car.getInfo(), 
					bodyTypeName);
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
