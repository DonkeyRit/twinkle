package com.github.donkeyrit.javaapp;

import com.github.donkeyrit.javaapp.container.ServiceContainer;
import com.github.donkeyrit.javaapp.database.MySqlProvider;
import com.github.donkeyrit.javaapp.panels.login.LoginPanel;
import com.github.donkeyrit.javaapp.ui.UiManager;


/**
 * @author Dima
 */
public class EntryPoint {

    public static void main(String[] args) {
        new EntryPoint().initialize();
    }

    private void initialize() {
        ServiceContainer container = ServiceContainer.getInstance();
        container.setDatabaseProvider(new MySqlProvider());
        container.setUiManager(new UiManager());
        container.getUiManager().setWindowPanels(new LoginPanel());
    }
}
