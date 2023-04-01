package com.github.donkeyrit.twinkle.panels.Content;

import java.awt.Color;

import javax.swing.JPanel;

import com.github.donkeyrit.twinkle.EntryPoint;

public class ContentCompositePanel extends JPanel
{

    private EntryPoint entryPoint;

    public ContentCompositePanel(EntryPoint entryPoint)
    {
        this.entryPoint = entryPoint;
        setBackground(new Color(255,255,255)); 
        setLayout(null); 

        showContent();
    }

    private void showContent(){
        JPanel header = entryPoint.new HeaderPanel(); 
        header.setBounds(0,0,875,80); 
        add(header); 
        
        JPanel filter = entryPoint.new FilterPanel(); 
        filter.setBounds(30, 100, 200, 550); 
        add(filter); 
        
        JPanel content = entryPoint.new ContentPanel(""); 
        content.setBounds(250,100,605,550); 
        add(content); 
    }
}
