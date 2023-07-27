package com.github.donkeyrit.twinkle.dal.models;

import com.github.donkeyrit.twinkle.dal.contracts.BaseDbModel;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import java.sql.Timestamp;

@Entity
@Table(name = "car")
public class Car extends BaseDbModel
{
	//#region Fields

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "model_year")
	private Timestamp ModelYear;

	private String info;

	@Column(name = "image")
	private int imageId;

	private double cost;

	@OneToOne()
	@JoinColumn(unique = true, name = "id_model")
	private ModelOfCar modelOfCar;

	//#endregion

	//#region Getters/Setters

	public int getId() 
	{
        return id;
    }

    public void setId(int id) 
	{
        this.id = id;
    }

	public Timestamp getModelYear() 
	{
		return ModelYear;
	}

	public void setModelYear(Timestamp modelYear) 
	{
		ModelYear = modelYear;
	}
	
	public String getInfo() 
	{
		return info;
	}

	public void setInfo(String info) 
	{
		this.info = info;
	}

	public int getImageId() 
	{
		return imageId;
	}

	public void setImageId(int imageId) 
	{
		this.imageId = imageId;
	}

	public double getCost() 
	{
		return cost;
	}

	public void setCost(double cost) 
	{
		this.cost = cost;
	}

	public ModelOfCar getModelOfCar() 
	{
		return modelOfCar;
	}

	public void setModelOfCar(ModelOfCar modelOfCar) 
	{
		this.modelOfCar = modelOfCar;
	}

	//#endregion
}
