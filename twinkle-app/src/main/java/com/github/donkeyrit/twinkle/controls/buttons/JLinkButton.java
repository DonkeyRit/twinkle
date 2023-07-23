package com.github.donkeyrit.twinkle.controls.buttons;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import com.github.donkeyrit.twinkle.styles.Colors;
import com.github.donkeyrit.twinkle.utils.Constants;

public class JLinkButton extends JButton 
{
	public JLinkButton(String text)
	{
		super(text);

		setActionCommand(Constants.SIGUP_PANEL_KEY);
        setBackground(Colors.AUTHORIZATION_BACKGROUND_COLOR);
        setForeground(Colors.AUTHORIZATION_BUTTON_FOREGROUD_COLOR);
        setFocusPainted(false);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setOpaque(true);
        setBorderPainted(false);
	}
}
