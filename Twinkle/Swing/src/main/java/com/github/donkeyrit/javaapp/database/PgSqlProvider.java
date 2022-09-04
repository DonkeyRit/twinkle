package com.github.donkeyrit.javaapp.database;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PgSqlProvider extends DatabaseProvider {

    private static final String DB_CONNECTION = "jdbc:postgresql://localhost:5432/carrental";
    private static final String DB_USER = "twinkle_user";
    private static final String DB_PASSWORD = "Sr412Tqew!";

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
