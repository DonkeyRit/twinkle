package com.github.donkeyrit.twinkle.panels.signup;

import com.github.donkeyrit.twinkle.listeners.ResetablePanelSwitcherActionListener;
import com.github.donkeyrit.twinkle.panels.signup.listeners.SignupActionListener;
import com.github.donkeyrit.twinkle.panels.common.ResettablePanel;
import com.github.donkeyrit.twinkle.controls.buttons.JConfirmationButton;
import com.github.donkeyrit.twinkle.controls.buttons.JLinkButton;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.UserRepository;
import com.github.donkeyrit.twinkle.frame.MainFrame;
import com.github.donkeyrit.twinkle.styles.Colors;
import com.github.donkeyrit.twinkle.utils.Constants;

import javax.swing.*;
import java.awt.*;


public class SignupPanel extends JPanel implements ResettablePanel
{
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton registerButton;
    private JButton loginButton;
    private JLabel errorLabel;
    
	private final UserRepository userRepository;
	private final MainFrame mainFrame;

    public SignupPanel(UserRepository userRepository, MainFrame mainFrame) 
    {
        this.userRepository = userRepository;
		this.mainFrame = mainFrame;

		this.setUp();
    }

	private void setUp()
	{
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
        passwordField = new JPasswordField(20);
        passwordField.setOpaque(false); // Make transparent
        passwordField.setForeground(Color.WHITE);
        passwordField.setCaretColor(Color.WHITE);
        passwordField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(passwordField, gbc);

        // Create confirm password label and text field
        gbc.gridy++;
        gbc.gridx = 0;
        
        JLabel confirmPasswordLabel = new JLabel("Confirm Password");
        confirmPasswordLabel.setForeground(Colors.AUTHORIZATION_LABEL_FOREGROUND_COLOR);
        add(confirmPasswordLabel, gbc);

        gbc.gridx++;
        confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setOpaque(false); // Make transparent
        confirmPasswordField.setForeground(Color.WHITE);
        confirmPasswordField.setCaretColor(Color.WHITE);
        confirmPasswordField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(confirmPasswordField, gbc);
        
        // Create register button
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;

        registerButton = new JConfirmationButton("Register");
		registerButton.addActionListener(new SignupActionListener(this.userRepository, this, this.mainFrame.getSwitchedPanel()));
        add(registerButton, gbc);
        
        // Create login link
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        loginButton = new JLinkButton("Already have an account? Login", Constants.LOGIN_PANEL_KEY);
		loginButton.addActionListener(new ResetablePanelSwitcherActionListener(this.mainFrame.getSwitchedPanel(), this));
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


    public String getUsername()
    {
        return this.usernameField.getText();
    }

    public String getPassword()
    {
        return new String(passwordField.getPassword());
    }

    public String getConfirmPassword()
    {
        return new String(confirmPasswordField.getPassword());
    }

    public void setErorr(String message)
    {
        errorLabel.setText(message);
        this.revalidate();
        this.repaint();
    }

	public void reset()
	{
		usernameField.setText("");
		passwordField.setText("");
		confirmPasswordField.setText("");
		registerButton.setText("");
		loginButton.setText("");
		errorLabel.setText("");
	}
}
    