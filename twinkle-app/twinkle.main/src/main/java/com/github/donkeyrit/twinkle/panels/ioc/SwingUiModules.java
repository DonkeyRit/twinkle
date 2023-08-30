package com.github.donkeyrit.twinkle.panels.ioc;

import com.github.donkeyrit.twinkle.bll.services.contracts.LoginService;
import com.github.donkeyrit.twinkle.bll.services.DefaultLoginService;
import com.google.inject.AbstractModule;

public class SwingUiModules extends AbstractModule{

	@Override
    protected void configure() {

		bind(LoginService.class).to(DefaultLoginService.class);
	}
}
