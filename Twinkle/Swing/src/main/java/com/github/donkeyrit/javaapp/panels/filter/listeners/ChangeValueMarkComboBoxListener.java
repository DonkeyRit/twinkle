package com.github.donkeyrit.javaapp.panels.filter.listeners;

import com.github.donkeyrit.javaapp.container.ServiceContainer;
import com.github.donkeyrit.javaapp.database.DatabaseModelProviders.CarModelProvider;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.stream.Stream;

public class ChangeValueMarkComboBoxListener implements ActionListener {

    private JComboBox<String> modelComboBox;
    private CarModelProvider carModelProvider;

    public ChangeValueMarkComboBoxListener(JComboBox<String> modelComboBox) {
        this.modelComboBox = modelComboBox;
        this.carModelProvider = ServiceContainer.getInstance()
                .getDatabaseProvider()
                .getCarModelProvider();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //noinspection rawtypes
        JComboBox temp = (JComboBox) e.getSource();
        String selectedItem = (String) temp.getSelectedItem();

        Stream<String> carsModelStream = Stream.empty();

        if (!"All marks".equals(selectedItem)) {
            carsModelStream = carModelProvider.getAllModelsForSpecificMark(selectedItem);
        }

        modelComboBox.removeAllItems();
        modelComboBox.addItem("All models");
        carsModelStream.forEachOrdered(model -> modelComboBox.addItem(model));
    }
}
