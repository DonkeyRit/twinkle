package com.github.donkeyrit.twinkle.panels.settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.border.*;

import com.github.donkeyrit.twinkle.DataBase;
import com.github.donkeyrit.twinkle.bll.models.UserInformation;
import com.github.donkeyrit.twinkle.controls.JCTextField;
import com.github.donkeyrit.twinkle.utils.AssetsRetriever;

public class PrivateDataPanel extends JPanel {

	public PrivateDataPanel(DataBase database) {
		setLayout(null);

		String queryUser = "SELECT first_name,second_name,middle_name,address,phone_number FROM client where id_user = (SELECT id_user FROMusersWHERE login = '"
				+ UserInformation.getLogin() + "')";
		ResultSet userSet = database.select(queryUser);
		ArrayList<String> infoUser = new ArrayList<String>();
		try {
			int numRows = userSet.getMetaData().getColumnCount();
			while (userSet.next()) {
				for (int i = 0; i < numRows; i++) {
					infoUser.add(userSet.getString(i + 1));
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		}

		Box box = Box.createVerticalBox();
		box.setBounds(202, 10, 200, 250);
		box.setBorder(new TitledBorder("Personal information"));

		String[] placeholders = new String[] { "Enter first_name", "Enter second_name", "Enter middle_name",
				"Enter address", "Enter phone_number" };
		ArrayList<JCTextField> fieldText = new ArrayList<JCTextField>();
		for (int i = 0; i < placeholders.length; i++) {
			JCTextField tempField = new JCTextField();
			tempField.setPlaceholder(placeholders[i]);
			fieldText.add(tempField);
			box.add(tempField);
			box.add(Box.createVerticalStrut(10));
		}
		if (infoUser.size() == fieldText.size()) {
			for (int i = 0; i < fieldText.size(); i++) {
				fieldText.get(i).setText(infoUser.get(i));
			}
		}

		JButton confirm = new JButton("Confirm");
		confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int counter = 0;
				ArrayList<String> inputData = new ArrayList<String>();
				for (int i = 0; i < fieldText.size(); i++) {
					if (!fieldText.get(i).getText().isEmpty()) {
						counter++;
						inputData.add(fieldText.get(i).getText());
					}
				}

				if (counter == fieldText.size()) {
					if (infoUser.size() == 0) {
						String createClient = "INSERT INTO client(first_name,second_name,middle_name,address,phone_number,id_user) VALUES (";
						for (int i = 0; i < fieldText.size(); i++) {
							createClient += "'" + fieldText.get(i).getText() + "',";
						}
						createClient += "(SELECT id_user FROMusersWHERE login = '" + UserInformation.getLogin() + "'))";

						database.insert(createClient);
						for (int i = 0; i < fieldText.size(); i++) {
							fieldText.get(i).setPlaceholder("Success");
							fieldText.get(i).setText("");
							fieldText.get(i).setPhColor(Color.green);
						}
					} else {
						String[] columnNames = new String[] { "first_name", "second_name", "middle_name", "address",
								"phone_number" };
						String updateClient = "UPDATE clients SET ";
						for (int i = 0; i < fieldText.size(); i++) {
							updateClient += columnNames[i] + " = '" + fieldText.get(i).getText() + "'";
							if (i != fieldText.size() - 1) {
								updateClient += ",";
							}
						}
						updateClient += " WHERE id_user = (SELECT id_user FROMusersWHERE login = '"
								+ UserInformation.getLogin() + "')";
						database.update(updateClient);
					}
				} else {
					for (int i = 0; i < fieldText.size(); i++) {
						String previousText = fieldText.get(i).getPlaceholder()
								.substring(fieldText.get(i).getPlaceholder().indexOf("Please") + 7);
						fieldText.get(i).setPlaceholder("Please," + previousText);
						fieldText.get(i).setPhColor(Color.red);
					}
				}

				revalidate();
				repaint();
			}
		});
		box.add(Box.createHorizontalStrut(60));
		box.add(confirm);

		add(box);

		System.out.println(infoUser);

	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(new Color(237, 237, 237));
		g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 30, 25);

		Image image = AssetsRetriever.retrieveAssetImageFromResources("assets/background/fill_data.png");
		g.drawImage(image, 50, 310, this);
	}
}
