package com.github.donkeyrit.javaapp.panels.filter.listeners;

import com.github.donkeyrit.javaapp.container.ServiceContainer;
import com.github.donkeyrit.javaapp.panels.content.ContentPanel;
import com.github.donkeyrit.javaapp.panels.filter.FilterPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ApplyButtonListener implements ActionListener {

    private FilterPanel filterPanel;
    private JPanel panel;

    public ApplyButtonListener(FilterPanel filterPanel) {
        this.filterPanel = filterPanel;
        this.panel = ServiceContainer.getInstance().getUiManager().getCanvas();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String resultCondition = "";

        String selectedMark = filterPanel.getMarkComboBox().getSelectedItem().toString();
        if (!selectedMark.equals("All marks")) {
            resultCondition += "markName = " + "'" + selectedMark + "'";
        } else {
            resultCondition += "!";
        }

        resultCondition += ":";

        String selectedModel = filterPanel.getModelComboBox().getSelectedItem().toString();
        if (!selectedModel.equals("All models")) {
            resultCondition += "modelName = " + "'" + selectedModel + "'";
        } else {
            resultCondition += "!";
        }

        resultCondition += ":";

        int selectedPrice = filterPanel.getPriceSlider().getValue();
        if (selectedPrice != 0) {
            resultCondition += "cost < " + selectedPrice * 1000;
        } else {
            resultCondition += "!";
        }
        resultCondition += ":";

        StringBuilder selectedCheckBoxes = new StringBuilder();
        ArrayList<String> selectedCB = new ArrayList<>();
        for (JCheckBox checkBox : filterPanel.getBodyTypesCheckBoxes()) {
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
    }
}
