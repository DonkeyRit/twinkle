package com.github.donkeyrit.twinkle.panels.content;

import com.google.inject.Inject;
import javax.swing.JPanel;
import java.awt.Color;

public class ContentCompositePanel extends JPanel
{
	private JPanel navigationPanel;
	private JPanel sidebarPanel;
	private JPanel contentPanel;

	@Inject
    public ContentCompositePanel(NavigationPanel navigationPanel, SideBarFilterPanel sideBarFilterPanel)
    {
        setBackground(new Color(255,255,255)); 
        setLayout(null); 
		setNavigationPanel(navigationPanel);
		setSidebarPanel(sideBarFilterPanel);
    }

	public ContentCompositePanel setNavigationPanel(JPanel navigationPanel)
	{
		if(this.navigationPanel != null) this.remove(this.navigationPanel);

		this.navigationPanel = navigationPanel;
		if(this.navigationPanel != null) 
		{
			this.navigationPanel.setBounds(0,0,875,80);
			this.add(this.navigationPanel);
		}
		
		return this;
	}

	public ContentCompositePanel setSidebarPanel(JPanel sidebarPanel)
	{
		if(this.sidebarPanel != null) this.remove(this.sidebarPanel);
		this.sidebarPanel = sidebarPanel;
		if(this.sidebarPanel != null) 
		{
			this.sidebarPanel.setBounds(30, 100, 200, 550);
			this.add(this.sidebarPanel);
		}
		return this;
	}

	public ContentCompositePanel setContentPanel(JPanel contentPanel)
	{
		if(this.contentPanel != null) this.remove(this.contentPanel);
		this.contentPanel = contentPanel;
		if(this.contentPanel != null) 
		{
			this.contentPanel.setBounds(250,100,605,550);
			this.add(this.contentPanel);
		}
		return this;
	}

	public void show()
	{
		this.revalidate();
		this.repaint();
	}
}
