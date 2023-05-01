package com.github.donkeyrit.twinkle.dal.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "model")
public class ModelOfCar 
{
	@Id
	@Column(name = "id_model")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;

	public int getId() 
	{
		return Id;
	}

	public void setId(int id) 
	{
		Id = id;
	}

	@Column(name = "model_name")
	private String modelName;

	public String getModelName() 
	{
		return modelName;
	}

	public void setModelName(String modelName) 
	{
		this.modelName = modelName;
	}

	@Column(name = "id_mark")
	private int markId;

	public int getMarkId() 
	{
		return markId;
	}

	public void setMarkId(int markId) 
	{
		this.markId = markId;
	}

	@Column(name = "id_body_type")
	private int bodyTypeId;

	public int getBodyTypeId() 
	{
		return bodyTypeId;
	}

	public void setBodyTypeId(int bodyTypeId)
	{
		this.bodyTypeId = bodyTypeId;
	}
}
