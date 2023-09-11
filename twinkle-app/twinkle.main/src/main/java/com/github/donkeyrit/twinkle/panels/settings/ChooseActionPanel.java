package com.github.donkeyrit.twinkle.panels.settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

import com.github.donkeyrit.twinkle.DataBase;
import com.github.donkeyrit.twinkle.bll.models.UserInformation;

public class ChooseActionPanel extends JPanel {

	public ChooseActionPanel(DataBase database, JPanel panel) {
		setLayout(null);

		ArrayList<String> actions = new ArrayList<String>();
		actions.add("Change password");
		actions.add("Personal data");
		if (UserInformation.isRole()) {
			actions.add("To change the data");
		}

		JLabel labelActions = new JLabel("List of actions");
		labelActions.setBounds(40, 10, 150, 30);
		Font font = new Font("Arial", Font.BOLD, 13);
		labelActions.setFont(font);
		add(labelActions);

		ArrayList<JToggleButton> buttonActions = new ArrayList<JToggleButton>();
		for (int i = 0; i < actions.size(); i++) {
			JToggleButton tmp1 = new JToggleButton(actions.get(i));
			tmp1.setBounds(10, 50 + 40 * i, 180, 30);
			tmp1.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					JToggleButton selectedTmp1 = (JToggleButton) e.getSource();
					for (int i = 0; i < buttonActions.size(); i++) {
						if (!buttonActions.get(i).getText().equals(selectedTmp1.getText())) {
							buttonActions.get(i).setSelected(false);
						}
					}

					Component[] componentsPanel = (Component[]) panel.getComponents();
					for (int i = 0; i < componentsPanel.length; i++) {
						if (!(componentsPanel[i].getClass().toString().indexOf("EntryPoint$ChooseActionPanel") > -1
								|| componentsPanel[i].getClass().toString().indexOf("NavigationPanel") > -1)) {
							panel.remove((JPanel) componentsPanel[i]);
						}
					}

					String selectedTextButton = selectedTmp1.getText();

					JPanel rightPanel = null;
					if (selectedTextButton.equals("Change password")) {
						rightPanel = new ChangePasswordPanel(database);
					}
					if (selectedTextButton.equals("Personal data")) {
						rightPanel = new PrivateDataPanel(database);
					}

					if(rightPanel == null)
					{
						return;
					}

					rightPanel.setBounds(250, 100, 605, 550);
					panel.add(rightPanel);

					panel.revalidate();
					panel.repaint();
				}
			});
			buttonActions.add(tmp1);
			add(tmp1);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(new Color(237, 237, 237));
		g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 30, 25);
	}
}
