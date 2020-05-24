package com.github.donkeyrit.javaapp.panels;

import com.github.donkeyrit.javaapp.EntryPoint;
import com.github.donkeyrit.javaapp.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ChooseActionPanel extends JPanel {

    private final JPanel panel;

    public ChooseActionPanel(EntryPoint point){
        setLayout(null);

        User user = point.user;
        panel = point.panel;

        ArrayList<String> actions = new ArrayList<>();
        actions.add("Change password");
        actions.add("Personal data");
        if(user.isRole()){
            actions.add("To change the data");
        }

        JLabel labelActions = new JLabel("List of actions");
        labelActions.setBounds(40,10,150,30);
        Font font = new Font("Arial", Font.BOLD, 13);
        labelActions.setFont(font);
        add(labelActions);


        ArrayList<JToggleButton> buttonActions = new ArrayList<>();
        for(int i = 0; i < actions.size(); i++){
            JToggleButton tmp1 = new JToggleButton(actions.get(i));
            tmp1.setBounds(10, 50 + 40 * i, 180, 30);
            tmp1.addActionListener(e -> {
                JToggleButton selectedTmp1 = (JToggleButton) e.getSource();
                for (JToggleButton buttonAction : buttonActions) {
                    if (!buttonAction.getText().equals(selectedTmp1.getText())) {
                        buttonAction.setSelected(false);
                    }
                }

                Component[] componentsPanel = panel.getComponents();
                for (Component component : componentsPanel) {
                    if (!(component.getClass().toString().contains("ChooseActionPanel") || component.getClass().toString().contains("HeaderPanel"))) {
                        panel.remove(component);
                    }
                }

                String selectedTextButton = selectedTmp1.getText();

                JPanel rightPanel = null;
                if(selectedTextButton.equals("Сменить пароль")){
                    rightPanel = new ChangePasswordPanel(point);
                }
                if(selectedTextButton.equals("Личные данные")){
                    rightPanel = new PrivateDataPanel(point);
                }
                if(selectedTextButton.equals("Изменить данные")){
                    rightPanel = new ChangeDataDatabasePanel(point);
                }
                rightPanel.setBounds(250,100,605,550);
                panel.add(rightPanel);

                panel.revalidate();
                panel.repaint();
            });
            buttonActions.add(tmp1);
            add(tmp1);
        }
    }

    @Override
    public void paintComponent(Graphics g){
        g.setColor(new Color(237,237,237));
        g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(),30,25);
    }
}
