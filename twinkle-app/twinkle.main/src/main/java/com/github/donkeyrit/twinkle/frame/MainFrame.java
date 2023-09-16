package com.github.donkeyrit.twinkle.frame;

import com.github.donkeyrit.twinkle.panels.common.SwitchedPanel;
import com.google.inject.Singleton;

import javax.swing.*;
import java.awt.*;

@Singleton
public class MainFrame extends JFrame 
{
    private final SwitchedPanel switchedPanel;

	public MainFrame(String title) 
    {
        super(title);
		this.switchedPanel = new SwitchedPanel();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(875,700); 
        setLocationRelativeTo(null);

        // Add container to frame
        add(switchedPanel, BorderLayout.CENTER);
    }


    public MainFrame(String title, SwitchedPanel switchedPanel) 
    {
        super(title);
		this.switchedPanel = switchedPanel;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(875,700); 
        setResizable(false);
        setLocationRelativeTo(null);

        // Add container to frame
        add(switchedPanel, BorderLayout.CENTER);
    }

	public SwitchedPanel getSwitchedPanel()
	{
		return this.switchedPanel;
	}
}

