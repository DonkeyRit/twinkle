package com.github.donkeyrit.javaapp.panels.filter;

import com.github.donkeyrit.javaapp.container.ServiceContainer;
import com.github.donkeyrit.javaapp.database.DatabaseModelProviders.CarModelProvider;
import com.github.donkeyrit.javaapp.database.DatabaseProvider;
import com.github.donkeyrit.javaapp.panels.CustomPanel;
import com.github.donkeyrit.javaapp.panels.filter.listeners.ApplyButtonListener;
import com.github.donkeyrit.javaapp.panels.filter.listeners.ChangeValueMarkComboBoxListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilterPanel extends CustomPanel {

    private static final Font font = new Font("Arial", Font.BOLD, 13);

    private final DatabaseProvider databaseProvider;

    private JLabel title;
    private JComboBox<String> markComboBox;
    private JComboBox<String> modelComboBox;
    private JLabel sliderTitle;
    private JSlider priceSlider;
    private JButton applyFilterButton;
    private Box bodyTypesCheckBoxesBox;
    private List<JCheckBox> bodyTypesCheckBoxes;
    private JScrollPane bodyTypesScrollPane;

    public JComboBox<String> getMarkComboBox() {
        return markComboBox;
    }
    public JComboBox<String> getModelComboBox() {
        return modelComboBox;
    }
    public JSlider getPriceSlider() {
        return priceSlider;
    }
    public List<JCheckBox> getBodyTypesCheckBoxes() {
        return bodyTypesCheckBoxes;
    }

    public FilterPanel() {
        setLayout(null);

        ServiceContainer serviceContainer = ServiceContainer.getInstance();
        databaseProvider = serviceContainer.getDatabaseProvider();

        initialize();

        add(title);
        add(markComboBox);
        add(modelComboBox);
        add(sliderTitle);
        add(priceSlider);
        add(bodyTypesScrollPane);
        add(applyFilterButton);
    }

    private void initialize() {

        title = new JLabel("Применить фильтр");
        title.setFont(font);
        title.setBounds(40, 10, 140, 20);

        CarModelProvider carModelProvider = databaseProvider.getCarModelProvider();
        List<String> carsMarkList = carModelProvider.getAllCarsMark().collect(Collectors.toList());
        carsMarkList.add(0, "All marks");

        modelComboBox = new JComboBox<>(new String[]{"All models"});
        modelComboBox.setBounds(10, 70, 180, 20);

        markComboBox = new JComboBox<>(carsMarkList.toArray(new String[0]));
        markComboBox.setBounds(10, 40, 180, 20);
        markComboBox.addActionListener(new ChangeValueMarkComboBoxListener(modelComboBox));

        sliderTitle = new JLabel("Choose price:");
        sliderTitle.setBounds(60, 110, 100, 30);

        Stream<Double> carsPriceStream = carModelProvider.getAllCarsPrice();
        int maxPrice = carsPriceStream.max(Comparator.comparingDouble(price -> price)).orElse(0d).intValue();
        int priceSliderTick = maxPrice / 10000;

        priceSlider = new JSlider(JSlider.HORIZONTAL, 0, 10 * priceSliderTick, 0);
        priceSlider.setMajorTickSpacing(((10 * priceSliderTick) / 4));
        priceSlider.setMinorTickSpacing(((10 * priceSliderTick) / 8));
        priceSlider.setPaintTicks(true);
        priceSlider.setPaintLabels(true);
        priceSlider.setSnapToTicks(true);
        priceSlider.setBounds(10, 150, 180, 45);

        Stream<String> bodyTypesStream = carModelProvider.getAllCarsBodyType();

        bodyTypesCheckBoxesBox = Box.createVerticalBox();
        bodyTypesCheckBoxes = new ArrayList<>();
        bodyTypesStream.forEachOrdered(bodyType -> {
            JCheckBox jCheckBox = new JCheckBox(bodyType);
            bodyTypesCheckBoxes.add(jCheckBox);
            bodyTypesCheckBoxesBox.add(jCheckBox);
        });

        bodyTypesScrollPane = new JScrollPane(bodyTypesCheckBoxesBox);
        bodyTypesScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        bodyTypesScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        bodyTypesScrollPane.setBorder(new TitledBorder("Типы кузова"));
        bodyTypesScrollPane.setBounds(10, 225, 180, 270);

        applyFilterButton = new JButton("Apply");
        applyFilterButton.setBounds(50, 520, 100, 20);
        applyFilterButton.addActionListener(new ApplyButtonListener(this));
    }

    @Override
    public Rectangle getBoundsRectangle() {
        return new Rectangle(30, 100, 200, 550);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(new Color(237, 237, 237));
        g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 30, 25);
    }
}
