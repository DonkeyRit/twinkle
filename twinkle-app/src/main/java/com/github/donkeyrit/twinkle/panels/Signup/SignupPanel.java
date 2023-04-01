package com.github.donkeyrit.twinkle.panels.Signup;

import java.awt.event.*;
import java.awt.*;
import javax.swing.*;

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
        setBackground(new Color(33, 33, 33));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        // Create username label
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setForeground(new Color(239, 239, 239));
        add(usernameLabel, gbc);
        gbc.gridy++;

        // Create password lable
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setForeground(new Color(239, 239, 239));
        add(passwordLabel, gbc);
        gbc.gridy++;

        // Create confirm password label
        JLabel confirmPasswordLabel = new JLabel("Confirm Password");
        confirmPasswordLabel.setForeground(new Color(239, 239, 239));
        add(confirmPasswordLabel, gbc);

        gbc.gridx++;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // Create username text field
        usernameField = new JTextField(20);
        usernameField.setOpaque(false);
        usernameField.setForeground(new Color(239, 239, 239));
        usernameField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(239, 239, 239)));
        usernameField.setCaretColor(new Color(239, 239, 239));
        usernameField.setHorizontalAlignment(JTextField.RIGHT);
        add(usernameField, gbc);
        gbc.gridy++;

        // Create password text field
        passwordField = new JPasswordField(20);
        passwordField.setOpaque(false);
        passwordField.setForeground(new Color(239, 239, 239));
        passwordField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(239, 239, 239)));
        passwordField.setCaretColor(new Color(239, 239, 239));
        passwordField.setHorizontalAlignment(JTextField.RIGHT);
        add(passwordField, gbc);
        gbc.gridy++;

        // Create confirm password text field
        confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setOpaque(false);
        confirmPasswordField.setForeground(new Color(239, 239, 239));
        confirmPasswordField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(239, 239, 239)));
        confirmPasswordField.setCaretColor(new Color(239, 239, 239));
        confirmPasswordField.setHorizontalAlignment(JTextField.RIGHT);
        add(confirmPasswordField, gbc);
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        
        // Create register button
        registerButton = new JButton("Register");
        registerButton.setFocusPainted(false);
        registerButton.setForeground(new Color(239, 239, 239));
        registerButton.setBackground(new Color(59, 89, 152));
        registerButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(59, 89, 152)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        add(registerButton, gbc);
        gbc.gridy++;

        // Create login link
        loginButton = new JButton("Already have an account? Login");
        loginButton.setForeground(new Color(239, 239, 239));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        add(loginButton, gbc);
        gbc.gridy++;
        errorLabel = new JLabel("");

        // Create error block
        errorLabel.setForeground(new Color(255, 0, 0));
        add(errorLabel, gbc);
    
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
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
    