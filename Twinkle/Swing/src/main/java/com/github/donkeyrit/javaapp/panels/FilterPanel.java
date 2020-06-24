package com.github.donkeyrit.javaapp.panels;

import com.github.donkeyrit.javaapp.EntryPoint;
import com.github.donkeyrit.javaapp.container.ServiceContainer;
import com.github.donkeyrit.javaapp.database.DatabaseProvider;
import com.github.donkeyrit.javaapp.panels.content.ContentPanel;
import com.github.donkeyrit.javaapp.ui.MainPanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FilterPanel extends JPanel {

    private final DatabaseProvider database;
    private final MainPanel panel;

    public FilterPanel(){
        setLayout(null);

        ServiceContainer serviceContainer = ServiceContainer.getInstance();
        database = serviceContainer.getDatabaseProvider();
        panel = serviceContainer.getUiManager().getMainPanel();

        JLabel mainLabel = new JLabel("Применить фильтр");
        Font font = new Font("Arial", Font.BOLD, 13);
        mainLabel.setFont(font);
        mainLabel.setBounds(40, 10, 140, 20);
        add(mainLabel);

        ResultSet markSet = database.select("SELECT DISTINCT(markName) FROM mark");
        ArrayList<String> markList = new ArrayList<>();
        markList.add(0,"All marks");
        try {
            while(markSet.next()){
                markList.add(markSet.getString("markName"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        String[] markMas = markList.toArray(new String[markList.size()]);

        JComboBox markCombo = new JComboBox(markMas);
        markCombo.setBounds(10, 40, 180, 20);
        JComboBox modelCombo = new JComboBox(new String[]{"All models"});
        modelCombo.setBounds(10, 70, 180, 20);

        markCombo.addActionListener(e -> {
            JComboBox temp = (JComboBox) e.getSource();
            String markSelected = (String) temp.getSelectedItem();
            ArrayList<Integer> idMarkList = new ArrayList<Integer>();
            ArrayList<String> modelList = new ArrayList<String>();

            if(!markSelected.equals("All marks")){
                ResultSet idMarkSet = database.select("SELECT idMark FROM mark WHERE markName = '" + markSelected + "'");

                try {
                    while(idMarkSet.next()){
                        idMarkList.add(idMarkSet.getInt("idMark"));
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                String queryModel = "SELECT modelName FROM model WHERE idMark in (";
                for(int i = 0; i < idMarkList.size(); i++){
                    queryModel += idMarkList.get(i);
                    if(i == idMarkList.size() - 1){
                        queryModel += ")";
                    }else{
                        queryModel += ",";
                    }
                }

                ResultSet modelSet = database.select(queryModel);
                try{
                    while(modelSet.next()){
                        modelList.add(modelSet.getString("modelName"));
                    }
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
            }

            modelCombo.removeAllItems();
            modelCombo.addItem("All models");
            for(int i = 0; i < modelList.size(); i++){
                modelCombo.addItem(modelList.get(i));
            }
        });
        add(markCombo);
        add(modelCombo);

        ResultSet priceSet = database.select("SELECT MAX(cost) as price FROM car ORDER BY cost ");
        int d = 1;
        try{
            while(priceSet.next()){
                double m2 = priceSet.getDouble("price");
                int max = (int) m2;
                d = (int) (max / 10000);
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }

        JLabel label = new JLabel("Choose price:");
        label.setBounds(60,110,100,30);
        add(label);

        JSlider price = new JSlider(JSlider.HORIZONTAL, 0, 10 * d,0);
        price.setMajorTickSpacing(((10 * d) / 4));
        price.setMinorTickSpacing(((10 * d) / 8));
        price.setPaintTicks(true);
        price.setPaintLabels(true);
        price.setSnapToTicks(true);
        price.setBounds(10, 150, 180, 45);
        add(price);

        ResultSet bodyTypeSet = database.select("SELECT DISTINCT(bodyTypeName) FROM bodytype");
        ArrayList<String> bodyTypeList = new ArrayList<>();
        try {
            while(bodyTypeSet.next()){
                bodyTypeList.add(bodyTypeSet.getString("bodyTypeName"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        Box box = Box.createVerticalBox();
        ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
        for (String s : bodyTypeList) {
            JCheckBox temp = new JCheckBox(s);
            checkBoxes.add(temp);
            box.add(temp);
        }
        JScrollPane scrollPane = new JScrollPane(box);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(new TitledBorder("Типы кузова"));
        scrollPane.setBounds(10, 225, 180, 270);
        add(scrollPane);



        JButton applyFilter = new JButton("Apply");
        applyFilter.setBounds(50, 520, 100, 20);
        applyFilter.addActionListener(e -> {
            String resultCondition = "";

            String selectedMark = markCombo.getSelectedItem().toString();
            if(!selectedMark.equals("All marks")){
                resultCondition += "markName = " + "'" + selectedMark + "'";
            }else{
                resultCondition += "!";
            }

            resultCondition += ":";

            String selectedModel = modelCombo.getSelectedItem().toString();
            if(!selectedModel.equals("All models")){
                resultCondition += "modelName = " + "'" + selectedModel + "'";
            }else{
                resultCondition += "!";
            }

            resultCondition += ":";

            int selectedPrice = price.getValue();
            if(selectedPrice != 0){
                resultCondition += "cost < " + selectedPrice * 1000;
            }else{
                resultCondition += "!";
            }
            resultCondition += ":";

            StringBuilder selectedCheckBoxes = new StringBuilder();
            ArrayList<String> selectedCB = new ArrayList<>();
            for (JCheckBox checkBox : checkBoxes) {
                boolean isTrue = checkBox.isSelected();
                if (isTrue) {
                    selectedCB.add(checkBox.getText());
                }
            }

            if(selectedCB.size() == 0){
                resultCondition += "!";
            }else{
                selectedCheckBoxes.append("bodyTypeName IN (");
                for(int i = 0; i < selectedCB.size(); i++){
                    selectedCheckBoxes.append("'").append(selectedCB.get(i)).append("'");
                    if(i != selectedCB.size() - 1){
                        selectedCheckBoxes.append(",");
                    }
                }
                selectedCheckBoxes.append(")");
            }
            resultCondition += selectedCheckBoxes;

            String res  = resultCondition.replaceAll("!", "");
            String[] masive = res.split(":");
            StringBuilder resStr = new StringBuilder();
            for(int i = 0; i < masive.length; i++){
                if(!masive[i].equals("")){
                    resStr.append(masive[i]);
                    if(i != masive.length - 1) {
                        resStr.append(" AND ");
                    }
                }
            }

            Component[] mas = panel.getComponents();
            JPanel temp = null;
            for (Component ma : mas) {
                if (ma.getClass().toString().contains("ContentPanel") || ma.getClass().toString().contains("AboutCarPanel")) {
                    temp = (JPanel) ma;
                }
            }

            panel.remove(temp);
            JPanel content = new ContentPanel(resStr.toString());
            content.setBounds(250,100,605,550);
            panel.add(content);
            panel.revalidate();
            panel.repaint();
        });
        add(applyFilter);
    }

    @Override
    public void paintComponent(Graphics g){
        g.setColor(new Color(237,237,237));
        g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(),30,25);
    }
}
