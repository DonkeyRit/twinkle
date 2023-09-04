package com.github.donkeyrit.twinkle.panels.content;

import com.github.donkeyrit.twinkle.dal.repositories.filters.CarQueryFilter;
import com.github.donkeyrit.twinkle.dal.models.filters.Paging;
import com.github.donkeyrit.twinkle.dal.models.Car;
import com.github.donkeyrit.twinkle.events.contracts.ContentEventsListener;
import com.github.donkeyrit.twinkle.bll.services.contracts.CarService;
import com.github.donkeyrit.twinkle.utils.AssetsRetriever;

import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Box;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class ContentPanel extends JPanel {
	private final CarQueryFilter filter;

	private final ContentEventsListener contentEventsListener;
	private final CarService carService;

	public CarQueryFilter getFilter() {
		return filter;
	}

	@Inject
	public ContentPanel(
		ContentEventsListener contentEventsListener, 
		CarService carService, 
		@Assisted CarQueryFilter filter
	) {
		setLayout(null);
		this.contentEventsListener = contentEventsListener;
		this.carService = carService;

		this.filter = filter;

		JLabel contentMainLabel = new JLabel("List of cars:");
		Font font = new Font("Arial", Font.BOLD, 13);
		contentMainLabel.setFont(font);
		contentMainLabel.setBounds(250, 10, 100, 20);
		add(contentMainLabel);

		JButton reload = new JButton();
		reload.setBounds(568, 10, 16, 16);
		ImageIcon iconExit = AssetsRetriever.retrieveAssetImageIconFromResources("assets/buttons/reload.png");
		reload.setIcon(iconExit);
		reload.setHorizontalTextPosition(SwingConstants.LEFT);
		reload.addActionListener(e -> contentEventsListener.onHomePageRequest());
		add(reload);

		List<Car> filteredCars = this.carService.getList(filter);
		int j = 0;

		for (Car car : filteredCars) {
			// TODO: Show cars
			// JPanel temp = new CarPanel(carRepository, rentRepository, database, panel,
			// car.getId());
			// temp.setBorder(new LineBorder(new Color(0,163,163), 4));
			// temp.setBounds(20,40 + j * 120,565,100);
			// j++;
			// add(temp);
		}

		ShowPageNumbers(filter.getPaging());
	}

	private void ShowPageNumbers(Paging paging) {
		// TODO: Move to separate panel

		int currentPageNumber = paging.getPageNumber();
		int firstPageNumber = ((int) (currentPageNumber / 5)) * 5 + 1;
		int endPageNumber = firstPageNumber + 4;

		Box buttonBox = Box.createHorizontalBox();
		buttonBox.setBounds(205, 520, 400, 30);

		if (firstPageNumber >= 5) {
			JButton backBut = new JButton(
					AssetsRetriever.retrieveAssetImageIconFromResources("assets/buttons/back.png"));
			backBut.addActionListener(e -> contentEventsListener.onNextContentPageRequest());

			buttonBox.add(backBut);
		}

		Font buttonFont = new Font("Arial", Font.ITALIC, 10);
		ArrayList<JButton> buttonsList = new ArrayList<JButton>();
		for (int i = firstPageNumber; i < endPageNumber; i++) {
			JButton temp = new JButton(i + "");

			temp.setFont(buttonFont);
			temp.addActionListener(e -> {
				JButton pressButton = (JButton) e.getSource();
				int pageNumber = Integer.parseInt(pressButton.getText());
				contentEventsListener.onNextContentPageRequest(pageNumber);
			});

			buttonBox.add(temp);
			buttonsList.add(temp);
		}

		add(buttonBox);
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(new Color(237, 237, 237));
		g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 30, 25);
	}
}
