package com.github.donkeyrit.twinkle.events.contracts;

import com.github.donkeyrit.twinkle.dal.repositories.filters.CarQueryFilter;
import com.github.donkeyrit.twinkle.panels.content.CarPanel;
import com.github.donkeyrit.twinkle.dal.models.Car;

public interface NavigationPanelEventsListener {
	void onSettingsPageRequest();
	void onHomePageRequest();
	void onContentPageRequest(CarQueryFilter queryFilter);
	void onNextContentPageRequest(int pageNumber);
	void onNextContentPageRequest();
	CarPanel onCarPanelCreateRequest(Car car);
}
