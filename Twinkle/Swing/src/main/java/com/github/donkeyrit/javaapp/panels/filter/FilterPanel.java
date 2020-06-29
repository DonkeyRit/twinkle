package com.github.donkeyrit.javaapp.panels.filter;

import com.github.donkeyrit.javaapp.container.ServiceContainer;
import com.github.donkeyrit.javaapp.database.DatabaseModelProviders.CarModelProvider;
import com.github.donkeyrit.javaapp.database.DatabaseProvider;
import com.github.donkeyrit.javaapp.panels.abstraction.CustomPanel;
import com.github.donkeyrit.javaapp.panels.content.ContentPanel;
import com.github.donkeyrit.javaapp.ui.Canvas;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilterPanel extends CustomPanel {

    private static final Font font = new Font("Arial", Font.BOLD, 13);

    private final DatabaseProvider databaseProvider;
    private final Canvas panel;

    private JLabel title;
    private JComboBox<String> markComboBox;
    private JComboBox<String> modelComboBox;
    private JLabel sliderTitle;
    private JSlider priceSlider;
    private JButton applyFilterButton;
    private Box bodyTypesCheckBoxesBox;
    private List<JCheckBox> bodyTypesCheckBoxes;
    private JScrollPane bodyTypesScrollPane;

    public FilterPanel() {
        setLayout(null);

        ServiceContainer serviceContainer = ServiceContainer.getInstance();
        databaseProvider = serviceContainer.getDatabaseProvider();
        panel = serviceContainer.getUiManager().getCanvas();

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

        markComboBox = new JComboBox<>(carsMarkList.toArray(new String[carsMarkList.size()]));
        markComboBox.setBounds(10, 40, 180, 20);
        markComboBox.addActionListener(e -> {
            JComboBox temp = (JComboBox) e.getSource();
            String markSelected = (String) temp.getSelectedItem();
            ArrayList<Integer> idMarkList = new ArrayList<Integer>();
            ArrayList<String> modelList = new ArrayList<String>();

            if (!markSelected.equals("All marks")) {
                ResultSet idMarkSet = databaseProvider.select("SELECT idMark FROM mark WHERE markName = '" + markSelected + "'");

                try {
                    while (idMarkSet.next()) {
                        idMarkList.add(idMarkSet.getInt("idMark"));
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                String queryModel = "SELECT modelName FROM model WHERE idMark in (";
                for (int i = 0; i < idMarkList.size(); i++) {
                    queryModel += idMarkList.get(i);
                    if (i == idMarkList.size() - 1) {
                        queryModel += ")";
                    } else {
                        queryModel += ",";
                    }
                }

                ResultSet modelSet = databaseProvider.select(queryModel);
                try {
                    while (modelSet.next()) {
                        modelList.add(modelSet.getString("modelName"));
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            modelComboBox.removeAllItems();
            modelComboBox.addItem("All models");
            for (int i = 0; i < modelList.size(); i++) {
                modelComboBox.addItem(modelList.get(i));
            }
        });

        modelComboBox = new JComboBox<>(new String[]{"All models"});
        modelComboBox.setBounds(10, 70, 180, 20);

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
        applyFilterButton.addActionListener(e -> {
            String resultCondition = "";

            String selectedMark = markComboBox.getSelectedItem().toString();
            if (!selectedMark.equals("All marks")) {
                resultCondition += "markName = " + "'" + selectedMark + "'";
            } else {
                resultCondition += "!";
            }

            resultCondition += ":";

            String selectedModel = modelComboBox.getSelectedItem().toString();
            if (!selectedModel.equals("All models")) {
                resultCondition += "modelName = " + "'" + selectedModel + "'";
            } else {
                resultCondition += "!";
            }

            resultCondition += ":";

            int selectedPrice = priceSlider.getValue();
            if (selectedPrice != 0) {
                resultCondition += "cost < " + selectedPrice * 1000;
            } else {
                resultCondition += "!";
            }
            resultCondition += ":";

            StringBuilder selectedCheckBoxes = new StringBuilder();
            ArrayList<String> selectedCB = new ArrayList<>();
            for (JCheckBox checkBox : bodyTypesCheckBoxes) {
                boolean isTrue = checkBox.isSelected();
                if (isTrue) {
                    selectedCB.add(checkBox.getText());
                }
            }

            if (selectedCB.size() == 0) {
                resultCondition += "!";
            } else {
                selectedCheckBoxes.append("bodyTypeName IN (");
                for (int i = 0; i < selectedCB.size(); i++) {
                    selectedCheckBoxes.append("'").append(selectedCB.get(i)).append("'");
                    if (i != selectedCB.size() - 1) {
                        selectedCheckBoxes.append(",");
                    }
                }
                selectedCheckBoxes.append(")");
            }
            resultCondition += selectedCheckBoxes;

            String res = resultCondition.replaceAll("!", "");
            String[] masive = res.split(":");
            StringBuilder resStr = new StringBuilder();
            for (int i = 0; i < masive.length; i++) {
                if (!masive[i].equals("")) {
                    resStr.append(masive[i]);
                    if (i != masive.length - 1) {
                        resStr.append(" AND ");
                    }
                }
            }

            Component[] mas = panel.getComponents();
            JPanel temp = null;
            for (Component ma : mas) {
                if (ma.getClass().toString().contains("ContentPanel") || ma.getClass().toString().contains("AboutCarPanel")) {
                    temp = (JPanel) ma;
                }
            }

            panel.remove(temp);
            JPanel content = new ContentPanel(resStr.toString());
            content.setBounds(250, 100, 605, 550);
            panel.add(content);
            panel.revalidate();
            panel.repaint();
        });
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
