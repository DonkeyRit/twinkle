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
import com.github.donkeyrit.javaapp.utils.IValidationEngine;
import com.github.donkeyrit.javaapp.utils.ValidationEngine;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class SignInPanel extends CustomPanel {

    private final ServiceContainer serviceContainer;
    private final DatabaseProvider databaseProvider;
    private final UiManager uiManager;
    private final UserModelProvider userModelProvider;
    private final IValidationEngine validationEngine;

    private JCustomTextField login;
    private JCustomPasswordField password;
    private JCustomPasswordField reEnterPassword;
    private JButton logInButton;
    private JButton backButton;


    public SignInPanel() {
        setLayout(null);

        serviceContainer = ServiceContainer.getInstance();
        databaseProvider = serviceContainer.getDatabaseProvider();
        userModelProvider = databaseProvider.getUserModelProvider();
        uiManager = serviceContainer.getUiManager();
        validationEngine = new ValidationEngine();

        configureValidator();
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

            if (validationEngine.validate()) {
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

            revalidate();
            repaint();
        });

        backButton = new JButton();
        backButton.setBounds(500, 360, 28, 30);
        backButton.setHorizontalTextPosition(SwingConstants.LEFT);
        backButton.setIcon(ResourceManager.getImageIconFromResources(Assets.BUTTONS, "return.png"));
        backButton.addActionListener(e -> uiManager.getLayout().setFullPagePanel(new LoginPanel()));
    }

    private void configureValidator() {
        validationEngine.addRule(() -> login.getText().isEmpty(), o -> login.setState("Please, enter login", Color.RED))
                .addRule(() -> password.getPassword().length == 0, o -> password.setState("Please, enter password", Color.RED))
                .addRule(() -> reEnterPassword.getPassword().length == 0, o -> reEnterPassword.setState("Please, repeat password", Color.RED))
                .addRule(() -> !Arrays.equals(password.getPassword(), reEnterPassword.getPassword()), o -> {
                    password.setState("Password do not match", Color.RED);
                    reEnterPassword.setState("Password do not match", Color.RED);
                })
                .addRule(() -> userModelProvider.getSpecificUserByLogin(ShieldingProvider.shielding(login.getText())) != null,
                        o -> login.setState("Login already exist", Color.RED)
                );
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
