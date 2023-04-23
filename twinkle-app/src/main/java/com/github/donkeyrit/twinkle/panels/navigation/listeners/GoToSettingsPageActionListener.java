package com.github.donkeyrit.twinkle.panels.navigation.listeners;

import com.github.donkeyrit.twinkle.panels.content.ContentCompositePanel;
import com.github.donkeyrit.twinkle.EntryPoint;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GoToSettingsPageActionListener implements ActionListener 
{
	private final ContentCompositePanel container;
	private final EntryPoint entryPoint; //TODO: Remove this

	public GoToSettingsPageActionListener(EntryPoint point, ContentCompositePanel container)
	{
		this.entryPoint = point;
		this.container = container;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		container
			.setSidebarPanel(this.entryPoint.new ChooseActionPanel())
			.setContentPanel(null)
			.show();
	}
}
