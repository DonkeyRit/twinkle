package com.github.donkeyrit.model;

import javax.persistence.*;

@Entity
public class Vehicle {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "car_brand")
    private String carBrand;
    public String getCarBrand() {
        return carBrand;
    }
    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    @Column(name = "car_model")
    private String carModel;
    public String getCarModel() {
        return carModel;
    }
    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    private String country;
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "body_style")
    private String bodyStyle;
    public String getBodyStyle() {
        return bodyStyle;
    }
    public void setBodyStyle(String bodyStyle) {
        this.bodyStyle = bodyStyle;
    }

    private float distance;
    public float getDistance() {
        return distance;
    }
    public void setDistance(float distance) {
        this.distance = distance;
    }

    private float years;
    public float getYears() {
        return years;
    }
    public void setYears(float years) {
        this.years = years;
    }

    private float cost;
    public float getCost() {
        return cost;
    }
    public void setCost(float cost) {
        this.cost = cost;
    }

    @Lob
    @Column(name = "image", columnDefinition="BLOB")
    private byte[] image;
    public byte[] getImage() {
        return image;
    }
    public void setImage(byte[] image) {
        this.image = image;
    }
}
