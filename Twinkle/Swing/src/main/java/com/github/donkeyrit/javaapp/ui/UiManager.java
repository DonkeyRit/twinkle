package com.github.donkeyrit.javaapp.ui;

import com.github.donkeyrit.javaapp.panels.abstraction.CustomPanel;

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
     * @param panels
     */
    public void replaceWindowPanel(CustomPanel ... panels) {
        mainPanel.removeAll();
        mainPanel.revalidate();
        mainPanel.repaint();
        for (CustomPanel panel : panels) {
            panel.setBounds(panel.getBoundsRectangle());
            mainPanel.add(panel);
        }
    }
}
