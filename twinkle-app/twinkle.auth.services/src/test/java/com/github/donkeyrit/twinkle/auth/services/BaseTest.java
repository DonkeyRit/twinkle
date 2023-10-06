package com.github.donkeyrit.twinkle.auth.services;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    protected static SessionFactory sessionFactory;

    @BeforeSuite
    public static void setUpBeforeSuite() {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterSuite
    public static void tearDownAfterSuite() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}