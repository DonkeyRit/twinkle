package com.github.donkeyrit.javaapp.ui;

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
}
