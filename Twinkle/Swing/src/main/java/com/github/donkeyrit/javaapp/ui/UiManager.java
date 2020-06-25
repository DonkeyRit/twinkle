package com.github.donkeyrit.javaapp.ui;

import com.github.donkeyrit.javaapp.panels.abstraction.CustomPanel;

public class UiManager {

    private Frame frame;
    private Canvas canvas;

    public Canvas getCanvas() {
        return canvas;
    }

    public UiManager() {
        frame = new Frame("Rent car");
        canvas = new Canvas();

        frame.initialize(canvas);
    }

    /**
     * WindowPanel is a type panel on the all window.
     * @param panels
     */
    public void setWindowPanel(CustomPanel ... panels) {
        canvas.removeAll();
        canvas.revalidate();
        canvas.repaint();
        for (CustomPanel panel : panels) {
            panel.setBounds(panel.getBoundsRectangle());
            canvas.add(panel);
        }
    }
}
