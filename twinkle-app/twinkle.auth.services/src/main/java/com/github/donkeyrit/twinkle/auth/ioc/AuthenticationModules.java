package com.github.donkeyrit.twinkle.auth.ioc;

import com.github.donkeyrit.twinkle.auth.services.interfaces.LoginService;
import com.github.donkeyrit.twinkle.auth.services.DefaultLoginService;
import com.google.inject.AbstractModule;

public class AuthenticationModules extends AbstractModule {
	
	@Override
    protected void configure() {
		bind(LoginService.class).to(DefaultLoginService.class);
	}
}