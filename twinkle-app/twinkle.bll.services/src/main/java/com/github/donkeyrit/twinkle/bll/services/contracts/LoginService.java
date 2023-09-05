package com.github.donkeyrit.twinkle.bll.services.contracts;

import com.github.donkeyrit.twinkle.bll.models.AuthenticationResult;

public interface LoginService {
	AuthenticationResult verifyCredentials(String username, String password);
	AuthenticationResult signUp(String username, String password, String confirmPassword);
}
