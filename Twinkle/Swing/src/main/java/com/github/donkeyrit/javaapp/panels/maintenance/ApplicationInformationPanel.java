package com.github.donkeyrit.javaapp.panels.maintenance;

import com.github.donkeyrit.javaapp.components.MyTableModel;
import com.github.donkeyrit.javaapp.container.ServiceContainer;
import com.github.donkeyrit.javaapp.database.DatabaseProvider;
import com.github.donkeyrit.javaapp.panels.CustomPanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ApplicationInformationPanel extends CustomPanel {

    private Box databaseNamesBox;
    private JScrollPane scrollPane;
    List<JToggleButton> listOfAvailableTables;

    public ApplicationInformationPanel() {
        setLayout(null);

        initialize();

        add(scrollPane);
    }

    private void initialize() {

        ServiceContainer serviceContainer = ServiceContainer.getInstance();
        DatabaseProvider database = serviceContainer.getDatabaseProvider();

        ArrayList<String> tableName = database.getTableNames();
        listOfAvailableTables = new ArrayList<>();


        databaseNamesBox = Box.createVerticalBox();
        databaseNamesBox.add(Box.createVerticalStrut(10));

        scrollPane = new JScrollPane(databaseNamesBox);

        TitledBorder title = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), "title");
        title.setTitleJustification(TitledBorder.CENTER);

        scrollPane.setBorder(title);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBounds(10, 10, 300, 500);

        for (String s : tableName) {
            JToggleButton specificTableName = new JToggleButton(s);
            specificTableName.setAlignmentX(Component.CENTER_ALIGNMENT);

            databaseNamesBox.add(specificTableName);
            databaseNamesBox.add(Box.createVerticalStrut(10));

            listOfAvailableTables.add(specificTableName);
            specificTableName.addActionListener(e -> {
                JToggleButton selectedJToggleButton = (JToggleButton) e.getSource();
                remove(scrollPane);

                JTable table = new JTable(new MyTableModel(database, selectedJToggleButton.getText()));
                JScrollPane pane = new JScrollPane(table);
                pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
                pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                pane.setBounds(170, 20, 400, 300);
                add(pane);

                revalidate();
                repaint();
            });
        }
    }

    @Override
    public Rectangle getBoundsRectangle() {
        return new Rectangle(250, 100, 605, 550);
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(new Color(237, 237, 237));
        g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(), 30, 25);
    }
}
