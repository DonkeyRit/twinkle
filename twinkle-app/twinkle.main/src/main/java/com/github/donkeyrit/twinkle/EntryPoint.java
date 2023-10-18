package com.github.donkeyrit.twinkle;

import com.github.donkeyrit.twinkle.panels.authentication.LoginPanel;
import com.github.donkeyrit.twinkle.panels.common.SwitchedPanel;
import com.github.donkeyrit.twinkle.panels.ioc.SwingUiModules;
import com.github.donkeyrit.twinkle.dal.ioc.PersistanceModules;
import com.github.donkeyrit.twinkle.auth.ioc.AuthenticationModules;
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
			new PersistanceModules(),
			new AuthenticationModules()
		);

		Logger logger = LoggerFactory.getLogger(EntryPoint.class);
		logger.info("Start application....");
		
		MainFrame mainFrame = injector.getInstance(MainFrame.class);
		SwitchedPanel switchedPanel = mainFrame.getSwitchedPanel();
		LoginPanel loginPanel = injector.getInstance(LoginPanel.class);
        switchedPanel.addPanel(Constants.LOGIN_PANEL_KEY, loginPanel);
		switchedPanel.showPanel(Constants.LOGIN_PANEL_KEY);
        mainFrame.setVisible(true);
    }
}
