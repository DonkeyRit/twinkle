package com.github.donkeyrit.javaapp.panels.content.listeners;

import com.github.donkeyrit.javaapp.container.ServiceContainer;
import com.github.donkeyrit.javaapp.database.DatabaseProvider;
import com.github.donkeyrit.javaapp.panels.content.AboutCarPanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class GetRentListener implements ActionListener {

    private final DatabaseProvider databaseProvider;
    private final AboutCarPanel aboutCarPanel;

    public GetRentListener(AboutCarPanel aboutCarPanel) {
        this.databaseProvider = ServiceContainer.getInstance().getDatabaseProvider();
        this.aboutCarPanel = aboutCarPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton tempBut = (JButton) e.getSource();
        tempBut.setVisible(false);
        JPanel tempPanel = (JPanel) tempBut.getParent();

        Component[] mas = tempPanel.getComponents();
        JScrollPane temp = null;
        for (Component ma : mas) {
            if (ma.getClass().toString().contains("JScrollPane")) {
                temp = (JScrollPane) ma;
            }
        }

        tempPanel.remove(temp);

        tempPanel.revalidate();
        tempPanel.repaint();

        ArrayList<JTextField> fields = new ArrayList<>();
        MaskFormatter formatter = null;

        Box yearsStart = Box.createHorizontalBox();
        for (int i = 0; i < 3; i++) {
            String form = "##";
            if (i == 0) {
                form = "####";
            }
            try {
                formatter = new MaskFormatter(form);
                formatter.setPlaceholderCharacter('0');
            } catch (Exception exec) {
                exec.printStackTrace();
            }
            JFormattedTextField ssnField = new JFormattedTextField(formatter);
            ssnField.setHorizontalAlignment(JTextField.CENTER);
            formatter.setPlaceholderCharacter('0');
            fields.add(ssnField);
            yearsStart.add(ssnField);
        }
        yearsStart.setBounds(350, 320, 200, 30);
        aboutCarPanel.add(yearsStart);

        Box yearsPlan = Box.createHorizontalBox();
        for (int i = 0; i < 3; i++) {
            String form = "##";
            if (i == 0) {
                form = "####";
            }
            try {
                formatter = new MaskFormatter(form);
                formatter.setPlaceholderCharacter('0');
            } catch (Exception exec) {
                exec.printStackTrace();
            }
            JFormattedTextField tempField = new JFormattedTextField(formatter);

            tempField.setHorizontalAlignment(JTextField.CENTER);
            fields.add(tempField);
            yearsPlan.add(tempField);
        }
        yearsPlan.setBounds(350, 400, 200, 30);
        aboutCarPanel.add(yearsPlan);

        JLabel startYearsLabel = new JLabel("Enter start rent date(гг.мм.дд)");
        startYearsLabel.setBounds(330, 290, 300, 30);
        aboutCarPanel.add(startYearsLabel);

        JLabel planYearsLabel = new JLabel("Enter end rent date(г.м.д)");
        planYearsLabel.setBounds(330, 370, 300, 30);
        aboutCarPanel.add(planYearsLabel);

        JLabel planPriceLabel = new JLabel("Approximately cost: ");
        planPriceLabel.setBounds(330, 440, 300, 30);
        aboutCarPanel.add(planPriceLabel);

        JButton countPrice = new JButton("Count");
        Border borderButton = fields.get(0).getBorder();
        countPrice.setBounds(340, 480, 120, 30);
        countPrice.addActionListener(e1 -> {
            Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
            calendar.setTime(new Date());
            int currYear = calendar.get(Calendar.YEAR);
            int currMont = calendar.get(Calendar.MONTH);
            int currDay = calendar.get(Calendar.DATE);

            ArrayList<Integer> yList = new ArrayList<>();
            int count = 0;
            for (JTextField field : fields) {
                if (!field.getText().equals("0000")) {
                    if (!field.getText().equals("00")) {
                        count++;
                        field.setBorder(borderButton);
                    } else {
                        field.setBorder(new LineBorder(Color.RED, 4));
                    }
                } else {
                    field.setBorder(new LineBorder(Color.RED, 4));
                }
            }
            if (count == fields.size()) {
                int tempNum = Integer.parseInt(fields.get(0).getText());
                if (tempNum < currYear || tempNum > currYear + 2) {
                    fields.get(0).setBorder(new LineBorder(Color.RED, 4));
                } else {
                    fields.get(0).setBorder(borderButton);
                    yList.add(tempNum);
                }

                tempNum = Integer.parseInt(fields.get(1).getText());
                if (tempNum < 0 || tempNum > 12) {
                    fields.get(1).setBorder(new LineBorder(Color.RED, 4));
                } else {
                    if (Integer.parseInt(fields.get(0).getText()) == currYear && tempNum < currMont) {
                        fields.get(1).setBorder(new LineBorder(Color.RED, 4));
                    } else {
                        fields.get(1).setBorder(borderButton);
                        yList.add(tempNum);
                    }
                }

                tempNum = Integer.parseInt(fields.get(2).getText());
                if (tempNum < 0 || tempNum > 28) {
                    fields.get(2).setBorder(new LineBorder(Color.RED, 4));
                } else {
                    if (Integer.parseInt(fields.get(0).getText()) == currYear && Integer.parseInt(fields.get(1).getText()) == currMont && tempNum < currDay) {
                        fields.get(2).setBorder(new LineBorder(Color.RED, 4));
                    } else {
                        fields.get(2).setBorder(borderButton);
                        yList.add(tempNum);
                    }
                }

                tempNum = Integer.parseInt(fields.get(3).getText());
                if (tempNum < currYear || tempNum > currYear + 4) {
                    fields.get(3).setBorder(new LineBorder(Color.RED, 4));
                } else {
                    if (tempNum >= Integer.parseInt(fields.get(0).getText())) {
                        fields.get(3).setBorder(borderButton);
                        yList.add(tempNum);
                    } else {
                        fields.get(3).setBorder(new LineBorder(Color.RED, 4));
                    }
                }

                tempNum = Integer.parseInt(fields.get(4).getText());
                if (tempNum < 0 || tempNum > 12) {
                    fields.get(4).setBorder(new LineBorder(Color.RED, 4));
                } else {
                    if (Integer.parseInt(fields.get(3).getText()) == Integer.parseInt(fields.get(0).getText())) {
                        if (tempNum >= Integer.parseInt(fields.get(1).getText())) {
                            fields.get(4).setBorder(borderButton);
                            yList.add(tempNum);
                        } else {
                            fields.get(4).setBorder(new LineBorder(Color.RED, 4));
                        }
                    } else {
                        if (Integer.parseInt(fields.get(3).getText()) > Integer.parseInt(fields.get(0).getText())) {
                            fields.get(4).setBorder(borderButton);
                            yList.add(tempNum);
                        } else {
                            fields.get(4).setBorder(new LineBorder(Color.RED, 4));
                        }
                    }
                }

                tempNum = Integer.parseInt(fields.get(5).getText());
                if (tempNum < 0 || tempNum > 28) {
                    fields.get(5).setBorder(new LineBorder(Color.RED, 4));
                } else {
                    if (Integer.parseInt(fields.get(3).getText()) == Integer.parseInt(fields.get(0).getText())) {
                        if (Integer.parseInt(fields.get(4).getText()) == Integer.parseInt(fields.get(1).getText())) {
                            if (tempNum > Integer.parseInt(fields.get(2).getText())) {
                                fields.get(5).setBorder(borderButton);
                                yList.add(tempNum);
                            } else {
                                fields.get(5).setBorder(new LineBorder(Color.RED, 4));
                            }
                        } else {
                            if (Integer.parseInt(fields.get(4).getText()) > Integer.parseInt(fields.get(1).getText())) {
                                fields.get(5).setBorder(borderButton);
                                yList.add(tempNum);
                            } else {
                                fields.get(5).setBorder(new LineBorder(Color.RED, 4));
                            }
                        }
                    } else {
                        if (Integer.parseInt(fields.get(3).getText()) > Integer.parseInt(fields.get(0).getText())) {
                            fields.get(5).setBorder(borderButton);
                            yList.add(tempNum);
                        } else {
                            fields.get(5).setBorder(new LineBorder(Color.RED, 4));
                        }
                    }
                }
            }

            if (yList.size() == 6) {
                Date startDateGetCar = new Date(yList.get(0), yList.get(1), yList.get(2));
                Date planDateReturnCar = new Date(yList.get(3), yList.get(4), yList.get(5));

                long difference = planDateReturnCar.getTime() - startDateGetCar.getTime();
                int days = (int) (difference / (24 * 60 * 60 * 1000));
                planPriceLabel.setText("Approximately cost: " + (days + 1) * aboutCarPanel.getCar().getCost() + " на " + (days + 1));

                JButton buttonGetCar = new JButton("Get rent.");
                buttonGetCar.addActionListener(e11 -> {
                    String checkQuery = "SELECT idClient FROM client INNER JOIN user ON client.idUser = user.idUser WHERE login = " + "'" + aboutCarPanel.getUser().getLogin() + "'";
                    ResultSet checkClientSet = databaseProvider.select(checkQuery);
                    int idClient = 0;
                    try {
                        while (checkClientSet.next()) {
                            idClient = checkClientSet.getInt("idClient");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                    if (idClient == 0) {
                        planPriceLabel.setText("Please, fill data");
                    } else {

                        String queryToDB = "SELECT * FROM renta where idClient = (SELECT idClient FROM client WHERE idUser = (SELECT idUser FROM user WHERE login = '" + aboutCarPanel.getUser().getLogin() + "')) ORDER BY dataEnd, dataPlan DESC LIMIT 1";
                        ResultSet checkRentaSet = databaseProvider.select(queryToDB);
                        System.out.println(queryToDB);
                        boolean isHaveRenta = false;
                        try {
                            while (checkRentaSet.next()) {
                                Date rentDate = checkRentaSet.getDate("dataEnd");
                                if (rentDate == null) {
                                    isHaveRenta = true;
                                }
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }

                        if (isHaveRenta) {
                            planPriceLabel.setText("You cannot take more than one car at a time");
                        } else {
                            String insertRenta = "INSERT INTO renta(idClient,idCar,dataStart,dataPlan) VALUES (" + idClient + "," + aboutCarPanel.getCar().getId();

                            String startDataIn = "'" + yList.get(0) + "-" + yList.get(1) + "-" + yList.get(2) + "'";
                            String planDataIn = "'" + yList.get(3) + "-" + yList.get(4) + "-" + yList.get(5) + "'";

                            insertRenta += "," + startDataIn + "," + planDataIn + ")";

                            databaseProvider.insert(insertRenta);

                            JButton selecBut = (JButton) e11.getSource();
                            JPanel selecPane = (JPanel) selecBut.getParent();

                            Component[] compons = selecPane.getComponents();
                            for (Component compon : compons) {
                                if (compon.getClass().toString().contains("JButton")) {
                                    JButton button = (JButton) compon;
                                    if (button.getText().contains("Get rent") || button.getText().contains("Return")) {
                                        selecPane.remove(button);
                                    }
                                }
                            }

                            selecPane.remove(startYearsLabel);

                            selecPane.remove(planPriceLabel);
                            selecPane.remove(planYearsLabel);
                            selecPane.remove(countPrice);
                            selecPane.remove(yearsPlan);
                            selecPane.remove(yearsStart);
                            selecPane.revalidate();
                            selecPane.repaint();


                            JTextArea textArea1 = new JTextArea(aboutCarPanel.getCar().getInfo());
                            textArea1.setLineWrap(true);
                            JScrollPane scrollPane1 = new JScrollPane(textArea1);
                            scrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                            scrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                            scrollPane1.setBounds(300, 290, 285, 190);
                            aboutCarPanel.add(scrollPane1);
                            System.out.println(insertRenta);
                        }
                    }
                });
                buttonGetCar.setBounds(375, 510, 150, 30);
                aboutCarPanel.add(buttonGetCar);
            } else {
                Component[] compons = tempPanel.getComponents();
                for (Component compon : compons) {
                    if (compon.getClass().toString().contains("JButton")) {
                        JButton button = (JButton) compon;
                        if (button.getText().contains("Get rent")) {
                            tempPanel.remove(button);
                        }
                    }
                    planPriceLabel.setText("Incorrect date: ");
                }
            }
            aboutCarPanel.revalidate();
            aboutCarPanel.repaint();
        });
        aboutCarPanel.add(countPrice);

        JButton back = new JButton("Return");
        back.setBounds(460, 480, 100, 30);
        back.addActionListener(e12 -> {
            JButton selecBut = (JButton) e12.getSource();
            JPanel selecPane = (JPanel) selecBut.getParent();

            Component[] compons = selecPane.getComponents();
            for (Component compon : compons) {
                if (compon.getClass().toString().contains("JButton")) {
                    JButton button = (JButton) compon;
                    if (button.getText().contains("Get rent")) {
                        selecPane.remove(button);
                    }
                }
            }

            selecPane.remove(startYearsLabel);
            selecPane.remove(back);
            selecPane.remove(planPriceLabel);
            selecPane.remove(planYearsLabel);
            selecPane.remove(countPrice);
            selecPane.remove(yearsPlan);
            selecPane.remove(yearsStart);
            selecPane.revalidate();
            selecPane.repaint();

            tempBut.setVisible(true);

            JTextArea textArea1 = new JTextArea(aboutCarPanel.getCar().getInfo());
            textArea1.setLineWrap(true);
            JScrollPane scrollPane1 = new JScrollPane(textArea1);
            scrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            scrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane1.setBounds(300, 290, 285, 190);
            aboutCarPanel.add(scrollPane1);
        });
        aboutCarPanel.add(back);
    }
}
