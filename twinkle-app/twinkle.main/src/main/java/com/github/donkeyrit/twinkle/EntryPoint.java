package com.github.donkeyrit.twinkle;

import com.github.donkeyrit.twinkle.panels.authentication.LoginPanel;
import com.github.donkeyrit.twinkle.panels.common.SwitchedPanel;
import com.github.donkeyrit.twinkle.panels.ioc.SwingUiModules;
import com.github.donkeyrit.twinkle.dal.ioc.HibernatePersistanceModules;
import com.github.donkeyrit.twinkle.dal.jooq.ioc.JooqPersistanceModules;
import com.github.donkeyrit.twinkle.auth.ioc.AuthenticationModules;
import com.github.donkeyrit.twinkle.bll.ioc.ServicesModules;
import com.github.donkeyrit.twinkle.security.HashManager;
import com.github.donkeyrit.twinkle.frame.MainFrame;
import com.github.donkeyrit.twinkle.ioc.MainModules;
import com.github.donkeyrit.twinkle.utils.Constants;

import com.google.inject.Injector;
import com.google.inject.Guice;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.slf4j.Logger;

public class EntryPoint 
{
    public static void main(String[] args) throws IOException
	{
        /**
         * Application start
         */
        System.out.println(HashManager.generateHash("qazxcftrew"));
        new EntryPoint().initGui();
    }
    
    private void initGui() throws IOException
    {
		Properties properties = loadProperties("application.properties");
		com.google.inject.Module persistanceModule = getPersistanceModule(properties);

		// Services
		Injector injector = Guice.createInjector(
			new MainModules(),
			new SwingUiModules(),
			new ServicesModules(),
			persistanceModule,
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

	/**
	 * TODO: Duplicated logic
	 */
	public static Properties loadProperties(String filename) throws IOException {
        Properties properties = new Properties();
        try (InputStream input = EntryPoint.class.getClassLoader().getResourceAsStream(filename)) {
            if (input == null) {
                throw new IOException("Unable to find " + filename);
            }
            properties.load(input);
        }
        return properties;
    }

	public static com.google.inject.Module getPersistanceModule(Properties properties) throws IOException {
		
        String persistenceStrategy = properties.getProperty("persistence.strategy", "jooq");

        com.google.inject.Module module;
        if ("hibernate".equalsIgnoreCase(persistenceStrategy)) {
            module = new HibernatePersistanceModules();
        } else if ("jooq".equalsIgnoreCase(persistenceStrategy)) {
            module = new JooqPersistanceModules();
        } else {
            throw new IllegalArgumentException("Unsupported persistence strategy: " + persistenceStrategy);
        }

		return module;
	}
}
