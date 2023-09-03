package com.github.donkeyrit.twinkle.bll.ioc;

import com.github.donkeyrit.twinkle.bll.services.contracts.CarService;
import com.github.donkeyrit.twinkle.bll.services.contracts.LoginService;
import com.github.donkeyrit.twinkle.bll.services.DefaultCarService;
import com.github.donkeyrit.twinkle.bll.services.DefaultLoginService;
import com.google.inject.AbstractModule;

public class ServicesModules extends AbstractModule {
	
	@Override
    protected void configure() {
		bind(LoginService.class).to(DefaultLoginService.class);
		bind(CarService.class).to(DefaultCarService.class);
	}
}
