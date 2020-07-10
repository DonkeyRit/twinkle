package com.github.donkeyrit.javaapp.panels.content;

import com.github.donkeyrit.javaapp.container.ServiceContainer;
import com.github.donkeyrit.javaapp.model.Car;
import com.github.donkeyrit.javaapp.panels.CustomPanel;
import com.github.donkeyrit.javaapp.resources.Assets;
import com.github.donkeyrit.javaapp.resources.ResourceManager;
import com.github.donkeyrit.javaapp.ui.UiManager;

import javax.swing.*;
import java.awt.*;

public class CarPanel extends CustomPanel {

    private static final Font FONT = new Font("Arial", Font.BOLD, 13);
    private static final Font ALTER_FONT = new Font("Arial", Font.ITALIC, 13);

    private final UiManager uiManager;
    private final Car car;

    private JLabel modelLabelKey;
    private JLabel modelLabelValue;
    private JLabel markLabelKey;
    private JLabel markLabelValue;
    private JLabel bodyTypeLabelKey;
    private JLabel bodyTypeLabelValue;
    private JButton moreButton;

    public CarPanel(Car car) {
        setLayout(null);

        this.uiManager = ServiceContainer.getInstance().getUiManager();
        this.car = car;

        initialize();

        add(modelLabelKey);
        add(modelLabelValue);
        add(markLabelKey);
        add(markLabelValue);
        add(bodyTypeLabelKey);
        add(bodyTypeLabelValue);
        add(moreButton);
    }

    private void initialize() {

        modelLabelKey = new JLabel("Model:");
        modelLabelKey.setBounds(200, 10, 60, 15);
        modelLabelKey.setFont(ALTER_FONT);

        modelLabelValue = new JLabel(this.car.getModelName());
        modelLabelValue.setBounds(290, 10, 150, 15);
        modelLabelValue.setFont(FONT);

        markLabelKey = new JLabel("Mark:");
        markLabelKey.setBounds(200, 30, 60, 15);
        markLabelKey.setFont(ALTER_FONT);

        markLabelValue = new JLabel(this.car.getMarkName());
        markLabelValue.setBounds(290, 30, 150, 15);
        markLabelValue.setFont(FONT);

        bodyTypeLabelKey = new JLabel("Type:");
        bodyTypeLabelKey.setBounds(200, 50, 70, 15);
        bodyTypeLabelKey.setFont(ALTER_FONT);

        bodyTypeLabelValue = new JLabel(this.car.getBodyTypeName());
        bodyTypeLabelValue.setBounds(290, 50, 150, 15);
        bodyTypeLabelValue.setFont(FONT);

        moreButton = new JButton("More");
        moreButton.setBounds(200, 70, 100, 20);
        moreButton.addActionListener(e -> {
            ContentPanel container = (ContentPanel) this.getParent();
            AboutCarPanel newPanel = new AboutCarPanel(this.car, container.getCarFilter(), container.getNumOfPage());
            uiManager.getLayout().setContent(newPanel);
        });
    }

    @Override
    public Rectangle getBoundsRectangle() {
        return null;
    }

    @Override
    public void paintComponent(Graphics g) {
        Image image = ResourceManager.getImageFromResources(Assets.MINI_CARS, String.format("%s.png", this.car.getId())),
                country = ResourceManager.getImageFromResources(Assets.FLAGS, String.format("%s.png", this.car.getNameCountry())),
                statusImage = ResourceManager.getImageFromResources(Assets.STATUS, String.format("%s.png", this.car.getStatus().getAssetName()));

        g.drawImage(image, 10, 10, this);
        g.drawImage(country, 425, 0, this);
        g.drawImage(statusImage, 10, 10, this);
    }
}
