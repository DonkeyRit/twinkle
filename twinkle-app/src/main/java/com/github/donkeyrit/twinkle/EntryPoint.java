package com.github.donkeyrit.twinkle;

import java.security.MessageDigest;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.*;
import java.util.Date;
import javax.swing.border.*;
import javax.swing.table.*;
import javax.swing.text.*;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.github.donkeyrit.twinkle.utils.AssetsRetriever;

/**
 *
 * @author Dima
 */
public class EntryPoint {
    
    JFrame frame; 
    JPanel panel; 
    DataBase database; 
    private int avatarNumber = 0; 
    User user; 
    
    public static void main(String[] args){
        /**
         * Application start
         */
        testHibernate();
        System.out.println(new EntryPoint().sha1("qazxcftrew"));
        new EntryPoint().initGui();
    }

    private static void testHibernate()
    {
        SessionFactory sessionFactory = new Configuration()
            .configure()
            .buildSessionFactory();
        Session session = sessionFactory.openSession();

        com.github.donkeyrit.twinkle.dal.models.User user = new com.github.donkeyrit.twinkle.dal.models.User();
        user.setLogin("admin");
        String password = sha1("Welcome01!");
        user.setPassword(password);
        user.setRole(true);

        session.beginTransaction();
        session.persist(user);
        session.getTransaction().commit();

        session.close();
        sessionFactory.close();
    }
    
    private static String sha1(String input){
        /**
         * Method convert input string into
         * hash with method sha1-1
         */
        String res = "";
        try{
            MessageDigest mDigest = MessageDigest.getInstance("SHA1");
            byte[] result = mDigest.digest(input.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < result.length; i++) {
                sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
            }
            res = sb.toString();
        }catch(Exception ex){
            ex.printStackTrace();
        }
         
        return res;
    }
    
    private static String shielding(String input) {
        /**
         * Method escaped input string from special characters
         * which may protect from sql injection attasks
         */
        if (input == null || input.isEmpty()) {
            return "";
        }
        int len = input.length();
        final StringBuilder result = new StringBuilder(len + len / 4);
        final StringCharacterIterator iterator = new StringCharacterIterator(input);
        char ch = iterator.current();
        while (ch != CharacterIterator.DONE) {
            if (ch == '\n') {
                result.append("\\n");
            } else if (ch == '\r') {
                result.append("\\r");
            } else if (ch == '\'') {
                result.append("\\\'");
            } else if (ch == '"') {
                result.append("\\\"");
            } else {
                result.append(ch);
            }
            ch = iterator.next();
        }
        return result.toString();
    }
    
    private void initGui(){
        /**
         * Method configure initial state of panel
         */ 
        frame = new JFrame("Rent car");
        database = new DataBase(); 
        
        panel = new JPanel(); 
        panel.setBackground(new Color(255,255,255)); 
        panel.setLayout(null); 
       
        showAuthorization(); 

        frame.getContentPane().add(BorderLayout.CENTER,panel); 
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        frame.setSize(875,700); 
        frame.setResizable(false);
        frame.setVisible(true); 
    }
    
    private void showAuthorization(){ 
        JPanel enterPanel = new EnterPanel(); 
        enterPanel.setBounds(0,0,875,700); 
        panel.add(enterPanel); 
    }
    
    private void showContent(){
        JPanel header = new HeaderPanel(); 
        header.setBounds(0,0,875,80); 
        panel.add(header); 
        
        JPanel filter = new FilterPanel(); 
        filter.setBounds(30, 100, 200, 550); 
        panel.add(filter); 
        
        JPanel content = new ContentPanel(""); 
        content.setBounds(250,100,605,550); 
        panel.add(content); 
    }
    
    private class EnterPanel extends JPanel{ 
        
        public EnterPanel() {
            setLayout(null); 
            
            JCTextField login = new JCTextField(); 
            login.setPlaceholder("Enter login"); 
            login.setBounds(358, 240, 170, 30); 
            add(login); 
            
            JPaswordField password = new JPaswordField(); 
            password.setPlaceholder("Enter password"); 
            password.setBounds(358, 280, 170, 30); 
            add(password); 
            
            JButton butSignIn = new JButton("Sign in"); 
            butSignIn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boolean isOne = login.getText().isEmpty(); 
                    boolean isTwo = password.getText().isEmpty(); 
                    
                    if(isOne){ 
                        login.setPlaceholder("Please, enter login"); 
                        login.setPhColor(Color.RED); 
                    }
                    if(isTwo){ 
                        password.setPlaceholder("Please, enter password"); 
                        password.setPhColor(Color.RED);
                    }
                    
                    panel.revalidate(); 
                    panel.repaint(); 
                    
                    if(!isOne && !isTwo){ 
                        
                        
                        
                        String query = String.format("SELECT id_user as count, role FROM users WHERE login = '%s' AND password = '%s'",shielding(login.getText()),sha1(shielding(password.getText())));
                        ResultSet userSet = database.select(query); 
                        boolean isCheckUser = false; 
                        boolean roleUser = false; 
                        try{ 
                            int tempNum = 0; 
                            while(userSet.next()){ 
                                tempNum = userSet.getInt("count"); 
                                roleUser = userSet.getBoolean("role");
                            }
                            isCheckUser = (tempNum != 0)? true : false; 
                            
                        }catch(SQLException ex){ 
                            ex.printStackTrace();
                        }
                        
                        if(isCheckUser){ 
					
                            panel.removeAll(); 
                            panel.revalidate(); 
                            panel.repaint(); 
                            showContent(); 
                            
                            user = new User(shielding(login.getText()),sha1(password.getText()),roleUser);
                            
                        }else{ 
					
                            login.setPlaceholder("Incorrect login"); 
                            login.setPhColor(Color.RED); 
                            login.setText(""); 
						
                            password.setPlaceholder("Incorrect password"); 
                            password.setPhColor(Color.RED);
                            password.setText(""); 
                        }
                    }
                    revalidate(); 
                    repaint(); 
                }
            });
            butSignIn.setBounds(358,320,80,20); 
            add(butSignIn); 
            
            JButton butLogIn = new JButton("Log in"); 
            butLogIn.setBounds(448,320,80,20); 
            butLogIn.addActionListener(new ActionListener(){ 
                @Override
                public void actionPerformed(ActionEvent e) {
                    panel.removeAll(); 
                    panel.revalidate();
                    panel.repaint(); 
                    JPanel registrationPanel = new RegistrationPanel();
                    registrationPanel.setBounds(0, 0, 875, 700); 
                    panel.add(registrationPanel); 
                }
            });
            add(butLogIn); 
        }
        
        @Override
        public void paintComponent(Graphics g){
            Image image = AssetsRetriever.retrieveAssetFromResources("assets/background/enter.jpg"); 
            g.drawImage(image,0,0,this); 
            GradientPaint gp = new GradientPaint(0, 0, Color.RED,120, 120, Color.BLUE, true); 
            Graphics2D g2 = (Graphics2D)g;  
            g2.setPaint(gp); 
            g2.fillRoundRect(338, 230, 208, 130, 30, 15); 
				  
            int random = (int) (Math.random() * 6 + 1); 
            if(avatarNumber == 0){ 
                avatarNumber = random; 
            }		  
            Image avatar = new ImageIcon("assets/avatar/" + avatarNumber + ".png").getImage(); 
            g.drawImage(avatar,380,100,this); 
        }
        
    }
    
    private class RegistrationPanel extends JPanel{
        
        public RegistrationPanel() {
            setLayout(null); 
            
            JCTextField login = new JCTextField(); 
            login.setPlaceholder("Enter login"); 
            login.setBounds(358, 240, 170, 30); 
            add(login); 
				
            JPaswordField password = new JPaswordField(); 
            password.setPlaceholder("Enter password"); 
            password.setBounds(358, 280, 170, 30); 
            add(password); 
				
            JPaswordField rePassword = new JPaswordField(); 
            rePassword.setPlaceholder("Repeat password"); 
            rePassword.setBounds(358, 320, 170, 30); 
            add(rePassword); 
            
            JButton regButton = new JButton("Log in"); 
            regButton.setBounds(358, 360, 135, 30);
            regButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ArrayList<String> data = new ArrayList<String>(); 
                    boolean isOne = login.getText().isEmpty(); 
                    boolean isTwo = password.getText().isEmpty(); 
                    boolean isThree = rePassword.getText().isEmpty(); 
                    
                    if(isOne){
                        login.setPlaceholder("Please, enter login"); 
                        login.setPhColor(Color.RED); 
                    }
						
                    if(isTwo){
                        password.setPlaceholder("Please, enter password"); 
                        password.setPhColor(Color.RED); 
                    }
						
                    if(isThree){
                        rePassword.setPlaceholder("Please, repeat password"); 
                        rePassword.setPhColor(Color.RED); 
                    }
                    
                    if(!isOne && !isTwo && !isThree){ 
                        if(!password.getText().equals(rePassword.getText())){ 
                            
                            password.setPlaceholder("Password do not match"); 
                            password.setPhColor(Color.RED); 
                            password.setText(""); 
								
                            rePassword.setPlaceholder("Password do not match"); 
                            rePassword.setPhColor(Color.RED); 
                            rePassword.setText(""); 
                        }else{
                            data.add(shielding(login.getText())); 
                            String query = String.format("SELECT count(id_user) as count FROMusersWHERE login = '%s'",shielding(login.getText())); 
                            
                            ResultSet userSet = database.select(query); 
                            boolean isCheckUser = false; 
                            try{ 
                                int tempNum = 0; 
                                while(userSet.next()){ 
                                    tempNum = userSet.getInt("count"); 
                                }
                                isCheckUser = (tempNum != 0)? true : false; 
                            
                            }catch(SQLException ex){
                                ex.printStackTrace();
                            } 
                            
                            if(isCheckUser){ 
                                login.setPlaceholder("Login already exist"); 
                                login.setPhColor(Color.RED); 
                                login.setText("");
                            }else{
                                data.add(sha1(shielding(password.getText()))); 
                                user = new User(login.getText(),sha1(password.getText()),false); 
                                database.insert("INSERT INTO user(login,password,role) VALUES ('" + data.get(0) + "','" + data.get(1) + "',0)"); 
                                panel.removeAll(); 
                                panel.revalidate();
                                panel.repaint(); 
                                showContent(); 
                            }
                        }
                    }
                    
                    panel.revalidate(); 
                    panel.repaint(); 
                }
            });
            add(regButton); 
            
            JButton backButton = new JButton(); 
            backButton.setBounds(500, 360, 28, 30);
            ImageIcon icon = new ImageIcon("assets/buttons/return.png"); 
            backButton.setIcon(icon); 
            backButton.setHorizontalTextPosition(SwingConstants.LEFT);
            backButton.addActionListener(new ActionListener(){  
                @Override
                public void actionPerformed(ActionEvent e) {
                    panel.removeAll(); 
                    panel.revalidate();
                    panel.repaint(); 
                    showAuthorization(); 
                }
            });
            add(backButton); 
        }
        
        @Override
        public void paintComponent(Graphics g){
            Image image = AssetsRetriever.retrieveAssetFromResources("assets/background/enter.jpg"); 
            g.drawImage(image,0,0,this); 
            GradientPaint gp = new GradientPaint(0, 0, Color.RED,120, 120, Color.BLUE, true); 
            Graphics2D g2 = (Graphics2D)g;  
            g2.setPaint(gp); 
            g2.fillRoundRect(338, 230, 208, 180, 30, 15); 
				  
            int random = (int) (Math.random() * 6 + 1); 
            if(avatarNumber == 0){ 
                avatarNumber = random; 
            }
				  
            Image avatar = AssetsRetriever.retrieveAssetFromResources("assets/avatar/" + avatarNumber + ".png"); 
            g.drawImage(avatar,380,100,this); 
        }
    }
    
    private class HeaderPanel extends JPanel{       
        HeaderPanel(){
            this.setLayout(null);
            JButton logo = new JButton(); 
            logo.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    panel.removeAll(); 
                    panel.revalidate();
                    panel.repaint(); 
                    showContent(); 
                }
            });
            
            logo.setBounds(30, 10, 60, 60); 
            ImageIcon icon = new ImageIcon("assets/logo/logo.png"); 
            logo.setIcon(icon);  
            logo.setHorizontalTextPosition(SwingConstants.LEFT);
            logo.setBorderPainted(false); 
            logo.setFocusPainted(false);
            logo.setContentAreaFilled(false); 
            add(logo); 

            JButton avatar = new JButton(); 
            avatar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    panel.removeAll();
                    panel.revalidate();
                    panel.repaint();

                    JPanel headerPanel = new HeaderPanel();
                    headerPanel.setBounds(0,0,875,80);
                    panel.add(headerPanel);

                    JPanel chooseActionPanel = new ChooseActionPanel();
                    chooseActionPanel.setBounds(30, 100, 200, 550);
                    panel.add(chooseActionPanel);
                }
            });
            avatar.setBounds(725, 10, 60, 60); 
            ImageIcon iconAvatar = new ImageIcon("assets/avatar/mini_avatar/" + avatarNumber + ".png"); 
            avatar.setIcon(iconAvatar); 
            avatar.setHorizontalTextPosition(SwingConstants.LEFT);
            avatar.setBorderPainted(false);
            avatar.setFocusPainted(false);
            avatar.setContentAreaFilled(false);
            add(avatar);


            JButton exit = new JButton(); 
            exit.setBounds(795,10,60,60); 
            ImageIcon iconExit = new ImageIcon("assets/buttons/exit.png"); 
            exit.setIcon(iconExit); 
            exit.setHorizontalTextPosition(SwingConstants.LEFT);
            exit.setBorderPainted(false);
            exit.setFocusPainted(false);
            exit.setContentAreaFilled(false);
            exit.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    user = null;
                    panel.removeAll(); 
                    showAuthorization(); 
                    panel.revalidate();
                    panel.repaint(); 
                }    
            });
            add(exit);
        }
        
        @Override
        public void paintComponent(Graphics g){
            g.setColor(new Color(0,163,163)); 
            g.fillRect(0,0,this.getWidth(),this.getHeight()); 
        }
    }
    
    private class FilterPanel extends JPanel{ 
        FilterPanel(){
            setLayout(null); 
            
            JLabel mainLabel = new JLabel("Применить фильтр"); 
            Font font = new Font("Arial", Font.BOLD, 13); 
            mainLabel.setFont(font); 
            mainLabel.setBounds(40, 10, 140, 20); 
            add(mainLabel);
            
            ResultSet markSet = database.select("SELECT DISTINCT(mark_name) FROM mark"); 
            ArrayList<String> markList = new ArrayList<String>(); 
            markList.add(0,"All marks"); 
            try { 
                while(markSet.next()){
                    markList.add(markSet.getString("mark_name")); 
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            String[] markMas = markList.toArray(new String[markList.size()]); 
            
            JComboBox markCombo = new JComboBox(markMas); 
            markCombo.setBounds(10, 40, 180, 20); 
            JComboBox modelCombo = new JComboBox(new String[]{"All models"}); 
            modelCombo.setBounds(10, 70, 180, 20); 
            
            markCombo.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                    JComboBox temp = (JComboBox) e.getSource(); 
                    String markSelected = (String) temp.getSelectedItem(); 
                    ArrayList<Integer> idMarkList = new ArrayList<Integer>(); 
                    ArrayList<String> modelList = new ArrayList<String>(); 
                    
                    if(!markSelected.equals("All marks")){ 
                        ResultSet idMarkSet = database.select("SELECT id_mark FROM mark WHERE mark_name = '" + markSelected + "'"); 

                        try {
                            while(idMarkSet.next()){
                                idMarkList.add(idMarkSet.getInt("id_mark")); 
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }

                        String queryModel = "SELECT model_name FROM model WHERE id_mark in ("; 
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
                                modelList.add(modelSet.getString("model_name")); 
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
                }
            });
            add(markCombo); 
            add(modelCombo); 

            ResultSet priceSet = database.select("SELECT MAX(cost) as price FROM car"); 
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
            
            ResultSet bodyTypeSet = database.select("SELECT DISTINCT(body_type_name) FROM body_type"); 
            ArrayList<String> bodyTypeList = new ArrayList<String>(); 
            try {
                while(bodyTypeSet.next()){
                    bodyTypeList.add(bodyTypeSet.getString("body_type_name")); 
                }
            } catch (SQLException ex) {
                    ex.printStackTrace();
            }
            
            Box box = Box.createVerticalBox(); 
            ArrayList<JCheckBox> checkBoxes = new ArrayList<JCheckBox>(); 
            for(int i = 0; i < bodyTypeList.size(); i++){
                JCheckBox temp = new JCheckBox(bodyTypeList.get(i)); 
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
            applyFilter.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) { 
                    String resultCondition = ""; 
                    
                    String selectedMark = markCombo.getSelectedItem().toString(); 
                    if(!selectedMark.equals("All marks")){
                        resultCondition += "mark_name = " + "'" + selectedMark + "'";
                    }else{
                        resultCondition += "!";
                    }
                    
                    resultCondition += ":"; 
                    
                    String selectedModel = modelCombo.getSelectedItem().toString(); 
                    if(!selectedModel.equals("All models")){
                        resultCondition += "model_name = " + "'" + selectedModel + "'";
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
                    
                    String selectedCheckBoxes = ""; 
                    ArrayList<String> selectedCB = new ArrayList<String>();
                    for(int i = 0; i< checkBoxes.size(); i++){
                        boolean isTrue = checkBoxes.get(i).isSelected(); 
                        if(isTrue){
                            selectedCB.add(checkBoxes.get(i).getText());
                        } 
                    }
                    
                    if(selectedCB.size() == 0){
                        resultCondition += "!";
                    }else{
                        selectedCheckBoxes += "body_type_name IN (";
                        for(int i = 0; i < selectedCB.size(); i++){
                            selectedCheckBoxes += "'" + selectedCB.get(i) + "'";
                            if(i != selectedCB.size() - 1){
                                selectedCheckBoxes += ",";
                            }
                        }
                        selectedCheckBoxes += ")";
                    }
                    resultCondition += selectedCheckBoxes;
                    
                    String res  = resultCondition.replaceAll("!", ""); 
                    String[] masive = res.split(":"); 
                    String resStr = ""; 
                    for(int i = 0; i < masive.length; i++){ 
                        if(masive[i].equals("")){
                            continue; 
                        }else{
                            resStr += masive[i]; 
                            if(i != masive.length - 1){ 
                                resStr += " AND "; 
                            }
                        }
                    }
                    
                    Component[] mas = panel.getComponents(); 
                    JPanel temp = null; 
                    for(int i = 0; i < mas.length; i++){
                        if(mas[i].getClass().toString().indexOf("ContentPanel") != -1 || mas[i].getClass().toString().indexOf("AboutCarPanel") != -1 ){
                            temp = (JPanel) mas[i];
                        }
                    }

                    panel.remove(temp); 
                    JPanel content = new ContentPanel(resStr); 
                    content.setBounds(250,100,605,550); 
                    panel.add(content); 
                    panel.revalidate(); 
                    panel.repaint(); 
                }
            
            });
            add(applyFilter);
        }
        
        @Override
        public void paintComponent(Graphics g){
            g.setColor(new Color(237,237,237));
            g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(),30,25);
        }
    }
    
    private class ContentPanel extends JPanel{ 
        int numOfPage = 1; 
        int startBut = 1; 
        String conditionPanel = ""; 
        
        ContentPanel(String condition, int ... args){
            setLayout(null); 
            conditionPanel = condition; 
            
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
            reload.addActionListener(new ActionListener(){ 
                @Override
                public void actionPerformed(ActionEvent e) {
                    Component[] mas = panel.getComponents(); 
                    JPanel temp = null; 
                    for(int i = 0; i < mas.length; i++){
                        if(mas[i].getClass().toString().indexOf("ContentPanel") != -1){
                            temp = (JPanel) mas[i];
                        }
                    }
                    
                    panel.remove(temp); 
                    JPanel content = new ContentPanel(""); 
                    content.setBounds(250,100,605,550); 
                    panel.add(content); 
                    panel.revalidate(); 
                    panel.repaint(); 
                }
            });
            add(reload);
            
            if(args.length != 0){ 
                numOfPage = args[0]; 
            }
            
            String conditionQuery = ""; 
            if(!condition.equals("")){ 
                conditionQuery = "WHERE " + condition;
            }
            
            String query = "SELECT id_car FROM\n" +
"                (SELECT id_car,model_year,info,cost,model_name,id_body_type,mark_name,country_name FROM\n" +
"                (SELECT id_car,model_year,image,info,cost,model_name,id_body_type,mark_name,id_country FROM \n" +
"                (SELECT id_car,model_year,image,info,cost,model_name,id_mark,id_body_type FROM car \n" +
"                INNER JOIN model ON car.id_model = model.id_model) as join1\n" +
"                INNER JOIN mark ON join1.id_mark = mark.id_mark) as join2\n" +
"                INNER JOIN country ON join2.id_country = country.id_country) as join3\n" +
"                INNER JOIN body_type ON join3.id_body_type = body_type.id_body_type " + conditionQuery + " ORDER BY model_year DESC";

            ResultSet carsSet = database.select(query); 
            ArrayList<Integer> carsList = new ArrayList<Integer>(); 
            int numString = 0; 
            try{
                while(carsSet.next()){
                    carsList.add(carsSet.getInt("id_car")); 
                }
                carsSet.last(); 
                numString = carsSet.getRow(); 
            }catch(SQLException ex){
                ex.printStackTrace();
            }
            
            int a  = numString - (numOfPage - 1) * 4; 
            int size = 0; 
            if(a > 4){ 
                size = 4; 
            }else{ 
                size = a; 
            }
            
            int start = (numOfPage - 1) * 4; 
            int end = (numOfPage - 1) * 4 + size; 
            
            ArrayList<JPanel> panelList = new ArrayList<JPanel>(); 
            for(int i = start,j = 0; i < end; i++,j++){
                JPanel temp = new CarPanel(carsList.get(i)); 
                temp.setBorder(new LineBorder(new Color(0,163,163), 4)); 
                temp.setBounds(20,40 + j * 120,565,100); 
                add(temp); 
            }
            
            int num = (int)(Math.ceil(numString / 4f)); 
            if(args.length != 0 && args.length > 1){
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
                backBut.addActionListener(new NextBackListener()); 
                
                
                buttonBox.add(backBut); 
            }
            
            Font buttonFont = new Font("Arial", Font.ITALIC, 10); 
            ArrayList<JButton> buttonsList = new ArrayList<JButton>(); 
            for(int i = startBut; i < m; i++){
                JButton temp = new JButton(i + ""); 
                
                temp.setFont(buttonFont); 
                temp.addActionListener(new ScrollPageListener()); 
                
                buttonBox.add(temp); 
                buttonsList.add(temp); 
            }
            
            if(m != (num + 1)){ 
                JButton nextBut = new JButton(new ImageIcon("assets/buttons/next.png")); 
                nextBut.addActionListener(new NextBackListener()); 
                
                
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
    
    private class CarPanel extends JPanel{
        
        private int imagesNum; 
        private Date modelYear; 
        private Double cost;  
        private String modelName; 
        private String markName; 
        private String nameCountry; 
        private String info;  
        private String bodyTypeName; 
        private String status; 
        
        
        CarPanel(int num){
            
            
            ResultSet statusSet = database.select("SELECT * FROM renta WHERE id_car = " + num + " ORDER BY dataEnd, dataPlan DESC LIMIT 1"); 
            status = "open"; 
            try{
                while(statusSet.next()){
                    Date rentDate = statusSet.getDate("dataEnd"); 
                    if(rentDate == null){ 
                        status = "lock"; 
                    }
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
            
            imagesNum = num; 
            setLayout(null); 
            String query = "SELECT id_car,model_year,info,cost,model_name,mark_name,country_name,body_type_name FROM\n" +
                "(SELECT id_car,model_year,info,cost,model_name,id_body_type,mark_name,country_name FROM\n" +
                "(SELECT id_car,model_year,image,info,cost,model_name,id_body_type,mark_name,id_country FROM \n" +
                "(SELECT id_car,model_year,image,info,cost,model_name,id_mark,id_body_type FROM car \n" +
                "INNER JOIN model ON car.id_model = model.id_model) as join1\n" +
                "INNER JOIN mark ON join1.id_mark = mark.id_mark) as join2\n" +
                "INNER JOIN country ON join2.id_country = country.id_country) as join3\n" +
                "INNER JOIN body_type ON join3.id_body_type = body_type.id_body_type WHERE id_car = " + imagesNum; 
            
            ResultSet carSet = database.select(query);
            try{
                while(carSet.next()){ 
                    modelYear = carSet.getDate("model_year"); 
                    cost = carSet.getDouble("cost");
                    modelName = carSet.getString("model_name");
                    markName = carSet.getString("markName");
                    nameCountry = carSet.getString("country_name");
                    info = carSet.getString("info");
                    bodyTypeName = carSet.getString("body_type_name");
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }

            Font font = new Font("Arial", Font.BOLD, 13); 
            Font alterfont = new Font("Arial", Font.ITALIC, 13); 
            
            JLabel modelLab = new JLabel("Model:"); 
            modelLab.setBounds(200, 10, 60, 15); 
            modelLab.setFont(alterfont); 
            add(modelLab); 
            
            JLabel modelLabel = new JLabel(modelName); 
            modelLabel.setBounds(290, 10, 150, 15); 
            modelLabel.setFont(font); 
            add(modelLabel); 
            
            
            JLabel markLab = new JLabel("Mark:"); 
            markLab.setBounds(200, 30, 60, 15); 
            markLab.setFont(alterfont);
            add(markLab); 
            
            JLabel markLabel = new JLabel(markName); 
            markLabel.setBounds(290, 30, 150, 15); 
            markLabel.setFont(font);
            add(markLabel);
            
            JLabel bodyTypeLab = new JLabel("Type:"); 
            bodyTypeLab.setBounds(200, 50, 70, 15); 
            bodyTypeLab.setFont(alterfont);
            add(bodyTypeLab); 
            
            JLabel bodyTypeLabel = new JLabel(bodyTypeName); 
            bodyTypeLabel.setBounds(290, 50, 150, 15); 
            bodyTypeLabel.setFont(font);
            add(bodyTypeLabel);
            
            JButton moreButton = new JButton("More"); 
            moreButton.setBounds(200, 70, 100, 20);
            moreButton.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    Component[] mas = panel.getComponents(); 
                    ContentPanel temp = null; 
                    for(int i = 0; i < mas.length; i++){
                        if(mas[i].getClass().toString().indexOf("ContentPanel") != -1){ 
                            temp = (ContentPanel) mas[i]; 
                        }
                    }
                    AboutCarPanel newPanel = new AboutCarPanel(imagesNum,modelYear,cost,modelName,markName,nameCountry,info,bodyTypeName); 
                    newPanel.setFilter(temp.conditionPanel); 
                    newPanel.setNumPage(temp.numOfPage); 
                    newPanel.setStartBut(temp.startBut); 
                    newPanel.setBounds(250,100,605,550); 
                    panel.remove(temp); 
                    panel.add(newPanel); 
                    panel.revalidate(); 
                    panel.repaint(); 
                }    
            });
            add(moreButton); 
            
            
        }
        
        @Override
        public void paintComponent(Graphics g){
            Image image = AssetsRetriever.retrieveAssetFromResources("assets/cars/min/" + imagesNum + ".png"); 
            g.drawImage(image,10,10,this); 
            
            Image country = AssetsRetriever.retrieveAssetFromResources("assets/flags/" + nameCountry + ".png"); 
            g.drawImage(country,425,0,this);
            
            Image statusImage = AssetsRetriever.retrieveAssetFromResources("assets/status/" + status + ".png"); 
            g.drawImage(statusImage, 10, 10, this);
        }        
    }
    
    private class AboutCarPanel extends JPanel{ 
        private int imagesNum; 
        private Date modelYear; 
        private Double cost; 
        private String modelName; 
        private String markName; 
        private String nameCountry; 
        private String info; 
        private String bodyTypeName; 
        
        private String filter; 
        private int numPage; 
        private int startBut; 

        public void setFilter(String filter) { 
            this.filter = filter;
        }

        public void setNumPage(int numPage) {
            this.numPage = numPage;
        }

        public void setStartBut(int startBut) {
            this.startBut = startBut;
        }

        public String getFilter() {
            return filter;
        }

        public int getNumPage() {
            return numPage;
        }

        public int getStartBut() {
            return startBut;
        }
        
        public AboutCarPanel(int imagesNum, Date modelYear, Double cost, String modelName, String markName, String nameCountry, String info, String bodyTypeName) {
            this.imagesNum = imagesNum; 
            this.modelYear = modelYear;
            this.cost = cost;
            this.modelName = modelName;
            this.markName = markName;
            this.nameCountry = nameCountry;
            this.info = info;
            this.bodyTypeName = bodyTypeName;
            
            setLayout(null); 
             
            JTextArea textArea = new JTextArea(info); 
            textArea.setLineWrap(true); 
            JScrollPane scrollPane = new JScrollPane(textArea); 
            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); 
            scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); 
            scrollPane.setBounds(300, 290, 285, 190); 
            add(scrollPane); 
            
            ResultSet statusSet = database.select("SELECT * FROM renta WHERE id_car = " + imagesNum + " ORDER BY dataEnd, dataPlan DESC LIMIT 1"); 
            String statusStr = "Свободно"; 
            try{
                while(statusSet.next()){
                    Date rentDate = statusSet.getDate("dataEnd"); 
                    if(rentDate == null){ 
                        statusStr = "Busy"; 
                    }
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
            
            Font font = new Font("Arial", Font.BOLD, 13); 
            Font alterfont = new Font("Arial", Font.ITALIC, 13); 
            
            String[] massArgs = new String[]{"Model:",this.modelName,"Mark:",this.markName,"Year:",this.modelYear.toString(),"Type:",this.bodyTypeName,
            "Cost per day:", this.cost + "","Status:",statusStr}; 
            for(int i = 0; i < 12; i++){ 
                JLabel temp = new JLabel(massArgs[i]); 
                if(i % 2 == 0){ 
                    temp.setBounds(30,300 + (i / 2) * 30,80,20); 
                    temp.setFont(alterfont); 
                }else{ 
                    temp.setBounds(150, 300 + (i / 2) * 30, 120, 20);
                    temp.setFont(font);
                }
                
                add(temp); 
            }
            
            JButton reloadButton = new JButton(new ImageIcon("assets/buttons/reload.png")); 
            reloadButton.setBounds(550,0,16,16); 
            reloadButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Component[] mas = panel.getComponents(); 
                    AboutCarPanel temp = null; 
                    for(int i = 0; i < mas.length; i++){
                        if(mas[i].getClass().toString().indexOf("AboutCarPanel") != -1){ 
                            temp = (AboutCarPanel) mas[i]; 
                        }
                    }
                    AboutCarPanel newPanel = new AboutCarPanel(imagesNum,modelYear,cost,modelName,markName,nameCountry,info,bodyTypeName); 
                    newPanel.setFilter(temp.getFilter()); 
                    newPanel.setNumPage(temp.getNumPage()); 
                    newPanel.setStartBut(temp.getStartBut());
                    newPanel.setBounds(250,100,605,550); 
                    panel.remove(temp); 
                    panel.add(newPanel); 
                    panel.revalidate();
                    panel.repaint(); 
                }
            });
            add(reloadButton);
            
            JButton returnButton = new JButton(new ImageIcon("assets/buttons/return.png"));
            returnButton.setBounds(570,0,16,16);
            returnButton.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    
                    Component[] mas = panel.getComponents(); 
                    JPanel temp = null; 
                    for(int i = 0; i < mas.length; i++){
                        if(mas[i].getClass().toString().indexOf("AboutCarPanel") != -1 ){ 
                            temp = (JPanel) mas[i]; 
                        }
                    }
                    
                    panel.remove(temp); 
                    JPanel contentPanel = new ContentPanel(filter, numPage,startBut); 
                    contentPanel.setBounds(250,100,605,550); 
                    panel.add(contentPanel); 
                    panel.revalidate();
                    panel.repaint(); 
                } 
            });
            add(returnButton);
            
            JButton actionWithCarButton = new JButton("ACTION"); 
            actionWithCarButton.setBounds(300, 500, 150, 20); 
            if(statusStr.equals("Free")){ 
                actionWithCarButton.setText("Get rent"); 
                actionWithCarButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton tempBut = (JButton) e.getSource(); 
                        tempBut.setVisible(false); 
                        JPanel tempPanel = (JPanel) tempBut.getParent(); 
                        
                        Component[] mas = tempPanel.getComponents(); 
                        JScrollPane temp = null; 
                        for(int i = 0; i < mas.length; i++){
                            if(mas[i].getClass().toString().indexOf("JScrollPane") != -1){ 
                                temp = (JScrollPane) mas[i]; 
                            }
                        }
                        
                        tempPanel.remove(temp); 
                        
                        tempPanel.revalidate(); 
                        tempPanel.repaint(); 
                        
                        ArrayList<JTextField> fields = new ArrayList<>(); 
                        MaskFormatter formatter = null; 
                       
                        Box yearsStart = Box.createHorizontalBox(); 
                        for(int i = 0; i < 3; i++){ 
                            String form = "##"; 
                            if(i == 0){ 
                                form = "####";
                            }
                            try{
                                formatter = new MaskFormatter(form); 
                                formatter.setPlaceholderCharacter('0'); 
                            }catch(Exception exec){
                                exec.printStackTrace();
                            }
                            JFormattedTextField ssnField = new JFormattedTextField(formatter); 
                            ssnField.setHorizontalAlignment(JTextField.CENTER); 
                            formatter.setPlaceholderCharacter('0'); 
                            fields.add(ssnField); 
                            yearsStart.add(ssnField); 
                        }
                        yearsStart.setBounds(350,320,200,30); 
                        add(yearsStart); 
                        
                        Box yearsPlan = Box.createHorizontalBox(); 
                        for(int i = 0; i < 3; i++){ 
                            String form = "##"; 
                            if(i == 0){ 
                                form = "####";
                            }
                            try{
                                formatter = new MaskFormatter(form);
                                formatter.setPlaceholderCharacter('0');
                            }catch(Exception exec){
                                exec.printStackTrace();
                            }
                            JFormattedTextField tempField = new JFormattedTextField(formatter);
                            
                            tempField.setHorizontalAlignment(JTextField.CENTER);
                            fields.add(tempField);
                            yearsPlan.add(tempField);
                        }
                        yearsPlan.setBounds(350,400,200,30);
                        add(yearsPlan);
                        
                        JLabel startYearsLabel = new JLabel("Enter start rent date(гг.мм.дд)"); 
                        startYearsLabel.setBounds(330,290,300,30); 
                        add(startYearsLabel); 
                        
                        JLabel planYearsLabel = new JLabel("Enter end rent date(г.м.д)");
                        planYearsLabel.setBounds(330, 370, 300, 30);
                        add(planYearsLabel);
                        
                        JLabel planPriceLabel = new JLabel("Approximately cost: ");
                        planPriceLabel.setBounds(330, 440, 300, 30);
                        add(planPriceLabel);
                        
                        JButton countPrice = new JButton("Count");
                        Border borderButton = fields.get(0).getBorder(); 
                        countPrice.setBounds(340,480,120,30);
                        countPrice.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault()); 
                                calendar.setTime(new Date()); 
                                int currYear = calendar.get(Calendar.YEAR); 
                                int currMont = calendar.get(Calendar.MONTH); 
                                int currDay = calendar.get(Calendar.DATE); 
                                
                                ArrayList<Integer> yList = new ArrayList<>(); 
                                int count = 0; 
                                for(int i = 0; i < fields.size(); i++){
                                   if(!fields.get(i).getText().equals("0000")){ 
                                       if(!fields.get(i).getText().equals("00")){ 
                                           count++; 
                                           fields.get(i).setBorder(borderButton); 
                                       }else{ 
                                           fields.get(i).setBorder(new LineBorder(Color.RED, 4)); 
                                       }
                                   }else{ 
                                       fields.get(i).setBorder(new LineBorder(Color.RED, 4)); 
                                   }
                                }
                                if(count == fields.size()){ 
                                    int tempNum = Integer.parseInt(fields.get(0).getText()); 
                                    if(tempNum < currYear || tempNum > currYear + 2){  
                                        fields.get(0).setBorder(new LineBorder(Color.RED, 4)); 
                                    }else{
                                        fields.get(0).setBorder(borderButton); 
                                        yList.add(tempNum); 
                                    }
                                    
                                    tempNum = Integer.parseInt(fields.get(1).getText()); 
                                    if(tempNum < 0 || tempNum > 12){ 
                                        fields.get(1).setBorder(new LineBorder(Color.RED, 4)); 
                                    }else{
                                        if(Integer.parseInt(fields.get(0).getText()) == currYear && tempNum < currMont){
                                            fields.get(1).setBorder(new LineBorder(Color.RED, 4)); 
                                        }else{
                                            fields.get(1).setBorder(borderButton);
                                            yList.add(tempNum);
                                        }
                                    }
                                    
                                    tempNum = Integer.parseInt(fields.get(2).getText());
                                    if(tempNum < 0 || tempNum > 28){
                                        fields.get(2).setBorder(new LineBorder(Color.RED, 4));
                                    }else{
                                        if(Integer.parseInt(fields.get(0).getText()) == currYear && Integer.parseInt(fields.get(1).getText()) == currMont && tempNum < currDay){
                                            fields.get(2).setBorder(new LineBorder(Color.RED, 4));
                                        }else{
                                            fields.get(2).setBorder(borderButton);
                                            yList.add(tempNum);
                                        }
                                    }
   
                                    tempNum = Integer.parseInt(fields.get(3).getText());
                                    if(tempNum < currYear || tempNum > currYear + 4){
                                        fields.get(3).setBorder(new LineBorder(Color.RED, 4));
                                    }else{
                                        if(tempNum >= Integer.parseInt(fields.get(0).getText())){
                                            fields.get(3).setBorder(borderButton);
                                            yList.add(tempNum);
                                        }else{
                                            fields.get(3).setBorder(new LineBorder(Color.RED, 4));
                                        }
                                    }
                                    
                                    tempNum = Integer.parseInt(fields.get(4).getText());
                                    if(tempNum < 0 || tempNum > 12){
                                        fields.get(4).setBorder(new LineBorder(Color.RED, 4));
                                    }else{
                                        if(Integer.parseInt(fields.get(3).getText()) == Integer.parseInt(fields.get(0).getText())){ 
                                            if(tempNum >= Integer.parseInt(fields.get(1).getText())){
                                                fields.get(4).setBorder(borderButton);
                                                yList.add(tempNum);
                                            }else{
                                                fields.get(4).setBorder(new LineBorder(Color.RED, 4));
                                            }
                                        }else{
                                            if(Integer.parseInt(fields.get(3).getText()) > Integer.parseInt(fields.get(0).getText())){
                                                fields.get(4).setBorder(borderButton);
                                                yList.add(tempNum);
                                            }else{
                                                fields.get(4).setBorder(new LineBorder(Color.RED, 4));
                                            }
                                        }
                                    }
                                    
                                    tempNum = Integer.parseInt(fields.get(5).getText());
                                    if(tempNum < 0 || tempNum > 28){
                                        fields.get(5).setBorder(new LineBorder(Color.RED, 4));
                                    }else{
                                        if(Integer.parseInt(fields.get(3).getText()) == Integer.parseInt(fields.get(0).getText())){
                                            if(Integer.parseInt(fields.get(4).getText()) == Integer.parseInt(fields.get(1).getText())){
                                                if(tempNum > Integer.parseInt(fields.get(2).getText())){
                                                    fields.get(5).setBorder(borderButton);
                                                    yList.add(tempNum);
                                                }else{
                                                    fields.get(5).setBorder(new LineBorder(Color.RED, 4));
                                                }
                                            }else{
                                                if(Integer.parseInt(fields.get(4).getText()) > Integer.parseInt(fields.get(1).getText())){
                                                    fields.get(5).setBorder(borderButton);
                                                    yList.add(tempNum);
                                                }else{
                                                    fields.get(5).setBorder(new LineBorder(Color.RED, 4));
                                                }
                                            }
                                        }else{
                                            if(Integer.parseInt(fields.get(3).getText()) > Integer.parseInt(fields.get(0).getText())){
                                                fields.get(5).setBorder(borderButton);
                                                yList.add(tempNum);
                                            }else{
                                                fields.get(5).setBorder(new LineBorder(Color.RED, 4));
                                            }
                                        }
                                    }
                                }
                                
                                if(yList.size() == 6){ 
                                    Date startDateGetCar = new Date(yList.get(0),yList.get(1),yList.get(2));
                                    Date planDateReturnCar = new Date(yList.get(3),yList.get(4),yList.get(5));
                                    
                                    long difference = planDateReturnCar.getTime() - startDateGetCar.getTime();
                                    int days = (int) (difference/(24*60*60*1000));
                                    planPriceLabel.setText("Approximately cost: " + (days + 1) * cost + " на " + (days + 1));
                                    
                                    JButton buttonGetCar = new JButton("Get rent.");
                                    buttonGetCar.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            String checkQuery = "SELECT id_client FROM client INNER JOINusersON client.id_user = user.id_user WHERE login = " + "'" + user.getLogin() + "'";
                                            ResultSet checkClientSet = database.select(checkQuery); 
                                            int idClient = 0; 
                                            try{
                                                while(checkClientSet.next()){
                                                    idClient = checkClientSet.getInt("id_client");
                                                }
                                            }catch(SQLException ex){
                                                ex.printStackTrace();
                                            }
                                            
                                            if(idClient == 0){ 
                                                planPriceLabel.setText("Please, fill data");
                                            }else{
                                                
                                                String queryToDB = "SELECT * FROM renta where id_client = (SELECT id_client FROM client WHERE id_user = (SELECT id_user FROM users WHERE login = '" + user.getLogin() + "')) ORDER BY dataEnd, dataPlan DESC LIMIT 1";
                                                ResultSet checkRentaSet = database.select(queryToDB); 
                                                System.out.println(queryToDB);
                                                boolean isHaveRenta = false;
                                                try{
                                                    while(checkRentaSet.next()){
                                                        Date rentDate = checkRentaSet.getDate("dataEnd");
                                                        if(rentDate == null){ 
                                                            isHaveRenta = true;; 
                                                        }
                                                    }
                                                }catch(SQLException ex){
                                                    ex.printStackTrace();
                                                }
                                                
                                                if(isHaveRenta){
                                                    planPriceLabel.setText("You cannot take more than one car at a time");
                                                }else{
                                                    String insertRenta = "INSERT INTO renta(id_client,id_car,dataStart,dataPlan) VALUES (" + idClient + "," + imagesNum; 
                                               
                                                    String startDataIn = "'" + yList.get(0) + "-" + yList.get(1) + "-" + yList.get(2) + "'"; 
                                                    String planDataIn = "'" + yList.get(3) + "-" + yList.get(4) + "-" + yList.get(5) + "'"; 

                                                    insertRenta += "," + startDataIn + "," + planDataIn +")"; 

                                                    database.insert(insertRenta); 

                                                    JButton selecBut = (JButton) e.getSource(); 
                                                    JPanel selecPane = (JPanel) selecBut.getParent(); 

                                                    Component[] compons = selecPane.getComponents(); 
                                                    for(int i = 0; i < compons.length; i++){
                                                        if(compons[i].getClass().toString().indexOf("JButton") > -1){ 
                                                            JButton button = (JButton) compons[i];
                                                            if(button.getText().indexOf("Get rent") > -1 || button.getText().indexOf("Return") > -1 ){ 
                                                                selecPane.remove(button); 
                                                            }
                                                        }
                                                    }

                                                    selecPane.remove(startYearsLabel); 
                                                    
                                                    selecPane.remove(planPriceLabel);
                                                    selecPane.remove(planYearsLabel);
                                                    selecPane.remove(countPrice);
                                                    selecPane.remove(yearsPlan);
                                                    selecPane.remove(yearsStart);
                                                    selecPane.revalidate();
                                                    selecPane.repaint();

                                                    

                                                    JTextArea textArea = new JTextArea(info); 
                                                    textArea.setLineWrap(true);
                                                    JScrollPane scrollPane = new JScrollPane(textArea); 
                                                    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); 
                                                    scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); 
                                                    scrollPane.setBounds(300, 290, 285, 190); 
                                                    add(scrollPane); 
                                                    System.out.println(insertRenta);
                                                } 
                                            }
                                        }
                                    });
                                    buttonGetCar.setBounds(375,510,150,30);
                                    add(buttonGetCar);
                                }else{
                                    Component[] compons = tempPanel.getComponents();
                                    for(int i = 0; i < compons.length; i++){
                                        if(compons[i].getClass().toString().indexOf("JButton") > -1){
                                            JButton button = (JButton) compons[i];
                                            if(button.getText().indexOf("Get rent") > -1){
                                                tempPanel.remove(button);
                                            }
                                        }
                                        planPriceLabel.setText("Incorrect date: ");
                                    }
                                }
                                revalidate();
                                repaint();  
                            }
                        });
                        add(countPrice); 
                        
                        JButton back = new JButton("Return");
                        back.setBounds(460, 480, 100, 30);
                        back.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                JButton selecBut = (JButton) e.getSource();
                                JPanel selecPane = (JPanel) selecBut.getParent();
                                
                                Component[] compons = selecPane.getComponents();
                                for(int i = 0; i < compons.length; i++){
                                    if(compons[i].getClass().toString().indexOf("JButton") > -1){
                                        JButton button = (JButton) compons[i];
                                        if(button.getText().indexOf("Get rent") > -1){
                                            selecPane.remove(button);
                                        }
                                    }
                                }
                                
                                selecPane.remove(startYearsLabel);
                                selecPane.remove(back);
                                selecPane.remove(planPriceLabel);
                                selecPane.remove(planYearsLabel);
                                selecPane.remove(countPrice);
                                selecPane.remove(yearsPlan);
                                selecPane.remove(yearsStart);
                                selecPane.revalidate();
                                selecPane.repaint();
                             
                                tempBut.setVisible(true);
                                
                                JTextArea textArea = new JTextArea(info);
                                textArea.setLineWrap(true);
                                JScrollPane scrollPane = new JScrollPane(textArea); 
                                scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); 
                                scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); 
                                scrollPane.setBounds(300, 290, 285, 190); 
                                add(scrollPane); 
                            }
                        });
                        add(back);
                    }
                });
                add(actionWithCarButton); 
            }else{ 
                
                String queryToDatabase = "SELECT * FROM users WHERE id_user = (SELECT id_user FROM client WHERE id_client = (SELECT id_client FROm renta WHERE id_car = " + imagesNum + " ORDER BY dataEnd,dataPlan DESC LIMIT 1))"; 
                ResultSet checkUserSet = database.select(queryToDatabase); 
                boolean isTrue = false; 
                try{
                    while(checkUserSet.next()){
                        if(user.getLogin().equals(checkUserSet.getString("login")) && user.getPassword().equals(checkUserSet.getString("password"))){
                            isTrue = true; 
                        }
                    }
                }catch(SQLException ex){
                    ex.printStackTrace();
                }
                
                if(isTrue){ 
                    actionWithCarButton.setText("Return car"); 
                    actionWithCarButton.addActionListener(new ActionListener() {
  
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            JButton tempBut = (JButton) e.getSource(); 
                            JPanel tempPanel = (JPanel) tempBut.getParent(); 
                            tempPanel.remove(scrollPane);
                            tempPanel.remove(tempBut);
                            
                            Box box = Box.createVerticalBox(); 
                            String queryReturnCar = "SELECT * FROM injury"; 
                            ResultSet queryReturnCarSer = database.select(queryReturnCar);
                            ArrayList<String> injuryNames = new ArrayList<>(); 
                            try{
                                while(queryReturnCarSer.next()){
                                    injuryNames.add(queryReturnCarSer.getString("injury_name"));
                                }
                            }catch(SQLException ex){
                                ex.printStackTrace();
                            }
                            
                            ArrayList<JCheckBox> checkBoxes = new ArrayList<JCheckBox>(); 
                            for(int i = 0; i < injuryNames.size(); i++){
                                JCheckBox temp = new JCheckBox(injuryNames.get(i)); 
                                checkBoxes.add(temp); 
                                box.add(temp); 
                            }
                            box.setBorder(new TitledBorder("Types of damage")); 
                            box.setBounds(330,290,250,150);
                            tempPanel.add(box);
                            
                            JButton countButton = new JButton("Count");
                            countButton.addActionListener(new ActionListener(){
             
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    JButton selectedButton = (JButton) e.getSource();
                                    JPanel selecButPanel = (JPanel) selectedButton.getParent();
                                    
                                    Component[] listMas = selecButPanel.getComponents();
                                    for(int i = 0; i< listMas.length; i++){
                                        if(listMas[i].getClass().toString().indexOf("JLabel") > -1){
                                            JLabel label = (JLabel) listMas[i];
                                            if(label.getText().indexOf("Sum") > - 1){
                                                selecButPanel.remove(label);
                                            }
                                        }
                                    }
                                    
                                    
                                    
                                    JButton newReturnButton = new JButton("Return");
                                    newReturnButton.setBounds(330, 500, 120, 30);
                                    newReturnButton.addActionListener(new ActionListener() {
                    
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            String injuryForCar = "";
                                            for(int i = 0; i < checkBoxes.size(); i++){
                                                if(checkBoxes.get(i).isSelected()){
                                                    injuryForCar = checkBoxes.get(i).getText();
                                                }
                                            }
                                            Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
                                            calendar.setTime(new Date());
                                            int currYear = calendar.get(Calendar.YEAR); 
                                            int currMont = calendar.get(Calendar.MONTH); 
                                            int currDay = calendar.get(Calendar.DATE); 
                                            
                                            String dataStr = currYear + "-" + currMont + "-" + currDay;
                                            String updateQuery = "UPDATE renta SET dataEnd = '" + dataStr + "' WHERE id_car = " + imagesNum + " AND id_client = (SELECT id_client FROM client INNER JOIN users ON client.id_user = user.id_user WHERE login = '" + user.getLogin() + "');"; 
                                            database.update(updateQuery);
                                            
                                            String idRentaStr = "SELECT id_rent FROM renta WHERE id_car = " + imagesNum + " AND id_client = (SELECT id_client FROM client INNER JOINusersON client.id_user = user.id_user WHERE login = '" + user.getLogin() + "') AND dataEnd = '" + dataStr + "'";
                                            ResultSet rentaSet = database.select(idRentaStr);
                                            int idRentaNum = 0;
                                            try{
                                                while(rentaSet.next()){
                                                    idRentaNum = rentaSet.getInt("id_rent");
                                                }
                                            }catch(SQLException ex){
                                                ex.printStackTrace();
                                            }
                                            
                                            String idInjuryStr = "SELECT id_injury FROM injury WHERE injury_name = '" + injuryForCar +"'";
                                            ResultSet injurySet = database.select(idInjuryStr);
                                            int idInjuryNum = 0;
                                            try{
                                                while(injurySet.next()){
                                                    idInjuryNum = injurySet.getInt("id_injury");
                                                }
                                            }catch(SQLException ex){
                                                ex.printStackTrace();
                                            }
                                            
                                            database.insert("INSERT INTO  resultinginjury(id_rent,id_injury) VALUES(" + idRentaNum + "," + idInjuryNum + ")");
                                            
                                            remove(box);
                                            remove(newReturnButton);
                                            remove(countButton);
                                            Component[] listMas = selecButPanel.getComponents();
                                            for(int i = 0; i< listMas.length; i++){
                                                if(listMas[i].getClass().toString().indexOf("JLabel") > -1){
                                                    JLabel label = (JLabel) listMas[i];
                                                    if(label.getText().indexOf("Sum") > - 1){
                                                        remove(label);
                                                    }
                                                }
                                            }
                                            JTextArea textArea = new JTextArea(info);
                                            textArea.setLineWrap(true);
                                            JScrollPane scrollPane = new JScrollPane(textArea); 
                                            scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); 
                                            scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); 
                                            scrollPane.setBounds(300, 290, 285, 190); 
                                            add(scrollPane); 
                                            revalidate();
                                            repaint();
                                        }
                                    });
                                    tempPanel.add(newReturnButton);
                                    
                                    
                                    Calendar calendar = Calendar.getInstance(TimeZone.getDefault(), Locale.getDefault());
                                    calendar.setTime(new Date());
                                    int currYear = calendar.get(Calendar.YEAR); 
                                    int currMont = calendar.get(Calendar.MONTH); 
                                    int currDay = calendar.get(Calendar.DATE); 
                                    
                                    String queryToDb = "SELECT login,id_car,join1.id_user,dataStart,dataPlan,dataEnd FROM\n" +
                                    "(SELECT id_car,id_user,renta.id_client,dataStart,dataPlan,dataEnd FROM renta INNER JOIN client ON renta.id_client = client.id_client) as join1\n" +
                                    "INNER JOINusersON join1.id_user = user.id_user WHERE login = '" + user.getLogin() + "' AND id_car = " + imagesNum + " ORDER BY dataEnd,dataPlan DESC LIMIT 1;"; 
                                    
                                    ResultSet queryToDbSet = database.select(queryToDb); 
                                    Date startRentaDate = null;
                                    Date dataRentaPlan = null;
                                    try{
                                        while(queryToDbSet.next()){
                                            startRentaDate = queryToDbSet.getDate("dataStart");
                                            dataRentaPlan = queryToDbSet.getDate("dataPlan");
                                        }
                                    }catch(SQLException ex){
                                        ex.printStackTrace();
                                    }
                                    
                                    Date currentGetData = new Date(currYear,currMont,currDay); 
                                    startRentaDate = new Date(currYear,currMont+1,currDay);
                                    
                                    JLabel labelCostRenta = new JLabel("5000");
                                    labelCostRenta.setBounds(480, 460, 120, 30);

                                    if(currentGetData.after(startRentaDate)){
                                        labelCostRenta.setText("Sum = 0");
                                    }else{
                                        double costForTheRent = 0f; 
                                        
                                        long difference = startRentaDate.getTime() - currentGetData.getTime();
                                        int days = (int) (difference/(24*60*60*1000));
                                        costForTheRent = (days + 1f) * cost;
                                        
                                        
                                        if(currentGetData.after(dataRentaPlan)){
                                            long diff = currentGetData.getTime() - dataRentaPlan.getTime();
                                            int overDay = (int) (difference/(24*60*60*1000));
                                            costForTheRent += (cost * overDay) * 0.2;
                                        }
                                        
                                        for(int i = 0; i < checkBoxes.size(); i++){
                                            if(checkBoxes.get(i).isSelected()){
                                                costForTheRent += (i+1) * 50000;
                                            }
                                        }
                                        
                                        labelCostRenta.setText("Sum " + costForTheRent + "");
                                    }
                                    
                                    tempPanel.add(labelCostRenta);
                                    
                                    tempPanel.revalidate();
                                    tempPanel.repaint();
                                }
                                
                            });
                            countButton.setBounds(330, 460, 120, 30);
                            tempPanel.add(countButton);
                            
                            tempPanel.revalidate();
                            tempPanel.repaint();
                        }
                    });
                    add(actionWithCarButton); 
                }
            }
        }
        
        @Override
        public void paintComponent(Graphics g){
            g.setColor(new Color(237,237,237)); 
            g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(),30,25); 
            Image image = AssetsRetriever.retrieveAssetFromResources("assets/cars/" + imagesNum + ".png"); 
            g.drawImage(image,30,10,this); 
            g.setColor(new Color(255,255,255));
            g.fillRect(20, 290, 260, 190);
            
        }
    }
    
    private class ChooseActionPanel extends JPanel{
        ChooseActionPanel(){
            setLayout(null);
            
            ArrayList<String> actions = new ArrayList<String>(); 
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
            
            
            ArrayList<JToggleButton> buttonActions = new ArrayList<JToggleButton>(); 
            for(int i = 0; i < actions.size(); i++){
                JToggleButton tmp1 = new JToggleButton(actions.get(i)); 
                tmp1.setBounds(10, 50 + 40 * i, 180, 30);
                tmp1.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JToggleButton selectedTmp1 = (JToggleButton) e.getSource(); 
                        for(int i = 0; i < buttonActions.size(); i++){
                            if(!buttonActions.get(i).getText().equals(selectedTmp1.getText())){
                                buttonActions.get(i).setSelected(false); 
                            }
                        }
                        
                        Component[] componentsPanel = (Component[]) panel.getComponents();
                        for(int i = 0; i < componentsPanel.length; i++){
                            if(!(componentsPanel[i].getClass().toString().indexOf("ChooseActionPanel") > -1 || componentsPanel[i].getClass().toString().indexOf("HeaderPanel") > -1 )){
                                panel.remove((JPanel) componentsPanel[i]); 
                            }
                        }
                        
                        String selectedTextButton = selectedTmp1.getText(); 
                        
                        JPanel rightPanel = null; 
                        if(selectedTextButton.equals("Сменить пароль")){
                            rightPanel = new ChangePasswordPanel();
                        }
                        if(selectedTextButton.equals("Личные данные")){
                            rightPanel = new PrivateDataPanel();
                        }
                        if(selectedTextButton.equals("Изменить данные")){
                            rightPanel = new ChangeDataDatabasePanel();
                        }
                        rightPanel.setBounds(250,100,605,550);
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
        public void paintComponent(Graphics g){
            g.setColor(new Color(237,237,237));
            g.fillRoundRect(0, 0, this.getWidth(), this.getHeight(),30,25);
        }
    }
     
    private class ChangePasswordPanel extends JPanel{

        public ChangePasswordPanel() {
            setLayout(null);
            
            Box mainBox = Box.createVerticalBox();
            mainBox.setBorder(new TitledBorder("Change Password"));
            mainBox.setBounds(202,10, 200, 200);
            
            String[] labels = new String[]{"Enter old pasword","Enter new password","Repeat new password"};
            ArrayList<JPaswordField> fieldPass = new ArrayList<JPaswordField>();
            for(int i = 0; i < labels.length; i++){
                JPaswordField passw = new JPaswordField();
                passw.setPlaceholder(labels[i]); 
                fieldPass.add(passw); 
                mainBox.add(passw); 
                mainBox.add(Box.createVerticalStrut(10)); 
            }
            
            JButton confirm = new JButton("Confirm");
            confirm.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boolean isOne = fieldPass.get(0).getText().isEmpty(); 
                    boolean isTwo = fieldPass.get(1).getText().isEmpty(); 
                    boolean isThree = fieldPass.get(2).getText().isEmpty(); 
                    
                    if(isOne){
                        fieldPass.get(0).setPlaceholder("Please, enter old password"); 
                        fieldPass.get(0).setPhColor(Color.RED); 
                    }
						
                    if(isTwo){
                        fieldPass.get(1).setPlaceholder("Please, enter new password"); 
                        fieldPass.get(1).setPhColor(Color.RED); 
                    }
						
                    if(isThree){
                        fieldPass.get(2).setPlaceholder("Please, repeat new password"); 
                        fieldPass.get(2).setPhColor(Color.RED); 
                    }
                    
                    if(!isOne && !isTwo && !isThree){ 
                        if(sha1(fieldPass.get(0).getText()).equals(user.getPassword())){
                            if(fieldPass.get(1).getText().equals(fieldPass.get(2).getText())){
                                if(fieldPass.get(0).getText().equals(fieldPass.get(1).getText())){
                                    fieldPass.get(0).setPlaceholder("Old and new match"); 
                                    fieldPass.get(0).setPhColor(Color.RED); 
                                    fieldPass.get(0).setText("");
                                    
                                    fieldPass.get(1).setPlaceholder("Old and new match"); 
                                    fieldPass.get(1).setPhColor(Color.RED); 
                                    fieldPass.get(1).setText("");
                                }else{                                 
                                    String updateUserQuery = "UPDATEusersSET password = '" + sha1(fieldPass.get(1).getText()) + "'" + " WHERE login = '" + user.getLogin() + "'";
                                    database.update(updateUserQuery);
                                    user.setPassword(sha1(fieldPass.get(1).getText()));
                                    
                                    for(int i = 0; i < fieldPass.size(); i++){
                                        fieldPass.get(i).setText("");
                                        fieldPass.get(i).setPlaceholder("Success");
                                        fieldPass.get(i).setPhColor(Color.green);
                                    }
                                }
                            }else{
                                fieldPass.get(1).setPlaceholder("Password does't match"); 
                                fieldPass.get(1).setPhColor(Color.RED); 
                                fieldPass.get(1).setText("");
                                
                                fieldPass.get(2).setPlaceholder("Password does't match"); 
                                fieldPass.get(2).setPhColor(Color.RED); 
                                fieldPass.get(2).setText("");
                            }
                        }else{
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
        public void paintComponent(Graphics g){
            g.setColor(new Color(237,237,237)); 
            g.fillRoundRect(0,0,this.getWidth(),this.getHeight(),30,25); 
            
            Image image = AssetsRetriever.retrieveAssetFromResources("assets/background/page.png"); 
            g.drawImage(image,50,250,this); 
        }
    }
    
    private class PrivateDataPanel extends JPanel{

        public PrivateDataPanel() {
            setLayout(null);
            
            String queryUser = "SELECT first_name,second_name,middle_name,address,phone_number FROM client where id_user = (SELECT id_user FROMusersWHERE login = '" + user.getLogin() + "')"; 
            ResultSet userSet = database.select(queryUser);
            ArrayList<String> infoUser = new ArrayList<String>(); 
            try{
                int numRows = userSet.getMetaData().getColumnCount(); 
                while(userSet.next()){
                    for(int i = 0; i < numRows; i++){
                        infoUser.add(userSet.getString(i+1));
                    }
                }
            }catch(SQLException ex){
                ex.printStackTrace();
            }
            
            Box box = Box.createVerticalBox(); 
            box.setBounds(202,10, 200, 250);
            box.setBorder(new TitledBorder("Personal information"));
            
            String[] placeholders = new String[]{"Enter first_name","Enter second_name","Enter middle_name","Enter address","Enter phone_number"};
            ArrayList<JCTextField> fieldText = new ArrayList<JCTextField>();
            for(int i = 0; i < placeholders.length; i++){
                JCTextField tempField = new JCTextField(); 
                tempField.setPlaceholder(placeholders[i]); 
                fieldText.add(tempField);
                box.add(tempField);
                box.add(Box.createVerticalStrut(10)); 
            }
            if(infoUser.size() == fieldText.size()){
                for(int i = 0; i < fieldText.size(); i++){
                    fieldText.get(i).setText(infoUser.get(i));
                }
            }
            
            JButton confirm = new JButton("Confirm");
            confirm.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   int counter = 0; 
                   ArrayList<String> inputData = new ArrayList<String>();
                   for(int i = 0; i < fieldText.size(); i++){
                       if(!fieldText.get(i).getText().isEmpty()){
                           counter++;
                           inputData.add(fieldText.get(i).getText());
                       }
                   }
                   
                   if(counter == fieldText.size()){
                       if(infoUser.size() == 0){
                           String createClient = "INSERT INTO client(first_name,second_name,middle_name,address,phone_number,id_user) VALUES (";
                           for(int i = 0; i < fieldText.size(); i++){
                               createClient += "'" + fieldText.get(i).getText() + "',";
                           }
                           createClient += "(SELECT id_user FROMusersWHERE login = '" + user.getLogin() + "'))";
                           
                           database.insert(createClient);
                           for(int i = 0 ; i < fieldText.size(); i++){
                               fieldText.get(i).setPlaceholder("Success");
                               fieldText.get(i).setText("");
                               fieldText.get(i).setPhColor(Color.green);
                           }
                       }else{
                           String[] columnNames = new String[]{"first_name","second_name","middle_name","address","phone_number"};
                           String updateClient = "UPDATE client SET ";
                           for(int i = 0 ; i < fieldText.size(); i++){
                               updateClient += columnNames[i] + " = '" + fieldText.get(i).getText() +"'";
                               if(i != fieldText.size() - 1){
                                   updateClient +=",";
                               }
                           }
                           updateClient += " WHERE id_user = (SELECT id_user FROMusersWHERE login = '" + user.getLogin() + "')";
                           database.update(updateClient);
                       }
                   }else{
                      for(int i = 0; i < fieldText.size(); i++){
                          String previousText = fieldText.get(i).getPlaceholder().substring(fieldText.get(i).getPlaceholder().indexOf("Please") + 7);
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
        public void paintComponent(Graphics g){
            g.setColor(new Color(237,237,237)); 
            g.fillRoundRect(0,0,this.getWidth(),this.getHeight(),30,25); 
            
            Image image = AssetsRetriever.retrieveAssetFromResources("assets/background/fill_data.png"); 
            g.drawImage(image,50,310,this); 
        }
    }
    
    private class ChangeDataDatabasePanel extends JPanel{


        public ChangeDataDatabasePanel() {
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
            
            for(int i = 0; i < tableName.size(); i++){
                JToggleButton tmp1 = new JToggleButton(tableName.get(i));
                tmp1.setAlignmentX(Component.CENTER_ALIGNMENT);
                databaseNamesBox.add(tmp1);
                databaseNamesBox.add(Box.createVerticalStrut(10));
                listTogBut.add(tmp1);
                tmp1.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JToggleButton selectedJToggleButton = (JToggleButton) e.getSource();
                        System.out.println(selectedJToggleButton.getParent().getParent().getParent().getParent().getClass());
                        Component[] massive = (Component[]) selectedJToggleButton.getParent().getParent().getParent().getParent().getComponents();
                        for(int i = 0; i < listTogBut.size(); i++){
                            if(!listTogBut.get(i).getText().equals(selectedJToggleButton.getText())){
                                listTogBut.get(i).setSelected(false);
                            }
                        }
                        for(int i = 0; i < massive.length; i++){
                            System.out.println(massive[i].getClass().toString());
                            JScrollPane tP = (JScrollPane) massive[i];
                            if(!tP.getBorder().equals(title)){
                                remove(massive[i]);
                            }
                        }
                        
                        JTable table = new JTable(new MyTableModel(selectedJToggleButton.getText()));
                        Dimension d = table.getPreferredSize();
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
        public void paintComponent(Graphics g){
            g.setColor(new Color(237,237,237)); 
            g.fillRoundRect(0,0,this.getWidth(),this.getHeight(),30,25); 
        }
    }
    
    private class ScrollPageListener implements ActionListener{ 

        @Override
        public void actionPerformed(ActionEvent e) {
            
            JButton pressButton = (JButton) e.getSource();
            String textButton = pressButton.getText(); 
            int numPage = Integer.parseInt(textButton); 
            
            Component[] mas = panel.getComponents(); 
            ContentPanel temp = null; 
            for(int i = 0; i < mas.length; i++){
                if(mas[i].getClass().toString().indexOf("ContentPanel") != -1){
                    temp = (ContentPanel) mas[i];
                }
            }
            
            panel.remove(temp); 
            JPanel content = new ContentPanel(temp.conditionPanel,numPage,temp.startBut); 
            content.setBounds(250,100,605,550); 
            panel.add(content); 
            panel.revalidate(); 
            panel.repaint(); 
        }
        
    }
    
    private class NextBackListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton selectedBut = (JButton) e.getSource(); 
            ContentPanel outerPanel = (ContentPanel) selectedBut.getParent().getParent();
            int numNowPage = outerPanel.startBut; 
            
            String nameBut = selectedBut.getIcon().toString();
            int indexName = nameBut.lastIndexOf("/");
            
            panel.remove(outerPanel);
            
            JPanel addPanel = null;
            if(nameBut.substring(indexName + 1, indexName + 5).equals("next")){
                addPanel = new ContentPanel(outerPanel.conditionPanel,numNowPage + 5,numNowPage + 5);
                panel.add(addPanel);
            }else{
               addPanel = new ContentPanel(outerPanel.conditionPanel,numNowPage - 5,numNowPage - 5);
                panel.add(addPanel);
            }
            addPanel.setBounds(250,100,605,550);
            panel.revalidate();
            panel.repaint();
        }
        
    }
    
    private class MyTableModel extends AbstractTableModel{

        private String tableName;
        private String query;
        private ResultSet rs;

        public MyTableModel(String name) {
            tableName = name;
            query = "SELECT * FROM " + tableName;
            rs = database.select(query);
        }


        @Override
        public int getRowCount() {
            int size = 0;
            try{
                rs.last();
                size = rs.getRow();
                rs.beforeFirst();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
            return size;
        }

        @Override
        public int getColumnCount() {
            int sizeRows = 0;
            try{
                sizeRows = rs.getMetaData().getColumnCount();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
            return sizeRows;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            ArrayList<ArrayList<Object>> allRows = new ArrayList<ArrayList<Object>>();
            try{
                while(rs.next()){
                    ArrayList<Object> oneRow = new ArrayList<Object>();
                    int sizeRows = rs.getMetaData().getColumnCount();
                    for(int i = 1; i < sizeRows+1; i++){
                        oneRow.add(rs.getObject(i));
                    }
                    allRows.add(oneRow);
                }
                rs.beforeFirst();
            }catch(SQLException ex){
                ex.printStackTrace();
            }
            return allRows.get(rowIndex).get(columnIndex);
        }
        
            @Override
        public String getColumnName(int c) {
            ArrayList<String> names = database.getNameRows(tableName);
            return names.get(c);
        }

    }
}
