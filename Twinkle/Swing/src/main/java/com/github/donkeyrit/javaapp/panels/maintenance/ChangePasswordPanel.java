package com.github.donkeyrit.javaapp.panels.maintenance;

import com.github.donkeyrit.javaapp.components.JCustomPasswordField;
import com.github.donkeyrit.javaapp.container.ServiceContainer;
import com.github.donkeyrit.javaapp.database.DatabaseModelProviders.UserModelProvider;
import com.github.donkeyrit.javaapp.model.User;
import com.github.donkeyrit.javaapp.panels.abstraction.CustomPanel;
import com.github.donkeyrit.javaapp.resources.Assets;
import com.github.donkeyrit.javaapp.resources.ResourceManager;
import com.github.donkeyrit.javaapp.security.SecurityProvider;
import com.github.donkeyrit.javaapp.utils.IValidationEngine;
import com.github.donkeyrit.javaapp.utils.ValidationEngine;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ChangePasswordPanel extends CustomPanel {

    private final UserModelProvider userModelProvider;
    private final IValidationEngine validator;
    private final User user;

    private Box mainBox;
    private JCustomPasswordField oldPasswordField;
    private JCustomPasswordField newPasswordField;
    private JCustomPasswordField repeatNewPasswordField;
    private JButton confirmButton;

    public ChangePasswordPanel() {
        setLayout(null);

        ServiceContainer serviceContainer = ServiceContainer.getInstance();
        userModelProvider = serviceContainer.getDatabaseProvider().getUserModelProvider();
        validator = new ValidationEngine();
        user = serviceContainer.getUser();

        configureValidator();
        initialize();

        add(mainBox);
    }

    private void initialize() {
        mainBox = Box.createVerticalBox();
        mainBox.setBorder(new TitledBorder("Change Password"));
        mainBox.setBounds(202, 10, 200, 200);

        oldPasswordField = new JCustomPasswordField();
        oldPasswordField.setState("Enter old password");

        mainBox.add(oldPasswordField);
        mainBox.add(Box.createVerticalStrut(10));

        newPasswordField = new JCustomPasswordField();
        newPasswordField.setState("Enter new password");

        mainBox.add(newPasswordField);
        mainBox.add(Box.createVerticalStrut(10));

        repeatNewPasswordField = new JCustomPasswordField();
        repeatNewPasswordField.setState("Repeat new password");

        mainBox.add(repeatNewPasswordField);
        mainBox.add(Box.createVerticalStrut(10));

        confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> {

            if (validator.validate()) {
                String password = SecurityProvider.sha1(new String(newPasswordField.getPassword()));

                userModelProvider.updateUserPassword(user, password);
                user.setPassword(password);

                for (JCustomPasswordField field : new JCustomPasswordField[]{oldPasswordField, newPasswordField, repeatNewPasswordField}) {
                    field.setText("");
                    field.setState("Success", Color.green);
                }
            }

            this.revalidate();
            this.repaint();
        });
        mainBox.add(Box.createHorizontalStrut(60));
        mainBox.add(confirmButton);
    }

    private void configureValidator() {

        validator.addRule(() -> new String(oldPasswordField.getPassword()).isEmpty(), o -> oldPasswordField.setState("Please, enter old password", Color.RED))
                .addRule(() -> new String(newPasswordField.getPassword()).isEmpty(), o -> newPasswordField.setState("Please, enter new password", Color.RED))
                .addRule(() -> new String(repeatNewPasswordField.getPassword()).isEmpty(), o -> repeatNewPasswordField.setState("Please, repeat new password", Color.RED))
                .addRule(() -> new String(newPasswordField.getPassword()).equals(new String(repeatNewPasswordField.getPassword())), o -> {
                    newPasswordField.setState("Password does't match", Color.RED);
                    repeatNewPasswordField.setState("Password does't match", Color.RED);
                    newPasswordField.setText("");
                    repeatNewPasswordField.setText("");
                })
                .addRule(() -> SecurityProvider.sha1(new String(oldPasswordField.getPassword())).equals(user.getPassword()), o -> {
                    oldPasswordField.setText("");
                    oldPasswordField.setState("Incorrect password", Color.RED);
                })
                .addRule(() -> new String(oldPasswordField.getPassword()).equals(new String(newPasswordField.getPassword())), o -> {
                    oldPasswordField.setState("Old and new match", Color.RED);
                    newPasswordField.setState("Old and new match", Color.RED);
                    oldPasswordField.setText("");
                    newPasswordField.setText("");
                });
    }

    @Override
    public Rectangle getBoundsRectangle() {
        return new Rectangle(250, 100, 605, 550);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(new Color(237, 237, 237));
        g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 30, 25);

        Image image = ResourceManager.getImageFromResources(Assets.BACKGROUND, "page.png");
        g.drawImage(image, 50, 250, this);
    }
}
