package com.github.donkeyrit.javaapp.panels.content.listeners;

import com.github.donkeyrit.javaapp.container.ServiceContainer;
import com.github.donkeyrit.javaapp.panels.content.ContentPanel;
import com.github.donkeyrit.javaapp.ui.Canvas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BackButtonListener implements ActionListener {

    private Canvas panel;
    private int numOfPage;

    public BackButtonListener(int numOfPage) {
        this.panel = ServiceContainer.getInstance().getUiManager().getCanvas();
        this.numOfPage = numOfPage;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton selectedButton = (JButton) e.getSource();
        ContentPanel outerPanel = (ContentPanel) selectedButton.getParent().getParent();

        JPanel newPanel = new ContentPanel(outerPanel.conditionPanel, (numOfPage / 4 - 1) * 4);
        newPanel.setBounds(250, 100, 605, 550);

        panel.remove(outerPanel);
        panel.add(newPanel);
        panel.revalidate();
        panel.repaint();
    }
}
