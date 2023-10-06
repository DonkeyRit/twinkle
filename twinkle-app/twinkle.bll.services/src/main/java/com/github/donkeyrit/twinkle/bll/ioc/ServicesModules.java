package com.github.donkeyrit.twinkle.bll.ioc;

import com.github.donkeyrit.twinkle.bll.services.interfaces.UserInfoService;
import com.github.donkeyrit.twinkle.bll.services.interfaces.CarService;
import com.github.donkeyrit.twinkle.bll.services.UserInfoServiceImpl;
import com.github.donkeyrit.twinkle.bll.services.DefaultCarService;

import com.google.inject.AbstractModule;

public class ServicesModules extends AbstractModule {
	
	@Override
    protected void configure() {
		bind(CarService.class).to(DefaultCarService.class);
		bind(UserInfoService.class).to(UserInfoServiceImpl.class);
	}
}
