package com.github.donkeyrit.javaapp;

import com.github.donkeyrit.javaapp.database.Database;
import com.github.donkeyrit.javaapp.model.User;
import com.github.donkeyrit.javaapp.panels.ContentPanel;
import com.github.donkeyrit.javaapp.panels.EnterPanel;
import com.github.donkeyrit.javaapp.panels.FilterPanel;
import com.github.donkeyrit.javaapp.panels.HeaderPanel;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Dima
 */
public class EntryPoint {
    
    public JFrame frame;
    public JPanel panel;
    public Database database;
    public int avatarNumber = 0;
    public User user;
    
    public static void main(String[] args){
        /**
         * Application start
         */
        new EntryPoint().initGui();
    }
    
    private void initGui(){
        /**
         * Method configure initial state of panel
         */ 
        frame = new JFrame("Rent car");
        database = new Database();
        
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
    
    public void showAuthorization(){
        JPanel enterPanel = new EnterPanel(this);
        enterPanel.setBounds(0,0,875,700); 
        panel.add(enterPanel); 
    }
    
    public void showContent(){
        JPanel header = new HeaderPanel(this);
        header.setBounds(0,0,875,80); 
        panel.add(header); 
        
        JPanel filter = new FilterPanel(this);
        filter.setBounds(30, 100, 200, 550); 
        panel.add(filter); 
        
        JPanel content = new ContentPanel(this, "");
        content.setBounds(250,100,605,550); 
        panel.add(content); 
    }
}