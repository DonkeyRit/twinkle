package com.github.donkeyrit.twinkle.panels.settings;

import com.github.donkeyrit.twinkle.bll.services.interfaces.UserInfoService;
import com.github.donkeyrit.twinkle.bll.models.UserInformation;
import com.github.donkeyrit.twinkle.controls.input.JCustomTextField;
import com.github.donkeyrit.twinkle.dal.models.Client;

import com.google.inject.Inject;
import javax.swing.border.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class ProfileUpdatePanel extends JPanel {

	private static final Color DARK_BG_COLOR = new Color(50, 50, 50);
    private static final Color DARK_FG_COLOR = Color.WHITE;

	private final String FIRST_NAME_FIELD = "Enter first_name";
	private final String SECOND_NAME_FIELD = "Enter second_name"; 
	private final String MIDDLE_NAME_FIELD = "Enter middle_name";
	private final String ADDRESS_FIELD = "Enter address"; 
	private final String PHONE_NUMBER_FIELD = "Enter phone_number";

	private final UserInfoService userInfoService;
	private JLabel errorMessageLabel;

	@Inject
	public ProfileUpdatePanel(UserInfoService userInfoService) {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setBackground(DARK_BG_COLOR);
		this.userInfoService = userInfoService;
		initializeComponents();
	}

	private void initializeComponents() {
		Optional<Client> client = this.userInfoService.get(UserInformation.getId());

		Box mainBox = Box.createVerticalBox();
        mainBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainBox.setBackground(DARK_BG_COLOR);

        TitledBorder titleBorder = BorderFactory.createTitledBorder("Personal information");
        titleBorder.setTitleColor(DARK_FG_COLOR);
        mainBox.setBorder(titleBorder);

		JCustomTextField firstNameField = addCustomTextField(FIRST_NAME_FIELD, client.map(Client::getFirstName), mainBox);
        JCustomTextField secondNameField = addCustomTextField(SECOND_NAME_FIELD, client.map(Client::getSecondName), mainBox);
        JCustomTextField middleNameField = addCustomTextField(MIDDLE_NAME_FIELD, client.map(Client::getMiddleName), mainBox);
        JCustomTextField addressField = addCustomTextField(ADDRESS_FIELD, client.map(Client::getAddress), mainBox);
        JCustomTextField phoneNumberField = addCustomTextField(PHONE_NUMBER_FIELD, client.map(Client::getPhoneNumber), mainBox);

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

		errorMessageLabel = new JLabel("");
        errorMessageLabel.setForeground(Color.RED);
        errorMessageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        this.add(Box.createVerticalStrut(20));
        this.add(mainBox);
        this.add(Box.createVerticalStrut(10));
        this.add(errorMessageLabel);
        this.add(Box.createVerticalStrut(10));
        this.add(confirm);
        this.add(Box.createVerticalGlue());
	}

	private JCustomTextField addCustomTextField(String label, Optional<String> value, Box box) {
		JCustomTextField field = new JCustomTextField();
        field.setPlaceholder(label);
        field.setMaximumSize(field.getPreferredSize());
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        box.add(field);
        box.add(Box.createVerticalStrut(10));
        value.ifPresent(field::setText);
        return field;
	}

	private void setError(String message){
		errorMessageLabel.setText(message);
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(new Color(237, 237, 237));
		g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 30, 25);
	}
}
