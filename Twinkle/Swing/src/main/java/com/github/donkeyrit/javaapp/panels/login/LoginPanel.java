package com.github.donkeyrit.javaapp.panels.login;

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
import com.github.donkeyrit.javaapp.panels.register.SignInPanel;
import com.github.donkeyrit.javaapp.resources.Assets;
import com.github.donkeyrit.javaapp.resources.ResourceManager;
import com.github.donkeyrit.javaapp.security.SecurityProvider;
import com.github.donkeyrit.javaapp.security.ShieldingProvider;
import com.github.donkeyrit.javaapp.ui.UiManager;
import com.github.donkeyrit.javaapp.utils.IValidationEngine;
import com.github.donkeyrit.javaapp.utils.ValidationEngine;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends CustomPanel {

    private final ServiceContainer serviceContainer;
    private final UiManager uiManager;
    private final DatabaseProvider databaseProvider;
    private final UserModelProvider userModelProvider;
    private final IValidationEngine validationEngine;

    private JCustomTextField login;
    private JCustomPasswordField password;
    private JButton signIn;
    private JButton register;

    public LoginPanel() {

        setLayout(null);

        this.serviceContainer = ServiceContainer.getInstance();
        this.uiManager = serviceContainer.getUiManager();
        this.databaseProvider = serviceContainer.getDatabaseProvider();
        this.userModelProvider = this.databaseProvider.getUserModelProvider();
        this.validationEngine = new ValidationEngine();

        initialize();
        configureValidator();

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

            if (validationEngine.validate()) {

                User currentUser = userModelProvider.getSpecificUserByCredentials(
                        ShieldingProvider.shielding(login.getText()),
                        SecurityProvider.sha1(ShieldingProvider.shielding(new String(password.getPassword())))
                );

                this.serviceContainer.setUser(currentUser);
                this.uiManager.getLayout()
                        .setHeader(new HeaderPanel())
                        .setSidebar(new FilterPanel())
                        .setContent(new ContentPanel());
            }
            
            revalidate();
            repaint();
        });
        signIn.setBounds(358, 320, 80, 20);

        register = new JButton("Log in");
        register.setBounds(448, 320, 80, 20);
        register.addActionListener(e -> this.uiManager.getLayout().setFullPagePanel(new SignInPanel()));
    }

    private void configureValidator() {
        validationEngine.addRule(() -> login.getText().isEmpty(), o -> login.setState("Please, enter login", Color.RED))
                .addRule(() -> password.getPassword().length == 0, o -> password.setState("Please, enter password", Color.RED))
                .addRule(() -> userModelProvider.getSpecificUserByCredentials(
                        ShieldingProvider.shielding(login.getText()),
                        SecurityProvider.sha1(ShieldingProvider.shielding(new String(password.getPassword())))
                        ) == null,
                        o -> {
                            login.setState("Incorrect login", Color.RED);
                            password.setState("Incorrect password", Color.RED);
                        }
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
        g2.fillRoundRect(338, 230, 208, 130, 30, 15);

        int avatarNumber = (int) (Math.random() * 6 + 1);
        Image avatar = ResourceManager.getImageFromResources(Assets.AVATAR, String.format("%d.png", avatarNumber));
        g.drawImage(avatar, 380, 100, this);
    }

}
