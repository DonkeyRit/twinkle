package com.github.donkeyrit.twinkle.panels.settings;

import com.github.donkeyrit.twinkle.bll.services.interfaces.UserInfoService;
import com.github.donkeyrit.twinkle.controls.input.JCustomPasswordField;
import com.github.donkeyrit.twinkle.utils.AssetsRetriever;

import javax.swing.border.TitledBorder;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Box;

import com.google.inject.Inject;
import java.util.Optional;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;

public class PasswordUpdatePanel extends JPanel {

	private final String OLD_PASSWORD_LABEL = "Enter old pasword";
	private final String NEW_PASSWORD_LABEL = "Enter new password";
	private final String REPEAT_NEW_PASSWORD_LABEL = "Repeat new password";
	private final UserInfoService userInfoService;

	@Inject
	public PasswordUpdatePanel(UserInfoService userInfoService) {
		setLayout(null);
		this.userInfoService = userInfoService;
		initializeComponents();
	}

	private void initializeComponents() {
		Box mainBox = Box.createVerticalBox();
		mainBox.setBorder(new TitledBorder("Change Password"));
		mainBox.setBounds(202, 10, 200, 200);

		JCustomPasswordField oldPasswordField = this.AddPasswordFieldToBox(OLD_PASSWORD_LABEL, mainBox);
		JCustomPasswordField newPasswordField = this.AddPasswordFieldToBox(NEW_PASSWORD_LABEL, mainBox);
		JCustomPasswordField repeatPasswordField = this.AddPasswordFieldToBox(REPEAT_NEW_PASSWORD_LABEL, mainBox);

		JButton confirm = new JButton("Confirm");
		confirm.addActionListener(e -> {

			String oldPassword = new String(oldPasswordField.getPassword());
			String newPassword = new String(newPasswordField.getPassword());
			String repeatPassword = new String(repeatPasswordField.getPassword());

			if (oldPassword.isEmpty()) {
				oldPasswordField.setPlaceholder("Please, enter old password");
				oldPasswordField.setPhColor(Color.RED);
				return;
			}

			if (newPassword.isEmpty()) {
				newPasswordField.setPlaceholder("Please, enter new password");
				newPasswordField.setPhColor(Color.RED);
				return;
			}

			if (repeatPassword.isEmpty()) {
				repeatPasswordField.setPlaceholder("Please, repeat new password");
				repeatPasswordField.setPhColor(Color.RED);
				return;
			}

			Optional<String> me = userInfoService.updatePassword(oldPassword, newPassword, repeatPassword);

			if(me.isPresent()){
				setError(me.get());
			}
		});

		mainBox.add(Box.createHorizontalStrut(60));
		mainBox.add(confirm);

		add(mainBox);
	}

	private JCustomPasswordField AddPasswordFieldToBox(String label, Box mainBox) {
		JCustomPasswordField field = new JCustomPasswordField(20);
		field.setPlaceholder(label);
		mainBox.add(field);
		mainBox.add(Box.createVerticalStrut(10));
		return field;
	}

	private void setError(String message){

	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(new Color(237, 237, 237));
		g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 30, 25);

		Image image = AssetsRetriever.retrieveAssetImageFromResources("assets/background/page.png");
		g.drawImage(image, 50, 250, this);
	}
}
