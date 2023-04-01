package com.github.donkeyrit.twinkle.panels.Login;

import java.awt.event.ActionListener;
import java.awt.*;

import javax.swing.*;

import com.github.donkeyrit.twinkle.security.HashManager;

public class LoginPanel extends JPanel 
{
    private JTextField loginField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton signupButton;
    private JLabel errorLabel;
    
    public LoginPanel() 
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
        titleLabel.setForeground(new Color(255, 255, 255, 150)); 
        gbc.gridwidth = 2;
        add(titleLabel, gbc);
        
        // Create login field with placeholder
        gbc.gridy++;
        gbc.gridwidth = 1;
        JLabel loginLabel = new JLabel("Login");
        loginLabel.setForeground(new Color(255, 255, 255, 150)); // Placeholder color
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
        passwordLabel.setForeground(new Color(255, 255, 255, 150)); // Placeholder color
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
        add(loginButton, gbc);
        
        // Create signup link
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        signupButton = new JButton("Don't have an account? Sign up");
        signupButton.setBackground(new Color(40, 40, 40));
        signupButton.setForeground(Color.WHITE);
        signupButton.setFocusPainted(false);
        signupButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        signupButton.setOpaque(true);
        signupButton.setBorderPainted(false);
        add(signupButton, gbc);

        // Create error label
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        add(errorLabel, gbc);

        setBackground(new Color(39, 40, 34));
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

    public void addLoginActionListener(ActionListener listener) 
    {
        loginButton.addActionListener(listener);
    }

    public void addSignupActionListener(ActionListener listener)
    {
        signupButton.addActionListener(listener);
    }
}