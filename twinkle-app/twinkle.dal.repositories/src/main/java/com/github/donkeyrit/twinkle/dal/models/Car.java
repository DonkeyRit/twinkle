package com.github.donkeyrit.twinkle.dal.models;

import com.github.donkeyrit.twinkle.dal.contracts.BaseDbModel;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

import java.sql.Date;

@Entity
@Table(name = "car")
public class Car extends BaseDbModel {
	
	// #region Fields

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "model_year")
	private Date ModelYear;

	private String info;

	@Column(name = "image")
	private int imageId;

	private double cost;

	@OneToOne()
	@JoinColumn(unique = true, name = "id_model")
	private ModelOfCar modelOfCar;

	// #endregion
	// #region Getters/Setters

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getModelYear() {
		return ModelYear;
	}

	public void setModelYear(Date modelYear) {
		ModelYear = modelYear;
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

	// #endregion

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((ModelYear == null) ? 0 : ModelYear.hashCode());
		result = prime * result + ((info == null) ? 0 : info.hashCode());
		result = prime * result + imageId;
		long temp;
		temp = Double.doubleToLongBits(cost);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((modelOfCar == null) ? 0 : modelOfCar.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Car other = (Car) obj;
		if (id != other.id)
			return false;
		//TODO: Compare model year
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		if (imageId != other.imageId)
			return false;
		if (Double.doubleToLongBits(cost) != Double.doubleToLongBits(other.cost))
			return false;
		if (modelOfCar == null) {
			if (other.modelOfCar != null)
				return false;
		} else if (!modelOfCar.equals(other.modelOfCar))
			return false;
		return true;
	}
}
