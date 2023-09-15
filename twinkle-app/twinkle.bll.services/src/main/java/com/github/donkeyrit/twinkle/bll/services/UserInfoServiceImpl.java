package com.github.donkeyrit.twinkle.bll.services;

import com.github.donkeyrit.twinkle.dal.repositories.interfaces.UserRepository;
import com.github.donkeyrit.twinkle.dal.models.Client;
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

	@Override
	public Optional<Client> get(int userId) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'get'");
	}

	@Override
	public Optional<String> updateUserProfile(
		String firstNameText, 
		String secondNameText, 
		String middleNameText,
		String addressText, 
		String phoneNumberText
	) {

		return Optional.empty();
		// int counter = 0;
		// ArrayList<String> inputData = new ArrayList<String>();
		// for (int i = 0; i < fieldText.size(); i++) {
		// 	if (!fieldText.get(i).getText().isEmpty()) {
		// 		counter++;
		// 		inputData.add(fieldText.get(i).getText());
		// 	}
		// }

		// if (counter == fieldText.size()) {
		// 	if (infoUser.size() == 0) {
		// 		String createClient = "INSERT INTO client(first_name,second_name,middle_name,address,phone_number,id_user) VALUES (";
		// 		for (int i = 0; i < fieldText.size(); i++) {
		// 			createClient += "'" + fieldText.get(i).getText() + "',";
		// 		}
		// 		createClient += "(SELECT id_user FROMusersWHERE login = '" + UserInformation.getLogin() + "'))";

		// 		// database.insert(createClient);
		// 		for (int i = 0; i < fieldText.size(); i++) {
		// 			fieldText.get(i).setPlaceholder("Success");
		// 			fieldText.get(i).setText("");
		// 			fieldText.get(i).setPhColor(Color.green);
		// 		}
		// 	} else {
		// 		String[] columnNames = new String[] { "first_name", "second_name", "middle_name", "address",
		// 				"phone_number" };
		// 		String updateClient = "UPDATE clients SET ";
		// 		for (int i = 0; i < fieldText.size(); i++) {
		// 			updateClient += columnNames[i] + " = '" + fieldText.get(i).getText() + "'";
		// 			if (i != fieldText.size() - 1) {
		// 				updateClient += ",";
		// 			}
		// 		}
		// 		updateClient += " WHERE id_user = (SELECT id_user FROMusersWHERE login = '" + UserInformation.getLogin()
		// 				+ "')";
		// 		// database.update(updateClient);
		// 	}
		// } else {
		// 	for (int i = 0; i < fieldText.size(); i++) {
		// 		String previousText = fieldText.get(i).getPlaceholder()
		// 				.substring(fieldText.get(i).getPlaceholder().indexOf("Please") + 7);
		// 		fieldText.get(i).setPlaceholder("Please," + previousText);
		// 		fieldText.get(i).setPhColor(Color.red);
		// 	}
		// }
	}
}
