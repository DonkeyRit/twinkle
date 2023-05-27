package com.github.donkeyrit.twinkle.dal.contracts;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public abstract class BaseDbModel 
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
}
