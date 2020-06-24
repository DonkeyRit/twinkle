package com.github.donkeyrit.javaapp.database;

/**
 * @author Dima
 */

import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlProvider extends DatabaseProvider {

    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/carrental?useUnicode=true&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    @Override
    protected void initialize() {
        try {
            Class.forName(DB_DRIVER);
            this.connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
