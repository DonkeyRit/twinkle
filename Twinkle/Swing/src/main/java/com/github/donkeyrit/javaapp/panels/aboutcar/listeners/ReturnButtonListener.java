package com.github.donkeyrit.javaapp.panels.aboutcar.listeners;

import com.github.donkeyrit.javaapp.EntryPoint;
import com.github.donkeyrit.javaapp.panels.ContentPanel;
import com.github.donkeyrit.javaapp.panels.aboutcar.AboutCarPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReturnButtonListener implements ActionListener {

    private EntryPoint point; //TODO Remove this reference

    public ReturnButtonListener(EntryPoint point) {
        this.point = point;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton jButton = (JButton) e.getSource();
        AboutCarPanel oldPanel = (AboutCarPanel) jButton.getParent();

        JPanel newPanel = new ContentPanel(point, oldPanel.getFilter(), oldPanel.getNumPage(),oldPanel.getStartBut());
        newPanel.setBounds(250,100,605,550);

        point.panel.remove(oldPanel);
        point.panel.add(newPanel);
        point.panel.revalidate();
        point.panel.repaint();
    }
}
