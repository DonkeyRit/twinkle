package com.github.donkeyrit.twinkle.auth.services;

import com.github.donkeyrit.twinkle.dal.specifications.factory.SpecificationFactory;
import com.github.donkeyrit.twinkle.dal.interfaces.UserRepository;
import com.github.donkeyrit.twinkle.dal.models.User;

import com.github.donkeyrit.twinkle.auth.services.interfaces.LoginService;
import com.github.donkeyrit.twinkle.auth.models.AuthenticationResult;
import com.github.donkeyrit.twinkle.auth.security.HashManager;

import com.google.inject.Inject;
import java.util.Optional;

public class DefaultLoginService implements LoginService{

	private final SpecificationFactory<?> specificationFactory;
	private final UserRepository userRepository;

	@Inject
	public DefaultLoginService(SpecificationFactory<?> specificationFactory, UserRepository userRepository) {
		this.specificationFactory = specificationFactory;
		this.userRepository = userRepository;
	}

	@Override
	public AuthenticationResult verifyCredentials(String username, String password) {

		if (username.isEmpty() || password.isEmpty()) {

			return AuthenticationResult.error("Please fill both fields.");
		}

		Optional<User> currentUser = userRepository.get(specificationFactory.createUserInfoSpecification(username, password));
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

        if(userRepository.get(specificationFactory.createUserInfoSpecification(username)).isPresent())
        {
            return AuthenticationResult.error("Login already exist");
        }
        
        String passwordHash = HashManager.generateHash(password);
        User user = new User(username, passwordHash, false); 
        userRepository.save(user);

		return AuthenticationResult.fromResult(Optional.of(user));
	}
}
