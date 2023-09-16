package com.github.donkeyrit.twinkle.bll.services;

import com.github.donkeyrit.twinkle.dal.repositories.interfaces.ClientRepository;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.UserRepository;
import com.github.donkeyrit.twinkle.bll.services.interfaces.UserInfoService;
import com.github.donkeyrit.twinkle.bll.models.UserInformation;
import com.github.donkeyrit.twinkle.dal.models.Client;
import com.github.donkeyrit.twinkle.bll.security.HashManager;

import com.google.inject.Inject;
import java.util.Optional;

public class UserInfoServiceImpl implements UserInfoService {

	private final ClientRepository clientRepository;
	private final UserRepository userRepository;

	@Inject
	public UserInfoServiceImpl(ClientRepository clientRepository, UserRepository userRepository) {
		this.clientRepository = clientRepository;
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

	@Override
	public Optional<Client> get(int userId) {
		return this.clientRepository.getByUserId(userId);
	}

	@Override
	public Optional<String> updateUserProfile(
		Optional<Client> client,
		String firstNameText, 
		String secondNameText, 
		String middleNameText,
		String addressText, 
		String phoneNumberText
	) {
		if(client.isPresent()){

			Client updatedClient = client.get();
			updatedClient.setFirstName(firstNameText);
			updatedClient.setSecondName(secondNameText);
			updatedClient.setMiddleName(middleNameText);
			updatedClient.setAddress(addressText);
			updatedClient.setPhoneNumber(phoneNumberText);

			this.clientRepository.update(updatedClient);
		}
		else{

			Client newClient = new Client();
			newClient.setFirstName(firstNameText);
			newClient.setSecondName(secondNameText);
			newClient.setMiddleName(middleNameText);
			newClient.setAddress(addressText);
			newClient.setPhoneNumber(phoneNumberText);

			this.clientRepository.insert(newClient);
		}

		return Optional.empty();
	}
}
