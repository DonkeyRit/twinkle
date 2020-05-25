package com.github.donkeyrit.javaapp.panels;

import com.github.donkeyrit.javaapp.EntryPoint;
import com.github.donkeyrit.javaapp.database.DatabaseProvider;
import com.github.donkeyrit.javaapp.listeners.NextBackListener;
import com.github.donkeyrit.javaapp.listeners.ScrollPageListener;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContentPanel extends JPanel {
    public int numOfPage = 1;
    public int startBut = 1;
    public String conditionPanel = "";

    private final JPanel panel;

    public ContentPanel(EntryPoint point, String condition, int ... args){
        setLayout(null);
        conditionPanel = condition;

        panel = point.panel;
        DatabaseProvider database = point.database;

        JLabel contentMainLabel = new JLabel("List of cars:");
        Font font = new Font("Arial", Font.BOLD, 13);
        contentMainLabel.setFont(font);
        contentMainLabel.setBounds(250,10,100,20);
        add(contentMainLabel);

        JButton reload = new JButton();
        reload.setBounds(568, 10,16,16);
        ImageIcon iconExit = new ImageIcon("assets/buttons/reload.png");
        reload.setIcon(iconExit);
        reload.setHorizontalTextPosition(SwingConstants.LEFT);
        reload.addActionListener(e -> {
            Component[] mas = panel.getComponents();
            JPanel temp = null;
            for (Component ma : mas) {
                if (ma.getClass().toString().contains("ContentPanel")) {
                    temp = (JPanel) ma;
                }
            }

            panel.remove(temp);
            JPanel content = new ContentPanel(point, "");
            content.setBounds(250,100,605,550);
            panel.add(content);
            panel.revalidate();
            panel.repaint();
        });
        add(reload);

        if(args.length != 0){
            numOfPage = args[0];
        }

        String conditionQuery = "";
        if(!condition.equals("")){
            conditionQuery = "WHERE " + condition;
        }

        String query = "SELECT idCar FROM\n" +
                "                (SELECT idCar,modelYear,info,cost,modelName,idBodyType,markName,nameCountry FROM\n" +
                "                (SELECT idCar,modelYear,image,info,cost,modelName,idBodyType,markName,idCountry FROM \n" +
                "                (SELECT idCar,modelYear,image,info,cost,modelName,idMark,idBodyType FROM car \n" +
                "                INNER JOIN model ON car.idModel = model.idModel) as join1\n" +
                "                INNER JOIN mark ON join1.idMark = mark.idMark) as join2\n" +
                "                INNER JOIN country ON join2.idCountry = country.idCountry) as join3\n" +
                "                INNER JOIN bodytype ON join3.idBodyType = bodytype.idBodyType " + conditionQuery + " ORDER BY modelYear DESC";

        ResultSet carsSet = database.select(query);
        ArrayList<Integer> carsList = new ArrayList<>();
        int numString = 0;
        try{
            while(carsSet.next()){
                carsList.add(carsSet.getInt("idCar"));
            }
            carsSet.last();
            numString = carsSet.getRow();
        }catch(SQLException ex){
            ex.printStackTrace();
        }

        int a  = numString - (numOfPage - 1) * 4;
        int size = 0;
        size = Math.min(a, 4);

        int start = (numOfPage - 1) * 4;
        int end = (numOfPage - 1) * 4 + size;

        ArrayList<JPanel> panelList = new ArrayList<>();
        for(int i = start,j = 0; i < end; i++,j++){
            JPanel temp = new CarPanel(point, carsList.get(i));
            temp.setBorder(new LineBorder(new Color(0,163,163), 4));
            temp.setBounds(20,40 + j * 120,565,100);
            add(temp);
        }

        int num = (int)(Math.ceil(numString / 4f));
        if(args.length > 1){
            startBut = args[1];
        }

        int m = startBut + 5;
        if(m > num){
            m = num + 1;
        }

        Box buttonBox = Box.createHorizontalBox();
        buttonBox.setBounds(205, 520, 400, 30);

        if(startBut > 5){
            JButton backBut = new JButton(new ImageIcon("assets/buttons/back.png"));
            backBut.addActionListener(new NextBackListener(point));


            buttonBox.add(backBut);
        }

        Font buttonFont = new Font("Arial", Font.ITALIC, 10);
        ArrayList<JButton> buttonsList = new ArrayList<>();
        for(int i = startBut; i < m; i++){
            JButton temp = new JButton(i + "");

            temp.setFont(buttonFont);
            temp.addActionListener(new ScrollPageListener(point));

            buttonBox.add(temp);
            buttonsList.add(temp);
        }

        if(m != (num + 1)){
            JButton nextBut = new JButton(new ImageIcon("assets/buttons/next.png"));
            nextBut.addActionListener(new NextBackListener(point));


            buttonBox.add(nextBut);
        }
        add(buttonBox);
    }

    @Override
    public void paintComponent(Graphics g){
        g.setColor(new Color(237,237,237));
        g.fillRoundRect(0,0,this.getWidth(),this.getHeight(),30,25);
    }
}