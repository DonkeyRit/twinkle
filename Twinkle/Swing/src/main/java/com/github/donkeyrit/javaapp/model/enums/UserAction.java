package com.github.donkeyrit.javaapp.model.enums;

import com.github.donkeyrit.javaapp.panels.ChangeDataDatabasePanel;
import com.github.donkeyrit.javaapp.panels.ChangePasswordPanel;
import com.github.donkeyrit.javaapp.panels.PrivateDataPanel;
import com.github.donkeyrit.javaapp.panels.abstraction.CustomPanel;

import javax.swing.*;
import java.util.function.Supplier;

public enum UserAction {

    CHANGE_PASSWORD("Change password", false, ChangePasswordPanel::new),
    PERSONAL_DATA("Personal data", false, PrivateDataPanel::new),
    CHANGE_DATA_IN_DB("To change the data", true, ChangeDataDatabasePanel::new);

    private String label;
    private boolean isRequiredAdmin;
    private Supplier<CustomPanel> actionPanel;

    public String getLabel() {
        return label;
    }

    public Supplier<CustomPanel> getActionPanel() {
        return actionPanel;
    }

    public boolean isApplicable(boolean userRole) {
        if (userRole) {
            return true;
        } else {
            return !isRequiredAdmin;
        }
    }

    public static UserAction valueOfFromLabel(String label) {

        UserAction[] actions = UserAction.values();

        for (int i = 0; i < actions.length; i++) {
            if (actions[i].getLabel().equals(label)) {
                return actions[i];
            }
        }

        throw new IllegalArgumentException("label");
    }

    UserAction(String label, boolean isRequiredAdmin, Supplier<CustomPanel> actionPanel) {
        this.label = label;
        this.isRequiredAdmin = isRequiredAdmin;
        this.actionPanel = actionPanel;
    }
}
