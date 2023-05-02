package com.github.donkeyrit.twinkle.dal.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name = "body_type")
public class CarBodyType 
{
	@Id
	@Column(name = "id_body_type")
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

	@Column(name = "body_type_name")
	private String type;

	public String getType() 
	{
		return type;
	}

	public void setType(String type) 
	{
		this.type = type;
	}
}
