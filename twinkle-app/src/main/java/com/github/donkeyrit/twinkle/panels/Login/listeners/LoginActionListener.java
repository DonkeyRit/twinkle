package com.github.donkeyrit.twinkle.panels.login.listeners;

import com.github.donkeyrit.twinkle.dal.repositories.interfaces.UserRepository;
import com.github.donkeyrit.twinkle.dal.models.User;
import com.github.donkeyrit.twinkle.panels.common.SwitchedPanel;
import com.github.donkeyrit.twinkle.panels.login.LoginPanel;
import com.github.donkeyrit.twinkle.bll.models.UserInformation;
import com.github.donkeyrit.twinkle.utils.Constants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Optional;

public class LoginActionListener implements ActionListener
{
    private UserRepository userRepository;
    private LoginPanel panel;
    private SwitchedPanel container;

    public LoginActionListener(UserRepository userRepository, LoginPanel panel, SwitchedPanel switchedPanel)
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

		UserInformation.setUser(currentUser.get());
		panel.reset();
        container.showPanel(Constants.CONTENT_PANEL_KEY);
    }
}
