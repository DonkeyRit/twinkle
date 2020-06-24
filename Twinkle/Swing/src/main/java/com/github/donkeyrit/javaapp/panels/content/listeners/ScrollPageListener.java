package com.github.donkeyrit.javaapp.panels.content.listeners;

import com.github.donkeyrit.javaapp.container.ServiceContainer;
import com.github.donkeyrit.javaapp.panels.content.ContentPanel;
import com.github.donkeyrit.javaapp.ui.MainPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScrollPageListener implements ActionListener {

    private MainPanel panel;

    public ScrollPageListener() {
        panel = ServiceContainer.getInstance().getUiManager().getMainPanel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton pressButton = (JButton) e.getSource();
        String textButton = pressButton.getText();
        int numPage = Integer.parseInt(textButton);

        Component[] mas = panel.getComponents();
        ContentPanel temp = null;
        for(int i = 0; i < mas.length; i++){
            if(mas[i].getClass().toString().contains("ContentPanel")){
                temp = (ContentPanel) mas[i];
            }
        }

        panel.remove(temp);
        JPanel content = new ContentPanel(temp.conditionPanel,numPage,temp.startBut);
        content.setBounds(250,100,605,550);
        panel.add(content);
        panel.revalidate();
        panel.repaint();
    }

}
