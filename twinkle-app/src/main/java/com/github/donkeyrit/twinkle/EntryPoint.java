package com.github.donkeyrit.twinkle;

import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import com.github.donkeyrit.twinkle.dal.repositories.CarBodyTypeRepositoryImpl;
import com.github.donkeyrit.twinkle.dal.repositories.CarRepositoryImpl;
import com.github.donkeyrit.twinkle.dal.repositories.MarkOfCarRepositoryImpl;
import com.github.donkeyrit.twinkle.dal.repositories.ModelOfCarRepositoryImpl;
import com.github.donkeyrit.twinkle.dal.repositories.UserRepositoryImpl;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.CarBodyTypeRepository;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.CarRepository;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.MarkOfCarRepository;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.ModelOfCarRepository;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.UserRepository;
import com.github.donkeyrit.twinkle.frame.MainFrame;
import com.github.donkeyrit.twinkle.panels.common.SwitchedPanel;
import com.github.donkeyrit.twinkle.panels.content.ContentCompositePanel;
import com.github.donkeyrit.twinkle.panels.content.ContentPanel;
import com.github.donkeyrit.twinkle.panels.login.LoginPanel;
import com.github.donkeyrit.twinkle.panels.navigation.NavigationPanel;
import com.github.donkeyrit.twinkle.panels.sidebar.SideBarFilterPanel;
import com.github.donkeyrit.twinkle.panels.signup.SignupPanel;
import com.github.donkeyrit.twinkle.security.HashManager;
import com.github.donkeyrit.twinkle.utils.Constants;

public class EntryPoint 
{
    
    // Repositories
    private final UserRepository userRepository; 
	private final MarkOfCarRepository markOfCarRepository;
	private final CarBodyTypeRepository carBodyTypeRepository;
	private final ModelOfCarRepository modelOfCarRepository;
	private final CarRepository carRepository;

    private MainFrame mainFrame;
    private DataBase database;
    
    public static void main(String[] args)
	{
        /**
         * Application start
         */
        System.out.println(HashManager.generateHash("qazxcftrew"));
        new EntryPoint().initGui();
    }

    public EntryPoint()
    {
        EntityManagerFactory sessionFactory = new Configuration()
            .configure()
            .buildSessionFactory();
        EntityManager session = sessionFactory.createEntityManager();

		this.database = new DataBase();
        this.userRepository = new UserRepositoryImpl(session);
		this.markOfCarRepository = new MarkOfCarRepositoryImpl(session);
		this.carBodyTypeRepository = new CarBodyTypeRepositoryImpl(session);
		this.modelOfCarRepository = new ModelOfCarRepositoryImpl(session);
		this.carRepository = new CarRepositoryImpl(session);
    }
    
    private void initGui()
    {
		Logger logger = LoggerFactory.getLogger(EntryPoint.class);
		logger.info("Start application....");

        this.mainFrame = new MainFrame("Rent car", new SwitchedPanel());
		SwitchedPanel switchedPanel = this.mainFrame.getSwitchedPanel();

        LoginPanel loginPanel = new LoginPanel(userRepository, mainFrame);
        switchedPanel.addPanel(Constants.LOGIN_PANEL_KEY, loginPanel);

        SignupPanel sigupPanel = new SignupPanel(userRepository, mainFrame);
        switchedPanel.addPanel(Constants.SIGUP_PANEL_KEY, sigupPanel);

        ContentCompositePanel contentPanel = new ContentCompositePanel();
		contentPanel
			.setNavigationPanel(new NavigationPanel(database, mainFrame, contentPanel))
			.setSidebarPanel(new SideBarFilterPanel(this.modelOfCarRepository, this.markOfCarRepository, this.carBodyTypeRepository, this.carRepository, database, contentPanel))
			.setContentPanel(new ContentPanel(contentPanel, database,""));
        switchedPanel.addPanel(Constants.CONTENT_PANEL_KEY, contentPanel);

		switchedPanel.showPanel(Constants.LOGIN_PANEL_KEY);
        this.mainFrame.setVisible(true);
    }
}
