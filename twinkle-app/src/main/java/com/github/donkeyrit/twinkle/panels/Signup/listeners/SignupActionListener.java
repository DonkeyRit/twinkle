package com.github.donkeyrit.twinkle.panels.signup.listeners;

import com.github.donkeyrit.twinkle.dal.repositories.interfaces.UserRepository;
import com.github.donkeyrit.twinkle.dal.models.User;
import com.github.donkeyrit.twinkle.panels.common.SwitchedPanel;
import com.github.donkeyrit.twinkle.panels.signup.SignupPanel;
import com.github.donkeyrit.twinkle.bll.models.UserInformation;
import com.github.donkeyrit.twinkle.security.HashManager;
import com.github.donkeyrit.twinkle.utils.Constants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SignupActionListener implements ActionListener 
{
    private final UserRepository userRepository;
    private final SignupPanel panel;
    private final SwitchedPanel container;

    public SignupActionListener(UserRepository userRepository, SignupPanel panel, SwitchedPanel switchedPanel) 
    {
        this.userRepository = userRepository;
        this.panel = panel;
        this.container = switchedPanel;
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
            return;
        }
        
        String passwordHash = HashManager.generateHash(password);
        User user = new User(username, passwordHash, false); 
        userRepository.insert(user);

		UserInformation.setUser(user);
		panel.reset();
        container.showPanel(Constants.CONTENT_PANEL_KEY);
    }
}
