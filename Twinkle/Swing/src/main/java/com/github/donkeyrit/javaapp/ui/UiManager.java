package com.github.donkeyrit.javaapp.ui;

import com.github.donkeyrit.javaapp.panels.abstraction.CustomPanel;

import javax.swing.*;

public class UiManager {

    private Frame frame;
    private MainPanel mainPanel;

    public MainPanel getMainPanel() {
        return mainPanel;
    }

    public UiManager() {
        frame = new Frame("Rent car");
        mainPanel = new MainPanel();

        frame.initialize(mainPanel);
    }

    /**
     * WindowPanel is a type panel on the all window.
     * @param newPanel
     */
    public void replaceWindowPanel(CustomPanel newPanel) {
        mainPanel.removeAll();
        mainPanel.revalidate();
        mainPanel.repaint();
        newPanel.setBounds(newPanel.getBoundsRectangle());
        mainPanel.add(newPanel);
    }
}
