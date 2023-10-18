package com.github.donkeyrit.twinkle.auth.services.interfaces;

import com.github.donkeyrit.twinkle.auth.models.AuthenticationResult;

public interface LoginService {
	AuthenticationResult verifyCredentials(String username, String password);
	AuthenticationResult signUp(String username, String password, String confirmPassword);
}
