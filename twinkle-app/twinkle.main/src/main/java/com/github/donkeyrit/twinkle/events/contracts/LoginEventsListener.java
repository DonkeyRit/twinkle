package com.github.donkeyrit.twinkle.events.contracts;

public interface LoginEventsListener {
	void onLoginSuccess();
	void onLoginRequest();
	void onSignupRequest();
}
