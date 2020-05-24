package com.github.donkeyrit.javaapp.panels;

import com.github.donkeyrit.javaapp.EntryPoint;
import com.github.donkeyrit.javaapp.components.JCTextField;
import com.github.donkeyrit.javaapp.components.JPaswordField;
import com.github.donkeyrit.javaapp.database.DatabaseProvider;
import com.github.donkeyrit.javaapp.model.User;
import com.github.donkeyrit.javaapp.security.SecurityProvider;
import com.github.donkeyrit.javaapp.security.ShieldingProvider;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RegistrationPanel extends JPanel {

    private final EntryPoint point;

    public RegistrationPanel(EntryPoint point) {
        setLayout(null);

        this.point = point;
        DatabaseProvider database = point.database;
        JPanel panel = point.panel;

        JCTextField login = new JCTextField();
        login.setPlaceholder("Enter login");
        login.setBounds(358, 240, 170, 30);
        add(login);

        JPaswordField password = new JPaswordField();
        password.setPlaceholder("Enter password");
        password.setBounds(358, 280, 170, 30);
        add(password);

        JPaswordField rePassword = new JPaswordField();
        rePassword.setPlaceholder("Repeat password");
        rePassword.setBounds(358, 320, 170, 30);
        add(rePassword);

        JButton regButton = new JButton("Log in");
        regButton.setBounds(358, 360, 135, 30);
        regButton.addActionListener(e -> {
            ArrayList<String> data = new ArrayList<>();
            boolean isOne = login.getText().isEmpty();
            boolean isTwo = password.getText().isEmpty();
            boolean isThree = rePassword.getText().isEmpty();

            if(isOne){
                login.setPlaceholder("Please, enter login");
                login.setPhColor(Color.RED);
            }

            if(isTwo){
                password.setPlaceholder("Please, enter password");
                password.setPhColor(Color.RED);
            }

            if(isThree){
                rePassword.setPlaceholder("Please, repeat password");
                rePassword.setPhColor(Color.RED);
            }

            if(!isOne && !isTwo && !isThree){
                if(!password.getText().equals(rePassword.getText())){

                    password.setPlaceholder("Password do not match");
                    password.setPhColor(Color.RED);
                    password.setText("");

                    rePassword.setPlaceholder("Password do not match");
                    rePassword.setPhColor(Color.RED);
                    rePassword.setText("");
                }else{
                    data.add(ShieldingProvider.shielding(login.getText()));
                    String query = String.format("SELECT count(idUser) as count FROM user WHERE login = '%s'", ShieldingProvider.shielding(login.getText()));

                    ResultSet userSet = database.select(query);
                    boolean isCheckUser = false;
                    try{
                        int tempNum = 0;
                        while(userSet.next()){
                            tempNum = userSet.getInt("count");
                        }
                        isCheckUser = tempNum != 0;

                    }catch(SQLException ex){
                        ex.printStackTrace();
                    }

                    if(isCheckUser){
                        login.setPlaceholder("Login already exist");
                        login.setPhColor(Color.RED);
                        login.setText("");
                    }else{
                        data.add(SecurityProvider.sha1(ShieldingProvider.shielding(password.getText())));
                        point.user = new User(login.getText(), SecurityProvider.sha1(password.getText()),false);
                        database.insert("INSERT INTO user(login,password,role) VALUES ('" + data.get(0) + "','" + data.get(1) + "',0)");
                        panel.removeAll();
                        panel.revalidate();
                        panel.repaint();
                        point.showContent();
                    }
                }
            }

            panel.revalidate();
            panel.repaint();
        });
        add(regButton);

        JButton backButton = new JButton();
        backButton.setBounds(500, 360, 28, 30);
        ImageIcon icon = new ImageIcon("assets/buttons/return.png");
        backButton.setIcon(icon);
        backButton.setHorizontalTextPosition(SwingConstants.LEFT);
        backButton.addActionListener(e -> {
            panel.removeAll();
            panel.revalidate();
            panel.repaint();
            point.showAuthorization();
        });
        add(backButton);
    }

    @Override
    public void paintComponent(Graphics g){
        Image image = new ImageIcon("assets/background/enter.jpg").getImage();
        g.drawImage(image,0,0,this);
        GradientPaint gp = new GradientPaint(0, 0, Color.RED,120, 120, Color.BLUE, true);
        Graphics2D g2 = (Graphics2D)g;
        g2.setPaint(gp);
        g2.fillRoundRect(338, 230, 208, 180, 30, 15);

        int random = (int) (Math.random() * 6 + 1);
        if(point.avatarNumber == 0){
            point.avatarNumber = random;
        }

        Image avatar = new ImageIcon("assets/avatar/" + point.avatarNumber + ".png").getImage();
        g.drawImage(avatar,380,100,this);
    }
}
