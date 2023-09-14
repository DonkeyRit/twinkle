package com.github.donkeyrit.twinkle.panels.authentication;

import com.github.donkeyrit.twinkle.events.contracts.LoginEventsListener;
import com.github.donkeyrit.twinkle.bll.models.AuthenticationResult;
import com.github.donkeyrit.twinkle.bll.models.UserInformation;
import com.github.donkeyrit.twinkle.bll.services.interfaces.LoginService;
import com.github.donkeyrit.twinkle.controls.input.JCustomPasswordField;
import com.github.donkeyrit.twinkle.controls.buttons.JConfirmationButton;
import com.github.donkeyrit.twinkle.controls.buttons.JLinkButton;
import com.github.donkeyrit.twinkle.panels.common.ResettablePanel;
import com.github.donkeyrit.twinkle.styles.Colors;
import com.github.donkeyrit.twinkle.utils.Constants;

import com.google.inject.Inject;
import javax.swing.*;
import java.awt.*;

public class SignupPanel extends JPanel implements ResettablePanel {

	private JTextField usernameField;
	private JPasswordField passwordField;
	private JPasswordField confirmPasswordField;
	private JButton registerButton;
	private JButton loginButton;
	private JLabel errorLabel;

	private final LoginEventsListener loginEventsListener;
	private final LoginService loginService;

	@Inject
	public SignupPanel(LoginEventsListener loginEventsListener, LoginService loginService) {
		this.loginEventsListener = loginEventsListener;
		this.loginService = loginService;

		this.setUp();
	}

	private void setUp() {
		// Set layout
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// Create title label
		JLabel titleLabel = new JLabel("Signup");
		titleLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 24));
		titleLabel.setForeground(Colors.AUTHORIZATION_LABEL_FOREGROUND_COLOR);
		gbc.gridwidth = 2;
		add(titleLabel, gbc);

		// Create username label and text field
		gbc.gridy++;
		gbc.gridwidth = 1;

		JLabel usernameLabel = new JLabel("Username");
		usernameLabel.setForeground(Colors.AUTHORIZATION_LABEL_FOREGROUND_COLOR);
		add(usernameLabel, gbc);

		gbc.gridx++;
		usernameField = new JTextField(20);
		usernameField.setOpaque(false);
		usernameField.setForeground(Color.WHITE);
		usernameField.setCaretColor(Color.WHITE);
		usernameField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		add(usernameField, gbc);

		// Create password lable and text field
		gbc.gridy++;
		gbc.gridx = 0;

		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setForeground(Colors.AUTHORIZATION_LABEL_FOREGROUND_COLOR);
		add(passwordLabel, gbc);

		gbc.gridx++;
		passwordField = new JCustomPasswordField(20);
		add(passwordField, gbc);

		// Create confirm password label and text field
		gbc.gridy++;
		gbc.gridx = 0;

		JLabel confirmPasswordLabel = new JLabel("Confirm Password");
		confirmPasswordLabel.setForeground(Colors.AUTHORIZATION_LABEL_FOREGROUND_COLOR);
		add(confirmPasswordLabel, gbc);

		gbc.gridx++;
		confirmPasswordField = new JCustomPasswordField(20);
		add(confirmPasswordField, gbc);

		// Create register button
		gbc.gridy++;
		gbc.gridx = 0;
		gbc.gridwidth = 2;

		registerButton = new JConfirmationButton("Register");
		registerButton.addActionListener(e -> {
			String username = this.getUsername();
			String password = this.getPassword();
			String confirmPassword = this.getConfirmPassword();

			AuthenticationResult authenticationResult = loginService.signUp(username, password, confirmPassword);
			if (!authenticationResult.isSuccessfull()) {
				this.setErorr(authenticationResult.errorMessage());
				return;
			}

			UserInformation.setUser(authenticationResult.authenticatedUser().get());
			this.loginEventsListener.onLoginSuccess();
		});
		add(registerButton, gbc);

		// Create login link
		gbc.gridy++;
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		loginButton = new JLinkButton("Already have an account? Login", Constants.LOGIN_PANEL_KEY);
		loginButton.addActionListener(e -> {
			this.loginEventsListener.onLoginRequest();
		});
		add(loginButton, gbc);

		// Create error block
		gbc.gridy++;
		gbc.gridx = 0;
		gbc.gridwidth = 2;
		errorLabel = new JLabel("");
		errorLabel.setForeground(new Color(255, 0, 0));
		add(errorLabel, gbc);

		setBackground(Colors.AUTHORIZATION_BACKGROUND_COLOR);
	}

	public String getUsername() {
		return this.usernameField.getText();
	}

	public String getPassword() {
		return new String(passwordField.getPassword());
	}

	public String getConfirmPassword() {
		return new String(confirmPasswordField.getPassword());
	}

	public void setErorr(String message) {
		errorLabel.setText(message);
		this.revalidate();
		this.repaint();
	}

	public void reset() {
		usernameField.setText("");
		passwordField.setText("");
		confirmPasswordField.setText("");
		registerButton.setText("");
		loginButton.setText("");
		errorLabel.setText("");
	}
}
