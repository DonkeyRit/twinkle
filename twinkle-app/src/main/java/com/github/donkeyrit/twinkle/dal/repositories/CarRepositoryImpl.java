package com.github.donkeyrit.twinkle.dal.repositories;

import com.github.donkeyrit.twinkle.dal.repositories.interfaces.CarRepository;
import com.github.donkeyrit.twinkle.dal.contracts.BaseCrudRepository;
import com.github.donkeyrit.twinkle.dal.contracts.QueryFilter;
import com.github.donkeyrit.twinkle.dal.models.Car;

import jakarta.persistence.EntityManager;
import java.util.List;

public class CarRepositoryImpl extends BaseCrudRepository<Car> implements CarRepository
{

	public CarRepositoryImpl(EntityManager session) 
	{
		super(session);
	}

	@Override
	public List<Car> getList(QueryFilter queryFilter) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getList'");
	}
}
