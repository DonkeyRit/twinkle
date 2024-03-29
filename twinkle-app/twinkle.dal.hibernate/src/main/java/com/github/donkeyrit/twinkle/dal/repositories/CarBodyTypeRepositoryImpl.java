package com.github.donkeyrit.twinkle.dal.repositories;

import com.github.donkeyrit.twinkle.dal.repositories.interfaces.CarBodyTypeRepository;
import com.google.inject.Inject;
import com.github.donkeyrit.twinkle.dal.models.CarBodyType;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.stream.Stream;


public class CarBodyTypeRepositoryImpl implements CarBodyTypeRepository
{
	private EntityManager session;

	@Inject
	public CarBodyTypeRepositoryImpl(EntityManager session) 
	{
		this.session = session;
	}

	@Override
	public Stream<CarBodyType> getList() 
	{
		TypedQuery<CarBodyType> query = session.createQuery("SELECT u FROM CarBodyType u", CarBodyType.class);
        return query.getResultStream();
	}
}
