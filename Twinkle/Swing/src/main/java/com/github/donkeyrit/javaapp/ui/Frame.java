package com.github.donkeyrit.javaapp.ui;

import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame {

    public Frame(String title) throws HeadlessException {
        super(title);
    }

    public void initialize(JPanel panel){
        this.getContentPane().add(BorderLayout.CENTER, panel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(875, 700);
        this.setResizable(false);
        this.setVisible(true);
    }
}
