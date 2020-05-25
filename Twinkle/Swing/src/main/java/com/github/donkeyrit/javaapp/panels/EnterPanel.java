package com.github.donkeyrit.javaapp.panels;

import com.github.donkeyrit.javaapp.EntryPoint;
import com.github.donkeyrit.javaapp.components.JCTextField;
import com.github.donkeyrit.javaapp.components.JPaswordField;
import com.github.donkeyrit.javaapp.database.DatabaseProvider;
import com.github.donkeyrit.javaapp.model.User;
import com.github.donkeyrit.javaapp.resources.Assets;
import com.github.donkeyrit.javaapp.resources.ResourceManager;
import com.github.donkeyrit.javaapp.security.SecurityProvider;
import com.github.donkeyrit.javaapp.security.ShieldingProvider;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnterPanel extends JPanel {

    private final JPanel panel;
    private final DatabaseProvider database;
    private final EntryPoint point;

    public EnterPanel(EntryPoint point) {
        setLayout(null);

        this.point = point;
        panel = point.panel;
        database = point.database;

        JCTextField login = new JCTextField();
        login.setPlaceholder("Enter login");
        login.setBounds(358, 240, 170, 30);
        add(login);

        JPaswordField password = new JPaswordField();
        password.setPlaceholder("Enter password");
        password.setBounds(358, 280, 170, 30);
        add(password);

        JButton butSignIn = new JButton("Sign in");
        butSignIn.addActionListener(e -> {
            boolean isOne = login.getText().isEmpty();
            boolean isTwo = password.getText().isEmpty();

            if(isOne){
                login.setPlaceholder("Please, enter login");
                login.setPhColor(Color.RED);
            }
            if(isTwo){
                password.setPlaceholder("Please, enter password");
                password.setPhColor(Color.RED);
            }

            panel.revalidate();
            panel.repaint();

            if(!isOne && !isTwo){



                String query = String.format("SELECT count(idUser) as count, role FROM user WHERE login = '%s' AND password = '%s'", ShieldingProvider.shielding(login.getText()), SecurityProvider.sha1(ShieldingProvider.shielding(password.getText())));
                ResultSet userSet = database.select(query);
                boolean isCheckUser = false;
                boolean roleUser = false;
                try{
                    int tempNum = 0;
                    while(userSet.next()){
                        tempNum = userSet.getInt("count");
                        roleUser = userSet.getBoolean("role");
                    }
                    isCheckUser = tempNum != 0;

                }catch(SQLException ex){
                    ex.printStackTrace();
                }

                if(isCheckUser){

                    panel.removeAll();
                    panel.revalidate();
                    panel.repaint();
                    point.showContent();

                    point.user = new User(ShieldingProvider.shielding(login.getText()), SecurityProvider.sha1(password.getText()),roleUser);

                }else{

                    login.setPlaceholder("Incorrect login");
                    login.setPhColor(Color.RED);
                    login.setText("");

                    password.setPlaceholder("Incorrect password");
                    password.setPhColor(Color.RED);
                    password.setText("");
                }
            }
            revalidate();
            repaint();
        });
        butSignIn.setBounds(358,320,80,20);
        add(butSignIn);

        JButton butLogIn = new JButton("Log in");
        butLogIn.setBounds(448,320,80,20);
        butLogIn.addActionListener(e -> {
            panel.removeAll();
            panel.revalidate();
            panel.repaint();
            JPanel registrationPanel = new RegistrationPanel(point);
            registrationPanel.setBounds(0, 0, 875, 700);
            panel.add(registrationPanel);
        });
        add(butLogIn);
    }

    @Override
    public void paintComponent(Graphics g){
        Image image = ResourceManager.getImageFromResources(Assets.BACKGROUND, "enter.jpg");
        g.drawImage(image,0,0,this);
        GradientPaint gp = new GradientPaint(0, 0, Color.RED,120, 120, Color.BLUE, true);
        Graphics2D g2 = (Graphics2D)g;
        g2.setPaint(gp);
        g2.fillRoundRect(338, 230, 208, 130, 30, 15);

        int random = (int) (Math.random() * 6 + 1);
        if(point.avatarNumber == 0){
            point.avatarNumber = random;
        }
        Image avatar = ResourceManager.getImageFromResources(Assets.AVATAR, String.format("%d.png", point.avatarNumber));
        g.drawImage(avatar,380,100,this);
    }

}
