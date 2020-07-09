package com.github.donkeyrit.javaapp.panels.register;

import com.github.donkeyrit.javaapp.components.JCustomPasswordField;
import com.github.donkeyrit.javaapp.components.JCustomTextField;
import com.github.donkeyrit.javaapp.container.ServiceContainer;
import com.github.donkeyrit.javaapp.database.DatabaseModelProviders.UserModelProvider;
import com.github.donkeyrit.javaapp.database.DatabaseProvider;
import com.github.donkeyrit.javaapp.model.User;
import com.github.donkeyrit.javaapp.panels.CustomPanel;
import com.github.donkeyrit.javaapp.panels.content.ContentPanel;
import com.github.donkeyrit.javaapp.panels.filter.FilterPanel;
import com.github.donkeyrit.javaapp.panels.header.HeaderPanel;
import com.github.donkeyrit.javaapp.panels.login.LoginPanel;
import com.github.donkeyrit.javaapp.resources.Assets;
import com.github.donkeyrit.javaapp.resources.ResourceManager;
import com.github.donkeyrit.javaapp.security.SecurityProvider;
import com.github.donkeyrit.javaapp.security.ShieldingProvider;
import com.github.donkeyrit.javaapp.ui.UiManager;

import javax.swing.*;
import java.awt.*;

public class SignInPanel extends CustomPanel {

    private final ServiceContainer serviceContainer;
    private final DatabaseProvider databaseProvider;
    private final UiManager uiManager;

    private JCustomTextField login;
    private JCustomPasswordField password;
    private JCustomPasswordField reEnterPassword;
    private JButton logInButton;
    private JButton backButton;


    public SignInPanel() {
        setLayout(null);

        serviceContainer = ServiceContainer.getInstance();
        databaseProvider = serviceContainer.getDatabaseProvider();
        uiManager = serviceContainer.getUiManager();

        initialize();

        add(login);
        add(password);
        add(reEnterPassword);
        add(logInButton);
        add(backButton);
    }

    private void initialize() {

        login = new JCustomTextField();
        login.setState("Enter login");
        login.setBounds(358, 240, 170, 30);

        password = new JCustomPasswordField();
        password.setState("Enter password");
        password.setBounds(358, 280, 170, 30);

        reEnterPassword = new JCustomPasswordField();
        reEnterPassword.setState("Repeat password");
        reEnterPassword.setBounds(358, 320, 170, 30);

        logInButton = new JButton("Log in");
        logInButton.setBounds(358, 360, 135, 30);
        logInButton.addActionListener(e -> {

            boolean isOne = login.getText().isEmpty();
            boolean isTwo = new String(password.getPassword()).isEmpty();
            boolean isThree = new String(reEnterPassword.getPassword()).isEmpty();

            if (isOne) {
                login.setState("Please, enter login", Color.RED);
            }

            if (isTwo) {
                password.setState("Please, enter password", Color.RED);
            }

            if (isThree) {
                reEnterPassword.setState("Please, repeat password", Color.RED);
            }

            if (!isOne && !isTwo && !isThree) {
                if (!new String(password.getPassword()).equals(new String(reEnterPassword.getPassword()))) {
                    password.setState("Password do not match", Color.RED);
                    reEnterPassword.setState("Password do not match", Color.RED);
                } else {

                    UserModelProvider userModelProvider = databaseProvider.getUserModelProvider();
                    User existingUser = userModelProvider.getSpecificUserByLogin(ShieldingProvider.shielding(login.getText()));

                    if (existingUser != null) {
                        login.setState("Login already exist", Color.RED);
                    } else {

                        User newUser = new User(
                                ShieldingProvider.shielding(login.getText()),
                                SecurityProvider.sha1(ShieldingProvider.shielding(new String(password.getPassword()))),
                                false
                        );

                        userModelProvider.addUser(newUser);
                        serviceContainer.setUser(newUser);
                        uiManager.getLayout()
                                .setHeader(new HeaderPanel())
                                .setSidebar(new FilterPanel())
                                .setContent(new ContentPanel());
                    }
                }
            }

            revalidate();
            repaint();
        });

        backButton = new JButton();
        backButton.setBounds(500, 360, 28, 30);
        backButton.setHorizontalTextPosition(SwingConstants.LEFT);
        backButton.setIcon(ResourceManager.getImageIconFromResources(Assets.BUTTONS, "return.png"));
        backButton.addActionListener(e -> uiManager.getLayout().setFullPagePanel(new LoginPanel()));
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
        g2.fillRoundRect(338, 230, 208, 180, 30, 15);

        int avatarNumber = (int) (Math.random() * 6 + 1);
        Image avatar = ResourceManager.getImageFromResources(Assets.AVATAR, String.format("%d.png", avatarNumber));
        g.drawImage(avatar, 380, 100, this);
    }
}
