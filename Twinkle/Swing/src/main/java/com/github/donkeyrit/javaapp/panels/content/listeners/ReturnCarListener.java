package com.github.donkeyrit.javaapp.panels.content.listeners;

import com.github.donkeyrit.javaapp.container.ServiceContainer;
import com.github.donkeyrit.javaapp.database.DatabaseModelProviders.InjuryModelProvider;
import com.github.donkeyrit.javaapp.database.DatabaseProvider;
import com.github.donkeyrit.javaapp.panels.content.AboutCarPanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.*;

public class ReturnCarListener implements ActionListener {

    private final DatabaseProvider databaseProvider;
    private final AboutCarPanel aboutCarPanel;

    private Box box;
    private List<JCheckBox> injuriesType;

    public ReturnCarListener(AboutCarPanel aboutCarPanel) {
        this.databaseProvider = ServiceContainer.getInstance().getDatabaseProvider();
        this.aboutCarPanel = aboutCarPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton tempBut = (JButton) e.getSource();
        JPanel tempPanel = (JPanel) tempBut.getParent();
        tempPanel.remove(aboutCarPanel.getInfoAboutCarScrollableContainer());
        tempPanel.remove(tempBut);

        box = Box.createVerticalBox();

        InjuryModelProvider injuryModelProvider = databaseProvider.getInjuryModelProvider();
        injuriesType = new ArrayList<>();
        injuryModelProvider.getInjuries().forEach(injury -> {
            JCheckBox temp = new JCheckBox(injury);
            injuriesType.add(temp);
            box.add(temp);
        });

        box.setBorder(new TitledBorder("Types of damage"));
        box.setBounds(330, 290, 250, 150);
        tempPanel.add(box);

        JButton countButton = new JButton("Count");
        countButton.addActionListener(e13 -> {
            JButton selectedButton = (JButton) e13.getSource();
            JPanel selecButPanel = (JPanel) selectedButton.getParent();

            Component[] listMas = selecButPanel.getComponents();
            for (Component litmas : listMas) {
                if (litmas.getClass().toString().contains("JLabel")) {
                    JLabel label = (JLabel) litmas;
                    if (label.getText().contains("Sum")) {
                        selecButPanel.remove(label);
                    }
                }
            }


            JButton newReturnButton = new JButton("Return");
            newReturnButton.setBounds(330, 500, 120, 30);
            newReturnButton.addActionListener(e131 -> {
                String injuryForCar = "";
                for (JCheckBox checkBox : injuriesType) {
                    if (checkBox.isSelected()) {
                        injuryForCar = checkBox.getText();
                    }
                }
                Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
                calendar.setTime(new Date());
                int currYear = calendar.get(Calendar.YEAR);
                int currMont = calendar.get(Calendar.MONTH);
                int currDay = calendar.get(Calendar.DATE);

                String dataStr = currYear + "-" + currMont + "-" + currDay;
                String updateQuery = "UPDATE renta SET dataEnd = '" + dataStr + "' WHERE idCar = " + aboutCarPanel.getCar().getId() + " AND idClient = (SELECT idClient FROM client INNER JOIN user ON client.idUser = user.idUser WHERE login = '" + aboutCarPanel.getUser().getLogin() + "');";
                databaseProvider.update(updateQuery);

                String idRentaStr = "SELECT idRenta FROM renta WHERE idCar = " + aboutCarPanel.getCar().getId() + " AND idClient = (SELECT idClient FROM client INNER JOIN user ON client.idUser = user.idUser WHERE login = '" + aboutCarPanel.getUser().getLogin() + "') AND dataEnd = '" + dataStr + "'";
                ResultSet rentaSet = databaseProvider.select(idRentaStr);
                int idRentaNum = 0;
                try {
                    while (rentaSet.next()) {
                        idRentaNum = rentaSet.getInt("idRenta");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                String idInjuryStr = "SELECT idInjury FROM injury WHERE injuryName = '" + injuryForCar + "'";
                ResultSet injurySet = databaseProvider.select(idInjuryStr);
                int idInjuryNum = 0;
                try {
                    while (injurySet.next()) {
                        idInjuryNum = injurySet.getInt("idInjury");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                databaseProvider.insert("INSERT INTO  resultinginjury(idRenta,idInjury) VALUES(" + idRentaNum + "," + idInjuryNum + ")");

                aboutCarPanel.remove(box);
                aboutCarPanel.remove(newReturnButton);
                aboutCarPanel.remove(countButton);
                Component[] listMas1 = selecButPanel.getComponents();
                for (Component litmas : listMas1) {
                    if (litmas.getClass().toString().contains("JLabel")) {
                        JLabel label = (JLabel) litmas;
                        if (label.getText().contains("Sum")) {
                            aboutCarPanel.remove(label);
                        }
                    }
                }
                JTextArea textArea12 = new JTextArea(aboutCarPanel.getCar().getInfo());
                textArea12.setLineWrap(true);
                JScrollPane scrollPane12 = new JScrollPane(textArea12);
                scrollPane12.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                scrollPane12.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                scrollPane12.setBounds(300, 290, 285, 190);
                aboutCarPanel.add(scrollPane12);
                aboutCarPanel.revalidate();
                aboutCarPanel.repaint();
            });
            tempPanel.add(newReturnButton);


            Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
            calendar.setTime(new Date());
            int currYear = calendar.get(Calendar.YEAR);
            int currMont = calendar.get(Calendar.MONTH);
            int currDay = calendar.get(Calendar.DATE);

            String queryToDb = "SELECT login,idCar,join1.idUser,dataStart,dataPlan,dataEnd FROM\n" +
                    "(SELECT idCar,idUSer,renta.idClient,dataStart,dataPlan,dataEnd FROM renta INNER JOIN client ON renta.idClient = client.idClient) as join1\n" +
                    "INNER JOIN user ON join1.idUser = user.idUser WHERE login = '" + aboutCarPanel.getUser().getLogin() + "' AND idCar = " + aboutCarPanel.getCar().getId() + " ORDER BY dataEnd,dataPlan DESC LIMIT 1;";

            ResultSet queryToDbSet = databaseProvider.select(queryToDb);
            Date startRentaDate;
            Date dataRentaPlan = null;
            try {
                while (queryToDbSet.next()) {
                    dataRentaPlan = queryToDbSet.getDate("dataPlan");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            Date currentGetData = new Date(currYear, currMont, currDay);
            startRentaDate = new Date(currYear, currMont + 1, currDay);

            JLabel labelCostRenta = new JLabel("5000");
            labelCostRenta.setBounds(480, 460, 120, 30);

            if (currentGetData.after(startRentaDate)) {
                labelCostRenta.setText("Sum = 0");
            } else {
                double costForTheRent;

                long difference = startRentaDate.getTime() - currentGetData.getTime();
                int days = (int) (difference / (24 * 60 * 60 * 1000));
                costForTheRent = (days + 1f) * aboutCarPanel.getCar().getCost();


                if (currentGetData.after(dataRentaPlan)) {
                    long diff = currentGetData.getTime() - dataRentaPlan.getTime();
                    int overDay = (int) (difference / (24 * 60 * 60 * 1000));
                    costForTheRent += (aboutCarPanel.getCar().getCost() * overDay) * 0.2;
                }

                for (int i = 0; i < injuriesType.size(); i++) {
                    if (injuriesType.get(i).isSelected()) {
                        costForTheRent += (i + 1) * 50000;
                    }
                }

                labelCostRenta.setText("Sum " + costForTheRent + "");
            }

            tempPanel.add(labelCostRenta);

            tempPanel.revalidate();
            tempPanel.repaint();
        });
        countButton.setBounds(330, 460, 120, 30);
        tempPanel.add(countButton);

        tempPanel.revalidate();
        tempPanel.repaint();
    }
}
