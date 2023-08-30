package com.github.donkeyrit.twinkle.bll.services;

import com.github.donkeyrit.twinkle.dal.repositories.interfaces.UserRepository;
import com.github.donkeyrit.twinkle.dal.models.User;
import com.github.donkeyrit.twinkle.bll.services.contracts.LoginService;
import com.github.donkeyrit.twinkle.bll.models.AuthenticationResult;

import com.google.inject.Inject;
import java.util.Optional;

public class DefaultLoginService implements LoginService{

	private final UserRepository userRepository;

	@Inject
	public DefaultLoginService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public AuthenticationResult verifyCredentials(String username, String password) {

		if (username.isEmpty() || password.isEmpty()) {

			return AuthenticationResult.error("Please fill both fields.");
		}

		Optional<User> currentUser = userRepository.getByLoginAndPassword(username, password);
        return AuthenticationResult.fromResult(currentUser);
	}
}
