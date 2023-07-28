package com.github.donkeyrit.twinkle.panels.content.listeners;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.github.donkeyrit.twinkle.panels.content.ContentPanel;
import com.github.donkeyrit.twinkle.DataBase;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.CarRepository;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.RentRepository;

public class ScrollPageListener implements ActionListener
{ 
	private RentRepository rentRepository;
	private CarRepository carRepository;
	private DataBase dataBase;
	private JPanel panel;
	
	public ScrollPageListener(JPanel panel, CarRepository carRepository, RentRepository rentRepository, DataBase dataBase) 
	{
		this.panel = panel;
		this.dataBase = dataBase;
		this.carRepository = carRepository;
		this.rentRepository = rentRepository;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		
		JButton pressButton = (JButton) e.getSource();
		String textButton = pressButton.getText(); 
		int numPage = Integer.parseInt(textButton); 
		
		Component[] mas = panel.getComponents(); 
		ContentPanel temp = null; 
		for(int i = 0; i < mas.length; i++){
			if(mas[i].getClass().toString().indexOf("ContentPanel") != -1){
				temp = (ContentPanel) mas[i];
			}
		}
		
		if(temp == null)
		{
			return;
		}

		panel.remove(temp); 
		JPanel content = new ContentPanel(this.panel, this.carRepository, this.rentRepository, this.dataBase, temp.conditionPanel,numPage,temp.startBut); 
		content.setBounds(250,100,605,550); 
		panel.add(content); 
		panel.revalidate(); 
		panel.repaint(); 
	}
	
}

