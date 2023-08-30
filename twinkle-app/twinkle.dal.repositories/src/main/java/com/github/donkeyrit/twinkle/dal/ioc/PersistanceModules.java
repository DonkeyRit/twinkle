package com.github.donkeyrit.twinkle.dal.ioc;

import com.github.donkeyrit.twinkle.dal.repositories.UserRepositoryImpl;
import com.github.donkeyrit.twinkle.dal.repositories.interfaces.UserRepository;
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
