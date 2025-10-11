package com.github.donkeyrit.twinkle.utils;

import com.github.donkeyrit.twinkle.dal.jooq.connections.SimpleDataSource;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Builds a DSLContext against the integration-test database (see database-test.properties and
 * deploy/local/docker-compose.yml, service postgres_local_test) for jOOQ repository tests, the
 * jOOQ counterpart of dal.hibernate's test/resources/hibernate.cfg.xml.
 */
public class JooqTestSupport {

	private static final String CONFIG_FILE = "database-test.properties";

	public static DSLContext createContext() {
		Properties properties = loadProperties();

		SimpleDataSource dataSource = new SimpleDataSource(
			properties.getProperty("database.url"),
			properties.getProperty("database.user"),
			properties.getProperty("database.password"));

		return DSL.using(dataSource, SQLDialect.POSTGRES);
	}

	private static Properties loadProperties() {
		Properties properties = new Properties();
		try (InputStream input = JooqTestSupport.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
			if (input == null) {
				throw new IllegalStateException("Missing " + CONFIG_FILE + " on the test classpath");
			}
			properties.load(input);
		} catch (IOException ex) {
			throw new IllegalStateException("Failed to load " + CONFIG_FILE, ex);
		}
		return properties;
	}
}
