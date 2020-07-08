package com.github.donkeyrit.javaapp.model.enums;

import com.github.donkeyrit.javaapp.panels.maintenance.ApplicationInformationPanel;
import com.github.donkeyrit.javaapp.panels.maintenance.ChangePasswordPanel;
import com.github.donkeyrit.javaapp.panels.maintenance.PersonalInformationPanel;
import com.github.donkeyrit.javaapp.panels.CustomPanel;

import java.util.function.Supplier;

public enum UserAction {

    CHANGE_PASSWORD("Change password", false, ChangePasswordPanel::new),
    PERSONAL_DATA("Personal data", false, PersonalInformationPanel::new),
    CHANGE_DATA_IN_DB("To change the data", true, ApplicationInformationPanel::new);

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
