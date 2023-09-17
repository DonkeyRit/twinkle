package com.github.donkeyrit.twinkle.dal.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "model")
public class ModelOfCar 
{
	//#region Fields

	@Id
	@Column(name = "id_model")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;

	@Column(name = "model_name")
	private String modelName;

	@OneToOne()
	@JoinColumn(unique = true, name = "id_mark")
	private MarkOfCar mark;

	@OneToOne()
	@JoinColumn(unique = true, name = "id_body_type")
	private CarBodyType bodyType;

	//#endregion

	//#region Getters/Setters
	
	public int getId() 
	{
		return Id;
	}

	public void setId(int id) 
	{
		Id = id;
	}

	public String getModelName() 
	{
		return modelName;
	}

	public void setModelName(String modelName) 
	{
		this.modelName = modelName;
	}

	public MarkOfCar getMark() 
	{
		return mark;
	}

	public void setMark(MarkOfCar mark) 
	{
		this.mark = mark;
	}

	public CarBodyType getBodyType() 
	{
		return bodyType;
	}

	public void setBodyTypeId(CarBodyType bodyType)
	{
		this.bodyType = bodyType;
	}

	//#endregion

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Id;
		result = prime * result + ((modelName == null) ? 0 : modelName.hashCode());
		result = prime * result + ((mark == null) ? 0 : mark.hashCode());
		result = prime * result + ((bodyType == null) ? 0 : bodyType.hashCode());
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
		ModelOfCar other = (ModelOfCar) obj;
		if (Id != other.Id)
			return false;
		if (modelName == null) {
			if (other.modelName != null)
				return false;
		} else if (!modelName.equals(other.modelName))
			return false;
		if (mark == null) {
			if (other.mark != null)
				return false;
		} else if (!mark.equals(other.mark))
			return false;
		if (bodyType == null) {
			if (other.bodyType != null)
				return false;
		} else if (!bodyType.equals(other.bodyType))
			return false;
		return true;
	}
}
