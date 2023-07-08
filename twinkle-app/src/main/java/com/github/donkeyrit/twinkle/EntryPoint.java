package com.github.donkeyrit.twinkle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.border.*;
import javax.swing.table.*;
import org.hibernate.cfg.Configuration;

import com.github.donkeyrit.twinkle.bll.models.UserInformation;
import com.github.donkeyrit.twinkle.dal.repositories.CarBodyTypeRepositoryImpl;
import com.github.donkeyrit.twinkle.dal.repositories.MarkOfCarRepositoryImpl;
import com.github.donkeyrit.twinkle.dal.repositories.ModelOfCarRepositoryImpl;
import com.github.donkeyrit.twinkle.dal.repositories.UserRepositoryImpl;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.CarBodyTypeRepository;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.MarkOfCarRepository;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.ModelOfCarRepository;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.UserRepository;
import com.github.donkeyrit.twinkle.frame.MainFrame;
import com.github.donkeyrit.twinkle.panels.common.SwitchedPanel;
import com.github.donkeyrit.twinkle.panels.content.ContentCompositePanel;
import com.github.donkeyrit.twinkle.panels.content.ContentPanel;
import com.github.donkeyrit.twinkle.panels.login.LoginPanel;
import com.github.donkeyrit.twinkle.panels.navigation.NavigationPanel;
import com.github.donkeyrit.twinkle.panels.sidebar.SideBarFilterPanel;
import com.github.donkeyrit.twinkle.panels.signup.SignupPanel;
import com.github.donkeyrit.twinkle.security.HashManager;
import com.github.donkeyrit.twinkle.utils.AssetsRetriever;
import com.github.donkeyrit.twinkle.utils.Constants;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

/**
 *
 * @author Dima
 */
public class EntryPoint {
    
    // Repositories
    private final UserRepository userRepository; 
	private final MarkOfCarRepository markOfCarRepository;
	private final CarBodyTypeRepository carBodyTypeRepository;
	private final ModelOfCarRepository modelOfCarRepository;

    private MainFrame mainFrame;
    private JPanel panel; 
    private DataBase database;
    
    public static void main(String[] args){
        /**
         * Application start
         */
        System.out.println(HashManager.generateHash("qazxcftrew"));
        new EntryPoint().initGui();
    }

    public EntryPoint()
    {
        EntityManagerFactory sessionFactory = new Configuration()
            .configure()
            .buildSessionFactory();
        EntityManager session = sessionFactory.createEntityManager();

        this.userRepository = new UserRepositoryImpl(session);
		this.markOfCarRepository = new MarkOfCarRepositoryImpl(session);
		this.carBodyTypeRepository = new CarBodyTypeRepositoryImpl(session);
		this.modelOfCarRepository = new ModelOfCarRepositoryImpl(session);
    }
    
    private void initGui()
    {
        database = new DataBase(); 
        this.mainFrame = new MainFrame("Rent car", new SwitchedPanel());
		SwitchedPanel switchedPanel = this.mainFrame.getSwitchedPanel();

        LoginPanel loginPanel = new LoginPanel(userRepository, mainFrame);
        switchedPanel.addPanel(Constants.LOGIN_PANEL_KEY, loginPanel);

        SignupPanel sigupPanel = new SignupPanel(userRepository, mainFrame);
        switchedPanel.addPanel(Constants.SIGUP_PANEL_KEY, sigupPanel);

        ContentCompositePanel contentPanel = new ContentCompositePanel();
		contentPanel
			.setNavigationPanel(new NavigationPanel(mainFrame, contentPanel, this))
			.setSidebarPanel(new SideBarFilterPanel(this.modelOfCarRepository, this.markOfCarRepository, this.carBodyTypeRepository, database, contentPanel))
			.setContentPanel(new ContentPanel(contentPanel, database, ""));
        switchedPanel.addPanel(Constants.CONTENT_PANEL_KEY, contentPanel);
        panel = contentPanel;

		switchedPanel.showPanel(Constants.LOGIN_PANEL_KEY);
        this.mainFrame.setVisible(true);
    }
    
    public class ChooseActionPanel extends JPanel{
        public ChooseActionPanel(){
            setLayout(null);
            
            ArrayList<String> actions = new ArrayList<String>(); 
            actions.add("Change password");
            actions.add("Personal data");
            if(UserInformation.isRole()){ 
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
                            if(!(componentsPanel[i].getClass().toString().indexOf("EntryPoint$ChooseActionPanel") > -1 || componentsPanel[i].getClass().toString().indexOf("NavigationPanel") > -1 )){
                                panel.remove((JPanel) componentsPanel[i]); 
                            }
                        }
                        
                        String selectedTextButton = selectedTmp1.getText(); 
                        
                        JPanel rightPanel = null; 
                        if(selectedTextButton.equals("Change password")){
                            rightPanel = new ChangePasswordPanel();
                        }
                        if(selectedTextButton.equals("Personal data")){
                            rightPanel = new PrivateDataPanel();
                        }
                        if(selectedTextButton.equals("To change the data")){
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
                        if(HashManager.generateHash(fieldPass.get(0).getText()).equals(UserInformation.getPassword())){
                            if(fieldPass.get(1).getText().equals(fieldPass.get(2).getText())){
                                if(fieldPass.get(0).getText().equals(fieldPass.get(1).getText())){
                                    fieldPass.get(0).setPlaceholder("Old and new match"); 
                                    fieldPass.get(0).setPhColor(Color.RED); 
                                    fieldPass.get(0).setText("");
                                    
                                    fieldPass.get(1).setPlaceholder("Old and new match"); 
                                    fieldPass.get(1).setPhColor(Color.RED); 
                                    fieldPass.get(1).setText("");
                                }else{                                 
                                    String updateUserQuery = "UPDATE users SET password = '" + HashManager.generateHash(fieldPass.get(1).getText()) + "'" + " WHERE login = '" + UserInformation.getLogin() + "'";
                                    database.update(updateUserQuery);


                                    UserInformation.setPassword(HashManager.generateHash(fieldPass.get(1).getText()));
                                    
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
            
            Image image = AssetsRetriever.retrieveAssetImageFromResources("assets/background/page.png"); 
            g.drawImage(image,50,250,this); 
        }
    }
    
    private class PrivateDataPanel extends JPanel{

        public PrivateDataPanel() {
            setLayout(null);
            
            String queryUser = "SELECT first_name,second_name,middle_name,address,phone_number FROM client where id_user = (SELECT id_user FROMusersWHERE login = '" + UserInformation.getLogin() + "')"; 
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
                           createClient += "(SELECT id_user FROMusersWHERE login = '" + UserInformation.getLogin() + "'))";
                           
                           database.insert(createClient);
                           for(int i = 0 ; i < fieldText.size(); i++){
                               fieldText.get(i).setPlaceholder("Success");
                               fieldText.get(i).setText("");
                               fieldText.get(i).setPhColor(Color.green);
                           }
                       }else{
                           String[] columnNames = new String[]{"first_name","second_name","middle_name","address","phone_number"};
                           String updateClient = "UPDATE clients SET ";
                           for(int i = 0 ; i < fieldText.size(); i++){
                               updateClient += columnNames[i] + " = '" + fieldText.get(i).getText() +"'";
                               if(i != fieldText.size() - 1){
                                   updateClient +=",";
                               }
                           }
                           updateClient += " WHERE id_user = (SELECT id_user FROMusersWHERE login = '" + UserInformation.getLogin() + "')";
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
            
            Image image = AssetsRetriever.retrieveAssetImageFromResources("assets/background/fill_data.png"); 
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
