package com.github.donkeyrit.twinkle.dal.models;

import com.github.donkeyrit.twinkle.dal.common.models.Identifiable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "resulting_injury")
public class ResultingInjury implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "id_rent")
    private int idRent;

    @Column(name = "id_injury")
    private int idInjury;

    // Constructors
    public ResultingInjury() {
    }

    public ResultingInjury(int id, int idRent, int idInjury) {
        this.id = id;
        this.idRent = idRent;
        this.idInjury = idInjury;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdRent() {
        return idRent;
    }

    public void setIdRent(int idRent) {
        this.idRent = idRent;
    }

    public int getIdInjury() {
        return idInjury;
    }

    public void setIdInjury(int idInjury) {
        this.idInjury = idInjury;
    }
}
