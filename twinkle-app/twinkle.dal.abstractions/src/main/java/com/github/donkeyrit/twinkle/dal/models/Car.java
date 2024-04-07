package com.github.donkeyrit.twinkle.dal.models;

import java.sql.Date;

import com.github.donkeyrit.twinkle.dal.common.models.Identifiable;

public class Car implements Identifiable {

    private int id;
    private Date modelYear;
    private String info;
    private int imageId;
    private double cost;
    private ModelOfCar modelOfCar;

    // Constructors
    public Car() {
    }

    public Car(int id, Date modelYear, String info, int imageId, double cost, ModelOfCar modelOfCar) {
        this.id = id;
        this.modelYear = modelYear;
        this.info = info;
        this.imageId = imageId;
        this.cost = cost;
        this.modelOfCar = modelOfCar;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getModelYear() {
        return modelYear;
    }

    public void setModelYear(Date modelYear) {
        this.modelYear = modelYear;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public ModelOfCar getModelOfCar() {
        return modelOfCar;
    }

    public void setModelOfCar(ModelOfCar modelOfCar) {
        this.modelOfCar = modelOfCar;
    }
}
