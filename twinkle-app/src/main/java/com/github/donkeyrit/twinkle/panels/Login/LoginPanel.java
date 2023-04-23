package com.github.donkeyrit.twinkle.panels.login;

import com.github.donkeyrit.twinkle.dal.repositories.interfaces.UserRepository;
import com.github.donkeyrit.twinkle.panels.login.listeners.LoginActionListener;
import com.github.donkeyrit.twinkle.listeners.PanelSwitcherActionListener;
import com.github.donkeyrit.twinkle.listeners.ResetablePanelSwitcherActionListener;
import com.github.donkeyrit.twinkle.panels.common.ResettablePanel;
import com.github.donkeyrit.twinkle.security.HashManager;
import com.github.donkeyrit.twinkle.frame.MainFrame;
import com.github.donkeyrit.twinkle.utils.Constants;
import com.github.donkeyrit.twinkle.styles.Colors;
import com.google.inject.Inject;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel implements ResettablePanel
{
    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signupButton;
    private JLabel errorLabel;

	private final UserRepository userRepository;
	private final MainFrame mainFrame;
    
	@Inject
    public LoginPanel(UserRepository userRepository, MainFrame mainFrame) 
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
        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 24));
        titleLabel.setForeground(Colors.AUTHORIZATION_LABEL_FOREGROUND_COLOR); 
        gbc.gridwidth = 2;
        add(titleLabel, gbc);
        
        // Create login field with placeholder
        gbc.gridy++;
        gbc.gridwidth = 1;
        
        JLabel loginLabel = new JLabel("Login");
        loginLabel.setForeground(Colors.AUTHORIZATION_LABEL_FOREGROUND_COLOR); // Placeholder color
        add(loginLabel, gbc);
        
        gbc.gridx++;
        loginField = new JTextField(20);
        loginField.setOpaque(false); // Make transparent
        loginField.setForeground(Color.WHITE);
        loginField.setCaretColor(Color.WHITE);
        loginField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(loginField, gbc);
        
        // Create password field with placeholder
        gbc.gridy++;
        gbc.gridx = 0;

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(Colors.AUTHORIZATION_LABEL_FOREGROUND_COLOR); // Placeholder color
        add(passwordLabel, gbc);
        
        gbc.gridx++;
        passwordField = new JPasswordField(20);
        passwordField.setOpaque(false); // Make transparent
        passwordField.setForeground(Color.WHITE);
        passwordField.setCaretColor(Color.WHITE);
        passwordField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(passwordField, gbc);
        
        // Create login button
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;

        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(18, 140, 126));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        loginButton.setOpaque(true);
        loginButton.setBorderPainted(false);
		loginButton.addActionListener(new LoginActionListener(this.userRepository, this, this.mainFrame.getSwitchedPanel()));
        add(loginButton, gbc);
        
        // Create signup link
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        signupButton = new JButton("Don't have an account? Sign up");
		signupButton.setActionCommand(Constants.SIGUP_PANEL_KEY);
        signupButton.setBackground(Colors.AUTHORIZATION_BACKGROUND_COLOR);
        signupButton.setForeground(Colors.AUTHORIZATION_BUTTON_FOREGROUD_COLOR);
        signupButton.setFocusPainted(false);
        signupButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        signupButton.setOpaque(true);
        signupButton.setBorderPainted(false);
		signupButton.addActionListener(new ResetablePanelSwitcherActionListener(mainFrame.getSwitchedPanel(), this));
        add(signupButton, gbc);

        // Create error label
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        add(errorLabel, gbc);

        setBackground(Colors.AUTHORIZATION_BACKGROUND_COLOR);
	}
    
    public String getUsername() 
    {
        return loginField.getText();
    }
    
    public String getPassword() 
    {
        String password = new String(passwordField.getPassword());
        return HashManager.generateHash(password);
    }

    public void setErorr(String message)
    {
        errorLabel.setText(message);
        this.revalidate();
        this.repaint();
    }

	public void reset()
	{
		loginField.setText("");
		passwordField.setText("");
		errorLabel.setText("");
	}
}