package com.github.donkeyrit.twinkle.dal.models;

import com.github.donkeyrit.twinkle.dal.common.models.Identifiable;

public class Country implements Identifiable {

    private Long id;
    private String countryName;

    // Constructors
    public Country() {
    }

    public Country(Long id, String countryName) {
        this.id = id;
        this.countryName = countryName;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
