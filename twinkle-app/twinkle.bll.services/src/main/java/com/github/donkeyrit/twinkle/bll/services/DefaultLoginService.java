package com.github.donkeyrit.twinkle.bll.services;

import com.github.donkeyrit.twinkle.dal.repositories.interfaces.UserRepository;
import com.github.donkeyrit.twinkle.dal.models.User;
import com.github.donkeyrit.twinkle.bll.models.AuthenticationResult;
import com.github.donkeyrit.twinkle.bll.security.HashManager;
import com.github.donkeyrit.twinkle.bll.services.interfaces.LoginService;
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

	@Override
	public AuthenticationResult signUp(String username, String password, String confirmPassword) {

		if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) 
        {
            return AuthenticationResult.error("All fields are required.");
        }

        if (!password.equals(confirmPassword)) 
        {
            return AuthenticationResult.error("Passwords do not match.");
        }

        if(userRepository.isUserExist(username))
        {
            return AuthenticationResult.error("Login already exist");
        }
        
        String passwordHash = HashManager.generateHash(password);
        User user = new User(username, passwordHash, false); 
        userRepository.insert(user);

		return AuthenticationResult.fromResult(Optional.of(user));
	}
}
