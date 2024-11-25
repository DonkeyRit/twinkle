package com.github.donkeyrit.twinkle.dal.jooq.ioc;

import com.github.donkeyrit.twinkle.dal.jooq.connections.DataSourceProvider;
import com.github.donkeyrit.twinkle.dal.jooq.listeners.CustomLoggerListener;
import com.github.donkeyrit.twinkle.dal.jooq.repositories.*;
import com.github.donkeyrit.twinkle.dal.interfaces.*;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

import javax.sql.DataSource;

import org.jooq.impl.DefaultExecuteListenerProvider;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DSL;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;

public class JooqPersistanceModules extends AbstractModule {

	@Override
	protected void configure() {
		bind(DataSource.class).toProvider(DataSourceProvider.class).asEagerSingleton();
		bind(UserRepository.class).to(JooqUserRepository.class);
		bind(ModelOfCarRepository.class).to(JooqModelOfCarRepository.class);
		bind(MarkOfCarRepository.class).to(JooqMarkOfCarRepository.class);
		bind(CarBodyTypeRepository.class).to(JooqCarBodyTypeRepository.class);
		bind(CarRepository.class).to(JooqCarRepository.class);
		bind(RentRepository.class).to(JooqRentRepository.class);
		bind(ClientRepository.class).to(JooqClientRepository.class);
	}

	@Provides
    @Singleton
    public DSLContext provideDSLContext(DataSource dataSource) {
        DefaultConfiguration configuration = new DefaultConfiguration();
        configuration.set(dataSource);
        configuration.set(SQLDialect.POSTGRES);
        configuration.set(new DefaultExecuteListenerProvider(new CustomLoggerListener()));

        return DSL.using(configuration);
    }
}
