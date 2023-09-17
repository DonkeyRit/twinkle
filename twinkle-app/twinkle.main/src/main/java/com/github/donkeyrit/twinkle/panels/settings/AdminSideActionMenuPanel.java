package com.github.donkeyrit.twinkle.panels.settings;

import com.github.donkeyrit.twinkle.bll.models.UserInformation;
import com.github.donkeyrit.twinkle.events.contracts.AdminSidePanelEventsListener;
import com.google.inject.Inject;

import java.awt.Graphics;
import java.awt.event.*;
import java.awt.Color;
import java.awt.Font;

import javax.swing.*;
import java.util.*;

public class AdminSideActionMenuPanel extends JPanel {

	private final List<JToggleButton> buttonActions;
	private final AdminSidePanelEventsListener eventsListener;

	@Inject
    public AdminSideActionMenuPanel(AdminSidePanelEventsListener eventsListener) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.eventsListener = eventsListener;
		this.buttonActions = new ArrayList<>(3);

        JLabel labelActions = new JLabel("List of actions");
        Font font = new Font("Arial", Font.BOLD, 13);
        labelActions.setFont(font);
        add(labelActions);

        List<String> actions = getAvailableActions();
        for (String action : actions) {
            JToggleButton actionButton = createActionButton(action);
            buttonActions.add(actionButton);
            add(actionButton);
        }
    }

    private List<String> getAvailableActions() {
        List<String> actions = new ArrayList<>();
        actions.add("Change password");
        actions.add("Personal data");

        if (UserInformation.isRole()) {
            actions.add("To change the data");
        }

        return actions;
    }

    private JToggleButton createActionButton(String actionName) {
        JToggleButton actionButton = new JToggleButton(actionName);
        actionButton.addActionListener(this::handleActionSelected);
        return actionButton;
    }

    private void handleActionSelected(ActionEvent e) {
        JToggleButton selectedButton = (JToggleButton) e.getSource();

        // Deselect other buttons
        for (JToggleButton btn : buttonActions) {
            if (btn != selectedButton) {
                btn.setSelected(false);
            }
        }

        switch (selectedButton.getText()) {
            case "Change password":
                eventsListener.onPasswordChangeRequest();
                break;
            case "Personal data":
                eventsListener.onUsernameInfoChangeRequest();
                break;
            default:
                return; // Or handle other cases
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(237, 237, 237));
        g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 30, 25);
    }
}
