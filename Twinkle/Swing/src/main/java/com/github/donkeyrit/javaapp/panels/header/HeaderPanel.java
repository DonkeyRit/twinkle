package com.github.donkeyrit.javaapp.panels.header;

import com.github.donkeyrit.javaapp.container.ServiceContainer;
import com.github.donkeyrit.javaapp.model.User;
import com.github.donkeyrit.javaapp.panels.maintenance.UserActionsProfilePanel;
import com.github.donkeyrit.javaapp.panels.filter.FilterPanel;
import com.github.donkeyrit.javaapp.panels.abstraction.CustomPanel;
import com.github.donkeyrit.javaapp.panels.content.ContentPanel;
import com.github.donkeyrit.javaapp.panels.login.LoginPanel;
import com.github.donkeyrit.javaapp.resources.Assets;
import com.github.donkeyrit.javaapp.resources.ResourceManager;
import com.github.donkeyrit.javaapp.ui.UiManager;

import javax.swing.*;
import java.awt.*;

public class HeaderPanel extends CustomPanel {

    private UiManager uiManager;
    private User currentUser;

    private JButton logoButton;
    private JButton userAvatarButton;
    private JButton exitButton;


    public HeaderPanel() {
        this.setLayout(null);

        ServiceContainer serviceContainer = ServiceContainer.getInstance();
        this.uiManager = serviceContainer.getUiManager();
        this.currentUser = serviceContainer.getUser();

        initialize();

        add(logoButton);
        add(userAvatarButton);
        add(exitButton);
    }

    private void initialize() {
        logoButton = new JButton();
        logoButton.setBounds(30, 10, 60, 60);
        setIconToButton(logoButton, ResourceManager.getImageIconFromResources(Assets.LOGO, "logo.png"));
        logoButton.addActionListener(e -> this.uiManager.getLayout()
                .setHeader(new HeaderPanel())
                .setSidebar(new FilterPanel())
                .setContent(new ContentPanel())
        );
        userAvatarButton = new JButton();
        userAvatarButton.setBounds(725, 10, 60, 60);
        setIconToButton(userAvatarButton, ResourceManager.getImageIconFromResources(Assets.MINI_AVATAR, String.format("%d.png", currentUser.getAvatarNumber())));
        userAvatarButton.addActionListener(e -> uiManager.getLayout()
                .setHeader(new HeaderPanel())
                .setSidebar(new UserActionsProfilePanel())
                .removeContent()
        );

        exitButton = new JButton();
        exitButton.setBounds(795, 10, 60, 60);
        setIconToButton(exitButton, ResourceManager.getImageIconFromResources(Assets.BUTTONS, "exit.png"));
        exitButton.addActionListener(e -> {
            ServiceContainer.getInstance().setUser(null);
            uiManager.getLayout().setFullPagePanel(new LoginPanel());
        });
    }

    private void setIconToButton(JButton button, ImageIcon icon) {
        button.setIcon(icon);
        button.setHorizontalTextPosition(SwingConstants.LEFT);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
    }

    @Override
    public Rectangle getBoundsRectangle() {
        return new Rectangle(0, 0, 875, 80);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(new Color(0, 163, 163));
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }
}
