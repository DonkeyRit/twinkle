package com.github.donkeyrit.twinkle.dal.models;

import com.github.donkeyrit.twinkle.dal.contracts.BaseDbModel;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "car")
public class Car extends BaseDbModel
{
	private Object ModelYear;
	private String info;
	private String imageId;
	private double cost;
	private ModelOfCar modelOfCar;
}
