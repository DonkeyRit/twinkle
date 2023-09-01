package com.github.donkeyrit.twinkle.panels.ioc;

import com.github.donkeyrit.twinkle.events.contracts.LoginEventsListener;
import com.github.donkeyrit.twinkle.events.AuthenticationListener;
import com.github.donkeyrit.twinkle.panels.signup.SignupPanel;
import com.github.donkeyrit.twinkle.panels.login.LoginPanel;
import com.github.donkeyrit.twinkle.frame.MainFrame;

import com.google.inject.AbstractModule;

public class SwingUiModules extends AbstractModule {

	@Override
	protected void configure() {

		bind(MainFrame.class).toInstance(new MainFrame("Rent car"));

		registerListeners();
		registerPanels();
	}

	private void registerListeners() {
		bind(LoginEventsListener.class).to(AuthenticationListener.class);
	}

	private void registerPanels() {
		bind(LoginPanel.class);
		bind(SignupPanel.class);
	}
}
