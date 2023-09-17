package com.github.donkeyrit.twinkle.panels.ioc.factories;

import com.github.donkeyrit.twinkle.panels.content.CarPanel;
import com.github.donkeyrit.twinkle.dal.models.Car;

public interface CarPanelFactory {
	CarPanel create(Car car);
}
