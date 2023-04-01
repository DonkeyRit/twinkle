package com.github.donkeyrit.twinkle.frame;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame 
{
    private JPanel container;
    private CardLayout cardLayout;

    public MainFrame(String title) 
    {
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(875,700); 
        setResizable(false);
        setLocationRelativeTo(null);

        // Create container panel
        container = new JPanel();
        cardLayout = new CardLayout();
        container.setLayout(cardLayout);

        // Add container to frame
        add(container, BorderLayout.CENTER);
    }

    public void SetPanel(String key)
    {
        this.cardLayout.show(container, key);
    }

    public void addPanel(String key, JPanel panel)
    {
        container.add(panel, key);
    }
}

