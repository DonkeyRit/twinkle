package com.github.donkeyrit.javaapp.panels.maintenance;

import com.github.donkeyrit.javaapp.container.ServiceContainer;
import com.github.donkeyrit.javaapp.model.User;
import com.github.donkeyrit.javaapp.model.enums.UserAction;
import com.github.donkeyrit.javaapp.panels.abstraction.CustomPanel;
import com.github.donkeyrit.javaapp.ui.Canvas;
import com.github.donkeyrit.javaapp.ui.UiManager;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserActionsProfilePanel extends CustomPanel {

    private final static Font FONT = new Font("Arial", Font.BOLD, 13);

    private UiManager uiManager;
    private final Canvas panel;
    private final User user;

    private JLabel labelActions;
    private List<JToggleButton> userActionButtonsList;

    public UserActionsProfilePanel() {
        setLayout(null);

        ServiceContainer container = ServiceContainer.getInstance();
        this.uiManager = container.getUiManager();
        this.user = container.getUser();
        this.panel = container.getUiManager().getCanvas();

        initialize();

        add(labelActions);
        userActionButtonsList.forEach(this::add);
    }

    private void initialize() {

        UserAction[] actions = Arrays.stream(UserAction.values())
                .filter(action -> action.isApplicable(user.isRole()))
                .toArray(UserAction[]::new);

        labelActions = new JLabel("List of actions");
        labelActions.setBounds(40, 10, 150, 30);
        labelActions.setFont(FONT);

        userActionButtonsList = new ArrayList<>();
        for (int i = 0; i < actions.length; i++) {
            JToggleButton userActionButton = new JToggleButton(actions[i].getLabel());
            userActionButton.setBounds(10, 50 + 40 * i, 180, 30);
            userActionButton.addActionListener(e -> {

                JToggleButton selectedUserAction = (JToggleButton) e.getSource();
                for (JToggleButton buttonAction : userActionButtonsList) {
                    if (!buttonAction.getText().equals(selectedUserAction.getText())) {
                        buttonAction.setSelected(false);
                    }
                }

                CustomPanel actionPanel = null;
                UserAction userAction = UserAction.valueOfFromLabel(selectedUserAction.getText());

                switch (userAction){
                    case CHANGE_PASSWORD:
                        actionPanel = UserAction.CHANGE_PASSWORD.getActionPanel().get();
                        break;
                    case CHANGE_DATA_IN_DB:
                        actionPanel = UserAction.CHANGE_DATA_IN_DB.getActionPanel().get();
                        break;
                    case PERSONAL_DATA:
                        actionPanel = UserAction.PERSONAL_DATA.getActionPanel().get();
                        break;
                }

                uiManager.getLayout().setContent(actionPanel);

                /*actionPanel.setBounds(250, 100, 605, 550);
                panel.add(actionPanel);

                panel.revalidate();
                panel.repaint();*/
            });
            userActionButtonsList.add(userActionButton);
        }
    }

    @Override
    public Rectangle getBoundsRectangle() {
        return new Rectangle(30, 100, 200, 550);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(new Color(237, 237, 237));
        g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 30, 25);
    }
}
