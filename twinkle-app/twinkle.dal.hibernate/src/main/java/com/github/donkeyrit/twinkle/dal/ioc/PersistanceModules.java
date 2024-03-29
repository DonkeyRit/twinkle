package com.github.donkeyrit.twinkle.dal.ioc;

import com.github.donkeyrit.twinkle.dal.repositories.interfaces.CarBodyTypeRepository;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.ModelOfCarRepository;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.MarkOfCarRepository;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.UserRepository;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.RentRepository;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.CarRepository;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.ClientRepository;
import com.github.donkeyrit.twinkle.dal.repositories.CarBodyTypeRepositoryImpl;
import com.github.donkeyrit.twinkle.dal.repositories.MarkOfCarRepositoryImpl;
import com.github.donkeyrit.twinkle.dal.repositories.ModelOfCarRepositoryImpl;
import com.github.donkeyrit.twinkle.dal.repositories.CarRepositoryImpl;
import com.github.donkeyrit.twinkle.dal.repositories.ClientRepositoryImpl;
import com.github.donkeyrit.twinkle.dal.repositories.RentRepositoryImpl;
import com.github.donkeyrit.twinkle.dal.repositories.UserRepositoryImpl;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityManager;
import org.hibernate.cfg.Configuration;

public class PersistanceModules extends AbstractModule {

	private static final ThreadLocal<EntityManager> ENTITY_MANAGER_CACHE = new ThreadLocal<EntityManager>();

	@Override
	protected void configure() {
		bind(UserRepository.class).to(UserRepositoryImpl.class);
		bind(ModelOfCarRepository.class).to(ModelOfCarRepositoryImpl.class);
		bind(MarkOfCarRepository.class).to(MarkOfCarRepositoryImpl.class);
		bind(CarBodyTypeRepository.class).to(CarBodyTypeRepositoryImpl.class);
		bind(CarRepository.class).to(CarRepositoryImpl.class);
		bind(RentRepository.class).to(RentRepositoryImpl.class);
		bind(ClientRepository.class).to(ClientRepositoryImpl.class);
	}

	@Provides
	@Singleton
	public EntityManagerFactory provideEntityManagerFactory() {
		return new Configuration()
            .configure()
            .buildSessionFactory();
	}

	@Provides
	public EntityManager provideEntityManager(EntityManagerFactory entityManagerFactory) {
		EntityManager entityManager = ENTITY_MANAGER_CACHE.get();
		if (entityManager == null) {
			ENTITY_MANAGER_CACHE.set(entityManager = entityManagerFactory.createEntityManager());
		}
		return entityManager;
	}
}
