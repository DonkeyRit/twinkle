package com.github.donkeyrit.javaapp.database;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DerbyProvider extends DatabaseProvider {

    private static final String DRIVER_NAME = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String CONNECTION = "jdbc:derby:twinkleDB;create=true";

    public void initialize(){
        try{
            Class.forName(DRIVER_NAME);
            this.connection = DriverManager.getConnection(CONNECTION);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
