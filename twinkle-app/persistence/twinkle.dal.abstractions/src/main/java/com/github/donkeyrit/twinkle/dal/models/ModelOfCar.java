package com.github.donkeyrit.twinkle.dal.models;

import com.github.donkeyrit.twinkle.dal.common.models.Identifiable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "model")
public class ModelOfCar implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "model_name")
    private String modelName;

    @ManyToOne
    @JoinColumn(name = "id_mark")
    private MarkOfCar mark;

    // See MarkOfCar.countryId for why these shadow FKs are read-only in Hibernate.
    @Column(name = "id_mark", insertable = false, updatable = false)
	private int markId;

    @ManyToOne
    @JoinColumn(name = "id_body_type")
	private CarBodyType bodyType;

    @Column(name = "id_body_type", insertable = false, updatable = false)
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
