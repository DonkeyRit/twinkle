package com.github.donkeyrit.javaapp.panels.filter.listeners;

import com.github.donkeyrit.javaapp.container.ServiceContainer;
import com.github.donkeyrit.javaapp.panels.content.ContentPanel;
import com.github.donkeyrit.javaapp.panels.filter.FilterPanel;
import com.github.donkeyrit.javaapp.panels.filter.model.CarFilter;
import com.github.donkeyrit.javaapp.ui.UiManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class ApplyButtonListener implements ActionListener {

    private final FilterPanel filterPanel;
    private final UiManager uiManager;

    public ApplyButtonListener(FilterPanel filterPanel) {
        this.filterPanel = filterPanel;
        this.uiManager = ServiceContainer.getInstance().getUiManager();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        CarFilter carFilter = new CarFilter();

        String selectedCarMark = (String) filterPanel.getMarkComboBox().getSelectedItem();
        String selectedModelMark = (String) filterPanel.getModelComboBox().getSelectedItem();
        int priceValue = filterPanel.getPriceSlider().getValue();
        List<String> selectedBodyTypes = filterPanel.getBodyTypesCheckBoxes().stream()
                .filter(AbstractButton::isSelected)
                .map(AbstractButton::getText)
                .collect(Collectors.toList());

        if (!"All marks".equals(selectedCarMark)) {
            carFilter.addMarkFilter(selectedCarMark);
        }
        if (!"All models".equals(selectedModelMark)) {
            carFilter.addModelFilter(selectedModelMark);
        }
        if (priceValue != 0) {
            carFilter.addPriceFilter(priceValue);
        }
        if (selectedBodyTypes.size() != 0) {
            carFilter.addBodyTypesFilter(selectedBodyTypes);
        }

        uiManager.getLayout().setContent(new ContentPanel(carFilter));
    }
}
