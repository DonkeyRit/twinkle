package com.github.donkeyrit.javaapp.panels.content.listeners;

import com.github.donkeyrit.javaapp.container.ServiceContainer;
import com.github.donkeyrit.javaapp.database.DatabaseProvider;
import com.github.donkeyrit.javaapp.panels.content.AboutCarPanel;
import com.github.donkeyrit.javaapp.utils.IValidationEngine;
import com.github.donkeyrit.javaapp.utils.ValidationEngine;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.basic.BasicBorders;
import javax.swing.plaf.metal.MetalBorders;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.function.Consumer;

public class GetRentListener implements ActionListener {

    private static final Border TEXT_FIELD_BORDER_WITH_INCORRECT_VALUE =  new LineBorder(Color.RED, 4);
    private static final Border DEFAULT_TEXT_FIELD_BORDER = new BorderUIResource(
            new BorderUIResource.CompoundBorderUIResource(new MetalBorders.TextFieldBorder(), new BasicBorders.MarginBorder()));

    private final DatabaseProvider databaseProvider;
    private final AboutCarPanel aboutCarPanel;
    private final IValidationEngine validationEngine;

    private Box yearsStart;
    private JLabel startYearsLabel;
    private JFormattedTextField startYear;
    private JFormattedTextField startMonth;
    private JFormattedTextField startDay;
    private Box yearsPlan;
    private JFormattedTextField endYear;
    private JFormattedTextField endMonth;
    private JFormattedTextField endDay;
    private JLabel planYearsLabel;
    private JLabel planPriceLabel;
    private JButton countPrice;
    private JButton back;

    public GetRentListener(AboutCarPanel aboutCarPanel) {
        this.databaseProvider = ServiceContainer.getInstance().getDatabaseProvider();
        this.validationEngine = new ValidationEngine();
        this.aboutCarPanel = aboutCarPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        try {
            initialize(e);
            configureValidations();
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        aboutCarPanel.add(startYearsLabel);
        aboutCarPanel.add(yearsStart);
        aboutCarPanel.add(planYearsLabel);
        aboutCarPanel.add(yearsPlan);
        aboutCarPanel.add(planPriceLabel);
        aboutCarPanel.add(countPrice);
        aboutCarPanel.add(back);
    }

    public void initialize(ActionEvent e) throws ParseException {
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

        startYearsLabel = new JLabel("Enter start rent date(гг.мм.дд)");
        startYearsLabel.setBounds(330, 290, 300, 30);

        MaskFormatter twoDigitsFormat = new MaskFormatter("##");
        twoDigitsFormat.setPlaceholderCharacter('0');

        MaskFormatter fourDigitsFormat = new MaskFormatter("####");
        fourDigitsFormat.setPlaceholderCharacter('0');

        startYear = new JFormattedTextField(fourDigitsFormat);
        startYear.setHorizontalAlignment(JTextField.CENTER);
        startYear.setBorder(DEFAULT_TEXT_FIELD_BORDER);

        startMonth = new JFormattedTextField(twoDigitsFormat);
        startMonth.setHorizontalAlignment(JTextField.CENTER);
        startMonth.setBorder(DEFAULT_TEXT_FIELD_BORDER);

        startDay = new JFormattedTextField(twoDigitsFormat);
        startDay.setHorizontalAlignment(JTextField.CENTER);
        startDay.setBorder(DEFAULT_TEXT_FIELD_BORDER);

        yearsStart = Box.createHorizontalBox();
        yearsStart.add(startYear);
        yearsStart.add(startMonth);
        yearsStart.add(startDay);
        /*Obsolete*/
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
        }
        /*Obsolete*/
        yearsStart.setBounds(350, 320, 200, 30);

        planYearsLabel = new JLabel("Enter end rent date(г.м.д)");
        planYearsLabel.setBounds(330, 370, 300, 30);

        endYear = new JFormattedTextField(fourDigitsFormat);
        endYear.setHorizontalAlignment(JTextField.CENTER);
        endYear.setBorder(DEFAULT_TEXT_FIELD_BORDER);

        endMonth = new JFormattedTextField(twoDigitsFormat);
        endMonth.setHorizontalAlignment(JTextField.CENTER);
        endMonth.setBorder(DEFAULT_TEXT_FIELD_BORDER);

        endDay = new JFormattedTextField(twoDigitsFormat);
        endDay.setHorizontalAlignment(JTextField.CENTER);
        endDay.setBorder(DEFAULT_TEXT_FIELD_BORDER);

        yearsPlan = Box.createHorizontalBox();
        yearsPlan.add(endYear);
        yearsPlan.add(endMonth);
        yearsPlan.add(endDay);

        /*Obsolete*/
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
        }
        /*Obsolete*/
        yearsPlan.setBounds(350, 400, 200, 30);

        planPriceLabel = new JLabel("Approximately cost: ");
        planPriceLabel.setBounds(330, 440, 300, 30);

        countPrice = new JButton("Count");
        countPrice.setBounds(340, 480, 120, 30);
        countPrice.addActionListener(e1 -> {

            validationEngine.validate();

            /*Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
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
            }*/
            aboutCarPanel.revalidate();
            aboutCarPanel.repaint();
        });

        back = new JButton("Return");
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
    }

    private void configureValidations() {

        Consumer<JFormattedTextField> setBorderIfIncorrectValue = textField -> textField.setBorder(TEXT_FIELD_BORDER_WITH_INCORRECT_VALUE);
        Consumer<JFormattedTextField> setBorderIfCorrectValue = textField -> textField.setBorder(DEFAULT_TEXT_FIELD_BORDER);

        validationEngine
                // Reset styles
                .addRule(() -> true, o -> setBorderIfCorrectValue.accept(startYear))
                .addRule(() -> true, o -> setBorderIfCorrectValue.accept(startMonth))
                .addRule(() -> true, o -> setBorderIfCorrectValue.accept(startDay))
                .addRule(() -> true, o -> setBorderIfCorrectValue.accept(endYear))
                .addRule(() -> true, o -> setBorderIfCorrectValue.accept(endMonth))
                .addRule(() -> true, o -> setBorderIfCorrectValue.accept(endDay))
                // Check if fields have a default value
                .addRule(() -> startYear.getText().equals("0000"), o -> setBorderIfIncorrectValue.accept(startYear))
                .addRule(() -> startMonth.getText().equals("00"), o -> setBorderIfIncorrectValue.accept(startMonth))
                .addRule(() -> startDay.getText().equals("00"), o -> setBorderIfIncorrectValue.accept(startDay))
                .addRule(() -> endYear.getText().equals("0000"), o -> setBorderIfIncorrectValue.accept(endYear))
                .addRule(() -> endMonth.getText().equals("00"), o -> setBorderIfIncorrectValue.accept(endMonth))
                .addRule(() -> endDay.getText().equals("00"), o -> setBorderIfIncorrectValue.accept(endDay));
    }
}
