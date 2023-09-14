package com.github.donkeyrit.twinkle.bll.services;

import com.github.donkeyrit.twinkle.dal.repositories.interfaces.UserRepository;
import com.github.donkeyrit.twinkle.bll.services.interfaces.UserInfoService;
import com.github.donkeyrit.twinkle.bll.models.UserInformation;
import com.github.donkeyrit.twinkle.bll.security.HashManager;

import com.google.inject.Inject;
import java.util.Optional;

public class UserInfoServiceImpl implements UserInfoService {

	private final UserRepository userRepository;

	@Inject
	public UserInfoServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public Optional<String> updatePassword(String oldPassword, String newPassword, String repeatPassword) {

		String oldPasswordHash = HashManager.generateHash(oldPassword);
		if (!oldPasswordHash.equals(UserInformation.getPassword())) {
			return Optional.of("Incorrect password");
		}

		if (oldPassword.equals(newPassword)) {
			return Optional.of("Old and new match");
		}

		if (!newPassword.equals(repeatPassword)) {
			return Optional.of("Password does't match");
		}

		String newPasswordHash = HashManager.generateHash(newPassword);
		userRepository.updatePassword(UserInformation.getId(), newPasswordHash);
		UserInformation.setPassword(HashManager.generateHash(newPassword));
		return Optional.empty();
	}
}
