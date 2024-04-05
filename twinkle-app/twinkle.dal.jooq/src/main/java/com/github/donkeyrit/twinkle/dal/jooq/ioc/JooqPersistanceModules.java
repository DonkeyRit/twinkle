package com.github.donkeyrit.twinkle.dal.jooq.ioc;

import com.github.donkeyrit.twinkle.dal.jooq.specifications.factory.JooqSpecificationFactory;
import com.github.donkeyrit.twinkle.dal.jooq.connections.DataSourceProvider;
import com.github.donkeyrit.twinkle.dal.jooq.repositories.*;
import com.github.donkeyrit.twinkle.dal.specifications.factory.SpecificationFactory;
import com.github.donkeyrit.twinkle.dal.interfaces.*;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;

import javax.sql.DataSource;
import org.jooq.Condition;

public class JooqPersistanceModules extends AbstractModule {

	@Override
	protected void configure() {

		bind(new TypeLiteral<SpecificationFactory<Condition>>() {}).to(JooqSpecificationFactory.class);

		bind(DataSource.class).toProvider(DataSourceProvider.class).asEagerSingleton();
		bind(UserRepository.class).to(JooqUserRepository.class);
		bind(ModelOfCarRepository.class).to(JooqModelOfCarRepository.class);
		bind(MarkOfCarRepository.class).to(JooqMarkOfCarRepository.class);
		bind(CarBodyTypeRepository.class).to(JooqCarBodyTypeRepository.class);
		bind(CarRepository.class).to(JooqCarRepository.class);
		bind(RentRepository.class).to(JooqRentRepository.class);
		bind(ClientRepository.class).to(JooqClientRepository.class);
	}
}
