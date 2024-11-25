package com.github.donkeyrit.twinkle.dal.jooq.connections;

import com.google.inject.name.Named;
import com.google.inject.Provider;
import com.google.inject.Inject;

import javax.sql.DataSource;

public class DataSourceProvider implements Provider<DataSource> {

    private final String url;
    private final String user;
    private final String password;

    @Inject
    public DataSourceProvider(
            @Named("database.url") String url,
            @Named("database.user") String user,
            @Named("database.password") String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public DataSource get() {
        return new SimpleDataSource(url, user, password);
    }
}
