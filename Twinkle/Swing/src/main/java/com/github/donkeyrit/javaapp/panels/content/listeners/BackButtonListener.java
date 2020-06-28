package com.github.donkeyrit.javaapp.panels.content.listeners;

import com.github.donkeyrit.javaapp.container.ServiceContainer;
import com.github.donkeyrit.javaapp.panels.content.ContentPanel;
import com.github.donkeyrit.javaapp.ui.UiManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BackButtonListener implements ActionListener {

    private UiManager uiManager;
    private int numOfPage;

    public BackButtonListener(int numOfPage) {
        this.uiManager = ServiceContainer.getInstance().getUiManager();
        this.numOfPage = numOfPage;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton selectedButton = (JButton) e.getSource();
        ContentPanel outerPanel = (ContentPanel) selectedButton.getParent().getParent();
        uiManager.redrawSpecificPanel(outerPanel, new ContentPanel(outerPanel.conditionPanel, (numOfPage / 4 - 1) * 4));
    }
}
