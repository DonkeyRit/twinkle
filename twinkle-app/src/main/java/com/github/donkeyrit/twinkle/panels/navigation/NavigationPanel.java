package com.github.donkeyrit.twinkle.panels.navigation;

import com.github.donkeyrit.twinkle.listeners.PanelSwitcherActionListener;
import com.github.donkeyrit.twinkle.panels.content.ContentCompositePanel;
import com.github.donkeyrit.twinkle.panels.navigation.listeners.GoToSettingsPageActionListener;
import com.github.donkeyrit.twinkle.utils.AssetsRetriever;
import com.github.donkeyrit.twinkle.utils.Constants;
import com.github.donkeyrit.twinkle.EntryPoint;
import com.github.donkeyrit.twinkle.frame.MainFrame;
import com.google.inject.Inject;

import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class NavigationPanel extends JPanel
{
	private final ContentCompositePanel container;
	private final MainFrame mainFrame;
	private final EntryPoint entryPoint; //TODO: Remove this

	@Inject
    public NavigationPanel(MainFrame mainFrame, ContentCompositePanel container, EntryPoint entryPoint)
    {
		this.container = container;
		this.mainFrame = mainFrame;
		this.entryPoint = entryPoint;
        this.setUp();
    }

	private void setUp()
	{
		this.setLayout(null);

		PanelSwitcherActionListener panelSwitcheractionListener = new PanelSwitcherActionListener(this.mainFrame.getSwitchedPanel());

		// Icon button
        JButton logo = new JButton(); 
		logo.setActionCommand(Constants.CONTENT_PANEL_KEY);
        logo.setBounds(30, 10, 60, 60); 
        ImageIcon icon = AssetsRetriever.retrieveAssetImageIconFromResources("assets/logo/logo.png"); 
        logo.setIcon(icon);  
        logo.setHorizontalTextPosition(SwingConstants.LEFT);
        logo.setBorderPainted(false); 
        logo.setFocusPainted(false);
        logo.setContentAreaFilled(false); 
        logo.addActionListener(panelSwitcheractionListener);

        add(logo); 

		// User 
        JButton avatar = new JButton(); 
        avatar.addActionListener(new GoToSettingsPageActionListener(entryPoint, container));
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
		logoutButton.setActionCommand(Constants.LOGIN_PANEL_KEY);
        ImageIcon iconExit = AssetsRetriever.retrieveAssetImageIconFromResources("assets/buttons/exit.png"); 
        logoutButton.setIcon(iconExit); 
        logoutButton.setHorizontalTextPosition(SwingConstants.LEFT);
        logoutButton.setBorderPainted(false);
        logoutButton.setFocusPainted(false);
        logoutButton.setContentAreaFilled(false);
        logoutButton.addActionListener(panelSwitcheractionListener);
        add(logoutButton);
	}
}

