package com.github.donkeyrit.twinkle.bll.services.interfaces;

import com.github.donkeyrit.twinkle.dal.models.Client;
import java.util.Optional;

public interface UserInfoService {
	Optional<String> updatePassword(String oldPassword, String newPassword, String repeatPassword);
	Optional<Client> get(int userId);
	Optional<String> updateUserProfile(
		Optional<Client> clientId,
		String firstNameText,
		String secondNameText,
		String middleNameText,
		String addressText,
		String phoneNumberText
	);
}
