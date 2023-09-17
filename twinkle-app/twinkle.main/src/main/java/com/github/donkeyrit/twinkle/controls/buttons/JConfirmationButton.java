package com.github.donkeyrit.twinkle.controls.buttons;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Font;


public class JConfirmationButton extends JButton 
{
    private Color backgroundColor;
    private Color foregroundColor;
    private Color hoverBackgroundColor;
    private Color hoverForegroundColor;

    public JConfirmationButton(String text) 
	{
        super(text);

        // Set default colors for light theme
        backgroundColor = Color.WHITE;
        foregroundColor = Color.BLACK;
        hoverBackgroundColor = Color.GRAY;
        hoverForegroundColor = Color.WHITE;

        // Add mouse listener to handle hover effect
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverBackgroundColor);
                setForeground(hoverForegroundColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(backgroundColor);
                setForeground(foregroundColor);
            }
        });

        // Set initial background and foreground colors
        setBackground(backgroundColor);
        setForeground(foregroundColor);

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setOpaque(true);

        // Remove default border and focus indication
        setBorderPainted(false);
        setFocusPainted(false);

        // Use a smaller font to achieve minimalistic look
        Font font = getFont();
        setFont(font.deriveFont(font.getSize2D() - 2f));
    }

    // Setter methods for colors to support dark theme
    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setForegroundColor(Color foregroundColor) {
        this.foregroundColor = foregroundColor;
    }

    public void setHoverBackgroundColor(Color hoverBackgroundColor) {
        this.hoverBackgroundColor = hoverBackgroundColor;
    }

    public void setHoverForegroundColor(Color hoverForegroundColor) {
        this.hoverForegroundColor = hoverForegroundColor;
    }
}

