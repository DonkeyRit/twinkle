package com.github.donkeyrit.javaapp.panels.content.listeners;

import com.github.donkeyrit.javaapp.EntryPoint;
import com.github.donkeyrit.javaapp.container.ServiceContainer;
import com.github.donkeyrit.javaapp.panels.content.ContentPanel;
import com.github.donkeyrit.javaapp.ui.MainPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NextBackListener implements ActionListener {

    private MainPanel panel;

    public NextBackListener() {
        panel = ServiceContainer.getInstance().getUiManager().getMainPanel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton selectedBut = (JButton) e.getSource();
        ContentPanel outerPanel = (ContentPanel) selectedBut.getParent().getParent();
        int numNowPage = outerPanel.startBut;

        String nameBut = selectedBut.getIcon().toString();
        int indexName = nameBut.lastIndexOf("/");

        panel.remove(outerPanel);

        JPanel addPanel = null;
        if(nameBut.substring(indexName + 1, indexName + 5).equals("next")){
            addPanel = new ContentPanel(outerPanel.conditionPanel,numNowPage + 5,numNowPage + 5);
            panel.add(addPanel);
        }else{
            addPanel = new ContentPanel(outerPanel.conditionPanel,numNowPage - 5,numNowPage - 5);
            panel.add(addPanel);
        }
        addPanel.setBounds(250,100,605,550);
        panel.revalidate();
        panel.repaint();
    }

}