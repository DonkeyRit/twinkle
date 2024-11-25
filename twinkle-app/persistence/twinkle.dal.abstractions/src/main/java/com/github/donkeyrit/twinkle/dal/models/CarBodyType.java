package com.github.donkeyrit.twinkle.dal.models;

import com.github.donkeyrit.twinkle.dal.common.models.Identifiable;

public class CarBodyType implements Identifiable {

    private int id;
    private String type;

    // Constructors
    public CarBodyType() {
    }

    public CarBodyType(int id, String type) {
        this.id = id;
        this.type = type;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
