package com.github.donkeyrit.twinkle.panels.ioc.factories;

import com.github.donkeyrit.twinkle.dal.specifications.CarQuerySpecification;
import com.github.donkeyrit.twinkle.panels.content.ContentPanel;

public interface ContentPanelFactory {
	ContentPanel create(CarQuerySpecification filter);
}
