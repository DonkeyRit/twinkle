package com.github.donkeyrit.javaapp.panels.content.listeners;

import com.github.donkeyrit.javaapp.container.ServiceContainer;
import com.github.donkeyrit.javaapp.panels.content.ContentPanel;
import com.github.donkeyrit.javaapp.ui.UiManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavigationButtonListener implements ActionListener {

    private UiManager uiManager;

    public NavigationButtonListener() {
        this.uiManager = ServiceContainer.getInstance().getUiManager();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton selectedButton = (JButton) e.getSource();
        int numPage = Integer.parseInt(selectedButton.getText());
        ContentPanel outerPanel = (ContentPanel) selectedButton.getParent().getParent();
        uiManager.redrawSpecificPanel(outerPanel,new ContentPanel(outerPanel.conditionPanel, --numPage));
    }

}
