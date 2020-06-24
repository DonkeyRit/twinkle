package com.github.donkeyrit.javaapp.ui;

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
}
