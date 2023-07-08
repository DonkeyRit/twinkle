package com.github.donkeyrit.twinkle.panels.content.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.github.donkeyrit.twinkle.DataBase;
import com.github.donkeyrit.twinkle.EntryPoint;
import com.github.donkeyrit.twinkle.panels.content.ContentPanel;

public class NextBackListener implements ActionListener
{
	private JPanel panel;
	private DataBase dataBase;
	private EntryPoint entryPoint;

	public NextBackListener(JPanel panel, DataBase dataBase, EntryPoint entryPoint) 
	{
		this.panel = panel;
		this.dataBase = dataBase;
		this.entryPoint = entryPoint;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		JButton selectedBut = (JButton) e.getSource(); 
		ContentPanel outerPanel = (ContentPanel) selectedBut.getParent().getParent();
		int numNowPage = outerPanel.startBut; 
		
		String nameBut = selectedBut.getIcon().toString();
		int indexName = nameBut.lastIndexOf("/");
		
		panel.remove(outerPanel);
		
		JPanel addPanel = null;
		if(nameBut.substring(indexName + 1, indexName + 5).equals("next")){
			addPanel = new ContentPanel(this.entryPoint, this.panel, this.dataBase, outerPanel.conditionPanel,numNowPage + 5,numNowPage + 5);
			panel.add(addPanel);
		}else{
		   addPanel = new ContentPanel(this.entryPoint, this.panel, this.dataBase, outerPanel.conditionPanel,numNowPage - 5,numNowPage - 5);
			panel.add(addPanel);
		}
		addPanel.setBounds(250,100,605,550);
		panel.revalidate();
		panel.repaint();
	}
	
}

