package com.github.donkeyrit.twinkle.events;

import com.github.donkeyrit.twinkle.events.contracts.LoginEventsListener;
import com.github.donkeyrit.twinkle.panels.signup.SignupPanel;
import com.github.donkeyrit.twinkle.panels.login.LoginPanel;
import com.github.donkeyrit.twinkle.utils.Constants;
import com.github.donkeyrit.twinkle.frame.MainFrame;

import com.google.inject.Inject;
import javax.inject.Provider;

public class AuthenticationListener implements LoginEventsListener {

	private final MainFrame mainFrame;
	private final Provider<LoginPanel> loginPanelProvider;
	private final Provider<SignupPanel> signupPanelProvider;

	@Inject
	public AuthenticationListener(
		MainFrame mainFrame, 
		Provider<LoginPanel> loginPanelProvider, 
		Provider<SignupPanel> signupPanelProvider
	){
		this.mainFrame = mainFrame;
		this.loginPanelProvider = loginPanelProvider;
		this.signupPanelProvider = signupPanelProvider;
		
	}

	@Override
	public void onLoginSuccess() {
		System.out.println("Login was successfull.");
	}

	@Override
	public void onLoginRequest() {
		mainFrame
			.getSwitchedPanel()
			.addPanel(Constants.LOGIN_PANEL_KEY, loginPanelProvider.get())
			.showPanel(Constants.LOGIN_PANEL_KEY);
	}

	@Override
	public void onSignupRequest() {
		mainFrame
			.getSwitchedPanel()
			.addPanel(Constants.SIGUP_PANEL_KEY, signupPanelProvider.get())
			.showPanel(Constants.SIGUP_PANEL_KEY);
	}
}
