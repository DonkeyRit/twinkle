package com.github.donkeyrit.twinkle;

import com.github.donkeyrit.twinkle.panels.content.ContentCompositePanel;
import com.github.donkeyrit.twinkle.panels.authentication.SignupPanel;
import com.github.donkeyrit.twinkle.panels.authentication.LoginPanel;
import com.github.donkeyrit.twinkle.panels.common.SwitchedPanel;
import com.github.donkeyrit.twinkle.panels.ioc.SwingUiModules;
import com.github.donkeyrit.twinkle.dal.ioc.PersistanceModules;
import com.github.donkeyrit.twinkle.bll.ioc.ServicesModules;
import com.github.donkeyrit.twinkle.security.HashManager;
import com.github.donkeyrit.twinkle.frame.MainFrame;
import com.github.donkeyrit.twinkle.utils.Constants;

import com.google.inject.Injector;
import com.google.inject.Guice;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class EntryPoint 
{
    
    // Repositories
    
    public static void main(String[] args)
	{
        /**
         * Application start
         */
        System.out.println(HashManager.generateHash("qazxcftrew"));
        new EntryPoint().initGui();
    }
    
    private void initGui()
    {
		// Services
		Injector injector = Guice.createInjector(
			new SwingUiModules(),
			new ServicesModules(),
			new PersistanceModules()
		);

		MainFrame mainFrame = injector.getInstance(MainFrame.class);
		// Services
		
		Logger logger = LoggerFactory.getLogger(EntryPoint.class);
		logger.info("Start application....");
		
		SwitchedPanel switchedPanel = mainFrame.getSwitchedPanel();
		
		LoginPanel loginPanel = injector.getInstance(LoginPanel.class);
        switchedPanel.addPanel(Constants.LOGIN_PANEL_KEY, loginPanel);

        SignupPanel sigupPanel = injector.getInstance(SignupPanel.class);
        switchedPanel.addPanel(Constants.SIGUP_PANEL_KEY, sigupPanel);

        ContentCompositePanel contentPanel = injector.getInstance(ContentCompositePanel.class);
        switchedPanel.addPanel(Constants.CONTENT_PANEL_KEY, contentPanel);

		switchedPanel.showPanel(Constants.LOGIN_PANEL_KEY);
        mainFrame.setVisible(true);
    }
}
