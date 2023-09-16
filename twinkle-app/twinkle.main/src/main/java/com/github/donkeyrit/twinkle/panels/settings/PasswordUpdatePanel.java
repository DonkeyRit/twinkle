package com.github.donkeyrit.twinkle.panels.settings;

import com.github.donkeyrit.twinkle.bll.services.interfaces.UserInfoService;
import com.github.donkeyrit.twinkle.controls.input.JCustomPasswordField;

import javax.swing.border.TitledBorder;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Box;

import com.google.inject.Inject;
import java.util.Optional;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Color;

public class PasswordUpdatePanel extends JPanel {

	private static final Color DARK_BG_COLOR = new Color(50, 50, 50);
    private static final Color DARK_FG_COLOR = Color.WHITE;

	private final String OLD_PASSWORD_LABEL = "Enter old pasword";
	private final String NEW_PASSWORD_LABEL = "Enter new password";
	private final String REPEAT_NEW_PASSWORD_LABEL = "Repeat new password";
	private final UserInfoService userInfoService;

	private JLabel errorMessageLabel;

	@Inject
	public PasswordUpdatePanel(UserInfoService userInfoService) {
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.setBackground(DARK_BG_COLOR);
		this.userInfoService = userInfoService;
		initializeComponents();
	}

	private void initializeComponents() {
		Box mainBox = Box.createVerticalBox();
        mainBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainBox.setBackground(DARK_BG_COLOR);

        TitledBorder titleBorder = BorderFactory.createTitledBorder("Change Password");
        titleBorder.setTitleColor(DARK_FG_COLOR);
        mainBox.setBorder(titleBorder);

		JCustomPasswordField oldPasswordField = this.AddPasswordFieldToBox(OLD_PASSWORD_LABEL, mainBox);
		JCustomPasswordField newPasswordField = this.AddPasswordFieldToBox(NEW_PASSWORD_LABEL, mainBox);
		JCustomPasswordField repeatPasswordField = this.AddPasswordFieldToBox(REPEAT_NEW_PASSWORD_LABEL, mainBox);

		JButton confirm = new JButton("Confirm");
		confirm.setAlignmentX(Component.CENTER_ALIGNMENT);
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

	private JCustomPasswordField AddPasswordFieldToBox(String label, Box mainBox) {
		JCustomPasswordField field = new JCustomPasswordField(20);
        field.setPlaceholder(label);
        field.setMaximumSize(field.getPreferredSize());
        field.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainBox.add(field);
        mainBox.add(Box.createVerticalStrut(10));
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
