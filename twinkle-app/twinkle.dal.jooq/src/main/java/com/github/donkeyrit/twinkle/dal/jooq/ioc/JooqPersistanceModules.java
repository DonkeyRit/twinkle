package com.github.donkeyrit.twinkle.dal.jooq.ioc;

import com.github.donkeyrit.twinkle.dal.jooq.connections.DataSourceProvider;
import com.github.donkeyrit.twinkle.dal.jooq.repositories.JooqUserRepository;
import com.github.donkeyrit.twinkle.dal.interfaces.UserRepository;

import com.google.inject.AbstractModule;
import javax.sql.DataSource;

public class JooqPersistanceModules extends AbstractModule {

	@Override
	protected void configure() {
		bind(DataSource.class).toProvider(DataSourceProvider.class).asEagerSingleton();
		bind(UserRepository.class).to(JooqUserRepository.class);
		// bind(ModelOfCarRepository.class).to(ModelOfCarRepositoryImpl.class);
		// bind(MarkOfCarRepository.class).to(MarkOfCarRepositoryImpl.class);
		// bind(CarBodyTypeRepository.class).to(CarBodyTypeRepositoryImpl.class);
		// bind(CarRepository.class).to(CarRepositoryImpl.class);
		// bind(RentRepository.class).to(RentRepositoryImpl.class);
		// bind(ClientRepository.class).to(ClientRepositoryImpl.class);
	}
}
