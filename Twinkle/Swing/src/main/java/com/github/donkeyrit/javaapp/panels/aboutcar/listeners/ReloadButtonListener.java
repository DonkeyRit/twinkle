package com.github.donkeyrit.javaapp.panels.aboutcar.listeners;

import com.github.donkeyrit.javaapp.container.ServiceContainer;
import com.github.donkeyrit.javaapp.panels.aboutcar.AboutCarPanel;
import com.github.donkeyrit.javaapp.ui.Canvas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReloadButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        Canvas panel = ServiceContainer.getInstance().getUiManager().getCanvas();

        JButton jButton = (JButton) e.getSource();
        AboutCarPanel oldPanel = (AboutCarPanel) jButton.getParent();

        AboutCarPanel newPanel = new AboutCarPanel(oldPanel.getCar());
        newPanel.setFilter(oldPanel.getFilter());
        newPanel.setNumPage(oldPanel.getNumPage());
        newPanel.setStartBut(oldPanel.getStartBut());
        newPanel.setBounds(250,100,605,550);

        panel.remove(oldPanel);
        panel.add(newPanel);
        panel.revalidate();
        panel.repaint();
    }
}
