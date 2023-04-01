package com.github.donkeyrit.twinkle.frame;

import javax.swing.*;

import com.github.donkeyrit.twinkle.panels.Content.ContentCompositePanel;
import com.github.donkeyrit.twinkle.panels.Login.LoginPanel;
import com.github.donkeyrit.twinkle.panels.Signup.SignupPanel;

import java.awt.*;

public class MainFrame extends JFrame 
{
    private JPanel container;
    private CardLayout cardLayout;

    public MainFrame(String title) 
    {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(875,700); 
        setResizable(false);
        setLocationRelativeTo(null);

        // Create container panel
        container = new JPanel();
        cardLayout = new CardLayout();
        container.setLayout(cardLayout);

        // Add container to frame
        add(container, BorderLayout.CENTER);
    }

    public void setLoginPanel(LoginPanel loginPanel)
    {
        addPanel("login", loginPanel);
    }

    public void showLoginPanel()
    {
        setPanel("login");
    }

    public void setSignupPanel(SignupPanel signupPanel)
    {
        addPanel("signup", signupPanel);
    }

    public void showSignupPanel()
    {
        setPanel("signup");
    }

    public void setContentCompositePanel(ContentCompositePanel contentCompositePanel)
    {
        addPanel("content", contentCompositePanel);
    }

    public void showContent()
    {
        setPanel("content");
    }

    private void setPanel(String key)
    {
        this.cardLayout.show(container, key);
    }

    private void addPanel(String key, JPanel panel)
    {
        container.add(panel, key);
    }
}

