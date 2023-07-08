package com.github.donkeyrit.twinkle.panels.navigation.listeners;

import com.github.donkeyrit.twinkle.panels.content.ContentCompositePanel;
import com.github.donkeyrit.twinkle.panels.settings.ChooseActionPanel;
import com.github.donkeyrit.twinkle.DataBase;

import java.awt.event.ActionListener;

import javax.swing.JPanel;

import java.awt.event.ActionEvent;

public class GoToSettingsPageActionListener implements ActionListener 
{
	private final ContentCompositePanel container;
	private final DataBase database;
	private final JPanel panel;

	public GoToSettingsPageActionListener(ContentCompositePanel container, DataBase database, JPanel panel)
	{
		this.database = database;
		this.panel = panel;
		this.container = container;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		container
			.setSidebarPanel(new ChooseActionPanel(database, panel))
			.setContentPanel(null)
			.show();
	}
}
