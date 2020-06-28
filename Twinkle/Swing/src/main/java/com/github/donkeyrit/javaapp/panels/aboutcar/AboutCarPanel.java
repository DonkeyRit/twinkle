package com.github.donkeyrit.javaapp.panels.aboutcar;

import com.github.donkeyrit.javaapp.container.ServiceContainer;
import com.github.donkeyrit.javaapp.database.DatabaseProvider;
import com.github.donkeyrit.javaapp.model.Car;
import com.github.donkeyrit.javaapp.model.User;
import com.github.donkeyrit.javaapp.panels.aboutcar.listeners.ReloadButtonListener;
import com.github.donkeyrit.javaapp.panels.aboutcar.listeners.ReturnButtonListener;
import com.github.donkeyrit.javaapp.panels.abstraction.CustomPanel;
import com.github.donkeyrit.javaapp.resources.Assets;
import com.github.donkeyrit.javaapp.resources.ResourceManager;
import com.github.donkeyrit.javaapp.ui.Canvas;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class AboutCarPanel extends CustomPanel {

    private final Car car;
    public Car getCar() {
        return car;
    }

    private DatabaseProvider database;
    private Canvas panel;
    private User user;

    private String filter;
    private int numPage;
    private int startBut;

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public void setNumPage(int numPage) {
        this.numPage = numPage;
    }

    public void setStartBut(int startBut) {
        this.startBut = startBut;
    }

    public String getFilter() {
        return filter;
    }

    public int getNumPage() {
        return numPage;
    }

    public int getStartBut() {
        return startBut;
    }

    public AboutCarPanel(Car car) {
        setLayout(null);

        this.car = car;

        ServiceContainer serviceContainer = ServiceContainer.getInstance();
        database = serviceContainer.getDatabaseProvider();
        panel = serviceContainer.getUiManager().getCanvas();
        user = serviceContainer.getUser();

        JTextArea textArea = new JTextArea(car.getInfo());
        textArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(300, 290, 285, 190);
        add(scrollPane);

        ResultSet statusSet = database.select("SELECT * FROM renta WHERE idCar = " + car.getId() + " ORDER BY dataEnd, dataPlan DESC LIMIT 1");
        String statusStr = "Свободно";
        try{
            while(statusSet.next()){
                Date rentDate = statusSet.getDate("dataEnd");
                if(rentDate == null){
                    statusStr = "Busy";
                }
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }

        Font font = new Font("Arial", Font.BOLD, 13);
        Font alterfont = new Font("Arial", Font.ITALIC, 13);

        String[] massArgs = new String[] {
                "Model:", car.getModelName(),
                "Mark:",car.getMarkName(),
                "Year:",car.getModelYear().toString(),
                "Type:",car.getBodyTypeName(),
                "Cost per day:", car.getCost() + "",
                "Status:",statusStr
        };
        for(int i = 0; i < 12; i++){
            JLabel temp = new JLabel(massArgs[i]);
            if(i % 2 == 0){
                temp.setBounds(30,300 + (i / 2) * 30,80,20);
                temp.setFont(alterfont);
            }else{
                temp.setBounds(150, 300 + (i / 2) * 30, 120, 20);
                temp.setFont(font);
            }

            add(temp);
        }

        JButton reloadButton = new JButton(ResourceManager.getImageIconFromResources(Assets.BUTTONS,"reload.png"));
        reloadButton.setBounds(550,0,16,16);
        reloadButton.addActionListener(new ReloadButtonListener());
        add(reloadButton);

        JButton returnButton = new JButton(ResourceManager.getImageIconFromResources(Assets.BUTTONS,"return.png"));
        returnButton.setBounds(570,0,16,16);
        returnButton.addActionListener(new ReturnButtonListener());
        add(returnButton);

        JButton actionWithCarButton = new JButton("ACTION");
        actionWithCarButton.setBounds(300, 500, 150, 20);
        if(statusStr.equals("Free")){
            actionWithCarButton.setText("Get rent");
            actionWithCarButton.addActionListener(e -> {
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
                for(int i = 0; i < 3; i++){
                    String form = "##";
                    if(i == 0){
                        form = "####";
                    }
                    try{
                        formatter = new MaskFormatter(form);
                        formatter.setPlaceholderCharacter('0');
                    }catch(Exception exec){
                        exec.printStackTrace();
                    }
                    JFormattedTextField ssnField = new JFormattedTextField(formatter);
                    ssnField.setHorizontalAlignment(JTextField.CENTER);
                    formatter.setPlaceholderCharacter('0');
                    fields.add(ssnField);
                    yearsStart.add(ssnField);
                }
                yearsStart.setBounds(350,320,200,30);
                add(yearsStart);

                Box yearsPlan = Box.createHorizontalBox();
                for(int i = 0; i < 3; i++){
                    String form = "##";
                    if(i == 0){
                        form = "####";
                    }
                    try{
                        formatter = new MaskFormatter(form);
                        formatter.setPlaceholderCharacter('0');
                    }catch(Exception exec){
                        exec.printStackTrace();
                    }
                    JFormattedTextField tempField = new JFormattedTextField(formatter);

                    tempField.setHorizontalAlignment(JTextField.CENTER);
                    fields.add(tempField);
                    yearsPlan.add(tempField);
                }
                yearsPlan.setBounds(350,400,200,30);
                add(yearsPlan);

                JLabel startYearsLabel = new JLabel("Enter start rent date(гг.мм.дд)");
                startYearsLabel.setBounds(330,290,300,30);
                add(startYearsLabel);

                JLabel planYearsLabel = new JLabel("Enter end rent date(г.м.д)");
                planYearsLabel.setBounds(330, 370, 300, 30);
                add(planYearsLabel);

                JLabel planPriceLabel = new JLabel("Approximately cost: ");
                planPriceLabel.setBounds(330, 440, 300, 30);
                add(planPriceLabel);

                JButton countPrice = new JButton("Count");
                Border borderButton = fields.get(0).getBorder();
                countPrice.setBounds(340,480,120,30);
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
                    if(count == fields.size()){
                        int tempNum = Integer.parseInt(fields.get(0).getText());
                        if(tempNum < currYear || tempNum > currYear + 2){
                            fields.get(0).setBorder(new LineBorder(Color.RED, 4));
                        }else{
                            fields.get(0).setBorder(borderButton);
                            yList.add(tempNum);
                        }

                        tempNum = Integer.parseInt(fields.get(1).getText());
                        if(tempNum < 0 || tempNum > 12){
                            fields.get(1).setBorder(new LineBorder(Color.RED, 4));
                        }else{
                            if(Integer.parseInt(fields.get(0).getText()) == currYear && tempNum < currMont){
                                fields.get(1).setBorder(new LineBorder(Color.RED, 4));
                            }else{
                                fields.get(1).setBorder(borderButton);
                                yList.add(tempNum);
                            }
                        }

                        tempNum = Integer.parseInt(fields.get(2).getText());
                        if(tempNum < 0 || tempNum > 28){
                            fields.get(2).setBorder(new LineBorder(Color.RED, 4));
                        }else{
                            if(Integer.parseInt(fields.get(0).getText()) == currYear && Integer.parseInt(fields.get(1).getText()) == currMont && tempNum < currDay){
                                fields.get(2).setBorder(new LineBorder(Color.RED, 4));
                            }else{
                                fields.get(2).setBorder(borderButton);
                                yList.add(tempNum);
                            }
                        }

                        tempNum = Integer.parseInt(fields.get(3).getText());
                        if(tempNum < currYear || tempNum > currYear + 4){
                            fields.get(3).setBorder(new LineBorder(Color.RED, 4));
                        }else{
                            if(tempNum >= Integer.parseInt(fields.get(0).getText())){
                                fields.get(3).setBorder(borderButton);
                                yList.add(tempNum);
                            }else{
                                fields.get(3).setBorder(new LineBorder(Color.RED, 4));
                            }
                        }

                        tempNum = Integer.parseInt(fields.get(4).getText());
                        if(tempNum < 0 || tempNum > 12){
                            fields.get(4).setBorder(new LineBorder(Color.RED, 4));
                        }else{
                            if(Integer.parseInt(fields.get(3).getText()) == Integer.parseInt(fields.get(0).getText())){
                                if(tempNum >= Integer.parseInt(fields.get(1).getText())){
                                    fields.get(4).setBorder(borderButton);
                                    yList.add(tempNum);
                                }else{
                                    fields.get(4).setBorder(new LineBorder(Color.RED, 4));
                                }
                            }else{
                                if(Integer.parseInt(fields.get(3).getText()) > Integer.parseInt(fields.get(0).getText())){
                                    fields.get(4).setBorder(borderButton);
                                    yList.add(tempNum);
                                }else{
                                    fields.get(4).setBorder(new LineBorder(Color.RED, 4));
                                }
                            }
                        }

                        tempNum = Integer.parseInt(fields.get(5).getText());
                        if(tempNum < 0 || tempNum > 28){
                            fields.get(5).setBorder(new LineBorder(Color.RED, 4));
                        }else{
                            if(Integer.parseInt(fields.get(3).getText()) == Integer.parseInt(fields.get(0).getText())){
                                if(Integer.parseInt(fields.get(4).getText()) == Integer.parseInt(fields.get(1).getText())){
                                    if(tempNum > Integer.parseInt(fields.get(2).getText())){
                                        fields.get(5).setBorder(borderButton);
                                        yList.add(tempNum);
                                    }else{
                                        fields.get(5).setBorder(new LineBorder(Color.RED, 4));
                                    }
                                }else{
                                    if(Integer.parseInt(fields.get(4).getText()) > Integer.parseInt(fields.get(1).getText())){
                                        fields.get(5).setBorder(borderButton);
                                        yList.add(tempNum);
                                    }else{
                                        fields.get(5).setBorder(new LineBorder(Color.RED, 4));
                                    }
                                }
                            }else{
                                if(Integer.parseInt(fields.get(3).getText()) > Integer.parseInt(fields.get(0).getText())){
                                    fields.get(5).setBorder(borderButton);
                                    yList.add(tempNum);
                                }else{
                                    fields.get(5).setBorder(new LineBorder(Color.RED, 4));
                                }
                            }
                        }
                    }

                    if(yList.size() == 6){
                        Date startDateGetCar = new Date(yList.get(0),yList.get(1),yList.get(2));
                        Date planDateReturnCar = new Date(yList.get(3),yList.get(4),yList.get(5));

                        long difference = planDateReturnCar.getTime() - startDateGetCar.getTime();
                        int days = (int) (difference/(24*60*60*1000));
                        planPriceLabel.setText("Approximately cost: " + (days + 1) * car.getCost() + " на " + (days + 1));

                        JButton buttonGetCar = new JButton("Get rent.");
                        buttonGetCar.addActionListener(e11 -> {
                            String checkQuery = "SELECT idClient FROM client INNER JOIN user ON client.idUser = user.idUser WHERE login = " + "'" + user.getLogin() + "'";
                            ResultSet checkClientSet = database.select(checkQuery);
                            int idClient = 0;
                            try{
                                while(checkClientSet.next()){
                                    idClient = checkClientSet.getInt("idClient");
                                }
                            }catch(SQLException ex){
                                ex.printStackTrace();
                            }

                            if(idClient == 0){
                                planPriceLabel.setText("Please, fill data");
                            }else{

                                String queryToDB = "SELECT * FROM renta where idClient = (SELECT idClient FROM client WHERE idUser = (SELECT idUser FROM user WHERE login = '" + user.getLogin() + "')) ORDER BY dataEnd, dataPlan DESC LIMIT 1";
                                ResultSet checkRentaSet = database.select(queryToDB);
                                System.out.println(queryToDB);
                                boolean isHaveRenta = false;
                                try{
                                    while(checkRentaSet.next()){
                                        Date rentDate = checkRentaSet.getDate("dataEnd");
                                        if(rentDate == null){
                                            isHaveRenta = true;
                                        }
                                    }
                                }catch(SQLException ex){
                                    ex.printStackTrace();
                                }

                                if(isHaveRenta){
                                    planPriceLabel.setText("You cannot take more than one car at a time");
                                }else{
                                    String insertRenta = "INSERT INTO renta(idClient,idCar,dataStart,dataPlan) VALUES (" + idClient + "," + car.getId();

                                    String startDataIn = "'" + yList.get(0) + "-" + yList.get(1) + "-" + yList.get(2) + "'";
                                    String planDataIn = "'" + yList.get(3) + "-" + yList.get(4) + "-" + yList.get(5) + "'";

                                    insertRenta += "," + startDataIn + "," + planDataIn +")";

                                    database.insert(insertRenta);

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



                                    JTextArea textArea1 = new JTextArea(car.getInfo());
                                    textArea1.setLineWrap(true);
                                    JScrollPane scrollPane1 = new JScrollPane(textArea1);
                                    scrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                                    scrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                                    scrollPane1.setBounds(300, 290, 285, 190);
                                    add(scrollPane1);
                                    System.out.println(insertRenta);
                                }
                            }
                        });
                        buttonGetCar.setBounds(375,510,150,30);
                        add(buttonGetCar);
                    }else{
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
                    revalidate();
                    repaint();
                });
                add(countPrice);

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

                    JTextArea textArea1 = new JTextArea(car.getInfo());
                    textArea1.setLineWrap(true);
                    JScrollPane scrollPane1 = new JScrollPane(textArea1);
                    scrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                    scrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                    scrollPane1.setBounds(300, 290, 285, 190);
                    add(scrollPane1);
                });
                add(back);
            });
            add(actionWithCarButton);
        }else{

            String queryToDatabase = "SELECT * FROM user WHERE idUser = (SELECT idUser FROM client WHERE idClient = (SELECT idClient FROm renta WHERE idCar = " + car.getId() + " ORDER BY dataEnd,dataPlan DESC LIMIT 1))";
            ResultSet checkUserSet = database.select(queryToDatabase);
            boolean isTrue = false;
            try{
                while(checkUserSet.next()){
                    if(user.getLogin().equals(checkUserSet.getString("login")) && user.getPassword().equals(checkUserSet.getString("password"))){
                        isTrue = true;
                    }
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }

            if(isTrue){
                actionWithCarButton.setText("Return car");
                actionWithCarButton.addActionListener(e -> {
                    JButton tempBut = (JButton) e.getSource();
                    JPanel tempPanel = (JPanel) tempBut.getParent();
                    tempPanel.remove(scrollPane);
                    tempPanel.remove(tempBut);

                    Box box = Box.createVerticalBox();
                    String queryReturnCar = "SELECT * FROM injury";
                    ResultSet queryReturnCarSer = database.select(queryReturnCar);
                    ArrayList<String> injuryNames = new ArrayList<>();
                    try{
                        while(queryReturnCarSer.next()){
                            injuryNames.add(queryReturnCarSer.getString("injuryName"));
                        }
                    }catch(SQLException ex){
                        ex.printStackTrace();
                    }

                    ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
                    for (String injuryName : injuryNames) {
                        JCheckBox temp = new JCheckBox(injuryName);
                        checkBoxes.add(temp);
                        box.add(temp);
                    }
                    box.setBorder(new TitledBorder("Types of damage"));
                    box.setBounds(330,290,250,150);
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
                            for (JCheckBox checkBox : checkBoxes) {
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
                            String updateQuery = "UPDATE renta SET dataEnd = '" + dataStr + "' WHERE idCar = " + car.getId() + " AND idClient = (SELECT idClient FROM client INNER JOIN user ON client.idUser = user.idUser WHERE login = '" + user.getLogin() + "');";
                            database.update(updateQuery);

                            String idRentaStr = "SELECT idRenta FROM renta WHERE idCar = " + car.getId() + " AND idClient = (SELECT idClient FROM client INNER JOIN user ON client.idUser = user.idUser WHERE login = '" + user.getLogin() + "') AND dataEnd = '" + dataStr + "'";
                            ResultSet rentaSet = database.select(idRentaStr);
                            int idRentaNum = 0;
                            try{
                                while(rentaSet.next()){
                                    idRentaNum = rentaSet.getInt("idRenta");
                                }
                            }catch(SQLException ex){
                                ex.printStackTrace();
                            }

                            String idInjuryStr = "SELECT idInjury FROM injury WHERE injuryName = '" + injuryForCar +"'";
                            ResultSet injurySet = database.select(idInjuryStr);
                            int idInjuryNum = 0;
                            try{
                                while(injurySet.next()){
                                    idInjuryNum = injurySet.getInt("idInjury");
                                }
                            }catch(SQLException ex){
                                ex.printStackTrace();
                            }

                            database.insert("INSERT INTO  resultinginjury(idRenta,idInjury) VALUES(" + idRentaNum + "," + idInjuryNum + ")");

                            remove(box);
                            remove(newReturnButton);
                            remove(countButton);
                            Component[] listMas1 = selecButPanel.getComponents();
                            for (Component litmas : listMas1) {
                                if (litmas.getClass().toString().contains("JLabel")) {
                                    JLabel label = (JLabel) litmas;
                                    if (label.getText().contains("Sum")) {
                                        remove(label);
                                    }
                                }
                            }
                            JTextArea textArea12 = new JTextArea(car.getInfo());
                            textArea12.setLineWrap(true);
                            JScrollPane scrollPane12 = new JScrollPane(textArea12);
                            scrollPane12.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                            scrollPane12.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                            scrollPane12.setBounds(300, 290, 285, 190);
                            add(scrollPane12);
                            revalidate();
                            repaint();
                        });
                        tempPanel.add(newReturnButton);


                        Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
                        calendar.setTime(new Date());
                        int currYear = calendar.get(Calendar.YEAR);
                        int currMont = calendar.get(Calendar.MONTH);
                        int currDay = calendar.get(Calendar.DATE);

                        String queryToDb = "SELECT login,idCar,join1.idUser,dataStart,dataPlan,dataEnd FROM\n" +
                                "(SELECT idCar,idUSer,renta.idClient,dataStart,dataPlan,dataEnd FROM renta INNER JOIN client ON renta.idClient = client.idClient) as join1\n" +
                                "INNER JOIN user ON join1.idUser = user.idUser WHERE login = '" + user.getLogin() + "' AND idCar = " + car.getId() + " ORDER BY dataEnd,dataPlan DESC LIMIT 1;";

                        ResultSet queryToDbSet = database.select(queryToDb);
                        Date startRentaDate;
                        Date dataRentaPlan = null;
                        try{
                            while(queryToDbSet.next()){
                                dataRentaPlan = queryToDbSet.getDate("dataPlan");
                            }
                        }catch(SQLException ex){
                            ex.printStackTrace();
                        }

                        Date currentGetData = new Date(currYear,currMont,currDay);
                        startRentaDate = new Date(currYear,currMont+1,currDay);

                        JLabel labelCostRenta = new JLabel("5000");
                        labelCostRenta.setBounds(480, 460, 120, 30);

                        if(currentGetData.after(startRentaDate)){
                            labelCostRenta.setText("Sum = 0");
                        }else{
                            double costForTheRent;

                            long difference = startRentaDate.getTime() - currentGetData.getTime();
                            int days = (int) (difference/(24*60*60*1000));
                            costForTheRent = (days + 1f) * car.getCost();


                            if(currentGetData.after(dataRentaPlan)){
                                long diff = currentGetData.getTime() - dataRentaPlan.getTime();
                                int overDay = (int) (difference/(24*60*60*1000));
                                costForTheRent += (car.getCost() * overDay) * 0.2;
                            }

                            for(int i = 0; i < checkBoxes.size(); i++){
                                if(checkBoxes.get(i).isSelected()){
                                    costForTheRent += (i+1) * 50000;
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
                });
                add(actionWithCarButton);
            }
        }
    }

    @Override
    public Rectangle getBoundsRectangle() {
        return new Rectangle(250, 100, 605, 550);
    }

    @Override
    public void paintComponent(Graphics g){
        g.setColor(new Color(237,237,237));
        g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(),30,25);
        Image image = ResourceManager.getImageFromResources(Assets.CARS, String.format("%d.png", car.getId()));
        g.drawImage(image,30,10,this);
        g.setColor(new Color(255,255,255));
        g.fillRect(20, 290, 260, 190);

    }
}
