package com.github.donkeyrit.twinkle.dal.models;

import com.github.donkeyrit.twinkle.dal.common.models.Identifiable;

public class ModelOfCar implements Identifiable {

    private int id;
    private String modelName;
    private MarkOfCar mark;
	private int markId;
	private CarBodyType bodyType;
	private int bodyTypeId;

	//#region Constructors
    public ModelOfCar() {
    }

    public ModelOfCar(int id, String modelName, MarkOfCar mark, CarBodyType bodyType) {
        this.id = id;
        this.modelName = modelName;
        this.mark = mark;
        this.bodyType = bodyType;
    }

	public ModelOfCar(int id, String modelName, int markId, int bodyTypeId) {
		this.id = id;
		this.modelName = modelName;
		this.markId = markId;
		this.bodyTypeId = bodyTypeId;
	}

	//#endregion

	//#region Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
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

	public int getMarkId() {
		return markId;
	}

	public void setMarkId(int markId) {
		this.markId = markId;
	}

    public CarBodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(CarBodyType bodyType) {
        this.bodyType = bodyType;
    }

	public int getBodyTypeId() {
		return bodyTypeId;
	}

	public void setBodyTypeId(int bodyTypeId) {
		this.bodyTypeId = bodyTypeId;
	}
	//#endregion
}
