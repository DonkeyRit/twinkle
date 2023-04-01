package com.github.donkeyrit.twinkle.panels.Signup;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

import com.github.donkeyrit.twinkle.styles.Colors;

public class SignupPanel extends JPanel 
{
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton registerButton;
    private JButton loginButton;
    private JLabel errorLabel;
    

    public SignupPanel() 
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

        registerButton = new JButton("Register");
        registerButton.setBackground(new Color(18, 140, 126));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        registerButton.setOpaque(true);
        registerButton.setBorderPainted(false);
        add(registerButton, gbc);
        
        // Create login link
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        loginButton = new JButton("Already have an account? Login");
        loginButton.setBackground(Colors.AUTHORIZATION_BUTTON_BACKGROUND_COLOR);
        loginButton.setForeground(Colors.AUTHORIZATION_BUTTON_FOREGROUD_COLOR);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        loginButton.setOpaque(true);
        loginButton.setBorderPainted(false);
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

    public void addSignupActionListener(ActionListener listener) 
    {
        registerButton.addActionListener(listener);
    }

    public void addLoginActionListener(ActionListener listener)
    {
        loginButton.addActionListener(listener);
    }
}
    