package com.github.donkeyrit.twinkle.panels.content.listeners;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.github.donkeyrit.twinkle.panels.content.ContentPanel;
import com.github.donkeyrit.twinkle.EntryPoint;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.CarRepository;
import com.github.donkeyrit.twinkle.DataBase;

public class ScrollPageListener implements ActionListener
{ 
	private DataBase dataBase;
	private JPanel panel;
	private CarRepository carRepository;
	
	public ScrollPageListener(JPanel panel, CarRepository carRepository, DataBase dataBase) 
	{
		this.panel = panel;
		this.dataBase = dataBase;
		this.carRepository = carRepository;
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
		
		panel.remove(temp); 
		JPanel content = new ContentPanel(this.panel, this.dataBase, this.carRepository, temp.conditionPanel,numPage,temp.startBut); 
		content.setBounds(250,100,605,550); 
		panel.add(content); 
		panel.revalidate(); 
		panel.repaint(); 
	}
	
}

