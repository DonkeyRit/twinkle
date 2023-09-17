package com.github.donkeyrit.twinkle.events;

import com.github.donkeyrit.twinkle.events.contracts.AdminSidePanelEventsListener;
import com.github.donkeyrit.twinkle.panels.content.ContentCompositePanel;
import com.github.donkeyrit.twinkle.panels.settings.PasswordUpdatePanel;
import com.github.donkeyrit.twinkle.panels.settings.ProfileUpdatePanel;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class AdminSidePanelEventsListenerImpl implements AdminSidePanelEventsListener {

	private final Provider<ContentCompositePanel> contentCompositePanelProvider;
	private final Provider<PasswordUpdatePanel> passwordUpdatePanelProvider;
	private final Provider<ProfileUpdatePanel> profileUpdatePanelProvider;

	@Inject
	public AdminSidePanelEventsListenerImpl(
		Provider<ContentCompositePanel> contentCompositePanelProvider,
		Provider<PasswordUpdatePanel> passwordUpdatePanelProvider,
		Provider<ProfileUpdatePanel> profileUpdatePanelProvider
	) {
		this.contentCompositePanelProvider = contentCompositePanelProvider;
		this.passwordUpdatePanelProvider = passwordUpdatePanelProvider;
		this.profileUpdatePanelProvider = profileUpdatePanelProvider;
	}

	@Override
	public void onPasswordChangeRequest() {
		contentCompositePanelProvider.get().setContentPanel(passwordUpdatePanelProvider.get());
	}

	@Override
	public void onUsernameInfoChangeRequest() {
		contentCompositePanelProvider.get().setContentPanel(profileUpdatePanelProvider.get());
	}
	
}
