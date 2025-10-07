package com.github.donkeyrit.twinkle.auth.services;

import com.github.donkeyrit.twinkle.dal.specifications.UserInfoSpecifciation;
import com.github.donkeyrit.twinkle.dal.interfaces.UserRepository;
import com.github.donkeyrit.twinkle.dal.models.User;

import com.github.donkeyrit.twinkle.auth.services.interfaces.LoginService;
import com.github.donkeyrit.twinkle.auth.models.AuthenticationResult;
import com.github.donkeyrit.twinkle.auth.security.PasswordHasher;
import com.github.donkeyrit.twinkle.telemetry.CorrelationContext;

import com.google.inject.Inject;
import java.util.Optional;

public class DefaultLoginService implements LoginService {

	private final UserRepository userRepository;

	@Inject
	public DefaultLoginService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public AuthenticationResult verifyCredentials(String username, String password) {

		try (CorrelationContext correlation = CorrelationContext.start("login-verify-credentials")) {
			if (username.isEmpty() || password.isEmpty()) {

				return AuthenticationResult.error("Please fill both fields.");
			}

			// Passwords are salted, so a hash can no longer be looked up by
			// equality: fetch the user by login, then verify the password
			// against their stored hash.
			Optional<User> currentUser = userRepository.get(new UserInfoSpecifciation(username));
			if (currentUser.isEmpty() || !PasswordHasher.verify(password, currentUser.get().getPassword())) {
				return AuthenticationResult.error("Incorrect login or password.");
			}

			return AuthenticationResult.fromResult(currentUser);
		}
	}

	@Override
	public AuthenticationResult signUp(String username, String password, String confirmPassword) {

		try (CorrelationContext correlation = CorrelationContext.start("login-sign-up")) {
			if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty())
			{
				return AuthenticationResult.error("All fields are required.");
			}

			if (!password.equals(confirmPassword))
			{
				return AuthenticationResult.error("Passwords do not match.");
			}

			if(userRepository.get(new UserInfoSpecifciation(username)).isPresent())
			{
				return AuthenticationResult.error("Login already exist");
			}

			String passwordHash = PasswordHasher.hash(password);
			User user = new User(username, passwordHash, false);
			userRepository.save(user);

			return AuthenticationResult.fromResult(Optional.of(user));
		}
	}
}
