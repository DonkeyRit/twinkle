package com.github.donkeyrit.twinkle.dal.models;

import com.github.donkeyrit.twinkle.dal.common.models.Identifiable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "rent")
public class Rent implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "id_client")
    private int idClient;

    @Column(name = "id_car")
    private int idCar;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "plan_date")
    private LocalDate planDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    // Constructors
    public Rent() {
    }

    public Rent(int id, int idClient, int idCar, LocalDate startDate, LocalDate planDate, LocalDate endDate) {
        this.id = id;
        this.idClient = idClient;
        this.idCar = idCar;
        this.startDate = startDate;
        this.planDate = planDate;
        this.endDate = endDate;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdCar() {
        return idCar;
    }

    public void setIdCar(int idCar) {
        this.idCar = idCar;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getPlanDate() {
        return planDate;
    }

    public void setPlanDate(LocalDate planDate) {
        this.planDate = planDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
