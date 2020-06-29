package com.github.donkeyrit.javaapp.panels;

import com.github.donkeyrit.javaapp.container.ServiceContainer;
import com.github.donkeyrit.javaapp.database.DatabaseProvider;
import com.github.donkeyrit.javaapp.model.Car;
import com.github.donkeyrit.javaapp.panels.aboutcar.AboutCarPanel;
import com.github.donkeyrit.javaapp.panels.abstraction.CustomPanel;
import com.github.donkeyrit.javaapp.panels.content.ContentPanel;
import com.github.donkeyrit.javaapp.resources.Assets;
import com.github.donkeyrit.javaapp.resources.ResourceManager;
import com.github.donkeyrit.javaapp.ui.Canvas;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class CarPanel extends CustomPanel {

    private Car car;

    private final Canvas panel;

    public CarPanel(int num) {

        ServiceContainer serviceContainer = ServiceContainer.getInstance();
        DatabaseProvider database = serviceContainer.getDatabaseProvider();
        panel = serviceContainer.getUiManager().getCanvas();

        Car.CarBuilder carBuilder = new Car.CarBuilder();

        ResultSet statusSet = database.select("SELECT * FROM renta WHERE idCar = " + num + " ORDER BY dataEnd, dataPlan DESC LIMIT 1");
        carBuilder.setStatus("open");
        try {
            while (statusSet.next()) {
                Date rentDate = statusSet.getDate("dataEnd");
                if (rentDate == null) {
                    carBuilder.setStatus("lock");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        carBuilder.setImagesNum(num);
        setLayout(null);
        String query = "SELECT idCar,modelYear,info,cost,modelName,markName,nameCountry,bodyTypeName FROM\n" +
                "(SELECT idCar,modelYear,info,cost,modelName,idBodyType,markName,nameCountry FROM\n" +
                "(SELECT idCar,modelYear,image,info,cost,modelName,idBodyType,markName,idCountry FROM \n" +
                "(SELECT idCar,modelYear,image,info,cost,modelName,idMark,idBodyType FROM car \n" +
                "INNER JOIN model ON car.idModel = model.idModel) as join1\n" +
                "INNER JOIN mark ON join1.idMark = mark.idMark) as join2\n" +
                "INNER JOIN country ON join2.idCountry = country.idCountry) as join3\n" +
                "INNER JOIN bodytype ON join3.idBodyType = bodytype.idBodyType WHERE idCar = " + num;

        ResultSet carSet = database.select(query);
        try {
            while (carSet.next()) {
                carBuilder.setModelYear(carSet.getDate("modelYear"))
                        .setCost(carSet.getDouble("cost"))
                        .setModelName(carSet.getString("modelName"))
                        .setMarkName(carSet.getString("markName"))
                        .setNameCountry(carSet.getString("nameCountry"))
                        .setInfo(carSet.getString("info"))
                        .setBodyTypeName(carSet.getString("bodyTypeName"));
                this.car = carBuilder.create();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        Font font = new Font("Arial", Font.BOLD, 13);
        Font alterfont = new Font("Arial", Font.ITALIC, 13);

        JLabel modelLab = new JLabel("Model:");
        modelLab.setBounds(200, 10, 60, 15);
        modelLab.setFont(alterfont);
        add(modelLab);

        JLabel modelLabel = new JLabel(this.car.getModelName());
        modelLabel.setBounds(290, 10, 150, 15);
        modelLabel.setFont(font);
        add(modelLabel);


        JLabel markLab = new JLabel("Mark:");
        markLab.setBounds(200, 30, 60, 15);
        markLab.setFont(alterfont);
        add(markLab);

        JLabel markLabel = new JLabel(this.car.getMarkName());
        markLabel.setBounds(290, 30, 150, 15);
        markLabel.setFont(font);
        add(markLabel);

        JLabel bodyTypeLab = new JLabel("Type:");
        bodyTypeLab.setBounds(200, 50, 70, 15);
        bodyTypeLab.setFont(alterfont);
        add(bodyTypeLab);

        JLabel bodyTypeLabel = new JLabel(this.car.getBodyTypeName());
        bodyTypeLabel.setBounds(290, 50, 150, 15);
        bodyTypeLabel.setFont(font);
        add(bodyTypeLabel);

        JButton moreButton = new JButton("More");
        moreButton.setBounds(200, 70, 100, 20);
        moreButton.addActionListener(e -> {
            Component[] mas = panel.getComponents();
            ContentPanel temp = null;
            for (Component ma : mas) {
                if (ma.getClass().toString().contains("ContentPanel")) {
                    temp = (ContentPanel) ma;
                }
            }
            AboutCarPanel newPanel = new AboutCarPanel(this.car);
            newPanel.setFilter(temp.getCarFilter());
            newPanel.setNumPage(temp.numOfPage);
            newPanel.setStartBut(temp.startBut);
            newPanel.setBounds(250, 100, 605, 550);
            panel.remove(temp);
            panel.add(newPanel);
            panel.revalidate();
            panel.repaint();
        });
        add(moreButton);


    }

    @Override
    public Rectangle getBoundsRectangle() {
        return null;
    }

    @Override
    public void paintComponent(Graphics g) {
        Image image = ResourceManager.getImageFromResources(Assets.MINI_CARS, String.format("%s.png", this.car.getId())),
                country = ResourceManager.getImageFromResources(Assets.FLAGS, String.format("%s.png", this.car.getNameCountry())),
                statusImage = ResourceManager.getImageFromResources(Assets.STATUS, String.format("%s.png", this.car.getStatus()));

        g.drawImage(image, 10, 10, this);
        g.drawImage(country, 425, 0, this);
        g.drawImage(statusImage, 10, 10, this);
    }
}
