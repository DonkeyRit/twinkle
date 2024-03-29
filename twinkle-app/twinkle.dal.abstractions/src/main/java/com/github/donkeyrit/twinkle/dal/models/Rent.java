package com.github.donkeyrit.twinkle.dal.models;

import java.sql.Date;

import com.github.donkeyrit.twinkle.dal.common.models.Identifiable;

public class Rent implements Identifiable {

    private Long id;
    private int idClient;
    private int idCar;
    private Date startDate;
    private Date planDate;
    private Date endDate;

    // Constructors
    public Rent() {
    }

    public Rent(Long id, int idClient, int idCar, Date startDate, Date planDate, Date endDate) {
        this.id = id;
        this.idClient = idClient;
        this.idCar = idCar;
        this.startDate = startDate;
        this.planDate = planDate;
        this.endDate = endDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
