package com.github.donkeyrit.javaapp.ui;

import com.github.donkeyrit.javaapp.panels.abstraction.CustomPanel;

import javax.swing.*;

public class UiManager {

    private Frame frame;

    private Canvas canvas;
    public Canvas getCanvas() {
        return canvas;
    }

    private WindowLayout layout;
    public WindowLayout getLayout() {
        return layout;
    }

    public UiManager() {
        frame = new Frame("Rent car");
        canvas = new Canvas();
        layout = new WindowLayout(canvas);

        frame.initialize(canvas);
    }

    /**
     * WindowPanel is a type panel on the all window.
     *
     * @param panels
     */
    public void setWindowPanels(CustomPanel... panels) {
        canvas.removeAll();
        canvas.revalidate();
        canvas.repaint();
        for (CustomPanel panel : panels) {
            panel.setBounds(panel.getBoundsRectangle());
            canvas.add(panel);
        }
    }

    public void redrawSpecificPanel(JPanel previousPanel, CustomPanel newPanel) {
        newPanel.setBounds(newPanel.getBoundsRectangle());
        canvas.remove(previousPanel);
        canvas.add(newPanel);
        canvas.revalidate();
        canvas.repaint();
    }
}
