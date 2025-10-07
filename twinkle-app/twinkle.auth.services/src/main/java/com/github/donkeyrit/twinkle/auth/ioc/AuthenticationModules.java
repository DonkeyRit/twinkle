package com.github.donkeyrit.twinkle.auth.ioc;

import com.github.donkeyrit.twinkle.auth.services.interfaces.LoginService;
import com.github.donkeyrit.twinkle.auth.services.DefaultLoginService;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class AuthenticationModules extends AbstractModule {

	@Override
    protected void configure() {
		// Singleton so the login-attempt limiter's state is shared across every
		// panel that requests a LoginService, instead of each getting its own.
		bind(LoginService.class).to(DefaultLoginService.class).in(Singleton.class);
	}
}