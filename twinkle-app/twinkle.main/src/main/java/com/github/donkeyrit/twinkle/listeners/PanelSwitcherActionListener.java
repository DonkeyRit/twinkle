package com.github.donkeyrit.twinkle.listeners;

import com.github.donkeyrit.twinkle.panels.common.SwitchedPanel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelSwitcherActionListener implements ActionListener
{
	private SwitchedPanel panelContainer;

    public PanelSwitcherActionListener(SwitchedPanel panelContainer) 
	{
        this.panelContainer = panelContainer;
    }

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		String actionCommand = e.getActionCommand();
		if(!actionCommand.isEmpty())
		{
			this.panelContainer.showPanel(actionCommand);
		}	
	}
}
