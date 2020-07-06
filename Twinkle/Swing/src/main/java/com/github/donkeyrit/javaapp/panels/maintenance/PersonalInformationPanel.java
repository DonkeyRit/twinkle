package com.github.donkeyrit.javaapp.panels.maintenance;

import com.github.donkeyrit.javaapp.components.JCustomTextField;
import com.github.donkeyrit.javaapp.container.ServiceContainer;
import com.github.donkeyrit.javaapp.database.DatabaseModelProviders.ClientModelProvider;
import com.github.donkeyrit.javaapp.model.Client;
import com.github.donkeyrit.javaapp.model.User;
import com.github.donkeyrit.javaapp.panels.abstraction.CustomPanel;
import com.github.donkeyrit.javaapp.resources.Assets;
import com.github.donkeyrit.javaapp.resources.ResourceManager;
import com.github.donkeyrit.javaapp.utils.IValidationEngine;
import com.github.donkeyrit.javaapp.utils.ValidationEngine;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class PersonalInformationPanel extends CustomPanel {

    private final IValidationEngine validator;

    private Box box;
    private JCustomTextField firstNameTextField;
    private JCustomTextField secondNameTextField;
    private JCustomTextField middleNameTextField;
    private JCustomTextField addressTextField;
    private JCustomTextField phoneNumberTextField;
    private JButton confirm;

    public PersonalInformationPanel() {
        setLayout(null);
        validator = new ValidationEngine();

        configureValidator();
        initialize();

        box.add(firstNameTextField);
        box.add(Box.createVerticalStrut(10));
        box.add(secondNameTextField);
        box.add(Box.createVerticalStrut(10));
        box.add(middleNameTextField);
        box.add(Box.createVerticalStrut(10));
        box.add(addressTextField);
        box.add(Box.createVerticalStrut(10));
        box.add(phoneNumberTextField);
        box.add(Box.createVerticalStrut(10));
        box.add(Box.createHorizontalStrut(60));
        box.add(confirm);
        add(box);
    }

    private void initialize() {
        ServiceContainer serviceContainer = ServiceContainer.getInstance();
        ClientModelProvider clientModelProvider = serviceContainer.getDatabaseProvider().getClientModelProvider();

        User user = serviceContainer.getUser();
        Client client = clientModelProvider.getClientByUserId(user);

        box = Box.createVerticalBox();
        box.setBounds(202, 10, 200, 250);
        box.setBorder(new TitledBorder("Personal information"));

        firstNameTextField = new JCustomTextField();
        firstNameTextField.setState("Enter firstName");

        secondNameTextField = new JCustomTextField();
        secondNameTextField.setState("Enter secondName");

        middleNameTextField = new JCustomTextField();
        middleNameTextField.setState("Enter patronimic");

        addressTextField = new JCustomTextField();
        addressTextField.setState("Enter address");

        phoneNumberTextField = new JCustomTextField();
        phoneNumberTextField.setState("Enter phoneNumber");

        if (client != null) {
            firstNameTextField.setText(client.getFirstName());
            secondNameTextField.setText(client.getSecondName());
            middleNameTextField.setText(client.getMiddleName());
            addressTextField.setText(client.getAddress());
            phoneNumberTextField.setText(client.getPhoneNumber());
        }

        confirm = new JButton("Confirm");
        confirm.addActionListener(e -> {

            if (validator.validate()) {

                if (client == null) {
                    Client.ClientBuilder clientBuilder = new Client.ClientBuilder();
                    Client newClient = clientBuilder.setFirstName(firstNameTextField.getText())
                            .setSecondName(secondNameTextField.getText())
                            .setMiddleName(middleNameTextField.getText())
                            .setAddress(addressTextField.getText())
                            .setPhoneNumber(phoneNumberTextField.getText())
                            .create();
                    clientModelProvider.createClient(newClient, user);
                } else {
                    clientModelProvider.updateClient(client);
                }

                for (JCustomTextField JCustomTextField : new JCustomTextField[]{firstNameTextField, secondNameTextField, middleNameTextField, addressTextField, phoneNumberTextField}) {
                    JCustomTextField.setState("Success", Color.green);
                    JCustomTextField.setText("");
                }
            }

            revalidate();
            repaint();
        });
    }

    private void configureValidator() {
        validator.addRule(() -> !firstNameTextField.getText().isEmpty(), o -> firstNameTextField.setState(firstNameTextField.getPlaceholder(), Color.red))
                .addRule(() -> !secondNameTextField.getText().isEmpty(), o -> secondNameTextField.setState(secondNameTextField.getPlaceholder(), Color.red))
                .addRule(() -> !middleNameTextField.getText().isEmpty(), o -> middleNameTextField.setState(middleNameTextField.getPlaceholder(), Color.red))
                .addRule(() -> !addressTextField.getText().isEmpty(), o -> addressTextField.setState(addressTextField.getPlaceholder(), Color.red))
                .addRule(() -> !phoneNumberTextField.getText().isEmpty(), o -> phoneNumberTextField.setState(phoneNumberTextField.getPlaceholder(), Color.red));
    }

    @Override
    public Rectangle getBoundsRectangle() {
        return new Rectangle(250, 100, 605, 550);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(new Color(237, 237, 237));
        g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 30, 25);

        Image image = ResourceManager.getImageFromResources(Assets.BACKGROUND, "fill_data.png");
        g.drawImage(image, 50, 310, this);
    }
}
