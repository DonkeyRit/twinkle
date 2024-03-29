package com.github.donkeyrit.twinkle.dal.models;

import com.github.donkeyrit.twinkle.dal.common.models.Identifiable;

public class MarkOfCar implements Identifiable {

    private Long id;
    private String name;
    private Country country;

    // Constructors
    public MarkOfCar() {
    }

    public MarkOfCar(Long id, String name, Country country) {
        this.id = id;
        this.name = name;
        this.country = country;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
