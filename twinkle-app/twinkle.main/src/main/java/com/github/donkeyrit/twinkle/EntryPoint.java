package com.github.donkeyrit.twinkle;

import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import com.github.donkeyrit.twinkle.bll.ioc.ServicesModules;
import com.github.donkeyrit.twinkle.dal.ioc.PersistanceModules;
import com.github.donkeyrit.twinkle.dal.repositories.CarBodyTypeRepositoryImpl;
import com.github.donkeyrit.twinkle.dal.repositories.CarRepositoryImpl;
import com.github.donkeyrit.twinkle.dal.repositories.MarkOfCarRepositoryImpl;
import com.github.donkeyrit.twinkle.dal.repositories.ModelOfCarRepositoryImpl;
import com.github.donkeyrit.twinkle.dal.repositories.RentRepositoryImpl;
import com.github.donkeyrit.twinkle.dal.repositories.UserRepositoryImpl;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.CarBodyTypeRepository;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.CarRepository;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.MarkOfCarRepository;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.ModelOfCarRepository;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.RentRepository;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.UserRepository;
import com.github.donkeyrit.twinkle.frame.MainFrame;
import com.github.donkeyrit.twinkle.panels.common.SwitchedPanel;
import com.github.donkeyrit.twinkle.panels.content.ContentCompositePanel;
import com.github.donkeyrit.twinkle.panels.content.ContentPanel;
import com.github.donkeyrit.twinkle.panels.ioc.SwingUiModules;
import com.github.donkeyrit.twinkle.panels.login.LoginPanel;
import com.github.donkeyrit.twinkle.panels.navigation.NavigationPanel;
import com.github.donkeyrit.twinkle.panels.sidebar.SideBarFilterPanel;
import com.github.donkeyrit.twinkle.panels.signup.SignupPanel;
import com.github.donkeyrit.twinkle.security.HashManager;
import com.github.donkeyrit.twinkle.utils.Constants;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class EntryPoint 
{
    
    // Repositories
    private final UserRepository userRepository; 
	private final MarkOfCarRepository markOfCarRepository;
	private final CarBodyTypeRepository carBodyTypeRepository;
	private final ModelOfCarRepository modelOfCarRepository;
	private final CarRepository carRepository;
	private final RentRepository rentRepository;

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
		this.rentRepository = new RentRepositoryImpl(session);
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

        ContentCompositePanel contentPanel = new ContentCompositePanel();
		contentPanel
			.setNavigationPanel(new NavigationPanel(database, mainFrame, contentPanel))
			.setSidebarPanel(new SideBarFilterPanel(this.modelOfCarRepository, this.markOfCarRepository, this.carBodyTypeRepository, this.carRepository, this.rentRepository, database, contentPanel))
			.setContentPanel(new ContentPanel(contentPanel, this.carRepository, this.rentRepository, database));
        switchedPanel.addPanel(Constants.CONTENT_PANEL_KEY, contentPanel);

		switchedPanel.showPanel(Constants.LOGIN_PANEL_KEY);
        mainFrame.setVisible(true);
    }
}
