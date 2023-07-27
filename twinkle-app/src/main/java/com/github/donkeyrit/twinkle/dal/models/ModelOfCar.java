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

	@Column(name = "id_body_type")
	private int bodyTypeId;

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

	public int getBodyTypeId() 
	{
		return bodyTypeId;
	}

	public void setBodyTypeId(int bodyTypeId)
	{
		this.bodyTypeId = bodyTypeId;
	}

	//#endregion
}
