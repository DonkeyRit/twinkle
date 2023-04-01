package com.github.donkeyrit.twinkle.panels.Login.listeners;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Optional;

import com.github.donkeyrit.twinkle.dal.repositories.Interfaces.UserRepository;
import com.github.donkeyrit.twinkle.dal.models.User;
import com.github.donkeyrit.twinkle.panels.Login.LoginPanel;

public class LoginActionListener implements ActionListener
{
    private UserRepository userRepository;
    private LoginPanel panel;

    public LoginActionListener(UserRepository userRepository, LoginPanel panel)
    {
        this.userRepository = userRepository;
        this.panel = panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        String username = panel.getUsername();
        String password = panel.getPassword();

        if(username.isEmpty() || password.isEmpty())
        {
            this.panel.setErorr("Please fill both fields.");
            return;
        }

        Optional<User> currentUser = userRepository.getByLoginAndPassword(username, password);
        if(currentUser.isEmpty())
        {
            this.panel.setErorr("Incorrect login or password.");
            return;
        }
        
        this.panel.setErorr("Success");
    }
}
