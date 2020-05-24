package com.github.donkeyrit.javaapp.panels;

import com.github.donkeyrit.javaapp.EntryPoint;
import com.github.donkeyrit.javaapp.components.JCTextField;
import com.github.donkeyrit.javaapp.database.Database;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PrivateDataPanel extends JPanel {

    public PrivateDataPanel(EntryPoint point) {
        setLayout(null);

        Database database = point.database;

        String queryUser = "SELECT firstName,secondName,Patronimic,address,phoneNumber FROM client where idUser = (SELECT idUser FROM user WHERE login = '" + point.user.getLogin() + "')";
        ResultSet userSet = database.select(queryUser);
        ArrayList<String> infoUser = new ArrayList<>();
        try{
            int numRows = userSet.getMetaData().getColumnCount();
            while(userSet.next()){
                for(int i = 0; i < numRows; i++){
                    infoUser.add(userSet.getString(i+1));
                }
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }

        Box box = Box.createVerticalBox();
        box.setBounds(202,10, 200, 250);
        box.setBorder(new TitledBorder("Personal information"));

        String[] placeholders = new String[]{"Enter firstName","Enter secondName","Enter patronimic","Enter address","Enter phoneNumber"};
        ArrayList<JCTextField> fieldText = new ArrayList<>();
        for (String placeholder : placeholders) {
            JCTextField tempField = new JCTextField();
            tempField.setPlaceholder(placeholder);
            fieldText.add(tempField);
            box.add(tempField);
            box.add(Box.createVerticalStrut(10));
        }
        if(infoUser.size() == fieldText.size()){
            for(int i = 0; i < fieldText.size(); i++){
                fieldText.get(i).setText(infoUser.get(i));
            }
        }

        JButton confirm = new JButton("Confirm");
        confirm.addActionListener(e -> {
            int counter = 0;
            ArrayList<String> inputData = new ArrayList<>();
            for (JCTextField textField : fieldText) {
                if (!textField.getText().isEmpty()) {
                    counter++;
                    inputData.add(textField.getText());
                }
            }

            if(counter == fieldText.size()){
                if(infoUser.size() == 0){
                    StringBuilder createClient = new StringBuilder("INSERT INTO client(firstName,secondName,Patronimic,address,phoneNumber,idUser) VALUES (");
                    for (JCTextField jcTextField : fieldText) {
                        createClient.append("'").append(jcTextField.getText()).append("',");
                    }
                    createClient.append("(SELECT idUser FROM user WHERE login = '").append(point.user.getLogin()).append("'))");

                    database.insert(createClient.toString());
                    for (JCTextField jcTextField : fieldText) {
                        jcTextField.setPlaceholder("Success");
                        jcTextField.setText("");
                        jcTextField.setPhColor(Color.green);
                    }
                }else{
                    String[] columnNames = new String[]{"firstName","secondName","Patronimic","address","phoneNumber"};
                    StringBuilder updateClient = new StringBuilder("UPDATE client SET ");
                    for(int i = 0 ; i < fieldText.size(); i++){
                        updateClient.append(columnNames[i]).append(" = '").append(fieldText.get(i).getText()).append("'");
                        if(i != fieldText.size() - 1){
                            updateClient.append(",");
                        }
                    }
                    updateClient.append(" WHERE idUser = (SELECT idUser FROM user WHERE login = '").append(point.user.getLogin()).append("')");
                    database.update(updateClient.toString());
                }
            }else{
                for (JCTextField jcTextField : fieldText) {
                    String previousText = jcTextField.getPlaceholder().substring(jcTextField.getPlaceholder().indexOf("Please") + 7);
                    jcTextField.setPlaceholder("Please," + previousText);
                    jcTextField.setPhColor(Color.red);
                }
            }

            revalidate();
            repaint();
        });
        box.add(Box.createHorizontalStrut(60));
        box.add(confirm);

        add(box);


        System.out.println(infoUser);

    }

    @Override
    public void paintComponent(Graphics g){
        g.setColor(new Color(237,237,237));
        g.fillRoundRect(0,0,this.getWidth(),this.getHeight(),30,25);

        Image image = new ImageIcon("assets/background/fill_data.png").getImage();
        g.drawImage(image,50,310,this);
    }
}
