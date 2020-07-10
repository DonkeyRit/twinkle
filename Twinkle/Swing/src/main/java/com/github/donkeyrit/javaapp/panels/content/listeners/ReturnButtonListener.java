package com.github.donkeyrit.javaapp.panels.content.listeners;

import com.github.donkeyrit.javaapp.container.ServiceContainer;
import com.github.donkeyrit.javaapp.panels.CustomPanel;
import com.github.donkeyrit.javaapp.panels.content.AboutCarPanel;
import com.github.donkeyrit.javaapp.panels.content.ContentPanel;
import com.github.donkeyrit.javaapp.ui.Canvas;
import com.github.donkeyrit.javaapp.ui.WindowLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReturnButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        WindowLayout layout = ServiceContainer.getInstance().getUiManager().getLayout();

        JButton jButton = (JButton) e.getSource();
        AboutCarPanel oldPanel = (AboutCarPanel) jButton.getParent();
        CustomPanel newPanel = new ContentPanel(oldPanel.getFilter(), oldPanel.getNumPage());

        layout.setContent(newPanel);
    }
}
