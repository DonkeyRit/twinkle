package com.github.donkeyrit.javaapp;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

public class JCTextField extends JTextField{ 

    private Dimension d = new Dimension(200,32); 
    private String placeholder = "";
    private Color phColor= new Color(0,0,0);
    private boolean band = true;

    /** Constructor de clase */
    public JCTextField()
    {
        super(); 
        setSize(d); 
        setPreferredSize(d); 
        setVisible(true); 
        setMargin( new Insets(3,6,3,6)); 
        getDocument().addDocumentListener(new DocumentListener() {  

            @Override
            public void removeUpdate(DocumentEvent e) {
                band = (getText().length()>0) ? false:true ;
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                band = false;
            }

            @Override
            public void changedUpdate(DocumentEvent de) {}

        });
    }

    public void setPlaceholder(String placeholder)
    {
        this.placeholder=placeholder;
    }

    public String getPlaceholder()
    {
        return placeholder;
    }

    public Color getPhColor() {
        return phColor;
    }

    public void setPhColor(Color phColor) {
        this.phColor = phColor;
    }    

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor( new Color(phColor.getRed(),phColor.getGreen(),phColor.getBlue(),90));
        g.drawString((band)?placeholder:"",
                     getMargin().left,
                     (getSize().height)/2 + getFont().getSize()/2 );
      }

}
