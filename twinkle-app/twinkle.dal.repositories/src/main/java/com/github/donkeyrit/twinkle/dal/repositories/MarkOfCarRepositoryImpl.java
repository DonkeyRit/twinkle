package com.github.donkeyrit.twinkle.dal.repositories;

import com.github.donkeyrit.twinkle.dal.repositories.interfaces.MarkOfCarRepository;
import com.google.inject.Inject;
import com.github.donkeyrit.twinkle.dal.models.MarkOfCar;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.stream.Stream;

public class MarkOfCarRepositoryImpl implements MarkOfCarRepository 
{
	private EntityManager session;
    
	@Inject
    public MarkOfCarRepositoryImpl(EntityManager session) 
	{
        this.session = session;
    }

	@Override
	public Stream<MarkOfCar> getList() 
	{
		TypedQuery<MarkOfCar> query = session.createQuery("SELECT u FROM MarkOfCar u", MarkOfCar.class);
        return query.getResultStream();
	}
}
