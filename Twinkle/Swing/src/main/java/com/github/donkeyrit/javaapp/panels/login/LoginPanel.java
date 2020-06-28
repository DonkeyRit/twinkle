package com.github.donkeyrit.javaapp.panels.login;

import com.github.donkeyrit.javaapp.components.JCustomTextField;
import com.github.donkeyrit.javaapp.components.JCustomPasswordField;
import com.github.donkeyrit.javaapp.container.ServiceContainer;
import com.github.donkeyrit.javaapp.database.DatabaseModelProviders.UserModelProvider;
import com.github.donkeyrit.javaapp.database.DatabaseProvider;
import com.github.donkeyrit.javaapp.model.User;
import com.github.donkeyrit.javaapp.panels.FilterPanel;
import com.github.donkeyrit.javaapp.panels.header.HeaderPanel;
import com.github.donkeyrit.javaapp.panels.register.SignInPanel;
import com.github.donkeyrit.javaapp.panels.abstraction.CustomPanel;
import com.github.donkeyrit.javaapp.panels.content.ContentPanel;
import com.github.donkeyrit.javaapp.resources.Assets;
import com.github.donkeyrit.javaapp.resources.ResourceManager;
import com.github.donkeyrit.javaapp.security.SecurityProvider;
import com.github.donkeyrit.javaapp.security.ShieldingProvider;
import com.github.donkeyrit.javaapp.ui.Canvas;
import com.github.donkeyrit.javaapp.ui.UiManager;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends CustomPanel {

    private final ServiceContainer serviceContainer;
    private final UiManager uiManager;
    private final DatabaseProvider databaseProvider;
    private final Canvas panel;

    private JCustomTextField login;
    private JCustomPasswordField password;
    private JButton signIn;
    private JButton register;

    public LoginPanel() {

        setLayout(null);

        this.serviceContainer = ServiceContainer.getInstance();
        this.uiManager = serviceContainer.getUiManager();
        this.panel = this.uiManager.getCanvas();
        this.databaseProvider = serviceContainer.getDatabaseProvider();

        initialize();

        add(login);
        add(password);
        add(signIn);
        add(register);
    }

    private void initialize() {
        login = new JCustomTextField();
        login.setState("Enter login");
        login.setBounds(358, 240, 170, 30);

        password = new JCustomPasswordField();
        password.setState("Enter password");
        password.setBounds(358, 280, 170, 30);

        signIn = new JButton("Sign in");
        signIn.addActionListener(e -> {
            boolean isOne = login.getText().isEmpty();
            boolean isTwo = password.getText().isEmpty();

            if (isOne) {
                login.setState("Please, enter login", Color.RED);
            }
            if (isTwo) {
                password.setState("Please, enter password", Color.RED);
            }

            panel.revalidate();
            panel.repaint();

            if (!isOne && !isTwo) {

                UserModelProvider provider = databaseProvider.getUserModelProvider();
                try {
                    User currentUser = provider.getSpecificUserByCredentials(
                            ShieldingProvider.shielding(login.getText()),
                            SecurityProvider.sha1(ShieldingProvider.shielding(password.getText()))
                    );

                    if (currentUser != null) {

                        this.serviceContainer.setUser(currentUser);
                        this.uiManager.setWindowPanels(new HeaderPanel(), new FilterPanel(), new ContentPanel(""));

                    } else {

                        login.setState("Incorrect login", Color.RED);
                        password.setState("Incorrect password", Color.RED);
                    }

                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
            revalidate();
            repaint();
        });
        signIn.setBounds(358, 320, 80, 20);

        register = new JButton("Log in");
        register.setBounds(448, 320, 80, 20);
        register.addActionListener(e -> this.uiManager.setWindowPanels(new SignInPanel()));
    }

    @Override
    public Rectangle getBoundsRectangle() {
        return new Rectangle(0, 0, 875, 700);
    }

    @Override
    public void paintComponent(Graphics g) {

        Image image = ResourceManager.getImageFromResources(Assets.BACKGROUND, "enter.jpg");
        g.drawImage(image, 0, 0, this);

        GradientPaint gp = new GradientPaint(0, 0, Color.RED, 120, 120, Color.BLUE, true);
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(gp);
        g2.fillRoundRect(338, 230, 208, 130, 30, 15);

        int avatarNumber = (int) (Math.random() * 6 + 1);
        Image avatar = ResourceManager.getImageFromResources(Assets.AVATAR, String.format("%d.png", avatarNumber));
        g.drawImage(avatar, 380, 100, this);
    }

}
