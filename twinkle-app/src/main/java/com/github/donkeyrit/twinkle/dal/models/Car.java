package com.github.donkeyrit.twinkle.dal.models;

import com.github.donkeyrit.twinkle.dal.contracts.BaseDbModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "car")
public class Car extends BaseDbModel
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

    public int getId() 
	{
        return id;
    }

    public void setId(int id) 
	{
        this.id = id;
    }

	@Column(name = "model_year")
	private Timestamp ModelYear;

	public Timestamp getModelYear() 
	{
		return ModelYear;
	}

	public void setModelYear(Timestamp modelYear) 
	{
		ModelYear = modelYear;
	}

	private String info;

	public String getInfo() 
	{
		return info;
	}

	public void setInfo(String info) 
	{
		this.info = info;
	}

	// @Column(name = "image")
	// private int imageId;

	// public int getImageId() 
	// {
	// 	return imageId;
	// }

	// public void setImageId(int imageId) 
	// {
	// 	this.imageId = imageId;
	// }

	private double cost;

	public double getCost() 
	{
		return cost;
	}

	public void setCost(double cost) 
	{
		this.cost = cost;
	}

	// @OneToOne(
	// 	fetch = FetchType.LAZY,
	// 	optional = false
	// )
	// @PrimaryKeyJoinColumn
	// private ModelOfCar modelOfCar;

	// public ModelOfCar getModelOfCar() 
	// {
	// 	return modelOfCar;
	// }

	// public void setModelOfCar(ModelOfCar modelOfCar) 
	// {
	// 	this.modelOfCar = modelOfCar;
	// }
}
