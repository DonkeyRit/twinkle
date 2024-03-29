package com.github.donkeyrit.twinkle.dal.models;

import com.github.donkeyrit.twinkle.dal.common.models.Identifiable;

public class ModelOfCar implements Identifiable {

    private Long id;
    private String modelName;
    private MarkOfCar mark;
    private CarBodyType bodyType;

    // Constructors
    public ModelOfCar() {
    }

    public ModelOfCar(Long id, String modelName, MarkOfCar mark, CarBodyType bodyType) {
        this.id = id;
        this.modelName = modelName;
        this.mark = mark;
        this.bodyType = bodyType;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public MarkOfCar getMark() {
        return mark;
    }

    public void setMark(MarkOfCar mark) {
        this.mark = mark;
    }

    public CarBodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(CarBodyType bodyType) {
        this.bodyType = bodyType;
    }
}
