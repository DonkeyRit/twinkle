package com.github.donkeyrit.javaapp.panels.content;

import com.github.donkeyrit.javaapp.container.ServiceContainer;
import com.github.donkeyrit.javaapp.model.Car;
import com.github.donkeyrit.javaapp.model.User;
import com.github.donkeyrit.javaapp.model.enums.CarStatus;
import com.github.donkeyrit.javaapp.panels.CustomPanel;
import com.github.donkeyrit.javaapp.panels.content.listeners.GetRentListener;
import com.github.donkeyrit.javaapp.panels.content.listeners.ReloadButtonListener;
import com.github.donkeyrit.javaapp.panels.content.listeners.ReturnButtonListener;
import com.github.donkeyrit.javaapp.panels.content.listeners.ReturnCarListener;
import com.github.donkeyrit.javaapp.panels.filter.model.ContentFilter;
import com.github.donkeyrit.javaapp.resources.Assets;
import com.github.donkeyrit.javaapp.resources.ResourceManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AboutCarPanel extends CustomPanel {

    private static final Font FONT = new Font("Arial", Font.BOLD, 13);
    private static final Font ALTER_FONT = new Font("Arial", Font.ITALIC, 13);

    private final Car car;
    private final User user;
    private final int numPage;
    private final ContentFilter<Car> filter;

    private JScrollPane infoAboutCarScrollableContainer;
    private JButton reloadButton;
    private JButton returnButton;
    private JButton actionWithCarButton;
    private List<JLabel> carDetailsList;

    public Car getCar() {
        return car;
    }
    public User getUser() {
        return user;
    }
    public int getNumPage() {
        return numPage;
    }
    public ContentFilter<Car> getFilter() {
        return filter;
    }
    public JScrollPane getInfoAboutCarScrollableContainer() {
        return infoAboutCarScrollableContainer;
    }

    public AboutCarPanel(Car car, ContentFilter<Car> filter, int numOfPage) {
        setLayout(null);

        ServiceContainer serviceContainer = ServiceContainer.getInstance();
        this.user = serviceContainer.getUser();
        this.car = car;
        this.filter = filter;
        this.numPage = numOfPage;

        initialize();

        add(infoAboutCarScrollableContainer);
        add(reloadButton);
        add(returnButton);
        carDetailsList.forEach(this::add);
        if (actionWithCarButton != null) {
            add(actionWithCarButton);
        }
    }

    private void initialize() {

        JTextArea infoAboutCar = new JTextArea(car.getInfo());
        infoAboutCar.setLineWrap(true);
        infoAboutCarScrollableContainer = new JScrollPane(infoAboutCar);
        infoAboutCarScrollableContainer.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        infoAboutCarScrollableContainer.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        infoAboutCarScrollableContainer.setBounds(300, 290, 285, 190);

        carDetailsList = new ArrayList<>();
        Map<String, String> carDetailsMap = Map.of(
                "Model:", car.getModelName(),
                "Mark:", car.getMarkName(),
                "Year:", car.getModelYear().toString(),
                "Type:", car.getBodyTypeName(),
                "Cost per day:", car.getCost().toString(),
                "Status:", car.getStatus().getUiValue()
        );

        int indexRow = 0;

        for (Map.Entry<String, String> pair : carDetailsMap.entrySet()) {

            JLabel labelKey = new JLabel(pair.getKey());
            labelKey.setBounds(30, 300 + (indexRow / 2) * 30, 80, 20);
            labelKey.setFont(ALTER_FONT);

            JLabel labelValue = new JLabel(pair.getValue());
            labelValue.setBounds(150, 300 + (indexRow / 2) * 30, 120, 20);
            labelValue.setFont(FONT);

            carDetailsList.add(labelKey);
            carDetailsList.add(labelValue);

            indexRow += 2;
        }

        reloadButton = new JButton(ResourceManager.getImageIconFromResources(Assets.BUTTONS, "reload.png"));
        reloadButton.setBounds(550, 0, 16, 16);
        reloadButton.addActionListener(new ReloadButtonListener());

        returnButton = new JButton(ResourceManager.getImageIconFromResources(Assets.BUTTONS, "return.png"));
        returnButton.setBounds(570, 0, 16, 16);
        returnButton.addActionListener(new ReturnButtonListener());

        actionWithCarButton = new JButton("");
        actionWithCarButton.setBounds(300, 500, 150, 20);

        if (car.getStatus() == CarStatus.OPEN) {
            actionWithCarButton.setText("Get rent");
            actionWithCarButton.addActionListener(new GetRentListener(this));
        } else if (car.getCurrentHolder() != null && car.getCurrentHolder().equals(user)) {
            actionWithCarButton.setText("Return car");
            actionWithCarButton.addActionListener(new ReturnCarListener(this));
        } else {
            actionWithCarButton = null;
        }
    }

    @Override
    public Rectangle getBoundsRectangle() {
        return new Rectangle(250, 100, 605, 550);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(new Color(237, 237, 237));
        g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 30, 25);
        Image image = ResourceManager.getImageFromResources(Assets.CARS, String.format("%d.png", car.getId()));
        g.drawImage(image, 30, 10, this);
        g.setColor(new Color(255, 255, 255));
        g.fillRect(20, 290, 260, 190);
    }
}
