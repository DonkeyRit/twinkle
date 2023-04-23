package com.github.donkeyrit.twinkle.listeners;

import com.github.donkeyrit.twinkle.panels.common.ResettablePanel;
import com.github.donkeyrit.twinkle.panels.common.SwitchedPanel;
import java.awt.event.ActionEvent;

public class ResetablePanelSwitcherActionListener extends PanelSwitcherActionListener
{
	private final ResettablePanel resettablePanel;

	public ResetablePanelSwitcherActionListener(SwitchedPanel panelContainer, ResettablePanel resettablePanel) 
	{
		super(panelContainer);
		this.resettablePanel = resettablePanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		this.resettablePanel.reset();
		super.actionPerformed(e);
	}
}
