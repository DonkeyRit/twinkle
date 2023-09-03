package com.github.donkeyrit.twinkle.events;

import com.github.donkeyrit.twinkle.dal.repositories.filters.CarQueryFilter;
import com.github.donkeyrit.twinkle.events.contracts.ContentEventsListener;
import com.github.donkeyrit.twinkle.panels.content.ContentPanel;

public class ContentEventsListenerImpl implements ContentEventsListener {

	@Override
	public void onSettingsPageRequest() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'onSettingsPageRequest'");
	}

	@Override
	public void onHomePageRequest() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'onHomeRequest'");
	}

	@Override
	public void onContentPageRequest(CarQueryFilter queryFilter) {
		// Component[] mas = panel.getComponents();
		// JPanel temp = null;
		// for (int i = 0; i < mas.length; i++) {
		// 	if (mas[i].getClass().toString().indexOf("ContentPanel") != -1
		// 			|| mas[i].getClass().toString().indexOf("AboutCarPanel") != -1) {
		// 		temp = (JPanel) mas[i];
		// 	}
		// }

		// panel.remove(temp);
		// JPanel content = new ContentPanel(panel, carRepository, rentRepository, database, filter);
		// content.setBounds(250, 100, 605, 550);
		// panel.add(content);
		// panel.revalidate();
		// panel.repaint();
	}

}
