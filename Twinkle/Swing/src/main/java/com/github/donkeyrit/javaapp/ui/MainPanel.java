package com.github.donkeyrit.javaapp.ui;

import com.github.donkeyrit.javaapp.panels.FilterPanel;
import com.github.donkeyrit.javaapp.panels.HeaderPanel;
import com.github.donkeyrit.javaapp.panels.content.ContentPanel;
import com.github.donkeyrit.javaapp.panels.login.LoginPanel;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    public MainPanel() {
        setLayout(null);
        setBackground(new Color(255, 255, 255));
    }

    public void showContent() {
        JPanel header = new HeaderPanel();
        header.setBounds(0, 0, 875, 80);
        this.add(header);

        JPanel filter = new FilterPanel();
        filter.setBounds(30, 100, 200, 550);
        this.add(filter);

        JPanel content = new ContentPanel("");
        content.setBounds(250, 100, 605, 550);
        this.add(content);
    }
}
