package com.github.donkeyrit.javaapp.panels;

import com.github.donkeyrit.javaapp.EntryPoint;

import javax.swing.*;
import java.awt.*;

public class HeaderPanel extends JPanel {

    private JPanel panel;

    public HeaderPanel(EntryPoint point){
        this.setLayout(null);

        panel = point.panel;

        JButton logo = new JButton();
        logo.addActionListener(e -> {
            panel.removeAll();
            panel.revalidate();
            panel.repaint();
            point.showContent();
        });

        logo.setBounds(30, 10, 60, 60);
        ImageIcon icon = new ImageIcon("assets/logo/logo.png");
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

            JPanel headerPanel = new HeaderPanel(point);
            headerPanel.setBounds(0,0,875,80);
            panel.add(headerPanel);

            JPanel chooseActionPanel = new ChooseActionPanel(point);
            chooseActionPanel.setBounds(30, 100, 200, 550);
            panel.add(chooseActionPanel);
        });
        avatar.setBounds(725, 10, 60, 60);
        ImageIcon iconAvatar = new ImageIcon("assets/avatar/mini_avatar/" + point.avatarNumber + ".png");
        avatar.setIcon(iconAvatar);
        avatar.setHorizontalTextPosition(SwingConstants.LEFT);
        avatar.setBorderPainted(false);
        avatar.setFocusPainted(false);
        avatar.setContentAreaFilled(false);
        add(avatar);


        JButton exit = new JButton();
        exit.setBounds(795,10,60,60);
        ImageIcon iconExit = new ImageIcon("assets/buttons/exit.png");
        exit.setIcon(iconExit);
        exit.setHorizontalTextPosition(SwingConstants.LEFT);
        exit.setBorderPainted(false);
        exit.setFocusPainted(false);
        exit.setContentAreaFilled(false);
        exit.addActionListener(e -> {
            point.user = null;
            panel.removeAll();
            point.showAuthorization();
            panel.revalidate();
            panel.repaint();
        });
        add(exit);
    }

    @Override
    public void paintComponent(Graphics g){
        g.setColor(new Color(0,163,163));
        g.fillRect(0,0,this.getWidth(),this.getHeight());
    }
}
