package com.github.donkeyrit.twinkle.events.contracts;

import com.github.donkeyrit.twinkle.dal.specifications.CarQuerySpecification;
import com.github.donkeyrit.twinkle.panels.content.CarPanel;
import com.github.donkeyrit.twinkle.dal.models.Car;

public interface NavigationPanelEventsListener {
	void onSettingsPageRequest();
	void onHomePageRequest();
	void onContentPageRequest(CarQuerySpecification queryFilter);
	void onNextContentPageRequest(int pageNumber);
	void onNextContentPageRequest(boolean direction);
	CarPanel onCarPanelCreateRequest(Car car);
}
