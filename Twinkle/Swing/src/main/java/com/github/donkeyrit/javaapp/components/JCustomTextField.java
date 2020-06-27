package com.github.donkeyrit.javaapp.components;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class JCustomTextField extends JTextField {

    private Dimension d = new Dimension(200, 32);
    private String placeholder = "";
    private Color phColor = new Color(0, 0, 0);
    private boolean band = true;

    public JCustomTextField() {
        super();
        setSize(d);
        setPreferredSize(d);
        setVisible(true);
        setMargin(new Insets(3, 6, 3, 6));
        getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                band = getText().length() <= 0;
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                band = false;
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
            }

        });
    }

    public void setState(String placeholder, Color phColor) {
        this.placeholder = placeholder;
        this.phColor = phColor;
        this.setText("");
    }

    public void setState(String placeholder){
        this.placeholder = placeholder;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(phColor.getRed(), phColor.getGreen(), phColor.getBlue(), 90));
        g.drawString((band) ? placeholder : "",
                getMargin().left,
                (getSize().height) / 2 + getFont().getSize() / 2);
    }

}
