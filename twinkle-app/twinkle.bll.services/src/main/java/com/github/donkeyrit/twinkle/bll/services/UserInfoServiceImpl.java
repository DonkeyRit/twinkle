package com.github.donkeyrit.twinkle.bll.services;

import com.github.donkeyrit.twinkle.bll.services.contracts.UserInfoService;
import com.github.donkeyrit.twinkle.bll.models.UserInformation;
import com.github.donkeyrit.twinkle.bll.security.HashManager;

import java.util.Optional;

public class UserInfoServiceImpl implements UserInfoService {

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

		//TODO: Use repository to update password
		// String updateUserQuery = "UPDATE users SET password = '"
		// 		+ HashManager.generateHash(new String(fieldPass.get(1).getPassword())) + "'" + " WHERE login = '"
		// 		+ UserInformation.getLogin() + "'";
		// database.update(updateUserQuery);

		UserInformation.setPassword(HashManager.generateHash(newPassword));
		return Optional.empty();
	}
}
