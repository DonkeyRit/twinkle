package com.github.donkeyrit.twinkle.controls.buttons;

import com.github.donkeyrit.twinkle.styles.Colors;
import javax.swing.BorderFactory;
import javax.swing.JButton;

public class JLinkButton extends JButton 
{
	public JLinkButton(String text, String panelKey)
	{
		super(text);

		setActionCommand(panelKey);
        setBackground(Colors.AUTHORIZATION_BACKGROUND_COLOR);
        setForeground(Colors.AUTHORIZATION_BUTTON_FOREGROUD_COLOR);
        setFocusPainted(false);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setOpaque(true);
        setBorderPainted(false);
	}
}
