package com.github.donkeyrit.twinkle.dal.models;

import com.github.donkeyrit.twinkle.dal.common.models.Identifiable;

public class Country implements Identifiable {

    private int id;
    private String countryName;

    // Constructors
    public Country() {
    }

    public Country(int id, String countryName) {
        this.id = id;
        this.countryName = countryName;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
