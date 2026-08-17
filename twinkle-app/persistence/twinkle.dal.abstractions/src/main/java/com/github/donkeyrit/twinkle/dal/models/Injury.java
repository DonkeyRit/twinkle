package com.github.donkeyrit.twinkle.dal.models;

import com.github.donkeyrit.twinkle.dal.common.models.Identifiable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "injury")
public class Injury implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "injury_name")
    private String injuryName;

    // Constructors
    public Injury() {
    }

    public Injury(int id, String injuryName) {
        this.id = id;
        this.injuryName = injuryName;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInjuryName() {
        return injuryName;
    }

    public void setInjuryName(String injuryName) {
        this.injuryName = injuryName;
    }
}
