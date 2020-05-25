package com.github.donkeyrit.javaapp.model;

import java.util.Date;

public class Car {

    private int imagesNum;
    private Date modelYear;
    private Double cost;
    private String modelName;
    private String markName;
    private String nameCountry;
    private String info;
    private String bodyTypeName;
    private String status;

    public Car(int imagesNum, Date modelYear, Double cost, String modelName, String markName, String nameCountry, String info, String bodyTypeName, String status) {
        this.imagesNum = imagesNum;
        this.modelYear = modelYear;
        this.cost = cost;
        this.modelName = modelName;
        this.markName = markName;
        this.nameCountry = nameCountry;
        this.info = info;
        this.bodyTypeName = bodyTypeName;
        this.status = status;
    }

    private Car(){ }

    public int getImagesNum() {
        return imagesNum;
    }
    public Date getModelYear() {
        return modelYear;
    }
    public Double getCost() {
        return cost;
    }
    public String getModelName() {
        return modelName;
    }
    public String getMarkName() {
        return markName;
    }
    public String getNameCountry() {
        return nameCountry;
    }
    public String getInfo() {
        return info;
    }
    public String getBodyTypeName() {
        return bodyTypeName;
    }
    public String getStatus() {
        return status;
    }

    public static class CarBuilder {

        private Car car;

        public CarBuilder() {
            this.car = new Car();
        }

        public CarBuilder setImagesNum(int imagesNum) {
            this.car.imagesNum = imagesNum;
            return this;
        }
        public CarBuilder setModelYear(Date modelYear) {
            this.car.modelYear = modelYear;
            return this;
        }
        public CarBuilder setCost(Double cost) {
            this.car.cost = cost;
            return this;
        }
        public CarBuilder setModelName(String modelName) {
            this.car.modelName = modelName;
            return this;
        }
        public CarBuilder setMarkName(String markName) {
            this.car.markName = markName;
            return this;
        }
        public CarBuilder setNameCountry(String nameCountry) {
            this.car.nameCountry = nameCountry;
            return this;
        }
        public CarBuilder setInfo(String info) {
            this.car.info = info;
            return this;
        }
        public CarBuilder setBodyTypeName(String bodyTypeName) {
            this.car.bodyTypeName = bodyTypeName;
            return this;
        }
        public CarBuilder setStatus(String status) {
           this.car.status = status;
            return this;
        }
        public Car create(){
            return this.car;
        }
    }
}