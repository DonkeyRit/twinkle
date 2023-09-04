package com.github.donkeyrit.twinkle.events.contracts;

import com.github.donkeyrit.twinkle.dal.repositories.filters.CarQueryFilter;

public interface ContentEventsListener {
	void onSettingsPageRequest();
	void onHomePageRequest();
	void onContentPageRequest(CarQueryFilter queryFilter);
	void onNextContentPageRequest(int pageNumber);
	void onNextContentPageRequest();
}
