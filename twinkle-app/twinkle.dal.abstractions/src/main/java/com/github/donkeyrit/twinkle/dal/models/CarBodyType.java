package com.github.donkeyrit.twinkle.dal.models;

import com.github.donkeyrit.twinkle.dal.common.models.Identifiable;

public class CarBodyType implements Identifiable {

    private Long id;
    private String type;

    // Constructors
    public CarBodyType() {
    }

    public CarBodyType(Long id, String type) {
        this.id = id;
        this.type = type;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
