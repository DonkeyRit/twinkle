package com.github.donkeyrit.twinkle.dal.models;

import com.github.donkeyrit.twinkle.dal.common.models.Identifiable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "car")
public class Car implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "model_year")
    private LocalDate modelYear;

    @Column(name = "info")
    private String info;

    @Column(name = "image")
    private int imageId;

    @Column(name = "cost")
    private double cost;

    @OneToOne
    @JoinColumn(name = "id_model", unique = true)
    private ModelOfCar modelOfCar;

    // See MarkOfCar.countryId for why this shadow FK is read-only in Hibernate.
    @Column(name = "id_model", insertable = false, updatable = false)
	private int modelOfCarId;

    // Constructors
    public Car() {
    }

    public Car(int id, LocalDate modelYear, String info, int imageId, double cost, ModelOfCar modelOfCar) {
        this.id = id;
        this.modelYear = modelYear;
        this.info = info;
        this.imageId = imageId;
        this.cost = cost;
        this.modelOfCar = modelOfCar;
    }

	public Car(int id, LocalDate modelYear, String info, int imageId, double cost, int modelOfCarId) {
        this.id = id;
        this.modelYear = modelYear;
        this.info = info;
        this.imageId = imageId;
        this.cost = cost;
        this.modelOfCarId = modelOfCarId;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getModelYear() {
        return modelYear;
    }

    public void setModelYear(LocalDate modelYear) {
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

    public int getModelOfCarId() {
        return modelOfCarId;
    }

    public void setModelOfCarId(int modelOfCarId) {
        this.modelOfCarId = modelOfCarId;
    }
}
