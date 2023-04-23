package com.github.donkeyrit.twinkle.layouts;

import java.awt.CardLayout;
import javax.swing.JPanel;

import java.util.HashMap;
import java.util.Map;

public class CustomCardLayoutImpl extends CardLayout implements CustomCardLayout
{
	private final Map<String, JPanel> panels;
	private final JPanel container;

	public CustomCardLayoutImpl(JPanel container)
	{
		this.panels = new HashMap<>();
		this.container = container;
	}

	@Override
	public void putPanel(String key, JPanel panel)
	{
		this.panels.put(key, panel);
	}

	@Override
	public void showPanel(String key)
	{
		// Handle if doesn't exist
		JPanel panel = panels.get(key);
		this.container.removeAll();
		this.container.add(panel, key);
		this.show(container, key);
	}
}
