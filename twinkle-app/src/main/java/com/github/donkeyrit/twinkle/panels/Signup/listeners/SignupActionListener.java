package com.github.donkeyrit.twinkle.panels.Signup.listeners;

import com.github.donkeyrit.twinkle.dal.repositories.Interfaces.UserRepository;
import com.github.donkeyrit.twinkle.panels.Signup.SignupPanel;
import com.github.donkeyrit.twinkle.security.HashManager;
import com.github.donkeyrit.twinkle.dal.models.User;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SignupActionListener implements ActionListener 
{
    private final UserRepository userRepository;
    private final SignupPanel panel;

    public SignupActionListener(UserRepository userRepository, SignupPanel panel) 
    {
        this.userRepository = userRepository;
        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        String username = panel.getUsername();
        String password = panel.getPassword();
        String confirmPassword = panel.getConfirmPassword();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) 
        {
            panel.setErorr("All fields are required.");
            return;
        }

        if (!password.equals(confirmPassword)) 
        {
            panel.setErorr("Passwords do not match.");
            return;
        }

        if(!userRepository.isUserExist(username))
        {
            panel.setErorr("Login already exist");
        }
        
        String passwordHash = HashManager.generateHash(password);
        User user = new User(username, passwordHash, false); 
        userRepository.insert(user);

        panel.setErorr("Registration successful!");
    }
}
