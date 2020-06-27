package com.github.donkeyrit.javaapp.panels;

import com.github.donkeyrit.javaapp.components.JCustomPasswordField;
import com.github.donkeyrit.javaapp.container.ServiceContainer;
import com.github.donkeyrit.javaapp.database.DatabaseProvider;
import com.github.donkeyrit.javaapp.model.User;
import com.github.donkeyrit.javaapp.panels.abstraction.CustomPanel;
import com.github.donkeyrit.javaapp.resources.Assets;
import com.github.donkeyrit.javaapp.resources.ResourceManager;
import com.github.donkeyrit.javaapp.security.SecurityProvider;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

public class ChangePasswordPanel extends CustomPanel {

    private DatabaseProvider database;
    private User user;

    public ChangePasswordPanel() {
        setLayout(null);

        ServiceContainer serviceContainer = ServiceContainer.getInstance();
        database = serviceContainer.getDatabaseProvider();
        user = serviceContainer.getUser();

        Box mainBox = Box.createVerticalBox();
        mainBox.setBorder(new TitledBorder("Change Password"));
        mainBox.setBounds(202,10, 200, 200);

        String[] labels = new String[]{"Enter old pasword","Enter new password","Repeat new password"};
        ArrayList<JCustomPasswordField> fieldPass = new ArrayList<>();
        for (String label : labels) {
            JCustomPasswordField passw = new JCustomPasswordField();
            passw.setState(label);
            fieldPass.add(passw);
            mainBox.add(passw);
            mainBox.add(Box.createVerticalStrut(10));
        }

        JButton confirm = new JButton("Confirm");
        confirm.addActionListener(e -> {
            boolean isOne = fieldPass.get(0).getText().isEmpty();
            boolean isTwo = fieldPass.get(1).getText().isEmpty();
            boolean isThree = fieldPass.get(2).getText().isEmpty();

            if(isOne){
                fieldPass.get(0).setState("Please, enter old password", Color.RED);
            }

            if(isTwo){
                fieldPass.get(1).setState("Please, enter new password", Color.RED);
            }

            if(isThree){
                fieldPass.get(2).setState("Please, repeat new password", Color.RED);
            }

            if(!isOne && !isTwo && !isThree){
                if(SecurityProvider.sha1(fieldPass.get(0).getText()).equals(user.getPassword())){
                    if(fieldPass.get(1).getText().equals(fieldPass.get(2).getText())){
                        if(fieldPass.get(0).getText().equals(fieldPass.get(1).getText())){
                            fieldPass.get(0).setState("Old and new match", Color.RED);
                            fieldPass.get(0).setText("");

                            fieldPass.get(1).setState("Old and new match", Color.RED);
                            fieldPass.get(1).setText("");
                        }else{
                            String updateUserQuery = "UPDATE user SET password = '" + SecurityProvider.sha1(fieldPass.get(1).getText()) + "'" + " WHERE login = '" + user.getLogin() + "'";
                            database.update(updateUserQuery);
                            user.setPassword(SecurityProvider.sha1(fieldPass.get(1).getText()));

                            for (JCustomPasswordField pass : fieldPass) {
                                pass.setText("");
                                pass.setState("Success", Color.green);
                            }
                        }
                    }else{
                        fieldPass.get(1).setState("Password does't match", Color.RED);
                        fieldPass.get(1).setText("");

                        fieldPass.get(2).setState("Password does't match", Color.RED);
                        fieldPass.get(2).setText("");
                    }
                }else{
                    fieldPass.get(0).setText("");
                    fieldPass.get(0).setState("Incorrect password", Color.RED);
                }
            }

            revalidate();
            repaint();
        });
        mainBox.add(Box.createHorizontalStrut(60));
        mainBox.add(confirm);

        add(mainBox);
    }

    @Override
    public Rectangle getBoundsRectangle() {
        return new Rectangle(250,100,605,550);
    }

    @Override
    public void paintComponent(Graphics g){
        g.setColor(new Color(237,237,237));
        g.fillRoundRect(0,0,this.getWidth(),this.getHeight(),30,25);

        Image image = ResourceManager.getImageFromResources(Assets.BACKGROUND,"page.png");
        g.drawImage(image,50,250,this);
    }
}
