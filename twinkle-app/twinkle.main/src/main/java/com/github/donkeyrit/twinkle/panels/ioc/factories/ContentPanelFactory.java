package com.github.donkeyrit.twinkle.panels.ioc.factories;

import com.github.donkeyrit.twinkle.models.CarSearchFilterViewModel;
import com.github.donkeyrit.twinkle.panels.content.ContentPanel;

public interface ContentPanelFactory {
	ContentPanel create(CarSearchFilterViewModel filter);
}
