package com.github.donkeyrit.twinkle.panels.settings;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

import com.github.donkeyrit.twinkle.DataBase;
import com.github.donkeyrit.twinkle.controls.MyTableModel;

public class ChangeDataDatabasePanel extends JPanel {

	public ChangeDataDatabasePanel(DataBase database) {
		setLayout(null);

		ArrayList<String> tableName = database.getTableNames();
		ArrayList<JToggleButton> listTogBut = new ArrayList<JToggleButton>();

		Box databaseNamesBox = Box.createVerticalBox();
		JScrollPane scrollPane = new JScrollPane(databaseNamesBox);

		TitledBorder title;
		title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "title");
		title.setTitleJustification(TitledBorder.CENTER);

		scrollPane.setBorder(title);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(10, 10, 140, 500);
		databaseNamesBox.add(Box.createVerticalStrut(10));

		for (int i = 0; i < tableName.size(); i++) {
			JToggleButton tmp1 = new JToggleButton(tableName.get(i));
			tmp1.setAlignmentX(Component.CENTER_ALIGNMENT);
			databaseNamesBox.add(tmp1);
			databaseNamesBox.add(Box.createVerticalStrut(10));
			listTogBut.add(tmp1);
			tmp1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					JToggleButton selectedJToggleButton = (JToggleButton) e.getSource();
					System.out
							.println(selectedJToggleButton.getParent().getParent().getParent().getParent().getClass());
					Component[] massive = (Component[]) selectedJToggleButton.getParent().getParent().getParent()
							.getParent().getComponents();
					for (int i = 0; i < listTogBut.size(); i++) {
						if (!listTogBut.get(i).getText().equals(selectedJToggleButton.getText())) {
							listTogBut.get(i).setSelected(false);
						}
					}
					for (int i = 0; i < massive.length; i++) {
						System.out.println(massive[i].getClass().toString());
						JScrollPane tP = (JScrollPane) massive[i];
						if (!tP.getBorder().equals(title)) {
							remove(massive[i]);
						}
					}

					JTable table = new JTable(new MyTableModel(database, selectedJToggleButton.getText()));
					JScrollPane pane = new JScrollPane(table);
					pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
					pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
					pane.setBounds(170, 20, 400, 300);
					add(pane);

					revalidate();
					repaint();
				}
			});
		}
		add(scrollPane);
	}

	@Override
	public void paintComponent(Graphics g) {
		g.setColor(new Color(237, 237, 237));
		g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 30, 25);
	}
}
