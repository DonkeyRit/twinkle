package com.github.donkeyrit.twinkle.bll.services;

import com.github.donkeyrit.twinkle.bll.services.interfaces.UserInfoService;
import com.github.donkeyrit.twinkle.bll.models.UserInformation;
import com.github.donkeyrit.twinkle.auth.security.PasswordHasher;

import com.github.donkeyrit.twinkle.dal.interfaces.ClientRepository;
import com.github.donkeyrit.twinkle.dal.interfaces.UserRepository;
import com.github.donkeyrit.twinkle.dal.models.Client;
import com.github.donkeyrit.twinkle.dal.models.User;
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

		// Verify against the current hash straight from the database rather than
		// a cached copy, so the session never needs to hold the password hash.
		User currentUser = userRepository.findById(UserInformation.getId());
		if (currentUser == null || !PasswordHasher.verify(oldPassword, currentUser.getPassword())) {
			return Optional.of("Incorrect password");
		}

		if (oldPassword.equals(newPassword)) {
			return Optional.of("Old and new match");
		}

		if (!newPassword.equals(repeatPassword)) {
			return Optional.of("Password does't match");
		}

		String newPasswordHash = PasswordHasher.hash(newPassword);
		User updatedUser = new User(UserInformation.getLogin(), newPasswordHash, UserInformation.isRole());
		updatedUser.setId(UserInformation.getId());
		if (!userRepository.update(updatedUser)) {
			return Optional.of("Could not update password. Please try again.");
		}
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

			this.clientRepository.save(newClient);
		}

		return Optional.empty();
	}
}
