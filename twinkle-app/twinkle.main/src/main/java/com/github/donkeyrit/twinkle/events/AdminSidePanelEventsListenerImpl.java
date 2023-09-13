package com.github.donkeyrit.twinkle.events;

import com.github.donkeyrit.twinkle.events.contracts.AdminSidePanelEventsListener;
import com.github.donkeyrit.twinkle.panels.content.ContentCompositePanel;
import com.github.donkeyrit.twinkle.panels.settings.PasswordUpdatePanel;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class AdminSidePanelEventsListenerImpl implements AdminSidePanelEventsListener {

	private final Provider<ContentCompositePanel> contentCompositePanelProvider;
	private final Provider<PasswordUpdatePanel> passwordUpdatePanel;

	@Inject
	public AdminSidePanelEventsListenerImpl(
		Provider<ContentCompositePanel> contentCompositePanelProvider,
		Provider<PasswordUpdatePanel> passwordUpdatePanel
	) {
		this.contentCompositePanelProvider = contentCompositePanelProvider;
		this.passwordUpdatePanel = passwordUpdatePanel;
	}

	@Override
	public void onPasswordChangeRequest() {
		contentCompositePanelProvider.get().setContentPanel(passwordUpdatePanel.get());
	}

	@Override
	public void onUsernameInfoChangeRequest() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'onUsernameInfoChangeRequest'");
	}
	
}
