package com.github.donkeyrit.twinkle.panels.content;

import com.github.donkeyrit.twinkle.panels.ioc.factories.ContentPanelFactory;
import com.github.donkeyrit.twinkle.dal.models.filters.Paging;
import com.github.donkeyrit.twinkle.dal.repositories.filters.CarQueryFilter;

import com.google.inject.Singleton;
import com.google.inject.Inject;

import javax.swing.JPanel;
import java.awt.Color;
import java.util.Optional;

@Singleton
public class ContentCompositePanel extends JPanel
{
	private JPanel navigationPanel;
	private JPanel sidebarPanel;
	private JPanel contentPanel;

	@Inject
    public ContentCompositePanel(
		NavigationPanel navigationPanel, 
		SideBarFilterPanel sideBarFilterPanel, 
		ContentPanelFactory contentPanelFactory
	){
        setBackground(new Color(255,255,255)); 
        setLayout(null); 
		setNavigationPanel(navigationPanel);
		setSidebarPanel(sideBarFilterPanel);
		setContentPanel(contentPanelFactory.create(new CarQueryFilter(new Paging(1, 4))));
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
		
		this.revalidate();
		this.repaint();
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

		this.revalidate();
		this.repaint();
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

		this.revalidate();
		this.repaint();
		return this;
	}

	public Optional<JPanel> getContentPanel(){
		return Optional.of(this.contentPanel);
	}
}
