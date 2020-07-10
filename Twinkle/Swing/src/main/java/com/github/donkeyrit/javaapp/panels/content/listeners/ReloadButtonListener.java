package com.github.donkeyrit.javaapp.panels.content.listeners;

import com.github.donkeyrit.javaapp.container.ServiceContainer;
import com.github.donkeyrit.javaapp.panels.content.AboutCarPanel;
import com.github.donkeyrit.javaapp.ui.WindowLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReloadButtonListener implements ActionListener {

    @Override
    public void actionPerformed(ActionEvent e) {

        WindowLayout layout = ServiceContainer.getInstance().getUiManager().getLayout();

        JButton jButton = (JButton) e.getSource();
        AboutCarPanel oldPanel = (AboutCarPanel) jButton.getParent();
        AboutCarPanel newPanel = new AboutCarPanel(oldPanel.getCar(), oldPanel.getFilter(), oldPanel.getNumPage());

        layout.setContent(newPanel);
    }
}
