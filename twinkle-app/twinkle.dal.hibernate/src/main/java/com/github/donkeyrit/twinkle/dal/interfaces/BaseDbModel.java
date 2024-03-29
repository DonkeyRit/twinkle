package com.github.donkeyrit.twinkle.dal.interfaces;

import java.io.Serializable;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public abstract class BaseDbModel implements Serializable
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
