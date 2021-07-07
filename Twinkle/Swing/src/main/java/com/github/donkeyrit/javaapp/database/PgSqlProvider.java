package com.github.donkeyrit.javaapp.database;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PgSqlProvider extends DatabaseProvider {

    private static final String DB_CONNECTION = "jdbc:postgresql://localhost/carrental";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "Welcome01_";

    @Override
    protected void initialize() {
        try {
            Properties props = new Properties();
            props.setProperty("user",DB_USER);
            props.setProperty("password",DB_PASSWORD);
            this.connection = DriverManager.getConnection(DB_CONNECTION, props);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
