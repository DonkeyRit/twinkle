package com.github.donkeyrit.twinkle.panels.content;

import com.github.donkeyrit.twinkle.dal.repositories.filters.CarQueryFilter;
import com.github.donkeyrit.twinkle.bll.services.interfaces.CarService;
import com.github.donkeyrit.twinkle.dal.models.Car;
import com.github.donkeyrit.twinkle.dal.models.filters.Paging;
import com.github.donkeyrit.twinkle.events.contracts.NavigationPanelEventsListener;
import com.github.donkeyrit.twinkle.panels.nestedpanels.PageNavigatorPanel;
import com.github.donkeyrit.twinkle.utils.AssetsRetriever;

import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.Inject;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.util.List;

public class ContentPanel extends JPanel {
	private final CarQueryFilter filter;

	private final NavigationPanelEventsListener contentEventsListener;
	private final CarService carService;

	public CarQueryFilter getFilter() {
		return filter;
	}

	@Inject
	public ContentPanel(
		NavigationPanelEventsListener contentEventsListener, 
		CarService carService, 
		@Assisted CarQueryFilter filter
	) {
		setLayout(new BorderLayout());

		this.contentEventsListener = contentEventsListener;
		this.carService = carService;
		this.filter = filter;

		// Header panel
		JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		// Header label
		JLabel contentMainLabel = new JLabel("List of cars:");
		Font font = new Font("Arial", Font.BOLD, 13);
		contentMainLabel.setFont(font);
		contentMainLabel.setBounds(250, 10, 100, 20);
		headerPanel.add(contentMainLabel);

		// Reload button
		JButton reload = new JButton();
		reload.setBounds(568, 10, 16, 16);
		ImageIcon iconExit = AssetsRetriever.retrieveAssetImageIconFromResources("assets/buttons/reload.png");
		reload.setIcon(iconExit);
		reload.setHorizontalTextPosition(SwingConstants.LEFT);
		reload.addActionListener(e -> contentEventsListener.onHomePageRequest());
		headerPanel.add(reload);

		add(headerPanel, BorderLayout.NORTH);

		// Cars container
		JPanel carsContainer = new JPanel();
        carsContainer.setLayout(new BoxLayout(carsContainer, BoxLayout.Y_AXIS));
        
		List<Car> filteredCars = this.carService.getList(filter);
		for (Car car : filteredCars) {
			CarPanel panel = this.contentEventsListener.onCarPanelCreateRequest(car);
            panel.setBorder(new LineBorder(new Color(0, 163, 163), 4));
            carsContainer.add(panel);
		}
		add(carsContainer, BorderLayout.CENTER);

		Paging paging = filter.getPaging();
		PageNavigatorPanel panel = new PageNavigatorPanel(
			contentEventsListener, 
			paging.getPageNumber(), 
			paging.getPageSize(), 
			100);
		panel.setBounds(205, 520, 400, 30);
		add(panel, BorderLayout.SOUTH);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(237, 237, 237));
		g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 30, 25);
	}
}
