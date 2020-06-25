package com.github.donkeyrit.javaapp.panels.content.listeners;

import com.github.donkeyrit.javaapp.container.ServiceContainer;
import com.github.donkeyrit.javaapp.panels.content.ContentPanel;
import com.github.donkeyrit.javaapp.ui.Canvas;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NextBackListener implements ActionListener {

    private Canvas panel;

    public NextBackListener() {
        panel = ServiceContainer.getInstance().getUiManager().getCanvas();
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