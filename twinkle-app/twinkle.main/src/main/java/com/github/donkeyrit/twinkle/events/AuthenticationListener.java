package com.github.donkeyrit.twinkle.events;

import com.github.donkeyrit.twinkle.events.contracts.LoginEventsListener;
import com.github.donkeyrit.twinkle.panels.authentication.LoginPanel;
import com.github.donkeyrit.twinkle.panels.authentication.SignupPanel;
import com.github.donkeyrit.twinkle.panels.content.ContentCompositePanel;
import com.github.donkeyrit.twinkle.utils.Constants;
import com.github.donkeyrit.twinkle.frame.MainFrame;

import com.google.inject.Inject;
import javax.inject.Provider;

public class AuthenticationListener implements LoginEventsListener {

	private final MainFrame mainFrame;
	private final Provider<LoginPanel> loginPanelProvider;
	private final Provider<SignupPanel> signupPanelProvider;
	private final Provider<ContentCompositePanel> contentCompositePanelProvider;

	@Inject
	public AuthenticationListener(
		MainFrame mainFrame, 
		Provider<LoginPanel> loginPanelProvider, 
		Provider<SignupPanel> signupPanelProvider,
		Provider<ContentCompositePanel> contentCompositePanelProvider
	){
		this.mainFrame = mainFrame;
		this.loginPanelProvider = loginPanelProvider;
		this.signupPanelProvider = signupPanelProvider;
		this.contentCompositePanelProvider = contentCompositePanelProvider;
		
	}

	@Override
	public void onLoginSuccess() {
		mainFrame
			.getSwitchedPanel()
			.addPanel(Constants.CONTENT_PANEL_KEY, contentCompositePanelProvider.get())
			.showPanel(Constants.CONTENT_PANEL_KEY);
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
