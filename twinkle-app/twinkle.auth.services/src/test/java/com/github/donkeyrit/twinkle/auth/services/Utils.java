package com.github.donkeyrit.twinkle.auth.services;

import com.github.donkeyrit.twinkle.dal.models.User;
import com.github.javafaker.Faker;

import java.util.Random;

public class Utils {
    public static User generateTestUser(){
        Faker faker = new Faker();
        return new User("test-" + faker.artist().name() + new Random().nextInt(100), faker.pokemon().name(), false);
    }
}
