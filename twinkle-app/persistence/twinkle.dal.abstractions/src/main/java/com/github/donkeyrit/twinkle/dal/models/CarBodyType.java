package com.github.donkeyrit.twinkle.dal.models;

import com.github.donkeyrit.twinkle.dal.common.models.Identifiable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "body_type")
public class CarBodyType implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "body_type_name")
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
