package com.github.donkeyrit.twinkle.panels.ioc.factories;

import com.github.donkeyrit.twinkle.dal.repositories.filters.CarQueryFilter;
import com.github.donkeyrit.twinkle.panels.content.ContentPanel;

public interface ContentPanelFactory {
	ContentPanel create(CarQueryFilter filter);
}
