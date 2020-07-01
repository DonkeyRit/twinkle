package com.github.donkeyrit.javaapp.panels.content;

import com.github.donkeyrit.javaapp.container.ServiceContainer;
import com.github.donkeyrit.javaapp.database.DatabaseModelProviders.CarModelProvider;
import com.github.donkeyrit.javaapp.database.DatabaseProvider;
import com.github.donkeyrit.javaapp.model.Car;
import com.github.donkeyrit.javaapp.panels.abstraction.CustomPanel;
import com.github.donkeyrit.javaapp.panels.content.listeners.BackButtonListener;
import com.github.donkeyrit.javaapp.panels.content.listeners.NavigationButtonListener;
import com.github.donkeyrit.javaapp.panels.content.listeners.NextButtonListener;
import com.github.donkeyrit.javaapp.panels.filter.model.ContentFilter;
import com.github.donkeyrit.javaapp.panels.filter.model.NullFilter;
import com.github.donkeyrit.javaapp.resources.Assets;
import com.github.donkeyrit.javaapp.resources.ResourceManager;
import com.github.donkeyrit.javaapp.ui.UiManager;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ContentPanel extends CustomPanel {

    public int numOfPage = 0;
    public int startBut = 1;

    private static final Font navigationButtonFont = new Font("Arial", Font.ITALIC, 10);

    private DatabaseProvider databaseProvider;
    private UiManager uiManager;

    private JLabel contentMainLabel;
    private JButton reloadButton;
    private List<CarPanel> carPanels;
    private Box navigationButtonsBox;

    private int countOfPages;
    private ContentFilter<Car> carFilter;

    public ContentFilter<Car> getCarFilter() {
        return carFilter;
    }

    public ContentPanel() {
        this(new NullFilter(), 0);
    }

    public ContentPanel(ContentFilter<Car> filter) {
        this(filter, 0);
    }

    public ContentPanel(ContentFilter<Car> filter, int numOfPage) {
        setLayout(null);

        ServiceContainer serviceContainer = ServiceContainer.getInstance();
        databaseProvider = serviceContainer.getDatabaseProvider();
        uiManager = serviceContainer.getUiManager();
        carFilter = filter;

        this.numOfPage = numOfPage;

        initialize();
        initializeCarList();
        initializeNavigationButton();

        add(contentMainLabel);
        add(reloadButton);
        carPanels.forEach(this::add);
        add(navigationButtonsBox);
    }

    private void initialize() {
        contentMainLabel = new JLabel("List of cars:");
        Font font = new Font("Arial", Font.BOLD, 13);
        contentMainLabel.setFont(font);
        contentMainLabel.setBounds(250, 10, 100, 20);

        reloadButton = new JButton();
        reloadButton.setBounds(568, 10, 16, 16);
        ImageIcon iconExit = ResourceManager.getImageIconFromResources(Assets.BUTTONS, "reload.png");
        reloadButton.setIcon(iconExit);
        reloadButton.setHorizontalTextPosition(SwingConstants.LEFT);
        reloadButton.addActionListener(e -> {
            JButton selectedButton = (JButton) e.getSource();
            JPanel outerPanel = (JPanel) selectedButton.getParent().getParent();
            uiManager.redrawSpecificPanel(outerPanel, new ContentPanel());
        });
    }

    private void initializeCarList() {

        CarModelProvider carModelProvider = databaseProvider.getCarModelProvider();
        carPanels = new ArrayList<>();

        List<Car> allCar = carFilter.filter(carModelProvider.getAllCars()).collect(Collectors.toList());
        countOfPages = (int) (Math.ceil(allCar.size() / 4f));
        List<Car> carsOnThePanel = allCar.stream().skip(numOfPage * 4).limit(4).collect(Collectors.toList());

        for (int i = 0; i < carsOnThePanel.size(); i++) {
            CarPanel currentCar = new CarPanel(carsOnThePanel.get(i));
            currentCar.setBorder(new LineBorder(new Color(0, 163, 163), 4));
            currentCar.setBounds(20, 40 + i * 120, 565, 100);
            carPanels.add(currentCar);
        }
    }

    private void initializeNavigationButton() {

        List<JButton> navigationButtons = new ArrayList<>();

        int indexOfRange = numOfPage / 4;
        int leftBoundary = indexOfRange * 4;
        int rightBoundary = IntStream.rangeClosed(1, countOfPages)
                .skip(leftBoundary)
                .limit(4)
                .reduce((first, second) -> second)
                .orElse(numOfPage);

        navigationButtonsBox = Box.createHorizontalBox();
        navigationButtonsBox.setBounds(205, 520, 400, 30);

        if (leftBoundary != 0) {
            JButton backButton = new JButton(ResourceManager.getImageIconFromResources(Assets.BUTTONS, "back.png"));
            backButton.addActionListener(new BackButtonListener(numOfPage));
            navigationButtons.add(backButton);
        }

        for (int i = leftBoundary; i < rightBoundary; i++) {
            JButton navigationButton = new JButton((i + 1) + "");
            navigationButton.setFont(navigationButtonFont);
            navigationButton.addActionListener(new NavigationButtonListener());

            navigationButtons.add(navigationButton);
        }

        if (countOfPages > rightBoundary) {
            JButton nextButton = new JButton(ResourceManager.getImageIconFromResources(Assets.BUTTONS, "next.png"));
            nextButton.addActionListener(new NextButtonListener(numOfPage));
            navigationButtons.add(nextButton);
        }

        navigationButtons.forEach(jButton -> navigationButtonsBox.add(jButton));
    }

    @Override
    public Rectangle getBoundsRectangle() {
        return new Rectangle(250, 100, 605, 550);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(new Color(237, 237, 237));
        g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 30, 25);
    }
}
