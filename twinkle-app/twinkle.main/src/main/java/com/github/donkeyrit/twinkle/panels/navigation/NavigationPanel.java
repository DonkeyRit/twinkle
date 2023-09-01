package com.github.donkeyrit.twinkle.panels.navigation;

import com.github.donkeyrit.twinkle.events.contracts.ContentEventsListener;
import com.github.donkeyrit.twinkle.events.contracts.LoginEventsListener;
import com.github.donkeyrit.twinkle.utils.AssetsRetriever;

import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import com.google.inject.Inject;

public class NavigationPanel extends JPanel
{
	private final LoginEventsListener loginEventsListener;
	private final ContentEventsListener contentEventsListener;

	@Inject
    public NavigationPanel(ContentEventsListener contentEventsListener, LoginEventsListener loginEventsListener)
    {
		this.contentEventsListener = contentEventsListener;
		this.loginEventsListener = loginEventsListener;

        this.setUp();
    }

	private void setUp()
	{
		this.setLayout(null);

		// Icon button
        JButton logo = new JButton(); 
        logo.setBounds(30, 10, 60, 60); 
        ImageIcon icon = AssetsRetriever.retrieveAssetImageIconFromResources("assets/logo/logo.png"); 
        logo.setIcon(icon);  
        logo.setHorizontalTextPosition(SwingConstants.LEFT);
        logo.setBorderPainted(false); 
        logo.setFocusPainted(false);
        logo.setContentAreaFilled(false); 
        logo.addActionListener(e -> this.contentEventsListener.onHomePageRequest());

        add(logo); 

		// User 
        JButton avatar = new JButton(); 
        avatar.addActionListener(e -> this.contentEventsListener.onSettingsPageRequest());
        avatar.setBounds(725, 10, 60, 60); 
		//TODO: Fix problem with avatarNumber
        ImageIcon iconAvatar = AssetsRetriever.retrieveAssetImageIconFromResources("assets/avatar/mini_avatar/" + 2 + ".png"); 
        avatar.setIcon(iconAvatar); 
        avatar.setHorizontalTextPosition(SwingConstants.LEFT);
        avatar.setBorderPainted(false);
        avatar.setFocusPainted(false);
        avatar.setContentAreaFilled(false);
        add(avatar);

		// Logout button
        JButton logoutButton = new JButton(); 
        logoutButton.setBounds(795,10,60,60); 
        ImageIcon iconExit = AssetsRetriever.retrieveAssetImageIconFromResources("assets/buttons/exit.png"); 
        logoutButton.setIcon(iconExit); 
        logoutButton.setHorizontalTextPosition(SwingConstants.LEFT);
        logoutButton.setBorderPainted(false);
        logoutButton.setFocusPainted(false);
        logoutButton.setContentAreaFilled(false);
        logoutButton.addActionListener(e -> this.loginEventsListener.onLoginRequest());
        add(logoutButton);
	}
}

