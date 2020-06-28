package com.github.donkeyrit.javaapp.panels.content;

import com.github.donkeyrit.javaapp.container.ServiceContainer;
import com.github.donkeyrit.javaapp.database.DatabaseModelProviders.CarModelProvider;
import com.github.donkeyrit.javaapp.database.DatabaseProvider;
import com.github.donkeyrit.javaapp.model.Car;
import com.github.donkeyrit.javaapp.panels.CarPanel;
import com.github.donkeyrit.javaapp.panels.abstraction.CustomPanel;
import com.github.donkeyrit.javaapp.panels.content.listeners.NextBackListener;
import com.github.donkeyrit.javaapp.panels.content.listeners.ScrollPageListener;
import com.github.donkeyrit.javaapp.resources.Assets;
import com.github.donkeyrit.javaapp.resources.ResourceManager;
import com.github.donkeyrit.javaapp.ui.Canvas;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ContentPanel extends CustomPanel {

    public int numOfPage = 1;
    public int startBut = 1;
    public String conditionPanel = "";

    private static final Font navigationButtonFont = new Font("Arial", Font.ITALIC, 10);

    private DatabaseProvider databaseProvider;
    private final Canvas panel;

    private JLabel contentMainLabel;
    private JButton reloadButton;
    private List<CarPanel> carPanels;
    private Box navigationButtonsBox;

    private List<Car> allCar;

    public ContentPanel(String condition, int... args) {
        setLayout(null);
        conditionPanel = condition;

        ServiceContainer serviceContainer = ServiceContainer.getInstance();
        panel = serviceContainer.getUiManager().getCanvas();
        databaseProvider = serviceContainer.getDatabaseProvider();

        if (args.length != 0) {
            numOfPage = args[0];
        }

        String conditionQuery = "";
        if (!condition.equals("")) {
            conditionQuery = "WHERE " + condition;
        }

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
            Component[] mas = panel.getComponents();
            JPanel temp = null;
            for (Component ma : mas) {
                if (ma.getClass().toString().contains("ContentPanel")) {
                    temp = (JPanel) ma;
                }
            }

            panel.remove(temp);
            JPanel content = new ContentPanel("");
            content.setBounds(250, 100, 605, 550);
            panel.add(content);
            panel.revalidate();
            panel.repaint();
        });
    }

    private void initializeCarList() {

        CarModelProvider carModelProvider = databaseProvider.getCarModelProvider();
        carPanels = new ArrayList<>();

        allCar = carModelProvider.getAllCars().collect(Collectors.toList());
        List<Car> carsOnThePanel = allCar.stream().skip((numOfPage - 1) * 4).limit(4).collect(Collectors.toList());

        for (int i = 0; i < carsOnThePanel.size(); i++) {
            CarPanel temp = new CarPanel(carsOnThePanel.get(i).getId());
            temp.setBorder(new LineBorder(new Color(0, 163, 163), 4));
            temp.setBounds(20, 40 + i * 120, 565, 100);
            carPanels.add(temp);
        }
    }

    private void initializeNavigationButton() {

        List<JButton> navigationButtons = new ArrayList<>();
        int countOfPages = (int) (Math.ceil(allCar.size() / 4f));
        int lastNumOfPage = IntStream.range(1, countOfPages)
                .skip(numOfPage)
                .limit(4)
                .reduce((first, second) -> second)
                .orElse(numOfPage);

        navigationButtonsBox = Box.createHorizontalBox();
        navigationButtonsBox.setBounds(205, 520, 400, 30);

        if (numOfPage - 4 > 0) {
            JButton backBut = new JButton(ResourceManager.getImageIconFromResources(Assets.BUTTONS, "back.png"));
            backBut.addActionListener(new NextBackListener());
            navigationButtons.add(backBut);
        }

        for (int i = numOfPage; i < lastNumOfPage + 1; i++) {
            JButton navigationButton = new JButton(i + "");
            navigationButton.setFont(navigationButtonFont);
            navigationButton.addActionListener(new ScrollPageListener());

            navigationButtons.add(navigationButton);
        }

        if (numOfPage + 4 >= countOfPages) {
            JButton nextBut = new JButton(ResourceManager.getImageIconFromResources(Assets.BUTTONS, "next.png"));
            nextBut.addActionListener(new NextBackListener());
            navigationButtons.add(nextBut);
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
