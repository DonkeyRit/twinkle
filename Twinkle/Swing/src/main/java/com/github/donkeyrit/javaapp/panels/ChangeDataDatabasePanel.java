package com.github.donkeyrit.javaapp.panels;

import com.github.donkeyrit.javaapp.EntryPoint;
import com.github.donkeyrit.javaapp.components.MyTableModel;
import com.github.donkeyrit.javaapp.container.ServiceContainer;
import com.github.donkeyrit.javaapp.database.DatabaseProvider;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

public class ChangeDataDatabasePanel extends JPanel {

    public ChangeDataDatabasePanel() {
        setLayout(null);

        ServiceContainer serviceContainer = ServiceContainer.getInstance();
        DatabaseProvider database = serviceContainer.getDatabaseProvider();

        ArrayList<String> tableName = database.getTableNames();
        ArrayList<JToggleButton> listTogBut = new ArrayList<>();

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

        for (String s : tableName) {
            JToggleButton tmp1 = new JToggleButton(s);
            tmp1.setAlignmentX(Component.CENTER_ALIGNMENT);
            databaseNamesBox.add(tmp1);
            databaseNamesBox.add(Box.createVerticalStrut(10));
            listTogBut.add(tmp1);
            tmp1.addActionListener(e -> {
                JToggleButton selectedJToggleButton = (JToggleButton) e.getSource();
                System.out.println(selectedJToggleButton.getParent().getParent().getParent().getParent().getClass());
                Component[] massive = selectedJToggleButton.getParent().getParent().getParent().getParent().getComponents();
                for (JToggleButton jToggleButton : listTogBut) {
                    if (!jToggleButton.getText().equals(selectedJToggleButton.getText())) {
                        jToggleButton.setSelected(false);
                    }
                }
                for (Component component : massive) {
                    System.out.println(component.getClass().toString());
                    JScrollPane tP = (JScrollPane) component;
                    if (!tP.getBorder().equals(title)) {
                        remove(component);
                    }
                }

                JTable table = new JTable(new MyTableModel(database, selectedJToggleButton.getText()));
                Dimension d = table.getPreferredSize();
                JScrollPane pane = new JScrollPane(table);
                pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
                pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
                pane.setBounds(170, 20, 400, 300);
                add(pane);

                revalidate();
                repaint();
            });
        }
        add(scrollPane);
    }

    @Override
    public void paintComponent(Graphics g){
        g.setColor(new Color(237,237,237));
        g.fillRoundRect(0,0,this.getWidth(),this.getHeight(),30,25);
    }
}
