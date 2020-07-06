package com.github.donkeyrit.javaapp.panels.maintenance;

import com.github.donkeyrit.javaapp.components.JCustomTextField;
import com.github.donkeyrit.javaapp.container.ServiceContainer;
import com.github.donkeyrit.javaapp.database.DatabaseModelProviders.ClientModelProvider;
import com.github.donkeyrit.javaapp.database.DatabaseProvider;
import com.github.donkeyrit.javaapp.model.Client;
import com.github.donkeyrit.javaapp.model.User;
import com.github.donkeyrit.javaapp.panels.abstraction.CustomPanel;
import com.github.donkeyrit.javaapp.resources.Assets;
import com.github.donkeyrit.javaapp.resources.ResourceManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

public class PersonalInformationPanel extends CustomPanel {

    private Box box;
    private JCustomTextField firstNameTextField;
    private JCustomTextField secondNameTextField;
    private JCustomTextField middleNameTextField;
    private JCustomTextField addressTextField;
    private JCustomTextField phoneNumberTextField;
    private JButton confirm;

    public PersonalInformationPanel() {
        setLayout(null);

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
        DatabaseProvider database = serviceContainer.getDatabaseProvider();
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
            /*int counter = 0;
            ArrayList<String> inputData = new ArrayList<>();
            for (JCustomTextField textField : fieldText) {
                if (!textField.getText().isEmpty()) {
                    counter++;
                    inputData.add(textField.getText());
                }
            }

            if (counter == fieldText.size()) {
                if (infoUser.size() == 0) {
                    StringBuilder createClient = new StringBuilder("INSERT INTO client(firstName,secondName,Patronimic,address,phoneNumber,idUser) VALUES (");
                    for (JCustomTextField JCustomTextField : fieldText) {
                        createClient.append("'").append(JCustomTextField.getText()).append("',");
                    }
                    createClient.append("(SELECT idUser FROM user WHERE login = '").append(user.getLogin()).append("'))");

                    database.insert(createClient.toString());
                    for (JCustomTextField JCustomTextField : fieldText) {
                        JCustomTextField.setState("Success", Color.green);
                        JCustomTextField.setText("");
                    }
                } else {
                    String[] columnNames = new String[]{"firstName", "secondName", "Patronimic", "address", "phoneNumber"};
                    StringBuilder updateClient = new StringBuilder("UPDATE client SET ");
                    for (int i = 0; i < fieldText.size(); i++) {
                        updateClient.append(columnNames[i]).append(" = '").append(fieldText.get(i).getText()).append("'");
                        if (i != fieldText.size() - 1) {
                            updateClient.append(",");
                        }
                    }
                    updateClient.append(" WHERE idUser = (SELECT idUser FROM user WHERE login = '").append(user.getLogin()).append("')");
                    database.update(updateClient.toString());
                }
            } else {
                for (JCustomTextField JCustomTextField : fieldText) {
                    String previousText = JCustomTextField.getPlaceholder().substring(JCustomTextField.getPlaceholder().indexOf("Please") + 7);
                    JCustomTextField.setState("Please," + previousText, Color.red);
                }
            }

            revalidate();
            repaint();*/
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

        Image image = ResourceManager.getImageFromResources(Assets.BACKGROUND, "fill_data.png");
        g.drawImage(image, 50, 310, this);
    }
}
