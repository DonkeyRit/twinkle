package com.github.donkeyrit.twinkle.panels.settings;

import com.github.donkeyrit.twinkle.bll.services.interfaces.UserInfoService;
import com.github.donkeyrit.twinkle.bll.models.UserInformation;
import com.github.donkeyrit.twinkle.controls.input.JCustomTextField;
import com.github.donkeyrit.twinkle.dal.models.Client;
import com.github.donkeyrit.twinkle.utils.AssetsRetriever;

import com.google.inject.Inject;
import javax.swing.border.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class ProfileUpdatePanel extends JPanel {

	private final String FIRST_NAME_FIELD = "Enter first_name";
	private final String SECOND_NAME_FIELD = "Enter second_name"; 
	private final String MIDDLE_NAME_FIELD = "Enter middle_name";
	private final String ADDRESS_FIELD = "Enter address"; 
	private final String PHONE_NUMBER_FIELD = "Enter phone_number";

	private final UserInfoService userInfoService;

	@Inject
	public ProfileUpdatePanel(UserInfoService userInfoService) {
		setLayout(null);
		this.userInfoService = userInfoService;
		initializeComponents();
	}

	private void initializeComponents() {
		Optional<Client> client = this.userInfoService.get(UserInformation.getId());

		Box box = Box.createVerticalBox();
		box.setBounds(202, 10, 200, 250);
		box.setBorder(new TitledBorder("Personal information"));

		JCustomTextField firstNameField = AddCustomTextField(FIRST_NAME_FIELD, client.map(Client::getFirstName), box);
		JCustomTextField secondNameField = AddCustomTextField(SECOND_NAME_FIELD, client.map(Client::getSecondName), box);
		JCustomTextField middleNameField = AddCustomTextField(MIDDLE_NAME_FIELD, client.map(Client::getMiddleName), box);
		JCustomTextField addressField = AddCustomTextField(ADDRESS_FIELD, client.map(Client::getAddress), box);
		JCustomTextField phoneNumberField = AddCustomTextField(PHONE_NUMBER_FIELD, client.map(Client::getPhoneNumber), box);

		JButton confirm = new JButton("Confirm");
		confirm.addActionListener(e -> {

			Optional<String> me = userInfoService.updateUserProfile(
				client,
				firstNameField.getText(),
				secondNameField.getText(),
				middleNameField.getText(),
				addressField.getText(),
				phoneNumberField.getText()
			);

			if(me.isPresent()){
				setError(me.get());
			}
		});
		box.add(Box.createHorizontalStrut(60));
		box.add(confirm);
		add(box);
	}

	private JCustomTextField AddCustomTextField(String label, Optional<String> value, Box box) {
		JCustomTextField field = new JCustomTextField();
		field.setPlaceholder(label);
		box.add(field);
		box.add(Box.createVerticalStrut(10));
		value.ifPresent(v -> field.setText(v));
		return field;
	}

	private void setError(String message){
		//TODO: Implement error message
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(new Color(237, 237, 237));
		g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 30, 25);

		Image image = AssetsRetriever.retrieveAssetImageFromResources("assets/background/fill_data.png");
		g.drawImage(image, 50, 310, this);
	}
}
