package com.github.donkeyrit.twinkle.panels.common;

import com.github.donkeyrit.twinkle.layouts.CustomCardLayoutImpl;
import com.github.donkeyrit.twinkle.layouts.CustomCardLayout;

import javax.swing.JPanel;

public class SwitchedPanel extends JPanel
{
	private final CustomCardLayout layoutManager;

	public SwitchedPanel()
	{
        this.layoutManager = new CustomCardLayoutImpl(this);
        this.setLayout(this.layoutManager);
	}

	public SwitchedPanel addPanel(String key, JPanel panel)
	{
		this.layoutManager.putPanel(key, panel);
		return this;
	}

	public void showPanel(String key)
	{
		this.layoutManager.showPanel(key);
	}
}
