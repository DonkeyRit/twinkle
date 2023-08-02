package com.github.donkeyrit.twinkle.panels.settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.border.*;

import com.github.donkeyrit.twinkle.DataBase;
import com.github.donkeyrit.twinkle.bll.models.UserInformation;
import com.github.donkeyrit.twinkle.controls.input.JCustomPasswordField;
import com.github.donkeyrit.twinkle.security.HashManager;
import com.github.donkeyrit.twinkle.utils.AssetsRetriever;

public class ChangePasswordPanel extends JPanel {

	public ChangePasswordPanel(DataBase database) {
		setLayout(null);

		Box mainBox = Box.createVerticalBox();
		mainBox.setBorder(new TitledBorder("Change Password"));
		mainBox.setBounds(202, 10, 200, 200);

		String[] labels = new String[] { "Enter old pasword", "Enter new password", "Repeat new password" };
		ArrayList<JCustomPasswordField> fieldPass = new ArrayList<JCustomPasswordField>();
		for (int i = 0; i < labels.length; i++) {
			JCustomPasswordField passw = new JCustomPasswordField(20);
			passw.setPlaceholder(labels[i]);
			fieldPass.add(passw);
			mainBox.add(passw);
			mainBox.add(Box.createVerticalStrut(10));
		}

		JButton confirm = new JButton("Confirm");
		confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean isOne = fieldPass.get(0).getPassword().length == 0;
				boolean isTwo = fieldPass.get(1).getPassword().length == 0;
				boolean isThree = fieldPass.get(2).getPassword().length == 0;

				if (isOne) {
					fieldPass.get(0).setPlaceholder("Please, enter old password");
					fieldPass.get(0).setPhColor(Color.RED);
				}

				if (isTwo) {
					fieldPass.get(1).setPlaceholder("Please, enter new password");
					fieldPass.get(1).setPhColor(Color.RED);
				}

				if (isThree) {
					fieldPass.get(2).setPlaceholder("Please, repeat new password");
					fieldPass.get(2).setPhColor(Color.RED);
				}

				if (!isOne && !isTwo && !isThree) {
					if (HashManager.generateHash(new String(fieldPass.get(0).getPassword())).equals(UserInformation.getPassword())) {
						if (new String(fieldPass.get(1).getPassword()).equals(new String(fieldPass.get(2).getPassword()))) {
							if (new String(fieldPass.get(0).getPassword()).equals(new String(fieldPass.get(1).getPassword()))) {
								fieldPass.get(0).setPlaceholder("Old and new match");
								fieldPass.get(0).setPhColor(Color.RED);
								fieldPass.get(0).setText("");

								fieldPass.get(1).setPlaceholder("Old and new match");
								fieldPass.get(1).setPhColor(Color.RED);
								fieldPass.get(1).setText("");
							} else {
								String updateUserQuery = "UPDATE users SET password = '"
										+ HashManager.generateHash(new String(fieldPass.get(1).getPassword())) + "'"
										+ " WHERE login = '" + UserInformation.getLogin() + "'";
								database.update(updateUserQuery);

								UserInformation.setPassword(HashManager.generateHash(new String(fieldPass.get(1).getPassword())));

								for (int i = 0; i < fieldPass.size(); i++) {
									fieldPass.get(i).setText("");
									fieldPass.get(i).setPlaceholder("Success");
									fieldPass.get(i).setPhColor(Color.green);
								}
							}
						} else {
							fieldPass.get(1).setPlaceholder("Password does't match");
							fieldPass.get(1).setPhColor(Color.RED);
							fieldPass.get(1).setText("");

							fieldPass.get(2).setPlaceholder("Password does't match");
							fieldPass.get(2).setPhColor(Color.RED);
							fieldPass.get(2).setText("");
						}
					} else {
						fieldPass.get(0).setText("");
						fieldPass.get(0).setPlaceholder("Incorrect password");
						fieldPass.get(0).setPhColor(Color.RED);
					}
				}

				revalidate();
				repaint();
			}
		});
		mainBox.add(Box.createHorizontalStrut(60));
		mainBox.add(confirm);

		add(mainBox);
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(new Color(237, 237, 237));
		g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 30, 25);

		Image image = AssetsRetriever.retrieveAssetImageFromResources("assets/background/page.png");
		g.drawImage(image, 50, 250, this);
	}
}
