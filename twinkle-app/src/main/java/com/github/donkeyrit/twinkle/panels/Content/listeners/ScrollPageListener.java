package com.github.donkeyrit.twinkle.panels.content.listeners;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.github.donkeyrit.twinkle.DataBase;
import com.github.donkeyrit.twinkle.EntryPoint;
import com.github.donkeyrit.twinkle.panels.content.ContentPanel;

public class ScrollPageListener implements ActionListener
{ 
	private JPanel panel;
	private DataBase dataBase;
	private EntryPoint entryPoint;
	
	public ScrollPageListener(JPanel panel, DataBase dataBase, EntryPoint entryPoint) 
	{
		this.panel = panel;
		this.dataBase = dataBase;
		this.entryPoint = entryPoint;
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
		JPanel content = new ContentPanel(this.entryPoint, this.panel, this.dataBase, temp.conditionPanel,numPage,temp.startBut); 
		content.setBounds(250,100,605,550); 
		panel.add(content); 
		panel.revalidate(); 
		panel.repaint(); 
	}
	
}

