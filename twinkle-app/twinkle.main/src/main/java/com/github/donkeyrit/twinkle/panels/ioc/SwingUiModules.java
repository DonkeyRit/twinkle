package com.github.donkeyrit.twinkle.panels.ioc;

import com.github.donkeyrit.twinkle.events.contracts.ContentEventsListener;
import com.github.donkeyrit.twinkle.events.contracts.LoginEventsListener;
import com.github.donkeyrit.twinkle.events.AuthenticationListener;
import com.github.donkeyrit.twinkle.events.ContentEventsListenerImpl;
import com.github.donkeyrit.twinkle.panels.authentication.LoginPanel;
import com.github.donkeyrit.twinkle.panels.authentication.SignupPanel;
import com.github.donkeyrit.twinkle.panels.content.ContentCompositePanel;
import com.github.donkeyrit.twinkle.panels.content.NavigationPanel;
import com.github.donkeyrit.twinkle.panels.content.SideBarFilterPanel;
import com.github.donkeyrit.twinkle.panels.ioc.factories.CarPanelFactory;
import com.github.donkeyrit.twinkle.panels.ioc.factories.ContentPanelFactory;
import com.github.donkeyrit.twinkle.panels.settings.AdminSideActionMenuPanel;
import com.github.donkeyrit.twinkle.frame.MainFrame;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class SwingUiModules extends AbstractModule {

	@Override
	protected void configure() {
		install(new FactoryModuleBuilder().build(ContentPanelFactory.class));
		install(new FactoryModuleBuilder().build(CarPanelFactory.class));
		bind(MainFrame.class).toInstance(new MainFrame("Rent car"));

		registerListeners();
		registerPanels();
	}

	private void registerListeners() {
		bind(LoginEventsListener.class).to(AuthenticationListener.class);
		bind(ContentEventsListener.class).to(ContentEventsListenerImpl.class);
	}

	private void registerPanels() {
		bind(LoginPanel.class);
		bind(SignupPanel.class);
		bind(NavigationPanel.class);
		bind(SideBarFilterPanel.class);
		bind(ContentCompositePanel.class);	
		bind(AdminSideActionMenuPanel.class);
	}
}
