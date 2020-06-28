package com.github.donkeyrit.javaapp.panels.content.listeners;

import com.github.donkeyrit.javaapp.container.ServiceContainer;
import com.github.donkeyrit.javaapp.panels.content.ContentPanel;
import com.github.donkeyrit.javaapp.ui.Canvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NavigationButtonListener implements ActionListener {

    private Canvas panel;

    public NavigationButtonListener() {
        panel = ServiceContainer.getInstance().getUiManager().getCanvas();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton selectedButton = (JButton) e.getSource();
        int numPage = Integer.parseInt(selectedButton.getText());
        ContentPanel outerPanel = (ContentPanel) selectedButton.getParent().getParent();

        JPanel newPanel = new ContentPanel(outerPanel.conditionPanel, --numPage);
        newPanel.setBounds(250, 100, 605, 550);

        panel.remove(outerPanel);
        panel.add(newPanel);
        panel.revalidate();
        panel.repaint();
    }

}
