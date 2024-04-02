package com.github.donkeyrit.twinkle.ioc;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import java.util.Properties;
import java.io.IOException;

public class MainModules extends AbstractModule {
	@Override
	protected void configure() {
		Properties properties = new Properties();
        try {
            properties.load(MainModules.class.getResourceAsStream("/application.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load application.properties", e);
        }
		Names.bindProperties(binder(), properties);
	}
}
