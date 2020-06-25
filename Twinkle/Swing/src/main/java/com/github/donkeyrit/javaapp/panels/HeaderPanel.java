package com.github.donkeyrit.javaapp.panels;

import com.github.donkeyrit.javaapp.container.ServiceContainer;
import com.github.donkeyrit.javaapp.panels.abstraction.CustomPanel;
import com.github.donkeyrit.javaapp.panels.content.ContentPanel;
import com.github.donkeyrit.javaapp.panels.login.LoginPanel;
import com.github.donkeyrit.javaapp.resources.Assets;
import com.github.donkeyrit.javaapp.resources.ResourceManager;
import com.github.donkeyrit.javaapp.ui.MainPanel;
import com.github.donkeyrit.javaapp.ui.UiManager;

import javax.swing.*;
import java.awt.*;

public class HeaderPanel extends CustomPanel {

    private UiManager uiManager;
    private MainPanel panel;

    public HeaderPanel(){
        this.setLayout(null);

        ServiceContainer serviceContainer = ServiceContainer.getInstance();
        this.uiManager = serviceContainer.getUiManager();
        panel = this.uiManager.getMainPanel();

        JButton logo = new JButton();
        logo.addActionListener(e -> this.uiManager.replaceWindowPanel(new HeaderPanel(), new FilterPanel(), new ContentPanel("")));

        logo.setBounds(30, 10, 60, 60);
        ImageIcon icon = ResourceManager.getImageIconFromResources(Assets.LOGO,"logo.png");
        logo.setIcon(icon);
        logo.setHorizontalTextPosition(SwingConstants.LEFT);
        logo.setBorderPainted(false);
        logo.setFocusPainted(false);
        logo.setContentAreaFilled(false);
        add(logo);

        JButton avatar = new JButton();
        avatar.addActionListener(e -> {
            panel.removeAll();
            panel.revalidate();
            panel.repaint();

            JPanel headerPanel = new HeaderPanel();
            headerPanel.setBounds(0,0,875,80);
            panel.add(headerPanel);

            JPanel chooseActionPanel = new ChooseActionPanel();
            chooseActionPanel.setBounds(30, 100, 200, 550);
            panel.add(chooseActionPanel);
        });
        avatar.setBounds(725, 10, 60, 60);
        ImageIcon iconAvatar = ResourceManager.getImageIconFromResources(Assets.MINI_AVATAR, String.format("%d.png", ServiceContainer.getInstance().getUser().getAvatarNumber()));
        avatar.setIcon(iconAvatar);
        avatar.setHorizontalTextPosition(SwingConstants.LEFT);
        avatar.setBorderPainted(false);
        avatar.setFocusPainted(false);
        avatar.setContentAreaFilled(false);
        add(avatar);


        JButton exit = new JButton();
        exit.setBounds(795,10,60,60);
        ImageIcon iconExit = ResourceManager.getImageIconFromResources(Assets.BUTTONS, "exit.png");
        exit.setIcon(iconExit);
        exit.setHorizontalTextPosition(SwingConstants.LEFT);
        exit.setBorderPainted(false);
        exit.setFocusPainted(false);
        exit.setContentAreaFilled(false);
        exit.addActionListener(e -> {
            ServiceContainer.getInstance().setUser(null);
            uiManager.replaceWindowPanel(new LoginPanel());
        });
        add(exit);
    }

    @Override
    public Rectangle getBoundsRectangle() {
        return new Rectangle(0, 0, 875, 80);
    }

    @Override
    public void paintComponent(Graphics g){
        g.setColor(new Color(0,163,163));
        g.fillRect(0,0,this.getWidth(),this.getHeight());
    }
}
