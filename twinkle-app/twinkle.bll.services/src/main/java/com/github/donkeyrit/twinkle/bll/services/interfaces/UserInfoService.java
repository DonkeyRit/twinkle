package com.github.donkeyrit.twinkle.bll.services.interfaces;

import java.util.Optional;

import com.github.donkeyrit.twinkle.dal.models.Client;

public interface UserInfoService {
	Optional<String> updatePassword(String oldPassword, String newPassword, String repeatPassword);
	Optional<Client> get(int userId);
	Optional<String> updateUserProfile(
		String firstNameText,
		String secondNameText,
		String middleNameText,
		String addressText,
		String phoneNumberText
	);
}
