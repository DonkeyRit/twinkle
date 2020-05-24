package com.github.donkeyrit.javaapp.panels.aboutcar.listeners;

import com.github.donkeyrit.javaapp.EntryPoint;
import com.github.donkeyrit.javaapp.panels.aboutcar.AboutCarPanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReloadButtonListener implements ActionListener {

    private EntryPoint point; //TODO Remove this reference

    public ReloadButtonListener(EntryPoint point) {
        this.point = point;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Component[] mas = point.panel.getComponents();
        AboutCarPanel temp = null;

        for(int i = 0; i < mas.length; i++){
            if(mas[i].getClass().toString().contains("AboutCarPanel")){
                temp = (AboutCarPanel) mas[i];
            }
        }
        AboutCarPanel newPanel = new AboutCarPanel(point, temp.getCar());
        newPanel.setFilter(temp.getFilter());
        newPanel.setNumPage(temp.getNumPage());
        newPanel.setStartBut(temp.getStartBut());
        newPanel.setBounds(250,100,605,550);

        point.panel.remove(temp);
        point.panel.add(newPanel);
        point.panel.revalidate();
        point.panel.repaint();
    }
}
