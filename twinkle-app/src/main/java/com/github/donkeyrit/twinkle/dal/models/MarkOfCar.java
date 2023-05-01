package com.github.donkeyrit.twinkle.dal.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.Id;

@Entity
@Table(name = "mark")
public class MarkOfCar 
{
	@Id
	@Column(name = "id_mark")
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

	@Column(name = "mark_name")
	private String name;

	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	@Column(name = "id_country")
	private int countryId;

	public int getCountryId() 
	{
		return countryId;
	}

	public void setCountryId(int countryId) 
	{
		this.countryId = countryId;
	}

}
